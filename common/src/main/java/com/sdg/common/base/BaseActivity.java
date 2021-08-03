package com.sdg.common.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.dimeno.commons.toolbar.ToolbarActivity;
import com.wangzhen.statusbar.DarkStatusBar;
import com.wangzhen.statusbar.listener.StatusBar;
import java.util.ArrayList;

public abstract class BaseActivity<P extends BasePresenter>  extends ToolbarActivity implements BaseView{
    public Context mContext;
    public AppCompatActivity mActivity;
    public P presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        BaseApplication.getRefWatcher().watch(this);
        //配置为竖屏
        this.presenter =  createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
        mContext = this;
        mActivity = this;
        //初始化Presenter
        initThings(savedInstanceState);
        //初始化控件，一般在BaseActivity中通过ButterKnife来绑定，所以该方法内部一般我们初始化界面相关的操作
        initViews();
        //获取数据
        initData();
        ActivityManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getAppManager().removeActivityStack(this);
        killPresenter();
    }

    public void fitDarkStatusBar(boolean dark){
        StatusBar statusBar = DarkStatusBar.get();
        if(dark) statusBar.fitDark(this);
    }

    /**
     * 设置布局
     */
    protected abstract int getLayoutId();
    /**
     * @param savedInstanceState 缓存数据
     *                           <p>
     *                           初始化一些事情
     */
    protected void initThings(Bundle savedInstanceState){}

    /**
     * 初始化界面
     */
    protected abstract void initViews();

    /**
     * 获取数据
     */
    protected abstract void initData();

    /**
     * 实例化presenter
     */
    protected abstract P createPresenter();
    /**
     * 解绑Presenter
     */
    public void killPresenter() {
        if (presenter != null) {
            presenter.detachView();
        }
    }

    //打开一个Activity 默认 不关闭当前activity
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        Intent intent = new Intent(mContext, clz);
        if (ex != null)
            intent.putExtras(ex);
        startActivity(intent);
        if (isCloseCurrentActivity) {
            ((BaseActivity) mContext).finish();
        }
    }


    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 点击EditText文本框之外的地方收起软键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }

        // 分发触摸事件给所有注册了MyTouchListener的接口
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return onTouchEvent(ev);
    }
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    public interface MyTouchListener
    {
        public void onTouchEvent(MotionEvent event);
    }
    /*
     * 保存MyTouchListener接口的列表
     */
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MyTouchListener>();


}