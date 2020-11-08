package com.sjzx.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorEnum {

    //自定义错误码
    E_500("500", "当前网速不稳定，请稍后再试"),
    ;

    private String errorCode;

    private String errorMsg;

}
