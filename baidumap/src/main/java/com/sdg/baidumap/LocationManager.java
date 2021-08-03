package com.sdg.baidumap;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;

public class LocationManager {

    private static Context mContext;
    private LocationService locationService;
    private static LocationManager manager;
    private boolean isAlways = false;
    private int time = 2000;

    public static void init(Context context) {
        mContext = context;
        SDKInitializer.initialize(context);
    }

    public static LocationManager get() {
        if (manager == null)
            manager = new LocationManager();
        return manager;
    }

    public LocationManager() {

    }

    /**
     * 是否总是定位，默认为false，false表示只定位一次，true则在后台连续定位
     */
    public LocationManager setAlways(boolean isAlways) {
        this.isAlways = isAlways;
        return manager;
    }

    /**
     * @param time 设置间隔时间 毫秒 默认2000
     * */
    public LocationManager setTime(int time) {
        this.time = time;
        return manager;
    }

    /**
     * 初始化并开始定位sdk
     * 初始化的场景最好是在activity里面，需要的时候就初始化
     */
    public void start(ILocation iLocation) {
        setiLocation(iLocation);
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService = new LocationService(mContext);
        locationService.setTime(time);
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();// 定位SDK
    }

    /**
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                switch (location.getLocType()) {
                    case BDLocation.TypeGpsLocation:   // GPS定位结果
                    case BDLocation.TypeNetWorkLocation:   // 网络定位结果
                    case BDLocation.TypeOffLineLocation:   // 离线定位结果
                        String address = location.getDistrict() + location.getStreet() + location.getStreetNumber();
                        iLocation.success(location.getLongitude(), location.getLatitude(), address, System.currentTimeMillis());
                        if (!isAlways) {
                            stop();//获取到定位之后就停止定位
                        }
                        break;
                    case BDLocation.TypeServerError:     //服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因
                    case BDLocation.TypeNetWorkException:  //网络不同导致定位失败，请检查网络是否通畅
                    case BDLocation.TypeCriteriaException:   //无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机
                        //定位失败
                        iLocation.error(location.getLocType());
                        locationService.stop();
                        break;
                }
            }
        }
    };

    /**
     * 注销监听，停止定位
     * 注销之后必须再次初始化才能起作用
     */
    public void stop() {
        if (locationService != null) {
            //注销掉监听
            locationService.unregisterListener(mListener);
            //停止定位服务
            locationService.stop();
        }
    }

    private ILocation iLocation;

    private void setiLocation(ILocation locationInfo) {
        iLocation = locationInfo;
    }

}
