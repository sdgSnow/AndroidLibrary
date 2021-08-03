package com.sdg.api.loading;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdg.api.R;
import com.sdg.api.RetrofitManager;

/**
 * Create by   :PNJ
 * Date        :2021/3/11
 * Description :
 */
public class ApiLoading {
    private Animation animation;
    private static ApiLoading sInstance = null;

    public static ApiLoading getInstance() {
        if (sInstance == null) {
            synchronized (ApiLoading.class) {
                if (sInstance == null) {
                    sInstance = new ApiLoading();
                }
            }
        }
        return sInstance;
    }

    private ProgressDialog mDialog = null;

    private ApiLoading() {
    }

    public void show(final Context context, final boolean isCancel) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            post(new Runnable() {
                @Override
                public void run() {
                    show(context, isCancel);
                }
            });
            return;
        }

        cancel();

        mDialog = new ProgressDialog(context,ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(isCancel);
        mDialog.show();
        View v = LayoutInflater.from(context).inflate(R.layout.api_loading, null);
        ImageView iv_loading = v.findViewById(R.id.loading);
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = px2dp(150);
        params.height = px2dp(130);
        mDialog.getWindow().setAttributes(params);
        mDialog.setContentView(v);
        initAnimation(context,iv_loading);
    }

    public void show(final String message, final boolean isCancel) {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            post(new Runnable() {
                @Override
                public void run() {
                    show(message, isCancel);
                }
            });
            return;
        }
        cancel();
        mDialog = new ProgressDialog(RetrofitManager.get().getContext(),ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(isCancel);
        mDialog.show();
        WindowManager.LayoutParams params = mDialog.getWindow().getAttributes();
        params.width = px2dp(150);
        params.height = px2dp(130);
        mDialog.getWindow().setAttributes(params);
        View v = LayoutInflater.from(RetrofitManager.get().getContext()).inflate(R.layout.api_loading, null);
        TextView title=v.findViewById(R.id.tv_message);
        if(title!=null){
            title.setText(message);
        }
        ImageView iv_loading = v.findViewById(R.id.loading);
        mDialog.setContentView(v);
        initAnimation(RetrofitManager.get().getContext(),iv_loading);
    }
    /**
     * 初始化默认的动画
     * */
    public void initAnimation(Context context,ImageView iv_loading){
        if(iv_loading!=null){
            Animation rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.loading);
            LinearInterpolator lin = new LinearInterpolator();
            rotateAnimation.setInterpolator(lin);
            if(animation == null){
                iv_loading.startAnimation(rotateAnimation);
            }else {
                iv_loading.startAnimation(animation);
            }
        }
    }

    /**
     * 修改Just
     *
     * @return
     */
    public boolean isShow() {
        return mDialog != null && mDialog.isShowing();
    }

    public void cancel() {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            post(new Runnable() {
                @Override
                public void run() {
                    cancel();
                }
            });
            return;
        }
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * 销毁，防止内存泄露
     */
    public void destory(){
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (mDialog != null ) {
            mDialog=null;
        }
    }

    /**
     * 在主线程运行任务
     */
    public static void post(Runnable task) {
        if (task == null) {
            return;
        }
        //判断当前线程是否是主线程
        if (RetrofitManager.get().getMainThreadId() == android.os.Process.myTid()) {
            //是主线程
            task.run();
        } else {
            //不是主线程
            RetrofitManager.get().getHandler().post(task);
        }
    }

    public static int px2dp(int px){
        return  RetrofitManager.get().getContext().getResources().getDimensionPixelSize(R.dimen.base_dp)*px;
    }
}
