package com.sdg.androidlibrary.adapter;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdg.adapter.test.LibraryBean;
import com.sdg.androidlibrary.R;

import java.util.List;

public class DemoAdapter extends BaseQuickAdapter<DemoBean, BaseViewHolder> {

    private Context mContext;
    private List<DemoBean> mData;

    public DemoAdapter(Context context, @Nullable List<DemoBean> data) {
        super(R.layout.item_demo_adapter, data);
        this.mContext = context;
        this.mData = data;
    }

    @Override
    protected void convert(BaseViewHolder helper, DemoBean item) {
        helper.addOnClickListener(R.id.title);
        helper.addOnClickListener(R.id.desc);
        helper.setText(R.id.title,item.title);
        helper.setText(R.id.desc,item.desc);
    }
}
