package com.sdg.update.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.sdg.update.R;

public class DownloadProgress extends BaseDialog {

    private ProgressBar progressBar;

    private int mProgress = 0;

    private CallBack callBack;

    public DownloadProgress(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int width() {
        return 100;
    }

    @Override
    protected int height() {
        return 100;
    }

    @Override
    protected int getLayout() {
        return R.layout.progress;
    }

    @Override
    protected void onInflated(View container, Bundle savedInstanceState) {
        progressBar = container.findViewById(R.id.progressBar);
    }

    public void setProgress(int progress) {
        this.mProgress = progress;
        progressBar.setProgress(progress);
        if(progress >= 100){
            if(callBack != null) {
                callBack.success();
            }
        }
    }

    public void show(){
        mProgress = 0;
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        if(!isShowing()){
            if(getContext() instanceof AppCompatActivity){
                if(!((AppCompatActivity) getContext()).isFinishing()){
                    show();
                }
            }
        }
    }

    public void hide(){
        mProgress = 0;
        dismiss();
    }

    public interface CallBack{
        void success();
    }

    public DownloadProgress setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public int px2dp(int px){
        return  getContext().getResources().getDimensionPixelSize(R.dimen.base_dp)*px;
    }
}
