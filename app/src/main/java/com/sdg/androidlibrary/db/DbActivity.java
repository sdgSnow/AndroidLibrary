package com.sdg.androidlibrary.db;

import android.view.View;
import android.widget.TextView;

import com.dimeno.commons.toolbar.impl.Toolbar;
import com.sdg.androidlibrary.R;
import com.sdg.common.base.BaseActivity;
import com.sdg.common.base.BasePresenter;
import com.sdg.common.toolbar.BackMenuToolbar;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;

public class DbActivity extends BaseActivity implements View.OnClickListener {

    private Box<Test> testBox;
    private List<Test> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_db;
    }

    @Override
    protected void initViews() {
        TextView bojectbox = findViewById(R.id.bojectbox);
        TextView litepal = findViewById(R.id.litepal);
        TextView room = findViewById(R.id.room);
        TextView add = findViewById(R.id.add);
        TextView delete = findViewById(R.id.delete);
        TextView update = findViewById(R.id.update);
        TextView query = findViewById(R.id.query);

        bojectbox.setOnClickListener(this::onClick);
        litepal.setOnClickListener(this::onClick);
        room.setOnClickListener(this::onClick);
        add.setOnClickListener(this::onClick);
        delete.setOnClickListener(this::onClick);
        update.setOnClickListener(this::onClick);
        query.setOnClickListener(this::onClick);
    }

    @Override
    protected void initData() {
        testBox = ObjectBoxManager.get().boxFor(Test.class);
        list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            Test test = new Test();
            test.name = "name" + i;
            test.desc = "desc" + i;
            test.createTime = "createTime" + i;
            test.updateTime = "updateTime" + i;
            list.add(test);
        }
    }

    @Override
    public Toolbar createToolbar() {
        return new BackMenuToolbar(this,"数据库演示");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bojectbox:

                break;
            case R.id.litepal:

                break;
            case R.id.room:

                break;
            case R.id.add:
                testBox.put(list);

                break;
            case R.id.delete:

                break;
            case R.id.update:

                break;
            case R.id.query:

                break;
        }
    }
}