package com.sdg.update.widget;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.sdg.update.R;

public class DialogProgress extends BaseDialog{

    private ProgressBar progressBar;

    private int mProgress = 0;

    private CallBack callBack;

    public DialogProgress(@NonNull Context context) {
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
            showDialog();
        }
    }

    public void hideProgress(){
        mProgress = 0;
        dismiss();
    }

    public interface CallBack{
        void success();
    }

    public DialogProgress setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }
}
