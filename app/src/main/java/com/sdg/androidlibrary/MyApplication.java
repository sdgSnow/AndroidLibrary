package com.sdg.androidlibrary;

import androidx.multidex.MultiDex;

import com.dimeno.network.Network;
import com.dimeno.network.config.NetConfig;
import com.sdg.androidlibrary.api.HttpUrl;
import com.sdg.api.RetrofitManager;
import com.sdg.baidumap.LocationManager;
import com.sdg.common.base.BaseApplication;
import com.sdg.androidlibrary.db.ObjectBoxManager;
import com.socks.library.KLog;

import io.objectbox.android.AndroidObjectBrowser;

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        LocationManager.init(this);
        RetrofitManager.get().setContext(this).setUrl(HttpUrl.getUrl());
        Network.init(this,new NetConfig.Builder().baseUrl("http://wm.dimenosys.com/").build());
        initOB();
    }

    public void initOB(){
        ObjectBoxManager.init(this);
        boolean start = new AndroidObjectBrowser(ObjectBoxManager.mBoxStore).start(this);
        KLog.i("开启浏览器查询：" + start);
    }

}
