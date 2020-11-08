package com.sjzx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjzx.entity.ConsolidatedAssetsLiabilities;
import com.sjzx.model.EasyUIResult;
import com.sjzx.model.vo.input.ConsolidatedAssetsLiabilitiesAddVO;
import com.sjzx.model.vo.input.ConsolidatedAssetsLiabilitiesUpdVO;
import com.sjzx.model.vo.output.ConsolidatedAssetsLiabilitiesVO;
import com.sjzx.model.vo.input.LiabilitiesInputVO;

/**
 * <p>
 * 合并资产负债表 服务类
 * </p>
 *
 * @author 
 * @since 2020-11-02
 */
public interface ConsolidatedAssetsLiabilitiesService extends IService<ConsolidatedAssetsLiabilities> {

    EasyUIResult<ConsolidatedAssetsLiabilitiesVO> listPage(LiabilitiesInputVO vo);

    void addLiabilities(ConsolidatedAssetsLiabilitiesAddVO vo);

    void updateLiabilities(ConsolidatedAssetsLiabilitiesUpdVO vo);

    void deleteLiabilities(ConsolidatedAssetsLiabilities vo);

    ConsolidatedAssetsLiabilities getByIndex(Integer companyId, Integer year, Integer reportType);
}
