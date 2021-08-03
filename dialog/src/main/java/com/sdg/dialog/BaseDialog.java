package com.sdg.dialog;

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

import com.sdg.dialog.R;

public abstract class BaseDialog extends Dialog {

    private final View mContentView;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.BaseDialog);
        mContentView = LayoutInflater.from(context).inflate(getLayout(), null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mContentView);
        if(mContentView != null){
            onInflated(mContentView,savedInstanceState);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        if(window != null){
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.gravity = Gravity.CENTER;
            attributes.width = width() > 0 ? px2dp(width()) : width();
            attributes.height = height() > 0 ? px2dp(height()) : height();
            window.setAttributes(attributes);
        }
    }

    /**
     * 返回当前自定义dialog的view布局
     *
     * @return int
     */
    protected abstract int width();

    /**
     * 返回当前自定义dialog的view布局
     *
     * @return int
     */
    protected abstract int height();

    /**
     * 返回当前自定义dialog的view布局
     *
     * @return int
     */
    protected abstract int getLayout();

    /**
     * 在此方法中对view进行初始化
     *
     * @param container 当前的view，通过该view findViewById可以找到子view
     * @param savedInstanceState 保存的Fragment状态
     */
    protected abstract void onInflated(View container, Bundle savedInstanceState);

    /**
     * 显示dialog之前先判断当前activity是否存在
     * */
    public void showDialog(){
        if(getContext() instanceof AppCompatActivity){
            if(!((AppCompatActivity) getContext()).isFinishing()){
                show();
            }
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getContext().getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public int px2dp(int px){
        return  getContext().getResources().getDimensionPixelSize(R.dimen.base_dp)*px;
    }
}
