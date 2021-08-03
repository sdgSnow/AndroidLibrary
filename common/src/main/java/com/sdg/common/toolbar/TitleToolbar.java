package com.sdg.common.toolbar;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.dimeno.commons.toolbar.impl.Toolbar;
import com.sdg.common.R;

public class TitleToolbar extends Toolbar {

    private String mTitle;

    public TitleToolbar( Activity activity, String title) {
        super(activity);
        this.mTitle = title;
    }

    @Override
    public int layoutRes() {
        return R.layout.toolbar_title;
    }

    @Override
    public void onViewCreated( View view) {
        TextView title = view.findViewById(R.id.title);
        title.setText(mTitle);
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

}
