package com.sdg.androidlibrary.api;

import com.sdg.androidlibrary.BuildConfig;

public class HttpUrl {

    public static final String URL_FORMAL = "http://wjtest.dimenosys.com/";//外网测试地址

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static String getUrl() {
        if (isDebug()) {
            return URL_FORMAL;
        }
        return URL_FORMAL;
    }

}
