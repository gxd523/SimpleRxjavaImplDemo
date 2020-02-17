package com.demo.rxjava.core;

import androidx.annotation.NonNull;

/**
 * Created by guoxiaodong on 2020-02-17 15:34
 */
public interface Scheduler {
    void schedule(@NonNull Runnable run);
}
