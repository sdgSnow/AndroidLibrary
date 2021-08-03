package com.sdg.androidlibrary.thread;

import com.dimeno.commons.toolbar.impl.Toolbar;
import com.sdg.androidlibrary.R;
import com.sdg.common.base.BaseActivity;
import com.sdg.common.base.BasePresenter;
import com.sdg.common.toolbar.BackMenuToolbar;

public class ThreadActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_thread;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public Toolbar createToolbar() {
        return new BackMenuToolbar(this,"thread演示");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}