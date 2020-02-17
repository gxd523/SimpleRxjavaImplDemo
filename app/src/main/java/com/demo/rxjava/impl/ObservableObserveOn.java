package com.demo.rxjava.impl;

import com.demo.rxjava.core.Disposable;
import com.demo.rxjava.core.ObservableSource;
import com.demo.rxjava.core.Observer;
import com.demo.rxjava.core.Scheduler;

/**
 * Created by guoxiaodong on 2020-02-17 13:53
 */
public class ObservableObserveOn<T> extends Observable<T> {
    private final ObservableSource<T> source;
    private final Scheduler scheduler;

    public ObservableObserveOn(ObservableSource<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        source.subscribe(new ObserveOnObserver<>(observer, scheduler));
    }

    private static final class ObserveOnObserver<T> implements Observer<T>, Disposable {
        private final Observer<? super T> observer;
        private Disposable disposable;
        private Scheduler scheduler;

        public ObserveOnObserver(Observer<? super T> observer, Scheduler scheduler) {
            this.observer = observer;
            this.scheduler = scheduler;
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
            observer.onSubscribe(disposable);
        }

        @Override
        public void onNext(final T t) {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    observer.onNext(t);
                }
            });
        }

        @Override
        public void onError(final Throwable throwable) {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    observer.onError(throwable);
                }
            });
        }

        @Override
        public void onComplete() {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    observer.onComplete();
                }
            });
        }
    }
}
