package com.sjzx.model.vo.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LiabilitiesStatisticsAddVO {

    @ApiModelProperty(required = true)
    private Integer companyId;

    @ApiModelProperty(allowableValues = "2020,2019,2018,2017,2016,2015", required = true)
    private Integer year;

    @ApiModelProperty(allowableValues = "1,2,3,4", required = true)
    private Integer reportType;

}
