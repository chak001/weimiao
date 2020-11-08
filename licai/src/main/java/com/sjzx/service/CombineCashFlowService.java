package com.sjzx.service;

import com.sjzx.entity.CombineCashFlow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sjzx.model.EasyUIResult;
import com.sjzx.model.vo.input.CombineCashFlowAddVO;
import com.sjzx.model.vo.input.CombineCashFlowInputVO;
import com.sjzx.model.vo.output.CombineCashFlowVO;

/**
 * <p>
 * 合并现金流量表 服务类
 * </p>
 *
 * @author 
 * @since 2020-11-04
 */
public interface CombineCashFlowService extends IService<CombineCashFlow> {

    EasyUIResult<CombineCashFlowVO> listPage(CombineCashFlowInputVO vo);

    void addCashFlow(CombineCashFlowAddVO vo);

    void updateCashFlow(CombineCashFlow vo);

    void deleteCashFlow(CombineCashFlow vo);

    CombineCashFlow getByIndex(Integer companyId, Integer year, Integer reportType);
}
