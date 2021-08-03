package com.sdg.androidlibrary.common;

import com.dimeno.commons.toolbar.impl.Toolbar;
import com.sdg.androidlibrary.R;
import com.sdg.common.base.BaseActivity;
import com.sdg.common.base.BasePresenter;
import com.sdg.common.toolbar.BackMenuToolbar;

public class CommonActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public Toolbar createToolbar() {
        return new BackMenuToolbar(this,"common演示");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}