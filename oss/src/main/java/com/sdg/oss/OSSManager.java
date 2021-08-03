package com.sdg.oss;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

/**
 * OSSManager
 * Created by sdg on 2020/9/16.
 */
public class OSSManager {
    private static OSSManager sInstance;
    private String endpoint = "";
    private String bucket = "";
    private String accessKeyId = "";
    private String accessKeySecret = "";
    private String securityToken = "";
    private String directory = "";
    private String fileLocalPath = "";
    private String ossPath = "";
    private Handler handler = new Handler(Looper.getMainLooper());
    private Callback callback;
    private Context context;

    public static OSSManager get() {
        if (sInstance == null)
            sInstance = new OSSManager();
        return sInstance;
    }

    private OSSManager() {

    }

    public OSSManager setContext(Context context) {
        this.context = context;
        return this;
    }

    public OSSManager accessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
        return this;
    }

    public OSSManager accessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
        return this;
    }

    public OSSManager securityToken(String securityToken) {
        this.securityToken = securityToken;
        return this;
    }

    public OSSManager endPoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public OSSManager bucket(String bucket) {
        this.bucket = bucket;
        return this;
    }

    public OSSManager directory(String directory) {
        this.directory = directory;
        return this;
    }

    public String getDirectory() {
        return this.directory;
    }

    public OSSManager file(String file) {
        this.fileLocalPath = file;
        return this;
    }

    public OSSManager ossPath(String path) {
        this.ossPath = path;
        return this;
    }

    public OSSManager callback(Callback callback) {
        this.callback = callback;
        return this;
    }

    private OSS newClient() {
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(accessKeyId, accessKeySecret, securityToken) {
            @Override
            public OSSFederationToken getFederationToken() {
                //可实现自动获取token
                return super.getFederationToken();
            }
        };
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSSLog.enableLog();
        return new OSSClient(context, endpoint, credentialProvider, conf);
    }

    /**
     * oss上传
     */
    public void upload() {
        if (TextUtils.isEmpty(fileLocalPath)){
            Toast.makeText(context,"本地文件路径不存在",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(ossPath)){
            Toast.makeText(context,"oss文件路径不存在",Toast.LENGTH_SHORT).show();
            return;
        }

        PutObjectRequest request = new PutObjectRequest(bucket, ossPath, fileLocalPath);

        request.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                callback.onProgress(request,currentSize,totalSize);
            }
        });

        newClient().asyncPutObject(request, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                runOnUiThread(() -> {
                    if (callback != null) {
                        callback.onSuccess();
                    }
                });
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientException, ServiceException serviceException) {
                runOnUiThread(() -> {
                    if (callback != null) {
                        String message = "上传失败";
                        if (clientException != null)
                            message = clientException.getMessage();
                        if (serviceException != null)
                            message = serviceException.getMessage();
                        callback.onFailure(message);
                    }
                });
            }
        });
    }

    private void runOnUiThread(Runnable r) {
        handler.post(r);
    }

    public interface Callback {

        void onProgress(PutObjectRequest request, long currentSize, long totalSize);

        void onSuccess();

        void onFailure(String message);
    }
}
