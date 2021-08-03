package com.sdg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdg.adapter.interf.IEmpty;

import java.util.List;

public class BaseAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder> {

    public List<T> mData;
    public Context mContext;

    public BaseAdapter(Context context,int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
        mData = data;
        mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {

    }

    /**
     * 清空数据并刷新
     * */
    public void clear(){
        mData.clear();
        notifyDataSetChanged();
    }

    public void setDefaultEmpty(IEmpty empty){
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.empty, null, false);
        isUseEmpty(true);
        TextView tv_retry = inflate.findViewById(R.id.tv_retry);
        tv_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empty.retry();
            }
        });
        setEmptyView(inflate);
    }

}
