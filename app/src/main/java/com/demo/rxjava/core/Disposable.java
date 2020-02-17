package com.demo.rxjava.core;

/**
 * Created by guoxiaodong on 2020-02-16 13:07
 */
public interface Disposable {
    boolean isDisposed();

    void dispose();
}
