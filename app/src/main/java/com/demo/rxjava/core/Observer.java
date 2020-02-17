package com.demo.rxjava.core;

/**
 * Created by guoxiaodong on 2020-02-16 13:05
 */
public interface Observer<T> {
    void onSubscribe(Disposable disposable);

    void onNext(T t);

    void onError(Throwable throwable);

    void onComplete();
}
