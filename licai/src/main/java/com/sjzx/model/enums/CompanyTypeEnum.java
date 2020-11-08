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
public enum CompanyTypeEnum {

    DEFAULT(0, "默认"),
    CAR_PRODUCT(1, "汽车制造"),
    WHITE_ELECTRICAL(2, "白色家电"),
    ;

    private int type;
    private String desc;

    public static Map<Integer, String> toMap() {
        Map<Integer, String> map = new HashMap<>();
        for (CompanyTypeEnum value : CompanyTypeEnum.values()) {
            map.put(value.getType(), value.getDesc());
        }
        return map;
    }

    public static Map<String, String> getCombobox() {
        Map<String, String> map = new HashMap<>();
        for (CompanyTypeEnum value : CompanyTypeEnum.values()) {
            map.put("key", value.getType() + "");
            map.put("value", value.getDesc());
        }
        return map;
    }
}
