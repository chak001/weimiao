package com.sjzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjzx.entity.CombineCashFlow;
import com.sjzx.exception.ServiceException;
import com.sjzx.mapper.CombineCashFlowMapper;
import com.sjzx.model.EasyUIResult;
import com.sjzx.model.vo.input.CombineCashFlowAddVO;
import com.sjzx.model.vo.input.CombineCashFlowInputVO;
import com.sjzx.model.vo.output.CombineCashFlowVO;
import com.sjzx.service.CashFlowStatisticsService;
import com.sjzx.service.CombineCashFlowService;
import com.sjzx.service.ProfitStatisticsService;
import com.sjzx.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 合并现金流量表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-11-04
 */
@Service
public class CombineCashFlowServiceImpl extends ServiceImpl<CombineCashFlowMapper, CombineCashFlow> implements CombineCashFlowService {

    @Autowired
    private CashFlowStatisticsService cashFlowStatisticsService;

    @Autowired
    private ProfitStatisticsService profitStatisticsService;

    @Override
    public EasyUIResult<CombineCashFlowVO> listPage(CombineCashFlowInputVO vo) {
        IPage<CombineCashFlowVO> iPage = new Page<>(vo.getPageNo(), vo.getPageSize());
        baseMapper.listPage(iPage, vo);
        return new EasyUIResult<>(iPage.getTotal(), iPage.getRecords());
    }

    @Override
    @Transactional
    public void addCashFlow(CombineCashFlowAddVO vo) {
        if(getByIndex(vo.getCompanyId(), vo.getYear(), vo.getReportType()) != null) {
            throw new ServiceException(vo.getYear() + "年数据已存在");
        }
        CombineCashFlow entity = BeanUtils.copyProperties(vo, CombineCashFlow::new);
        entity.setCreateTime(new Date()).insert();

        profitStatisticsService.statistics(vo.getCompanyId(), vo.getYear(), vo.getReportType());
        cashFlowStatisticsService.statistics(vo.getCompanyId(), vo.getYear(), vo.getReportType());
    }

    @Override
    @Transactional
    public void updateCashFlow(CombineCashFlow vo) {
        CombineCashFlow old = getById(vo.getId());
        if(old == null) {
            throw new ServiceException("数据不存在");
        }
        CombineCashFlow sameIndexEntity = getByIndex(old.getCompanyId(), vo.getYear(), vo.getReportType());
        if(sameIndexEntity != null && !sameIndexEntity.getId().equals(vo.getId())) {
            throw new ServiceException(vo.getYear() + "年数据已存在");
        }
        CombineCashFlow entity = BeanUtils.copyProperties(vo, CombineCashFlow::new);
        entity.setUpdateTime(new Date()).updateById();

        profitStatisticsService.statistics(old.getCompanyId(), vo.getYear(), vo.getReportType());
        cashFlowStatisticsService.statistics(old.getCompanyId(), vo.getYear(), vo.getReportType());
    }

    @Override
    public void deleteCashFlow(CombineCashFlow vo) {
        removeById(vo.getId());
    }

    @Override
    public CombineCashFlow getByIndex(Integer companyId, Integer year, Integer reportType) {
        if(companyId == null || year == null || reportType == null) {
            return null;
        }
        LambdaQueryWrapper<CombineCashFlow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CombineCashFlow::getCompanyId, companyId)
                .eq(CombineCashFlow::getYear, year)
                .eq(CombineCashFlow::getReportType, reportType);
        return getOne(wrapper);
    }

}
