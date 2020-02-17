package com.demo.rxjava.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.demo.rxjava.impl.R;
import com.demo.rxjava.core.Disposable;
import com.demo.rxjava.core.Emitter;
import com.demo.rxjava.impl.Observable;
import com.demo.rxjava.impl.ObservableOnSubscribe;
import com.demo.rxjava.core.Observer;
import com.demo.rxjava.impl.scheduler.Schedulers;
import com.demo.rxjava.impl.tool.Function;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(Emitter<String> emitter) {
                        Log.d("gxd", Thread.currentThread().getName() + "...subscribe...");
                        emitter.onNext("aaa");
                        emitter.onComplete();
                    }
                })
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) {
                        Log.d("gxd", Thread.currentThread().getName() + "...map..." + s);
                        return 999;
                    }
                })
                .observeOn(Schedulers.ui())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        Log.d("gxd", Thread.currentThread().getName() + "...onSubscribe...");
//                disposable.dispose();
                    }

                    @Override
                    public void onNext(Integer s) {
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
