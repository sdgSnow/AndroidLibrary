package com.sdg.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LinearSpacingItemDecoration extends RecyclerView.ItemDecoration  {

    private int mLeft;
    private int mTop;
    private int mRight;
    private int mBottom;

    public LinearSpacingItemDecoration(Context context, int left, int top, int right, int bottom) {
        this.mLeft = DensityUtil.px2dp(context,left);
        this.mTop = DensityUtil.px2dp(context,top);
        this.mRight = DensityUtil.px2dp(context,right);
        this.mBottom = DensityUtil.px2dp(context,bottom);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mLeft;//左边间距
        outRect.top = mTop;//item上边的间距
        outRect.right = mRight;//右边间距
        outRect.bottom = mBottom;//设置bottom padding
    }

}
