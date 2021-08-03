package com.sdg.androidlibrary.api;

import com.sdg.api.Res;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AppService {

    /**
     * 获取OSS信息
     */
    @POST("api/AliYun/GetToken")
    Observable<OssInfoEntity> getToken();

    /**
     * ‘关于我们’信息
     *
     * @param ProCode
     * @return
     */
    @GET("api/Que/GetAboutUs")
    Observable<Res<AboutUsBean>> getAboutUs(@Query("ProCode") String ProCode);


}
