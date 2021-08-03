package com.sdg.androidlibrary.update;

import android.view.View;
import android.widget.TextView;

import com.dimeno.commons.toolbar.impl.Toolbar;
import com.sdg.androidlibrary.R;
import com.sdg.common.base.BaseActivity;
import com.sdg.common.base.BasePresenter;
import com.sdg.common.toolbar.BackMenuToolbar;

public class UpdateActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update;
    }

    @Override
    protected void initViews() {
        TextView update = findViewById(R.id.update);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VersionManager.get().checkVersion(mActivity);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public Toolbar createToolbar() {
        return new BackMenuToolbar(this,"update演示");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}