package com.sjzx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sjzx.entity.Company;
import com.sjzx.model.EasyUIResult;
import com.sjzx.model.vo.input.CompanyInputVO;
import com.sjzx.model.vo.input.CompanyUpdVO;
import com.sjzx.model.vo.output.CompanyVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 
 * @since 2020-11-02
 */
public interface CompanyService extends IService<Company> {

    EasyUIResult<CompanyVO> companyPage(CompanyInputVO vo);

    void addCompany(Company vo);

    void updateCompany(CompanyUpdVO vo);

    void deleteCompany(Company vo);

    List<Company> combogrid(String q);
}
