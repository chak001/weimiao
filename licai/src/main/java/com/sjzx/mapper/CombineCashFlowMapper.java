package com.sjzx.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sjzx.entity.CombineCashFlow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sjzx.model.vo.input.CombineCashFlowInputVO;
import com.sjzx.model.vo.output.CombineCashFlowVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 合并现金流量表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2020-11-04
 */
public interface CombineCashFlowMapper extends BaseMapper<CombineCashFlow> {

    IPage<CombineCashFlowVO> listPage(IPage<CombineCashFlowVO> iPage, @Param("vo") CombineCashFlowInputVO vo);
}
