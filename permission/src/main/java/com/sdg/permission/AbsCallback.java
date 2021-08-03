package com.sdg.permission;

public interface AbsCallback {

    /**
     * all permissions are granted
     *
     * @param permissions permissions
     */
    void onGrant(String[] permissions);

    /**
     * permissions are denied or refused
     *
     * @param deniedPermissions   denied permissions
     * @param neverAskPermissions refused permissions
     */
    void onDeny(String[] deniedPermissions, String[] neverAskPermissions);
}
