package com.sdg.androidlibrary.baidumap;

import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.blankj.utilcode.util.ToastUtils;
import com.dimeno.commons.toolbar.impl.Toolbar;
import com.sdg.androidlibrary.R;
import com.sdg.baidumap.ILocation;
import com.sdg.baidumap.LocationManager;
import com.sdg.baidumap.MapUtil;
import com.sdg.baidumap.CallBack;
import com.sdg.common.base.BaseActivity;
import com.sdg.common.base.BasePresenter;
import com.sdg.common.toolbar.BackMenuToolbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BaiduMapActivity extends BaseActivity implements View.OnClickListener {

    private TextView once;
    private TextView always;
    private TextView jumpBaidu;
    private TextView jumpGaode;
    private TextView jumpTenxun;
    private TextView poi;
    private TextView message;
    private StringBuilder locationMessage = new StringBuilder();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_baidu_map;
    }

    @Override
    protected void initViews() {
        once = findViewById(R.id.once);
        always = findViewById(R.id.always);
        jumpBaidu = findViewById(R.id.jumpBaidu);
        jumpGaode = findViewById(R.id.jumpGaode);
        jumpTenxun = findViewById(R.id.jumpTenxun);
        poi = findViewById(R.id.poi);
        message = findViewById(R.id.message);

        once.setOnClickListener(this::onClick);
        always.setOnClickListener(this::onClick);
        jumpBaidu.setOnClickListener(this::onClick);
        jumpGaode.setOnClickListener(this::onClick);
        jumpTenxun.setOnClickListener(this::onClick);
        poi.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.once:
                LocationManager.get().start(new ILocation() {
                    @Override
                    public void success(double longitude, double latitude, String address, long currentTime) {
                        message.setText("longitude:" + longitude + ",latitude:" + latitude + ",address:" + address);
                    }

                    @Override
                    public void error(int errorType) {
                        ToastUtils.showShort("error:" + errorType);
                    }
                });
                break;
            case R.id.always:
                LocationManager.get().setAlways(true).setTime(5000).start(new ILocation() {
                    @Override
                    public void success(double longitude, double latitude, String address, long currentTime) {
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String timestr = df.format(new Date(currentTime));
                        locationMessage.append("longitude:" + longitude + ",latitude:" + latitude + ",address:" + address + ",timestr" + timestr + "\n");
                        message.setText(locationMessage.toString());
                    }

                    @Override
                    public void error(int errorType) {
                        ToastUtils.showShort("error:" + errorType);
                    }
                });
                break;
            case R.id.jumpBaidu:
                MapUtil.openBaiDuNavi(mContext,22.561183,114.115394,"",22.569900,114.119900,"桂园街道社康中心");
                break;
            case R.id.jumpGaode:
                MapUtil.openGaoDeNavi(mContext,22.561183,114.115394,"",22.569900,114.119900,"桂园街道社康中心");
                break;
            case R.id.jumpTenxun:
                MapUtil.openTencentNavi(mContext,22.561183,114.115394,"",22.569900,114.119900,"桂园街道社康中心");
                break;
            case R.id.poi:
                LocationManager.get().start(new ILocation() {
                    @Override
                    public void success(double longitude, double latitude, String address, long currentTime) {
                        MapUtil.searchPoiNearby(new LatLng(latitude, longitude), address, 500, new CallBack() {
                            @Override
                            public void poiData(List<PoiInfo> poiInfoList) {
                                for (PoiInfo poiInfo : poiInfoList) {
                                    locationMessage.append("longitude:" + poiInfo.location + ",latitude:" + poiInfo.location + ",address:" + poiInfo.address + "\n");
                                    message.setText(locationMessage.toString());
                                }
                            }

                            @Override
                            public void noData() {

                            }
                        });
                    }

                    @Override
                    public void error(int errorType) {
                        ToastUtils.showShort("error:" + errorType);
                    }
                });

                break;
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public Toolbar createToolbar() {
        BackMenuToolbar menu = new BackMenuToolbar(this, "baidumap演示");
        menu.setMyOnClickListener(new BackMenuToolbar.MyOnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.back:
                        finish();
                        break;
                    case R.id.menu:
                        ToastUtils.showShort("help me");
                        break;
                }
            }
        });
        return menu;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocationManager.get().stop();
    }
}