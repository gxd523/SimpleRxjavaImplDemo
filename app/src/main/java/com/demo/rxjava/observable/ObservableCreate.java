package com.demo.rxjava.observable;

import com.demo.rxjava.core.Disposable;
import com.demo.rxjava.core.Emitter;
import com.demo.rxjava.core.Observer;

/**
 * Created by guoxiaodong on 2020-02-16 16:38
 */
public class ObservableCreate<T> extends Observable<T> {
    private final ObservableOnSubscribe<T> source;

    public ObservableCreate(ObservableOnSubscribe<T> source) {
        this.source = source;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        CreateEmitter<T> parent = new CreateEmitter<>(observer);
        observer.onSubscribe(parent);
        try {
            source.subscribe(parent);
        } catch (Exception e) {
            e.printStackTrace();
            parent.onError(e);
        }
    }

    private static final class CreateEmitter<T> implements Emitter<T>, Disposable {
        private final Observer<? super T> observer;
        private boolean isDisposed;

        public CreateEmitter(Observer<? super T> observer) {
            this.observer = observer;
        }

        @Override
        public boolean isDisposed() {
            return isDisposed;
        }

        @Override
        public void dispose() {
            isDisposed = true;
        }

        @Override
        public void onNext(T t) {
            if (!isDisposed) {
                observer.onNext(t);
            }
        }

        @Override
        public void onError(Throwable throwable) {
            if (!isDisposed) {
                observer.onError(throwable);
            }
        }

        @Override
        public void onComplete() {
            if (!isDisposed) {
                observer.onComplete();
            }
        }
    }
}
