package com.sjzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjzx.entity.CombineCashFlow;
import com.sjzx.entity.CombineProfit;
import com.sjzx.entity.Company;
import com.sjzx.entity.ProfitStatistics;
import com.sjzx.mapper.ProfitStatisticsMapper;
import com.sjzx.model.EasyUIResult;
import com.sjzx.model.vo.input.LiabilitiesStatisticsInputVO;
import com.sjzx.model.vo.output.ProfitStatisticsVO;
import com.sjzx.service.CombineCashFlowService;
import com.sjzx.service.CombineProfitService;
import com.sjzx.service.CompanyService;
import com.sjzx.service.ProfitStatisticsService;
import com.sjzx.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

import static com.sjzx.utils.NumberUtils.*;

/**
 * <p>
 * 合并利润表统计 服务实现类
 * </p>
 *
 * @author
 * @since 2020-11-03
 */
@Service
public class ProfitStatisticsServiceImpl extends ServiceImpl<ProfitStatisticsMapper, ProfitStatistics> implements ProfitStatisticsService {

    @Autowired
    private CombineProfitService combineProfitService;

    @Autowired
    private CombineCashFlowService combineCashFlowService;

    @Autowired
    private CompanyService companyService;

    @Override
    public EasyUIResult<ProfitStatisticsVO> listPage(LiabilitiesStatisticsInputVO vo) {
        IPage<ProfitStatisticsVO> iPage = new Page<>(vo.getPageNo(), vo.getPageSize());
        baseMapper.listPage(iPage, vo);
        iPage.getRecords().forEach(e ->
                e.setBusinessIncomeGrowthRate(toPercent(e.getBusinessIncomeGrowthRate()))
                        .setGrossProfitMargin(toPercent(e.getGrossProfitMargin()))
                        .setCostRate(toPercent(e.getCostRate()))
                        .setCostInProfit(toPercent(e.getCostInProfit()))
                        .setProfitQuality(toPercent(e.getProfitQuality()))
                        .setProfitQualityOne(toPercent(e.getProfitQualityOne()))
                        .setMainProfitInProfitTotal(toPercent(e.getMainProfitInProfitTotal()))
                        .setMainProfitInIncomeTotal(toPercent(e.getMainProfitInIncomeTotal()))
                        .setBelongMotherNetProfitGrowthRate(toPercent(e.getBelongMotherNetProfitGrowthRate()))
        );
        return new EasyUIResult<>(iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public void statistics(Integer companyId, Integer year, Integer reportType) {
        Company company = companyService.getById(companyId);
        if (company == null) {
            return;
        }
        CombineProfit current = combineProfitService.getByIndex(companyId, year, reportType);
        if (current == null) {
            return;
        }
        ProfitStatistics statistics = BeanUtils.copyProperties(current, ProfitStatistics::new);

        CombineProfit previous = combineProfitService.getByIndex(companyId, year - 1, reportType);
        if (previous != null) {
            statistics.setBusinessIncomeGrowthRate(addRate(current.getBusinessIncome(), previous.getBusinessIncome()))
                    .setBusinessIncomeGrowthRate(addRate(current.getBusinessIncome(), previous.getBusinessIncome()))
                    .setBusinessIncomeGrowthRate(addRate(current.getBusinessIncome(), previous.getBusinessIncome()))
                    .setBelongMotherNetProfitGrowthRate(addRate(current.getBelongMotherNetProfit(), previous.getBelongMotherNetProfit()));
        } else {
            statistics.setBusinessIncomeGrowthRate(0).setBusinessIncomeGrowthRate(0)
                    .setBusinessIncomeGrowthRate(0).setBelongMotherNetProfitGrowthRate(0);
        }
        long threeCost = current.getSellingExpenses() + current.getManageExpenses() + (current.getFinancialExpenses() >= 0 ? current.getFinancialExpenses() : 0);
        //主营利润 营业收入 - 营业成本 - 税金及附加 - (销售费用 + 管理费用 + 财务费用）
        long mainProfit = current.getBusinessIncome() - current.getBusinessCosts() - current.getTaxRevenueTotal() - threeCost;
        //每股收益
        BigDecimal sharesProfit = new BigDecimal(current.getBelongMotherNetProfit())
                .divide(new BigDecimal(company.getTotalEquity()), 2, BigDecimal.ROUND_HALF_UP);
        statistics.setRemark(null).setMainProfit(mainProfit).setTotalEquity(company.getTotalEquity())
                .setGrossProfitMargin(addRate(current.getBusinessIncome(), current.getBusinessCosts()))
                .setCostRate(divide(threeCost, current.getBusinessIncome()))
                .setCostInProfit(divide(statistics.getCostRate(), statistics.getGrossProfitMargin()))
                .setMainProfitInProfitTotal(divide(mainProfit, current.getTotalProfit()))
                .setMainProfitInIncomeTotal(divide(mainProfit, current.getBusinessIncome()))
                .setBelongMotherNetProfit(current.getBelongMotherNetProfit())
                .setSharesProfit(sharesProfit);
        ;
        CombineCashFlow cashFlow = combineCashFlowService.getByIndex(companyId, year, reportType);
        if (cashFlow != null) {
            statistics.setBusinessToProfit(cashFlow.getBusinessToProfit())
                    .setProfitQuality(divide(cashFlow.getBusinessToProfit(), mainProfit))
                    .setProfitQualityOne(divide(cashFlow.getBusinessToProfit(), current.getNetProfit()));
        } else {
            statistics.setBusinessToProfit(0L).setProfitQuality(0).setProfitQualityOne(0);
        }
        ProfitStatistics old = getByIndex(companyId, year, reportType);
        if (old == null) {
            statistics.setCreateTime(new Date()).setUpdateTime(null).insert();
        } else {
            statistics.setId(old.getId()).setUpdateTime(new Date()).updateById();
        }
    }

    private ProfitStatistics getByIndex(Integer companyId, Integer year, Integer reportType) {
        if (companyId == null || year == null || reportType == null) {
            return null;
        }
        LambdaQueryWrapper<ProfitStatistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProfitStatistics::getCompanyId, companyId)
                .eq(ProfitStatistics::getYear, year)
                .eq(ProfitStatistics::getReportType, reportType);
        return getOne(wrapper);
    }


}
