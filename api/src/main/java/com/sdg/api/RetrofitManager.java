package com.sdg.api;

import android.content.Context;
import android.os.Handler;

import okhttp3.OkHttpClient;

public class RetrofitManager {
    public static RetrofitManager retrofitManager;
    private static volatile OkHttpClient okHttpClient;
    private static int mMainThreadId;
    private static Handler mHandler;
    public Context context;
    public static RetrofitManager get() {
        if (retrofitManager == null) {
            retrofitManager = new RetrofitManager();
            return retrofitManager;
        }
        return retrofitManager;
    }

    /**
     * 传入定义api的接口
     * */
    public <T> T getApi(Class<T> service){
        return OkHttpUtils.get().getRetrofit(url,connectTimeout,readTimeout,writeTimeout,retryOnConnectionFailure).create(service);
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
     * 连接超时时间，默认30秒
     * */
    private int connectTimeout = 30;
    /**
     * 读取数据超时时间，默认30秒
     * */
    private int readTimeout = 30;
    /**
     * 写入数据超时时间，默认30秒
     * */
    private int writeTimeout = 30;
    /**
     * 失败后是否重连，默认false
     * */
    private boolean retryOnConnectionFailure = false;
    /**
     * api的地址
     * */
    private String url;

    public RetrofitManager setConnectTimeout(int connectTimeout){
        this.connectTimeout = connectTimeout;
        return this;
    }

    public RetrofitManager setReadTimeout(int readTimeout){
        this.readTimeout = readTimeout;
        return this;
    }

    public RetrofitManager setWriteTimeout(int writeTimeout){
        this.writeTimeout = writeTimeout;
        return this;
    }

    public RetrofitManager setRetryOnConnectionFailure(boolean retryOnConnectionFailure){
        this.retryOnConnectionFailure = retryOnConnectionFailure;
        return this;
    }

    public RetrofitManager setUrl(String url){
        this.url = url;
        return this;
    }

    public RetrofitManager setContext(Context context) {
        this.context = context;
        // 获取主线程ID
        mMainThreadId = android.os.Process.myTid();
        // 获取Handler
        mHandler = new Handler();
        return this;
    }

    public Context getContext() {
        return context;
    }
}
