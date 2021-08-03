package com.sdg.common.toolbar;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dimeno.commons.toolbar.impl.Toolbar;
import com.sdg.common.R;


public class BackLeftToolbar extends Toolbar {

    private String mTitle;
    private Activity activity;
    private  TextView title;
    private  TextView rightTitle;
    public BackLeftToolbar(Activity activity, String title) {
        super(activity);
        this.activity = activity;
        this.mTitle = title;
    }


    @Override
    public int layoutRes() {
        return R.layout.toolbar_back_left;
    }

    @Override
    public void onViewCreated( View view) {
        title = view.findViewById(R.id.title);
        rightTitle = view.findViewById(R.id.rightTitle);
        ImageView back = view.findViewById(R.id.back);
        title.setText(mTitle);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(myOnItemListener!=null){
                    myOnItemListener.onClick(view);
                }else {
                    activity.finish();
                }
            }
        });
        rightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myOnItemListener!=null){
                    myOnItemListener.onClick(v);
                }
            }
        });
    }
    public TextView getRightTitle(){
        if(rightTitle!=null){
            return rightTitle;
        }
        return null;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public void setViewTitle(String mTitle) {
        if(title!=null) {
            title.setText(mTitle);
        }
    }
    public void setRightTitle(String mTitle) {
        if(rightTitle!=null) {
            rightTitle.setText(mTitle);
        }
    }
    public void setRightVisible(boolean visible){
        if(rightTitle!=null){
            if(visible){
                rightTitle.setVisibility(View.VISIBLE);
            }else {
                rightTitle.setVisibility(View.GONE);
            }
        }
    }
    public void setMyOnClickListener(MyOnClickListener myOnItemListener) {
        this.myOnItemListener = myOnItemListener;
    }

    private MyOnClickListener myOnItemListener;

    public interface MyOnClickListener {
        void onClick(View view);
    }
}
