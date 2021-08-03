package com.sdg.androidlibrary.api;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dimeno.commons.toolbar.impl.Toolbar;
import com.sdg.androidlibrary.R;
import com.sdg.api.BaseNormalObserver;
import com.sdg.api.BaseObserver;
import com.sdg.api.Res;
import com.sdg.api.RetrofitManager;
import com.sdg.api.RetrofitUtil;
import com.sdg.common.base.BaseActivity;
import com.sdg.common.base.BasePresenter;
import com.sdg.common.toolbar.BackMenuToolbar;
import com.socks.library.KLog;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ApiActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_api;
    }

    @Override
    protected void initViews() {
        TextView get = findViewById(R.id.get);
        TextView post = findViewById(R.id.post);
        TextView put = findViewById(R.id.put);

        get.setOnClickListener(this::onClick);
        post.setOnClickListener(this::onClick);
        put.setOnClickListener(this::onClick);
    }

    @Override
    protected void initData() {

    }

    @Override
    public Toolbar createToolbar() {
        return new BackMenuToolbar(this,"api演示");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.get:
                RetrofitUtil.get().request(ApiImpl.getAboutUs("weiduques"), new BaseObserver<AboutUsBean>(mContext,false) {
                    @Override
                    protected void onPrepare(Disposable d) {
                        KLog.i("onPrepare");
                    }

                    @Override
                    protected void onSuccess(Res<AboutUsBean> res) {
                        KLog.i("onSuccess");
                    }

                    @Override
                    protected void onFaild(String error) {
                        KLog.i("onFaild");
                    }

                });
                break;
            case R.id.post:
                RetrofitUtil.get().request(ApiImpl.getToken(), new BaseNormalObserver<OssInfoEntity>(mContext,false) {
                    @Override
                    protected void onPrepare(Disposable d) {
                        KLog.i("onPrepare");
                    }

                    @Override
                    protected void onSuccess(OssInfoEntity res) {
                        KLog.i("onSuccess");
                    }

                    @Override
                    protected void onFaild(int code, String error) {
                        KLog.i("onFaild " + code + ":" + error);
                        ToastUtils.showShort("上传附件失败：" + error);
                    }

                });
                break;
            case R.id.put:
                break;
        }
    }
}