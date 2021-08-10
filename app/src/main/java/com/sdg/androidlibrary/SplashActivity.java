package com.sdg.androidlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.dimeno.permission.callback.PermissionCallback;
import com.sdg.common.base.BaseActivity;
import com.sdg.common.base.BasePresenter;
import com.sdg.permission.PermissionManage;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION};
        PermissionManage.request(mActivity, new PermissionCallback() {
            @Override
            public void onGrant(String[] permissions) {
                gotoActivity(MainActivity.class,true);
            }

            @Override
            public void onDeny(String[] deniedPermissions, String[] neverAskPermissions) {
                ToastUtils.showShort("onDeny");
            }

            @Override
            public void onNotDeclared(String[] permissions) {
                ToastUtils.showShort("onNotDeclared");
            }
        },permissions);

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}