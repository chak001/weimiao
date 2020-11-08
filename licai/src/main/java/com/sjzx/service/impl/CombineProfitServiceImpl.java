package com.sjzx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sjzx.entity.CombineProfit;
import com.sjzx.exception.ServiceException;
import com.sjzx.mapper.CombineProfitMapper;
import com.sjzx.model.EasyUIResult;
import com.sjzx.model.vo.input.CombineProfitAddVO;
import com.sjzx.model.vo.input.CombineProfitInputVO;
import com.sjzx.model.vo.output.CombineProfitVO;
import com.sjzx.service.CombineProfitService;
import com.sjzx.utils.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 合并利润表 服务实现类
 * </p>
 *
 * @author 
 * @since 2020-11-03
 */
@Service
public class CombineProfitServiceImpl extends ServiceImpl<CombineProfitMapper, CombineProfit> implements CombineProfitService {

    @Override
    public EasyUIResult<CombineProfitVO> listPage(CombineProfitInputVO vo) {
        IPage<CombineProfitVO> iPage = new Page<>(vo.getPageNo(), vo.getPageSize());
        baseMapper.listPage(iPage, vo);
        return new EasyUIResult<>(iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public void addCombineProfit(CombineProfitAddVO vo) {
        if(getByIndex(vo.getCompanyId(), vo.getYear(), vo.getReportType()) != null) {
            throw new ServiceException(vo.getYear() + "年数据已存在");
        }
        CombineProfit entity = BeanUtils.copyProperties(vo, CombineProfit::new);
        entity.setCreateTime(new Date()).insert();
    }

    @Override
    public void updateCombineProfit(CombineProfit vo) {
        CombineProfit old = getById(vo.getId());
        if(old == null) {
            throw new ServiceException("数据不存在");
        }
        CombineProfit sameIndexEntity = getByIndex(old.getCompanyId(), vo.getYear(), vo.getReportType());
        if(sameIndexEntity != null && !sameIndexEntity.getId().equals(vo.getId())) {
            throw new ServiceException(vo.getYear() + "年数据已存在");
        }
        CombineProfit entity = BeanUtils.copyProperties(vo, CombineProfit::new);
        entity.setUpdateTime(new Date()).updateById();
    }

    @Override
    public void deleteCombineProfit(CombineProfit vo) {
        removeById(vo.getId());
    }

    @Override
    public CombineProfit getByIndex(Integer companyId, Integer year, Integer reportType) {
        if(companyId == null || year == null || reportType == null) {
            return null;
        }
        LambdaQueryWrapper<CombineProfit> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CombineProfit::getCompanyId, companyId)
                .eq(CombineProfit::getYear, year)
                .eq(CombineProfit::getReportType, reportType);
        return getOne(wrapper);
    }

}
