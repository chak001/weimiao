package com.sjzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjzx.entity.CashFlowStatistics;
import com.sjzx.entity.CombineCashFlow;
import com.sjzx.entity.CombineProfit;
import com.sjzx.mapper.CashFlowStatisticsMapper;
import com.sjzx.model.EasyUIResult;
import com.sjzx.model.vo.input.LiabilitiesStatisticsInputVO;
import com.sjzx.model.vo.output.CashFlowStatisticsVO;
import com.sjzx.service.CashFlowStatisticsService;
import com.sjzx.service.CombineCashFlowService;
import com.sjzx.service.CombineProfitService;
import com.sjzx.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.sjzx.utils.NumberUtils.divide;
import static com.sjzx.utils.NumberUtils.toPercent;

/**
 * <p>
 * 合并现金流量表指标 服务实现类
 * </p>
 *
 * @author
 * @since 2020-11-04
 */
@Service
public class CashFlowStatisticsServiceImpl extends ServiceImpl<CashFlowStatisticsMapper, CashFlowStatistics> implements CashFlowStatisticsService {

    @Autowired
    private CombineCashFlowService combineCashFlowService;

    @Autowired
    private CombineProfitService combineProfitService;

    @Override
    public EasyUIResult<CashFlowStatisticsVO> listPage(LiabilitiesStatisticsInputVO vo) {
        IPage<CashFlowStatisticsVO> iPage = new Page<>(vo.getPageNo(), vo.getPageSize());
        baseMapper.listPage(iPage, vo);
        iPage.getRecords().forEach(e ->
                e.setCashInIncoming(toPercent(e.getCashInIncoming()))
                        .setExpandInProfitRate(toPercent(e.getExpandInProfitRate()))
                        .setSellInExpandRate(toPercent(e.getSellInExpandRate()))
        );
        return new EasyUIResult<>(iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public void statistics(Integer companyId, Integer year, Integer reportType) {
        CombineCashFlow current = combineCashFlowService.getByIndex(companyId, year, reportType);
        if (current == null) {
            return;
        }

        CashFlowStatistics statistics = BeanUtils.copyProperties(current, CashFlowStatistics::new);
        statistics.setRemark(null).setProfitSubstractBonus(current.getBusinessToProfit() - current.getBonusCash())
                .setExpandInProfitRate(divide(current.getInvestmentInsideOut(), current.getBusinessToProfit()))
                .setSellInExpandRate(divide(current.getInvestmentInsideIn(), current.getBusinessToProfit()));

        CombineProfit combineProfit = combineProfitService.getByIndex(companyId, year, reportType);
        if (combineProfit != null) {
            statistics.setCashInIncoming(divide(current.getSaleToCash(), combineProfit.getBusinessIncome()));
        } else {
            statistics.setCashInIncoming(0);
        }
        CashFlowStatistics old = getByIndex(companyId, year, reportType);
        if (old == null) {
            statistics.setCreateTime(new Date()).setUpdateTime(null).insert();
        } else {
            statistics.setId(old.getId()).setUpdateTime(new Date()).updateById();
        }
    }

    private CashFlowStatistics getByIndex(Integer companyId, Integer year, Integer reportType) {
        if (companyId == null || year == null || reportType == null) {
            return null;
        }
        LambdaQueryWrapper<CashFlowStatistics> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CashFlowStatistics::getCompanyId, companyId)
                .eq(CashFlowStatistics::getYear, year)
                .eq(CashFlowStatistics::getReportType, reportType);
        return getOne(wrapper);
    }

}
