package com.sjzx.service;

import com.sjzx.entity.CombineProfit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sjzx.model.EasyUIResult;
import com.sjzx.model.vo.input.CombineProfitAddVO;
import com.sjzx.model.vo.input.CombineProfitInputVO;
import com.sjzx.model.vo.output.CombineProfitVO;

/**
 * <p>
 * 合并利润表 服务类
 * </p>
 *
 * @author 
 * @since 2020-11-03
 */
public interface CombineProfitService extends IService<CombineProfit> {

    EasyUIResult<CombineProfitVO> listPage(CombineProfitInputVO vo);

    void addCombineProfit(CombineProfitAddVO vo);

    void updateCombineProfit(CombineProfit vo);

    void deleteCombineProfit(CombineProfit vo);

    CombineProfit getByIndex(Integer companyId, Integer year, Integer reportType);

}
