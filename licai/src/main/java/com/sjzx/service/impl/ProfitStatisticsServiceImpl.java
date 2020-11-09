package com.sjzx.service.impl;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
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
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

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

    /**
     * 导出数据
     **/
    @Override
    @SneakyThrows
    public void exportData(HttpServletResponse response, LiabilitiesStatisticsInputVO vo) {
        List<ProfitStatisticsVO> list = baseMapper.getList(vo);
        formatToPercent(list);
        ExcelWriter writer = ExcelUtil.getWriter();
        addHeaderAlias(writer);
        writer.setOnlyAlias(true);
        writer.write(list, true);

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String name = URLEncoder.encode("合并利润表指标表", "utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xls");

        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
        }
        IoUtil.close(out);
    }

    private void addHeaderAlias(ExcelWriter writer) {
        writer.merge(19, "合并利润表指标数据统计");
        writer.addHeaderAlias("code", "股票代码");
        writer.addHeaderAlias("name", "公司名称");
        writer.addHeaderAlias("year", "年份");
        writer.addHeaderAlias("reportType", "参考标准");
        writer.addHeaderAlias("businessIncomeGrowthRate", "营业收入增速");
        writer.addHeaderAlias("grossProfitMargin", "毛利率");
        writer.addHeaderAlias("costRate", "费用率");
        writer.addHeaderAlias("costInProfit", "费用率/毛利率");
        writer.addHeaderAlias("mainProfit", "主营利润");
        writer.addHeaderAlias("businessToProfit", "经营活动产生的现金流量净额");
        writer.addHeaderAlias("profitQuality", "利润质量 经营活动产生的现金流量净额/主营利润");
        writer.addHeaderAlias("profitQualityOne", "利润质量 经营活动产生的现金流量净额/净利润");
        writer.addHeaderAlias("mainProfitInProfitTotal", "主营利润/利润总额");
        writer.addHeaderAlias("mainProfitInIncomeTotal", "主营利润率 主营利润/营业收入");
        writer.addHeaderAlias("belongMotherNetProfit", "归属于母公司所有者的净利润");
        writer.addHeaderAlias("belongMotherNetProfitGrowthRate", "归属于母公司所有者的净利润增速");
        writer.addHeaderAlias("totalEquity", "总股本");
        writer.addHeaderAlias("sharesProfit", "每股收益");
        writer.addHeaderAlias("createTime", "创建时间");
        writer.addHeaderAlias("updateTime", "更新时间");
    }

    /** 数据百分比格式化**/
    private void formatToPercent(List<ProfitStatisticsVO> records) {
        records.forEach(e ->
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
