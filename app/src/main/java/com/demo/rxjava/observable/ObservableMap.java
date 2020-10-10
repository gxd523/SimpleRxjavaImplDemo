package com.demo.rxjava.observable;

import com.demo.rxjava.core.Disposable;
import com.demo.rxjava.core.ObservableSource;
import com.demo.rxjava.core.Observer;
import com.demo.rxjava.tool.Function;

/**
 * Created by guoxiaodong on 2020-02-16 17:56
 */
public class ObservableMap<T, R> extends Observable<R> {
    private final ObservableSource<T> source;
    private final Function<? super T, ? extends R> mapper;

    public ObservableMap(ObservableSource<T> source, Function<? super T, ? extends R> mapper) {
        this.source = source;
        this.mapper = mapper;
    }

    @Override
    protected void subscribeActual(Observer<? super R> observer) {
        MapObserver<T, R> mapObserver = new MapObserver<>(observer, mapper);
        source.subscribe(mapObserver);
    }

    private static final class MapObserver<T, R> implements Observer<T>, Disposable {
        private final Observer<? super R> observer;
        private final Function<? super T, ? extends R> mapper;
        private Disposable disposable;

        public MapObserver(Observer<? super R> observer, Function<? super T, ? extends R> mapper) {
            this.observer = observer;
            this.mapper = mapper;
        }

        @Override
        public void onSubscribe(Disposable disposable) {
            this.disposable = disposable;
            observer.onSubscribe(disposable);
        }

        @Override
        public void onNext(T t) {
            if (!disposable.isDisposed()) {
                try {
                    R result = mapper.apply(t);
                    observer.onNext(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!disposable.isDisposed()) {
                observer.onError(throwable);
            }
        }

        @Override
        public void onComplete() {
            if (!disposable.isDisposed()) {
                observer.onComplete();
            }
        }

        @Override
        public boolean isDisposed() {
            return disposable.isDisposed();
        }

        @Override
        public void dispose() {
            disposable.dispose();
        }
    }
}
