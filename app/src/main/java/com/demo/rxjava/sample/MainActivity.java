package com.demo.rxjava.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.demo.rxjava.R;
import com.demo.rxjava.core.Disposable;
import com.demo.rxjava.core.Emitter;
import com.demo.rxjava.core.Observer;
import com.demo.rxjava.impl.Observable;
import com.demo.rxjava.impl.ObservableOnSubscribe;
import com.demo.rxjava.impl.scheduler.Schedulers;
import com.demo.rxjava.impl.tool.Function;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "new");
            }
        });
        Log.d("gxd", "MainActivity.onCreate-->" + Executors.newSingleThreadExecutor().getClass().getName());
        scheduledExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                Observable
                        .create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(Emitter<String> emitter) {
                                Log.d("gxd", Thread.currentThread().getName() + "...subscribe...");
                                emitter.onNext("aaa");
                                emitter.onComplete();
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.net())
                        .map(new Function<String, Integer>() {
                            @Override
                            public Integer apply(String s) {
                                Log.d("gxd", Thread.currentThread().getName() + "...map..." + s);
                                return 999;
                            }
                        })
                        .observeOn(Schedulers.ui())
                        .subscribe(new Observer<Integer>() {
                            @Override
                            public void onSubscribe(Disposable disposable) {
                                Log.d("gxd", Thread.currentThread().getName() + "...onSubscribe...");
//                                disposable.dispose();
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
        });
    }
}
