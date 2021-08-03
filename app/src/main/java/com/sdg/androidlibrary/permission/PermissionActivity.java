package com.sdg.androidlibrary.permission;

import android.Manifest;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dimeno.commons.toolbar.impl.Toolbar;
import com.dimeno.permission.callback.PermissionCallback;
import com.sdg.androidlibrary.R;
import com.sdg.common.base.BaseActivity;
import com.sdg.common.base.BasePresenter;
import com.sdg.common.toolbar.BackMenuToolbar;
import com.sdg.permission.AbsCallback;
import com.sdg.permission.PermissionManage;

public class PermissionActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_permission;
    }

    @Override
    protected void initViews() {
        TextView onGrant = findViewById(R.id.onGrant);
        TextView onDeny = findViewById(R.id.onDeny);
        TextView onDeny2 = findViewById(R.id.onDeny2);
        TextView onNotDeclared = findViewById(R.id.onNotDeclared);

        onGrant.setOnClickListener(this::onClick);
        onDeny.setOnClickListener(this::onClick);
        onDeny2.setOnClickListener(this::onClick);
        onNotDeclared.setOnClickListener(this::onClick);
    }

    @Override
    protected void initData() {

    }

    @Override
    public Toolbar createToolbar() {
        return new BackMenuToolbar(this, "permission演示");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.onGrant:
                PermissionManage.request(mActivity, new PermissionCallback() {
                    @Override
                    public void onGrant(String[] permissions) {
                        ToastUtils.showShort("申请成功");
                    }

                    @Override
                    public void onDeny(String[] deniedPermissions, String[] neverAskPermissions) {
                        ToastUtils.showShort("拒绝申请");
                    }

                    @Override
                    public void onNotDeclared(String[] permissions) {
                        ToastUtils.showShort("拒绝并不再提示");
                    }
                }, Manifest.permission.ACCESS_FINE_LOCATION);
                break;
            case R.id.onDeny:
                PermissionManage.request(mActivity, new PermissionCallback() {
                    @Override
                    public void onGrant(String[] permissions) {
                        ToastUtils.showShort("申请成功");
                    }

                    @Override
                    public void onDeny(String[] deniedPermissions, String[] neverAskPermissions) {
                        ToastUtils.showShort("拒绝申请");
                    }

                    @Override
                    public void onNotDeclared(String[] permissions) {
                        ToastUtils.showShort("拒绝并不再提示");
                        PermissionManage.go2Setting(mContext);
                    }
                }, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission_group.STORAGE);
                break;
            case R.id.onDeny2:
                PermissionManage.request(mActivity,true ,new PermissionCallback() {
                    @Override
                    public void onGrant(String[] permissions) {

                    }

                    @Override
                    public void onDeny(String[] deniedPermissions, String[] neverAskPermissions) {
                        ToastUtils.showShort("拒绝并提示设置");
                    }

                    @Override
                    public void onNotDeclared(String[] permissions) {

                    }
                },Manifest.permission.CAMERA);
                break;
            case R.id.onNotDeclared:

                break;
        }
    }
}