package com.sdg.androidlibrary.dialog;

import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dimeno.commons.toolbar.impl.Toolbar;
import com.sdg.androidlibrary.R;
import com.sdg.common.base.BaseActivity;
import com.sdg.common.base.BasePresenter;
import com.sdg.common.toolbar.BackMenuToolbar;
import com.sdg.dialog.DialogManage;
import com.sdg.dialog.DialogNormal;
import com.sdg.dialog.DialogProgress;
import com.sdg.dialog.DialogTip;

public class DialogActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initViews() {
        TextView normal = findViewById(R.id.normal);
        TextView tip = findViewById(R.id.tip);
        TextView progress = findViewById(R.id.progress);
        TextView loading = findViewById(R.id.loading);

        normal.setOnClickListener(this::onClick);
        tip.setOnClickListener(this::onClick);
        progress.setOnClickListener(this::onClick);
        loading.setOnClickListener(this::onClick);
    }

    @Override
    protected void initData() {

    }

    @Override
    public Toolbar createToolbar() {
        return new BackMenuToolbar(this, "dialog演示");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.normal:
                new DialogNormal(mContext).setTitle("DialogNormal")
                        .setContent("this is a dialognormal")
                        .setConfirm("确认")
                        .setCancle("取消")
                        .setCallBack(new DialogNormal.CallBack() {
                            @Override
                            public void confirm() {
                                Toast.makeText(mContext, "点击了确定", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void cancle() {
                                Toast.makeText(mContext, "点击了取消", Toast.LENGTH_SHORT).show();
                            }
                        }).showDialog();
                break;
            case R.id.tip:
                new DialogTip(mContext).setContent("this is a dialogtip")
                        .setConfirm("请确认")
                        .setCallBack(new DialogTip.CallBack() {
                            @Override
                            public void confirm() {
                                Toast.makeText(mContext, "点击了确定", Toast.LENGTH_SHORT).show();
                            }
                        }).showDialog();
                break;
            case R.id.progress:
                DialogManage.get().showProgress(mContext);
                handler.postDelayed(runnable, 100);
                break;
            case R.id.loading:
                DialogManage.get().showLoading(mContext);
                break;
        }
    }

    private int progress = 0;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(runnable, 50);
            progress++;
            DialogManage.get().setProgress(progress);
            DialogManage.get().setCallBack(new DialogProgress.CallBack() {
                @Override
                public void success() {
                    progress = 0;
                    handler.removeCallbacks(runnable);
                    DialogManage.get().hideProgress();
                    Toast.makeText(mContext, "上传成功", Toast.LENGTH_SHORT).show();
                }
            });
        }
    };
}