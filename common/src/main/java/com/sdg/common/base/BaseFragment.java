package com.sdg.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements BaseView {
    //    Unbinder unbinder;
    public P presenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView =  inflater.inflate(getLayoutId(), container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.presenter = createPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
        initThings(view,savedInstanceState);
        initData();
        initListeners();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        killPresenter();
    }

    /**
     * 设置布局
     */
    protected abstract int getLayoutId();
    /**
     * 绑定Presenter
     */
    public abstract P createPresenter();

    /**
     * 解绑Presenter
     */
    public void killPresenter() {
        if (presenter != null) {
            presenter.detachView();
        }
    }
    /**
     * 初始化一些事情
     */
    protected abstract void initThings(View view,Bundle savedInstanceState);
    /**
     * 初始化数据
     */
    protected void initData() {}

    /**
     * 初始化事件监听者
     */
    public abstract void initListeners();

    //打开一个Activity 默认 不关闭当前activity
    public void gotoActivity(Class<?> clz) {
        gotoActivity(clz, false, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity) {
        gotoActivity(clz, isCloseCurrentActivity, null);
    }

    public void gotoActivity(Class<?> clz, boolean isCloseCurrentActivity, Bundle ex) {
        Intent intent = new Intent(getActivity(), clz);
        if (ex != null)
            intent.putExtras(ex);
        startActivity(intent);
        if (isCloseCurrentActivity) {
            ((BaseActivity) getActivity()).finish();
        }
    }
}
