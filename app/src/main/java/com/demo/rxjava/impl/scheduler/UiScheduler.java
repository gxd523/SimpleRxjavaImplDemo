package com.demo.rxjava.impl.scheduler;

import android.os.Handler;
import android.os.Looper;

import com.demo.rxjava.core.Scheduler;

import androidx.annotation.NonNull;

/**
 * Created by guoxiaodong on 2020-02-17 15:35
 */
public class UiScheduler implements Scheduler {
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void schedule(@NonNull Runnable run) {
        handler.post(run);
    }
}
