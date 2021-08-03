package com.sdg.common.base;

import android.app.Application;
import android.os.Handler;

import com.sdg.common.BuildConfig;
import com.socks.library.KLog;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class BaseApplication extends Application {
    private static BaseApplication mContext;
    private static int mMainThreadId;
    private static Handler mHandler;
    private static RefWatcher refWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        // 获取主线程ID
        mMainThreadId = android.os.Process.myTid();
        // 获取Handler
        mHandler = new Handler();
        KLog.init(true);
        initLeakCanary();
    }
    /**
     * 获取全局上下文获取
     */
    public static BaseApplication getContext() {
        return mContext;
    }
    /**
     * 获取主线程ID
     */
    public static int getMainThreadId() {
        return mMainThreadId;
    }
    /**
     * 获取Handler
     */
    public static Handler getHandler() {
        return mHandler;
    }

    /**
     * 初始化内存溢出监测工具
     */
    private void initLeakCanary() {
        if (!BuildConfig.DEBUG) {
            refWatcher = LeakCanary.install(this);
        } else {
            refWatcher = installLeakCanary();
        }
    }

    /**
     * 监控Activity和Fragment
     *
     * @return
     */
    public static RefWatcher getRefWatcher() {
        return refWatcher;
    }

    /**
     * release版本使用此方法
     */
    protected RefWatcher installLeakCanary() {
        return RefWatcher.DISABLED;
    }


}
