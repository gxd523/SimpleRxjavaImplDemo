package com.demo.rxjava.core;

/**
 * Created by guoxiaodong on 2020-02-16 13:02
 */
public interface ObservableSource<T> {
    /**
     * 订阅
     */
    void subscribe(Observer<? super T> observer);
}
