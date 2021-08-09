package com.sdg.androidlibrary.db;

import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.dimeno.commons.toolbar.impl.Toolbar;
import com.sdg.androidlibrary.R;
import com.sdg.common.base.BaseActivity;
import com.sdg.common.base.BasePresenter;
import com.sdg.common.toolbar.BackMenuToolbar;
import com.sdg.objectbox.ObjectBoxManager;
import com.sdg.objectbox.Test;
import com.socks.library.KLog;

import java.text.SimpleDateFormat;
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
        TextView objectbox = findViewById(R.id.objectbox);
        TextView litepal = findViewById(R.id.litepal);
        TextView room = findViewById(R.id.room);
        TextView add = findViewById(R.id.add);
        TextView delete = findViewById(R.id.delete);
        TextView update = findViewById(R.id.update);
        TextView query = findViewById(R.id.query);
        TextView limit_query = findViewById(R.id.limit_query);

        objectbox.setOnClickListener(this::onClick);
        litepal.setOnClickListener(this::onClick);
        room.setOnClickListener(this::onClick);
        add.setOnClickListener(this::onClick);
        delete.setOnClickListener(this::onClick);
        update.setOnClickListener(this::onClick);
        query.setOnClickListener(this::onClick);
        limit_query.setOnClickListener(this::onClick);
    }

    @Override
    protected void initData() {
        testBox = ObjectBoxManager.get().boxFor(Test.class);
        list = new ArrayList<>();
        for (int i = 0; i < 112; i++) {
            Test test = new Test();
            test.name = "name" + i;
            test.desc = "desc" + i;
            test.age = i;
            test.createTime = "createTime" + i;
            test.updateTime = "updateTime" + i;
            list.add(test);
        }
    }

    @Override
    public Toolbar createToolbar() {
        return new BackMenuToolbar(this, "数据库演示");
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.objectbox:

                break;
            case R.id.litepal:

                break;
            case R.id.room:

                break;
            case R.id.add:
                KLog.i("开始插入:" + getFormatMMSS(System.currentTimeMillis()));
                testBox.put(list);
                KLog.i("插入完成:" + getFormatMMSS(System.currentTimeMillis()));
                break;
            case R.id.delete:
//                List<Test> removeList = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//                    removeList.add(list.get(i));
//                }
//                testBox.remove(removeList);
                KLog.i("开始删除:" + getFormatMMSS(System.currentTimeMillis()));
                testBox.removeAll();
                KLog.i("删除完成:" + getFormatMMSS(System.currentTimeMillis()));
                break;
            case R.id.update:
                Test test = list.get(0);
                test.name = "修改数据name";
                testBox.put(test);
                break;
            case R.id.query:
                KLog.i("开始查询:" + getFormatMMSS(System.currentTimeMillis()));
                List<Test> querylist = testBox.query().build().find();
                ToastUtils.showShort("查询共" + querylist.size() + "条数据");
                KLog.i("查询完成:" + getFormatMMSS(System.currentTimeMillis()));
                break;
            case R.id.limit_query:
                int offset = 0;
                int limit = 10;
                List<Test> sumlist = new ArrayList<>();
                while (true){
                    List<Test> fylist = testBox.query().build().find(offset, limit);
                    sumlist.addAll(fylist);
                    offset = sumlist.size();
                    KLog.i("分页查询结果长度为：" + fylist.size() + ",offset = " + offset + ",查询总条数为：" + sumlist.size());
                    if(offset >= this.list.size()){
                        break;
                    }
                }
        }
    }

    public static String getFormatMMSS(Long needFromatTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        String formatTime = formatter.format(needFromatTime);
        return formatTime;
    }
}