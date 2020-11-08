package com.sjzx.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sjzx.entity.ConsolidatedAssetsLiabilities;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjzx.model.vo.output.ConsolidatedAssetsLiabilitiesVO;
import com.sjzx.model.vo.input.LiabilitiesInputVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 合并资产负债表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-11-02
 */
public interface ConsolidatedAssetsLiabilitiesMapper extends BaseMapper<ConsolidatedAssetsLiabilities> {

    IPage<ConsolidatedAssetsLiabilitiesVO> listPage(IPage<ConsolidatedAssetsLiabilitiesVO> iPage, @Param("vo") LiabilitiesInputVO vo);
}
