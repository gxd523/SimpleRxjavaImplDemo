package com.demo.rxjava.scheduler;

import com.demo.rxjava.core.Scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import androidx.annotation.NonNull;

/**
 * Created by guoxiaodong on 2020-02-17 15:35
 */
public class IoScheduler implements Scheduler {
    private ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "io");
        }
    });

    @Override
    public void schedule(@NonNull Runnable run) {
        executorService.submit(run);
    }
}
