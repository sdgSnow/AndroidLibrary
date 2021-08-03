package com.sdg.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class DialogNormal extends BaseDialog {

    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvConfirm;
    private TextView tvCancle;

    private String title = "";
    private String content = "";
    private String confirm = "确定";
    private String cancle = "取消";
    private CallBack callBack;
    private boolean mCancelableDismiss = false;

    public DialogNormal(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int width() {
        return WindowManager.LayoutParams.MATCH_PARENT;
    }

    @Override
    protected int height() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    @Override
    protected int getLayout() {
        return R.layout.dialog_normal;
    }

    @Override
    protected void onInflated(View container, Bundle savedInstanceState) {
        tvTitle = container.findViewById(R.id.title);
        tvContent = container.findViewById(R.id.content);
        tvConfirm = container.findViewById(R.id.confirm);
        tvCancle = container.findViewById(R.id.cancle);

        tvTitle.setText(title);
        tvContent.setText(content);
        tvConfirm.setText(confirm);
        tvCancle.setText(cancle);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callBack != null) {
                    callBack.confirm();
                }
                dismiss();
            }
        });

        tvCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callBack != null) {
                    callBack.cancle();
                }
                dismiss();
            }
        });
    }


    public DialogNormal setTitle(String title) {
        this.title = title;
        return this;
    }

    public DialogNormal setContent(String content) {
        this.content = content;
        return this;
    }

    public DialogNormal setConfirm(String confirm) {
        this.confirm = confirm;
        return this;
    }

    public DialogNormal setCancle(String cancle) {
        this.cancle = cancle;
        return this;
    }

    public DialogNormal setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    //点击返回键和触摸dialog之外区域是否消失
    public DialogNormal setCancelableDismiss(boolean isCancelableDismiss) {
        this.mCancelableDismiss = isCancelableDismiss;
        return this;
    }

    public void show(){
        setCancelable(mCancelableDismiss);
        setCanceledOnTouchOutside(mCancelableDismiss);
        showDialog();
    }

    public interface CallBack {
        void confirm();

        void cancle();
    }
}
