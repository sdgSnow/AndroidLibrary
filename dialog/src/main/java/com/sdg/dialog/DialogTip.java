package com.sdg.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

public class DialogTip extends BaseDialog{

    private TextView tvContent;
    private TextView tvConfirm;

    private String content = "";
    private String confirm = "确定";
    private CallBack callBack;
    private boolean mCancelableDismiss = true;

    public DialogTip(@NonNull Context context) {
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
        return R.layout.dialog_tip;
    }

    @Override
    protected void onInflated(View container, Bundle savedInstanceState) {
        tvContent = container.findViewById(R.id.content);
        tvConfirm = container.findViewById(R.id.confirm);

        tvContent.setText(content);
        tvConfirm.setText(confirm);

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callBack != null) {
                    callBack.confirm();
                }
                dismiss();
            }
        });
    }

    public DialogTip setContent(String content) {
        this.content = content;
        return this;
    }

    public DialogTip setConfirm(String confirm) {
        this.confirm = confirm;
        return this;
    }

    public DialogTip setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public interface CallBack {
        void confirm();
    }
}
