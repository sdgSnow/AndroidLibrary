package com.sdg.androidlibrary.adapter;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dimeno.commons.toolbar.impl.Toolbar;
import com.sdg.adapter.GridSpacesItemDecoration;
import com.sdg.androidlibrary.R;
import com.sdg.common.base.BaseActivity;
import com.sdg.common.base.BasePresenter;
import com.sdg.common.toolbar.BackMenuToolbar;

import java.util.ArrayList;
import java.util.List;

public class AdapterActivity extends BaseActivity {

    private RecyclerView rcyDemo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adapter;
    }

    @Override
    protected void initViews() {
        rcyDemo = findViewById(R.id.rcyDemo);
    }

    @Override
    protected void initData() {
        List<DemoBean> list = new ArrayList<>();
        for (int i = 0;i < 20;i++){
            list.add(new DemoBean("item ->" + i,"item desc ->" + i));
        }

        DemoAdapter adapter = new DemoAdapter(mContext,list);
        if(rcyDemo.getItemDecorationCount() == 0) {
            rcyDemo.addItemDecoration(new GridSpacesItemDecoration(this, 1, 10));
        }
        rcyDemo.setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        rcyDemo.setAdapter(adapter);
    }

    @Override
    public Toolbar createToolbar() {
        return new BackMenuToolbar(this,"adapter演示");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}