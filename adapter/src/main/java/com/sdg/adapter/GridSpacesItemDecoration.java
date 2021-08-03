package com.sdg.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class GridSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpanCount; //列数
    private int mSpacing; //间隔
    private boolean mIsIncludeEdge; //是否有边缘

    /**
     * 添加条目镖局时最好判断边距是否为0
     * recycleview.getItemDecorationCount()==0
     * */
    public GridSpacesItemDecoration(Context context, int spanCount, int spacing) {
        this.mSpanCount = spanCount;
        this.mSpacing = DensityUtil.px2dp(context,spacing);
        this.mIsIncludeEdge = false;
    }

    /**
     * 添加条目镖局时最好判断边距是否为0
     * recycleview.getItemDecorationCount()==0
     * */
    public GridSpacesItemDecoration(Context context, int spanCount, int spacing, boolean isIncludeEdge) {
        this.mSpanCount = spanCount;
        this.mSpacing = DensityUtil.px2dp(context,spacing);
        this.mIsIncludeEdge = isIncludeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //判断你有几列，设置item
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % mSpanCount; // item column

        if (mIsIncludeEdge) {
            outRect.left = mSpacing - column * mSpacing / mSpanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * mSpacing / mSpanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < mSpanCount) { // top edge
                outRect.top = mSpacing;
            }
            outRect.bottom = mSpacing; // item bottom
        } else {
            outRect.left = column * mSpacing / mSpanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = mSpacing - (column + 1) * mSpacing / mSpanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= mSpanCount) {
                outRect.top = mSpacing; // item top
            }
        }
    }

}
