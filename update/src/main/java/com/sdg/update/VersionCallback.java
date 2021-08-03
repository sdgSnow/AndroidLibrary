package com.sdg.update;

public interface VersionCallback {
    /**
     * 有新版本
     * */
    void hasNewApp();
    /**
     * 没有新版本
     * */
    void noNewApp();
    /**
     * 有新版本但取消了更新
     * */
    void dontUpdateApp();
}
