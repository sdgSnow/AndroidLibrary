package com.sdg.androidlibrary.api;

import com.sdg.api.HttpConstant;
import com.sdg.api.Res;
import com.sdg.api.RetrofitManager;

import io.reactivex.Observable;

public class ApiImpl {

    public static Observable<Res<AboutUsBean>> getAboutUs(String procode){
        return RetrofitManager.get().getApi(AppService.class).getAboutUs(procode);
    }

    public static Observable<OssInfoEntity> getToken(){
        return RetrofitManager.get().getApi(AppService.class).getToken();
    }

}
