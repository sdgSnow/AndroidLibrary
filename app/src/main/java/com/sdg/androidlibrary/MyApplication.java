package com.sdg.androidlibrary;

import androidx.multidex.MultiDex;

import com.dimeno.network.Network;
import com.dimeno.network.config.NetConfig;
import com.sdg.androidlibrary.api.HttpUrl;
import com.sdg.api.HttpConstant;
import com.sdg.api.RetrofitManager;
import com.sdg.baidumap.LocationManager;
import com.sdg.common.base.BaseApplication;
import com.sdg.objectbox.ObjectBoxManager;
import com.socks.library.KLog;
import com.tencent.bugly.crashreport.CrashReport;

import io.objectbox.android.AndroidObjectBrowser;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashReport.initCrashReport(this, "9ead381a13", BuildConfig.DEBUG);
        MultiDex.install(this);
        LocationManager.init(this);
        RetrofitManager.get().setContext(this).setUrl(HttpUrl.getUrl());
        Network.init(this,new NetConfig.Builder().baseUrl("http://wm.dimenosys.com/").build());
    }
}
