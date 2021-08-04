package com.sdg.dialog;

import android.content.Context;

public class DialogManage {

    private static DialogManage dialogManage = null;
    private DialogLoading dialogLoading = null;
    private DialogProgress dialogProgress = null;

    public static DialogManage get() {
        if (dialogManage == null) {
            dialogManage = new DialogManage();
        }
        return dialogManage;
    }

    public void showLoading(Context context) {
        if (dialogLoading != null) {
            dialogLoading.dismiss();
        } else {
            dialogLoading = new DialogLoading(context);
        }
        dialogLoading.showDialog();
    }

    public void hideLoading(){
        dialogLoading.dismiss();
    }

    public void showProgress(Context context) {
        if (dialogProgress != null) {
            dialogProgress.dismiss();
        } else {
            dialogProgress = new DialogProgress(context);
        }
        dialogProgress.showDialog();
    }

    public void hideProgress(){
        dialogProgress.hideProgress();
    }

    public void setProgress(int progress) {
        dialogProgress.setProgress(progress);
    }

    public void setCallBack(DialogProgress.CallBack callBack) {
        dialogProgress.setCallBack(callBack);
    }
}
