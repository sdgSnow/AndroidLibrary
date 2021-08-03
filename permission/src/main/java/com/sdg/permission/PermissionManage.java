package com.sdg.permission;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.FragmentActivity;

import com.dimeno.permission.PermissionManager;
import com.dimeno.permission.callback.PermissionCallback;

public class PermissionManage {

    /**
     *  回调：同意，拒绝
     * @param activity
     * @param callback 只有同意和拒绝接口
     * @param permissions
     * */
    public static void request(FragmentActivity activity, AbsCallback callback,String... permissions){
        PermissionManager.request(activity, new PermissionCallback() {
            @Override
            public void onGrant(String[] permissions) {
                callback.onGrant(permissions);
            }

            @Override
            public void onDeny(String[] deniedPermissions, String[] neverAskPermissions) {
                callback.onDeny(deniedPermissions,neverAskPermissions);
            }

            @Override
            public void onNotDeclared(String[] permissions) {
                callback.onDeny(permissions,permissions);
            }
        }, permissions);
    }

    /**
     *  回调：同意，拒绝，不再提示
     * @param activity
     * @param callback
     * @param permissions
     * */
    public static void request(FragmentActivity activity, PermissionCallback callback,String... permissions){
        PermissionManager.request(activity, new PermissionCallback() {
            @Override
            public void onGrant(String[] permissions) {
                callback.onGrant(permissions);
            }

            @Override
            public void onDeny(String[] deniedPermissions, String[] neverAskPermissions) {
                callback.onDeny(deniedPermissions,neverAskPermissions);
            }

            @Override
            public void onNotDeclared(String[] permissions) {
                callback.onNotDeclared(permissions);
            }
        }, permissions);
    }

    /**
     *  回调：同意，拒绝，不再提示并前往设置页面
     * @param activity
     * @param isGo2Setting 权限拒绝后是否前往设置页面
     * @param callback
     * @param permissions
     * */
    public static void request(FragmentActivity activity,boolean isGo2Setting, PermissionCallback callback,String... permissions){
        PermissionManager.request(activity, new PermissionCallback() {
            @Override
            public void onGrant(String[] permissions) {
                callback.onGrant(permissions);
            }

            @Override
            public void onDeny(String[] deniedPermissions, String[] neverAskPermissions) {
                callback.onDeny(deniedPermissions,neverAskPermissions);
            }

            @Override
            public void onNotDeclared(String[] permissions) {
                callback.onNotDeclared(permissions);
                if(isGo2Setting){
                    go2Setting(activity);
                }
            }
        }, permissions);
    }

    public static void go2Setting(Context context){
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(intent);
    }
}
