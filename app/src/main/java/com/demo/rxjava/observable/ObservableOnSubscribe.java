package com.demo.rxjava.observable;

import com.demo.rxjava.core.Emitter;

/**
 * Created by guoxiaodong on 2020-02-16 15:52
 */
public interface ObservableOnSubscribe<T> {
    void subscribe(Emitter<T> emitter) throws Exception;
}
