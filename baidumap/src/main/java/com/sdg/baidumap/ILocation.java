package com.sdg.baidumap;

/**
 * 此接口用于回调获取到的定位信息
 * 需要定位信息的实现此接口，并setLocationInfoInterface，即可，前提是先初始化这个类获取到定位信息
 * */
public interface ILocation {
    /**
     * 定位成功，返回经纬度，地址，定位时间
     * */
    void success(double longitude, double latitude, String address, long currentTime);

    /**
     * 定位失败，范围失败定位类型 int类型
     * @param errorType 失败类型，具体值代表什么需要去官网看
     * */
    void error(int errorType);
}
