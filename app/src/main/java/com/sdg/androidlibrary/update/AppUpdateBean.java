package com.sdg.androidlibrary.update;

public class AppUpdateBean {


    /**
     * code : 200
     * data : {"appDownloadurl":"http://192.168.188.56:8600/upload/appVersion/2019-11-21/20191121150401_440.apk","appLastforce":1,"appName":"数智文明2.0","appRemark":"","appServerversion":"2","appStatus":1,"appStr1":null,"appStr2":null,"createTime":"2019-11-21 14:51:00","createUser":"1","id":"1197407043003834369","updateTime":"2019-11-21 15:05:29","updateUser":"1"}
     * message : 查询成功！
     * success : true
     */

    public int code;
    public DataBean data;
    public String message;
    public boolean success;

    public static class DataBean {
        /**
         * appDownloadurl : http://192.168.188.56:8600/upload/appVersion/2019-11-21/20191121150401_440.apk
         * appLastforce : 1
         * appName : 数智文明2.0
         * appRemark :
         * appServerversion : 2
         * id : 1197407043003834369
         * updateTime : 2019-11-21 15:05:29
         * updateUser : 1
         */

        //apk的下载路径
        public String appDownloadurl;
        //是否强制更新 1强制 0非强制
        public int appLastforce;
        //APPname
        public String appName;
        //更新日志
        public String appRemark;
        //apk的服务器版本
        public long appServerversion;
        public String id;
        //更新时间
        public String updateTime;
        //更新者
        public String updateUser;
    }
}
