package com.sdg.common.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.sdg.common.R;

/**
 * Date        :2021/3/11
 * Description :带有ToolBar的基类
 */
public abstract class ToolBarActivity<P extends BasePresenter>  extends BaseActivity<P> {

    protected Toolbar toolBar;
    protected ImageView imgBack;
    protected TextView tvTitle;
    protected
    @Nullable
    AppBarLayout appBar;
    protected boolean mIsHidden = false;
    protected
    @Nullable
    TextView tvAction;
    protected
    @Nullable
    ImageView imgAction;

    private LinearLayout mDectorView = null;//根布局
    private FrameLayout mContentView = null;//activity内容布局
    /**
     * @return 提供标题
     */
    abstract protected CharSequence provideTitle();

    /**
     * @param savedInstanceState 缓存数据
     *                           <p/>
     *                           初始化一些事情
     */
    @Override
    protected void initThings(Bundle savedInstanceState) {
        initToolBar();
    }

    private void initToolBar() {
        initDectorView();
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        imgBack = (ImageView) findViewById(R.id.img_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvAction = (TextView) findViewById(R.id.tv_action);
        appBar = (AppBarLayout) findViewById(R.id.app_bar_layout);

        if (canBack()) {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        } else {
            imgBack.setVisibility(View.GONE);
        }


        if (canAction()) {
            if (tvAction != null) {
                tvAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action();
                    }
                });
            }
            if (imgAction != null) {
                imgAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        action();
                    }
                });
            }
        } else {
            if (tvAction != null) {

                tvAction.setVisibility(View.GONE);
            }

            if (imgAction != null) {

                imgAction.setVisibility(View.GONE);
            }


        }


        tvTitle.setText(provideTitle());

    }
    private void initDectorView() {
        mDectorView = new LinearLayout(this);
        mDectorView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mDectorView.setOrientation(LinearLayout.VERTICAL);
        View view = getLayoutInflater().inflate(R.layout.toolbar_layout, mDectorView);
        mContentView = new FrameLayout(this);
        mContentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mDectorView.addView(mContentView);
    }
    /**
     * Toolbar右边按钮的点击事件
     */
    protected void action() {

    }


    /**
     * @param alpha 设置标题栏的透明度
     */
    protected void setAppBarAlpha(float alpha) {
        if (appBar != null) {
            appBar.setAlpha(alpha);
        }
    }

    /**
     * 隐藏和显示Toolbar
     */
    protected void hideOrShowToolbar() {
        if (appBar != null) {
            appBar.animate()
                    .translationY(mIsHidden ? 0 : -appBar.getHeight())
                    .setInterpolator(new DecelerateInterpolator(2))
                    .start();
            mIsHidden = !mIsHidden;
        }

    }

    /**
     * @return 返回按钮是否可以显示
     */
    public boolean canBack() {
        return true;
    }


    /**
     * @return 右边按钮是否显示
     */
    public boolean canAction() {
        return false;
    }

}
