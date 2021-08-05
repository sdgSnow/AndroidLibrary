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

public abstract class BaseDialog extends Dialog {

    private final View mContentView;
    private Context mContext;
    private boolean isTouchOrClickDismiss = true;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.BaseDialog);
        this.mContext = context;
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
     * @param touchOrClickDismiss 点击空白区域和返回键是否消失（结合在一起），默认为 true，代表点击可消失
     *
     * */
    public BaseDialog setTouchOrClickDismiss(boolean touchOrClickDismiss){
        this.isTouchOrClickDismiss = touchOrClickDismiss;
        return this;
    }

    /**
     * 显示dialog之前先判断当前activity是否存在
     * 且dialog未显示的情况下才可show
     * */
    public void showDialog(){
        setCancelable(isTouchOrClickDismiss);
        setCanceledOnTouchOutside(isTouchOrClickDismiss);
        if(mContext instanceof AppCompatActivity){
            if(!((AppCompatActivity) mContext).isFinishing() && !isShowing()){
                show();
            }
        }
    }

    public int px2dp(int px){
        return  getContext().getResources().getDimensionPixelSize(R.dimen.base_dp)*px;
    }
}
