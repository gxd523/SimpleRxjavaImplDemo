package com.demo.rxjava.impl.tool;

/**
 * Created by guoxiaodong on 2020-02-16 18:11
 */
public interface Function<T, R> {
    R apply(T t) throws Exception;
}
