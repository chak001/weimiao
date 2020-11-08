package com.sjzx.utils;

@FunctionalInterface
public interface BeanCopyCallBack<S, T> {

    void callback(S source, T target);


}
