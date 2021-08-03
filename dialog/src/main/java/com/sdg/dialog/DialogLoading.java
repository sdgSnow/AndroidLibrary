package com.sdg.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;

public class DialogLoading extends BaseDialog {

    private ImageView loading;
    private Context mContext;

    public DialogLoading(@NonNull Context context) {
        super(context);
        this.mContext = context;
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
        return R.layout.dialog_loading;
    }

    @Override
    protected void onInflated(View container, Bundle savedInstanceState) {
        loading = container.findViewById(R.id.loading);
        showAnimation();
    }

    /**
     * 初始化默认的动画
     */
    public void showAnimation() {
        Animation rotateAnimation = AnimationUtils.loadAnimation(mContext, R.anim.loading);
        LinearInterpolator lin = new LinearInterpolator();
        rotateAnimation.setInterpolator(lin);
        loading.startAnimation(rotateAnimation);
    }

    public void show(){
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        if(!isShowing()){
            showDialog();
        }
    }

    public void hideLoading(){
        dismiss();
    }
}
