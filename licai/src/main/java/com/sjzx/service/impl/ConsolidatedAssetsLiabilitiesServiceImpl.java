package com.sjzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjzx.entity.ConsolidatedAssetsLiabilities;
import com.sjzx.exception.ServiceException;
import com.sjzx.mapper.ConsolidatedAssetsLiabilitiesMapper;
import com.sjzx.model.EasyUIResult;
import com.sjzx.model.vo.input.ConsolidatedAssetsLiabilitiesAddVO;
import com.sjzx.model.vo.input.ConsolidatedAssetsLiabilitiesUpdVO;
import com.sjzx.model.vo.input.LiabilitiesInputVO;
import com.sjzx.model.vo.output.ConsolidatedAssetsLiabilitiesVO;
import com.sjzx.service.ConsolidatedAssetsLiabilitiesService;
import com.sjzx.service.LiabilitiesStatisticsService;
import com.sjzx.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 * 合并资产负债表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-11-02
 */
@Service
public class ConsolidatedAssetsLiabilitiesServiceImpl extends ServiceImpl<ConsolidatedAssetsLiabilitiesMapper, ConsolidatedAssetsLiabilities> implements ConsolidatedAssetsLiabilitiesService {

    @Autowired
    private LiabilitiesStatisticsService liabilitiesStatisticsService;

    @Override
    public EasyUIResult<ConsolidatedAssetsLiabilitiesVO> listPage(LiabilitiesInputVO vo) {
        IPage<ConsolidatedAssetsLiabilitiesVO> iPage = new Page<>(vo.getPageNo(), vo.getPageSize());
        baseMapper.listPage(iPage, vo);
        return new EasyUIResult<>(iPage.getTotal(), iPage.getRecords());
    }

    @Override
    @Transactional
    public void addLiabilities(ConsolidatedAssetsLiabilitiesAddVO vo) {
        if(getByIndex(vo.getCompanyId(), vo.getYear(), vo.getReportType()) != null) {
            throw new ServiceException(vo.getYear() + "年数据已存在");
        }
        ConsolidatedAssetsLiabilities entity = BeanUtils.copyProperties(vo, ConsolidatedAssetsLiabilities::new);
        entity.setCreateTime(new Date()).insert();

        liabilitiesStatisticsService.statistics(entity.getCompanyId(), entity.getYear(), entity.getReportType());
    }

    @Override
    @Transactional
    public void updateLiabilities(ConsolidatedAssetsLiabilitiesUpdVO vo) {
        ConsolidatedAssetsLiabilities old = getById(vo.getId());
        if(old == null) {
            throw new ServiceException("数据不存在");
        }
        ConsolidatedAssetsLiabilities sameIndexEntity = getByIndex(old.getCompanyId(), vo.getYear(), vo.getReportType());
        if(sameIndexEntity != null && !sameIndexEntity.getId().equals(vo.getId())) {
            throw new ServiceException(vo.getYear() + "年数据已存在");
        }
        ConsolidatedAssetsLiabilities entity = BeanUtils.copyProperties(vo, ConsolidatedAssetsLiabilities::new);
        entity.setUpdateTime(new Date()).updateById();

        liabilitiesStatisticsService.statistics(old.getCompanyId(), vo.getYear(), vo.getReportType());
    }

    @Override
    public void deleteLiabilities(ConsolidatedAssetsLiabilities vo) {
        removeById(vo.getId());
    }

    @Override
    public ConsolidatedAssetsLiabilities getByIndex(Integer companyId, Integer year, Integer reportType) {
        if(companyId == null || year == null || reportType == null) {
            return null;
        }
        LambdaQueryWrapper<ConsolidatedAssetsLiabilities> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ConsolidatedAssetsLiabilities::getCompanyId, companyId)
                .eq(ConsolidatedAssetsLiabilities::getYear, year)
                .eq(ConsolidatedAssetsLiabilities::getReportType, reportType);
        return getOne(wrapper);
    }

}
