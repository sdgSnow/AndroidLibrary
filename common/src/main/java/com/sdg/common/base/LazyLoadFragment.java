package com.sdg.common.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class LazyLoadFragment<P extends BasePresenter> extends BaseLazyFragment<P> {
    /**
     * 是否创建过布局
     */
    protected boolean isViewCreat;
    /**
     * 当前界面是否可见
     */
    protected boolean isUIVisible;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInit=false;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewCreat = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //页面销毁,恢复标记
        isViewCreat = false;
        isUIVisible = false;
    }

    @Override
    public void onDetach() {
        //解绑,防止内存泄漏
        if (presenter != null){
            presenter.detachView();
        }
        super.onDetach();

    }

    /**
     * 懒加载,获取数据
     */
    public abstract void loadData();
    /**
     * 懒加载,重新加载 旧的数据
     */
    public abstract void reLoadData();

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreat && isUIVisible) {
            if(!isDataInit) {
                loadData();
                isDataInit=true;
            }else {
                reLoadData();
            }
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreat = false;
            isUIVisible = false;
        }
    }

}
