package com.demo.rxjava.impl.observable;

import com.demo.rxjava.core.ObservableSource;
import com.demo.rxjava.core.Observer;
import com.demo.rxjava.core.Scheduler;
import com.demo.rxjava.impl.tool.Function;

import androidx.annotation.NonNull;

/**
 * Created by guoxiaodong on 2020-02-16 13:01
 */
public abstract class Observable<T> implements ObservableSource<T> {
    @NonNull
    public static <T> Observable<T> create(ObservableOnSubscribe<T> onSubscribe) {
        return new ObservableCreate<>(onSubscribe);
    }

    @NonNull
    public final <R> Observable<R> map(Function<? super T, ? extends R> function) {
        return new ObservableMap<>(this, function);
    }

    @NonNull
    public final Observable<T> subscribeOn(Scheduler scheduler) {
        return new ObservableSubscribeOn<>(this, scheduler);
    }

    @NonNull
    public final Observable<T> observeOn(Scheduler scheduler) {
        return new ObservableObserveOn<>(this, scheduler);
    }

    @Override
    public void subscribe(Observer<? super T> observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer<? super T> observer);
}
