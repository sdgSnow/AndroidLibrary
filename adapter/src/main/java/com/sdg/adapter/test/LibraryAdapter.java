package com.sdg.adapter.test;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.sdg.adapter.BaseAdapter;
import com.sdg.adapter.R;

import java.util.List;

public class LibraryAdapter extends BaseAdapter<LibraryBean> {

    public LibraryAdapter(Context context, @Nullable List<LibraryBean> data) {
        super(context,R.layout.item_library, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LibraryBean item) {
        helper.addOnClickListener(R.id.title);
        helper.addOnClickListener(R.id.desc);
        helper.setText(R.id.title,item.title);
        helper.setText(R.id.desc,item.desc);

        View view = helper.getView(R.id.point);
        if(item.state == 0){
            view.setBackgroundResource(R.drawable.shape_waiting);
        }
        if(item.state == 1){
            view.setBackgroundResource(R.drawable.shape_going);
        }
        if(item.state == 2){
            view.setBackgroundResource(R.drawable.shape_finish);
        }

    }
}
