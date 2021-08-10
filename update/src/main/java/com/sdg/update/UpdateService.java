package com.sdg.update;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.sdg.dialog.DialogManage;
import com.sdg.update.widget.DialogProgress;
import com.sdg.update.widget.DownloadProgress;
import com.wangzhen.download.DownloadClient;
import com.wangzhen.download.bean.ParamsBody;
import com.wangzhen.download.callback.AbsDownloadCallback;
import java.io.File;

public class UpdateService {

    public static void downloadApk(AppCompatActivity activity, String url){
//        url = "http://wm.dimenosys.com/upload/appVersion/20210205200356_572.apk";
        url = "https://cos.pgyer.com/c7b2a811c3a00eef3c1a9178b84f6a33.apk?sign=0c444adfbb63d74e775a6eaf5d21b522&t=1628491291&response-content-disposition=attachment%3Bfilename%3DAndroid%E5%BA%93_1.1.0.apk";
        String[] split = url.split("/");
        String apkName = split[split.length - 1];
        Log.i("DownloadService","安装包：" + apkName);
        DialogManage.get().showProgress(activity);
        DownloadClient.get().enqueue(new ParamsBody.Builder()
                .fileName("update.apk")
                .dir(activity.getApplicationContext().getExternalFilesDir("") + "/librarys")
                .url(url)
                .callback(new AbsDownloadCallback() {
                    @Override
                    public void onLoading(int progress) {
                        DialogManage.get().setProgress(progress);
                    }

                    @Override
                    public void onSuccess(String path) {
                        Log.i("download","apkPath:" + path);
                        DialogManage.get().hideProgress();
                        AppUtil.installApk(activity,new File(path));
                    }

                    @Override
                    public void onFail(String err) {
                        DialogManage.get().hideProgress();
                        Toast.makeText(activity, "下载安装包失败：" + err, Toast.LENGTH_LONG).show();
                    }
                }).build());
    }

}
