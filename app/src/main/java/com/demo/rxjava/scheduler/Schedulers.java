package com.demo.rxjava.scheduler;

import com.demo.rxjava.core.Scheduler;

/**
 * Created by guoxiaodong on 2020-02-17 13:47
 */
public class Schedulers {
    public static Scheduler io() {
        return IoSchedulerHolder.IO;
    }

    public static Scheduler net() {
        return NetSchedulerHolder.NET;
    }

    public static Scheduler ui() {
        return UiSchedulerHolder.UI;
    }

    private static final class IoSchedulerHolder {
        private static final Scheduler IO = new IoScheduler();
    }

    private static final class NetSchedulerHolder {
        private static final Scheduler NET = new NetScheduler();
    }

    private static final class UiSchedulerHolder {
        private static final Scheduler UI = new UiScheduler();
    }
}
