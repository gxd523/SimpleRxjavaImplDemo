package com.demo.rxjava.impl.scheduler;

import com.demo.rxjava.core.Scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;

/**
 * Created by guoxiaodong on 2020-02-17 15:35
 */
public class IoScheduler implements Scheduler {
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public void schedule(@NonNull Runnable run) {
        executorService.submit(run);
    }
}
