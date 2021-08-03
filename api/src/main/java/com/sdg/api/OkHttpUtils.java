package com.sdg.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class OkHttpUtils{

    public static OkHttpUtils okHttpUtils;

    public static OkHttpUtils get() {
        if (okHttpUtils == null) {
            okHttpUtils = new OkHttpUtils();
            return okHttpUtils;
        }
        return okHttpUtils;
    }

    /**
     * okhttp
     */
    private static OkHttpClient okHttpClient;

    /**
     * Retrofit
     */
    private static Retrofit retrofit;

    /**
     * 获取Retrofit的实例
     *
     * @return retrofit
     */
    public Retrofit getRetrofit(String url, int connectTimeout, int readTimeout, int writeTimeout, boolean retryOnConnectionFailure) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient(connectTimeout,readTimeout,writeTimeout,retryOnConnectionFailure))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }



    private OkHttpClient getOkHttpClient(int connectTimeout,int readTimeout,int writeTimeout,boolean retryOnConnectionFailure){
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(connectTimeout, TimeUnit.SECONDS)
                    .readTimeout(readTimeout, TimeUnit.SECONDS)
                    .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(retryOnConnectionFailure)
                    .addInterceptor(mLoggingInterceptor)
                    .addInterceptor(mRewriteCacheControlInterceptor);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    private static final Interceptor mLoggingInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Log.i("url:",String.format("发送请求: %s%n%s", request.url(), chain.connection(), request.headers()));
            Response response = chain.proceed(request);
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            return response;
        }
    };

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected(RetrofitManager.get().getContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected(RetrofitManager.get().getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + HttpConstant.sCACHE_STALE_SEC)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };
}
