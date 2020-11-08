package com.sjzx.model.vo.input;

import com.sjzx.model.vo.BasePage;
import lombok.Data;

/**
 * @ClassName : CombineProfitInputVO
 * @Description : 利润表数据查询入参对象
 * @Author : Horus
 * @Date: 2020-11-02 14:04
 */
@Data
public class CombineProfitInputVO extends BasePage {

    private Integer companyId;

    private Integer reportType;

    private Integer type;

    private Integer year;

}
