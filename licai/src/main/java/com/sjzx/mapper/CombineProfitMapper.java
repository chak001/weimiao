package com.sjzx.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sjzx.entity.CombineProfit;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjzx.model.vo.input.CombineProfitInputVO;
import com.sjzx.model.vo.output.CombineProfitVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 合并利润表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-11-03
 */
public interface CombineProfitMapper extends BaseMapper<CombineProfit> {

    IPage<CombineProfitVO> listPage(IPage<CombineProfitVO> iPage, @Param("vo") CombineProfitInputVO vo);

}
