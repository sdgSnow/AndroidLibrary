package com.sdg.baidumap;

import com.baidu.mapapi.search.core.PoiInfo;

import java.util.List;

public interface CallBack {
    void poiData(List<PoiInfo> poiInfoList);
    void noData();
}
