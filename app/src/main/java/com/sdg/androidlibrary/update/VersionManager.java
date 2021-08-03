package com.sdg.androidlibrary.update;

import androidx.appcompat.app.AppCompatActivity;
import com.dimeno.network.callback.LoadingCallback;
import com.sdg.update.UpdateService;

public class VersionManager {

    private static VersionManager mInstance;
    private static final Object lock = new Object();
    public static VersionManager get() {
        if (mInstance == null) {
            synchronized (lock) {
                if (mInstance == null) {
                    mInstance = new VersionManager();
                }
            }
        }
        return mInstance;
    }

    public void checkVersion(AppCompatActivity activity) {
        new CheckVersionTask(new LoadingCallback<AppUpdateBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(AppUpdateBean data) {
                UpdateService.downloadApk(activity, data.data.appDownloadurl);
            }

            @Override
            public void onError(int code, String message) {

            }

            @Override
            public void onComplete() {

            }
        }).setApi("/icsscore/api/getAppInfo").setTag(this).exe();
    }

}
