package com.demo.rxjava.core;

/**
 * Created by guoxiaodong on 2020-02-16 15:54
 */
public interface Emitter<T> {
    void onNext(T t);

    void onError(Throwable throwable);

    void onComplete();
}
