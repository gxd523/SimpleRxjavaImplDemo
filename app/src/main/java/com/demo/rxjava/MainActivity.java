package com.demo.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.demo.rxjava.core.Disposable;
import com.demo.rxjava.core.Emitter;
import com.demo.rxjava.core.Observer;
import com.demo.rxjava.observable.Observable;
import com.demo.rxjava.observable.ObservableOnSubscribe;
import com.demo.rxjava.scheduler.Schedulers;
import com.demo.rxjava.tool.Function;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(Emitter<Integer> emitter) {
                Log.d("gxd", Thread.currentThread().getName() + "...subscribe...");
                emitter.onNext(123);
                emitter.onComplete();
            }
        })
                .subscribeOn(Schedulers.net())
                .observeOn(Schedulers.io())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return String.valueOf(integer);
                    }
                })
                .observeOn(Schedulers.ui())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        Log.d("gxd", Thread.currentThread().getName() + "...onSubscribe...");
                        disposable.dispose();
                    }

                    @Override
                    public void onNext(String s) {
                        Log.d("gxd", Thread.currentThread().getName() + "...onNext..." + s);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("gxd", "onError..." + throwable);
                    }

                    @Override
                    public void onComplete() {
                        Log.d("gxd", Thread.currentThread().getName() + "...onComplete...");
                    }
                });
    }
}
