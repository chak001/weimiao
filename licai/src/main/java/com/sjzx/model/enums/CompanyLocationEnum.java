package com.sjzx.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : CompanyTypeEnum
 * @Description : 公司归属于哪一类行业枚举
 * @Author : Horus
 * @Date: 2020-11-02 14:22
 */
@Getter
@AllArgsConstructor
public enum CompanyLocationEnum {

    SHANGHAI(1, "沪"),
    SHENZHEN(2, "深"),
    GANG(3, "港"),
    MEI(4, "美"),
    ;

    private int location;
    private String desc;

    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        for (CompanyLocationEnum value : CompanyLocationEnum.values()) {
            map.put(value.getLocation(), value.getDesc());
        }
        return map;
    }

}
