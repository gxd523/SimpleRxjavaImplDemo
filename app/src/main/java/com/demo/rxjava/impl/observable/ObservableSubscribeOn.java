package com.demo.rxjava.impl.observable;

import com.demo.rxjava.core.Disposable;
import com.demo.rxjava.core.ObservableSource;
import com.demo.rxjava.core.Observer;
import com.demo.rxjava.core.Scheduler;

/**
 * Created by guoxiaodong on 2020-02-17 13:53
 */
public class ObservableSubscribeOn<T> extends Observable<T> {
    private final ObservableSource<T> source;
    private final Scheduler scheduler;

    public ObservableSubscribeOn(ObservableSource<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        final SubscribeOnObserver<T> subscribeOnObserver = new SubscribeOnObserver<>(observer);

        observer.onSubscribe(subscribeOnObserver);

        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                source.subscribe(subscribeOnObserver);
            }
        });
    }

    private static final class SubscribeOnObserver<T> implements Observer<T>, Disposable {
        private final Observer<? super T> observer;
        private Disposable disposable;

        public SubscribeOnObserver(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public boolean isDisposed() {
            return disposable.isDisposed();
        }

        @Override
        public void dispose() {
            disposable.dispose();
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.disposable = disposable;
        }

        @Override
        public void onNext(T t) {
            observer.onNext(t);
        }

        @Override
        public void onError(Throwable throwable) {
            observer.onError(throwable);
        }

        @Override
        public void onComplete() {
            observer.onComplete();
        }
    }
}
