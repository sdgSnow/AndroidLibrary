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
import io.objectbox.android.AndroidObjectBrowser;

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

        initOB();
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

    public void initOB(){
        ObjectBoxManager.init(this);
        boolean start = new AndroidObjectBrowser(ObjectBoxManager.mBoxStore).start(this);
        KLog.i("????????????????????????" + start);
    }

    @Override
    public Toolbar createToolbar() {
        return new BackMenuToolbar(this, "???????????????");
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
                KLog.i("????????????:" + getFormatMMSS(System.currentTimeMillis()));
                testBox.put(list);
                KLog.i("????????????:" + getFormatMMSS(System.currentTimeMillis()));
                break;
            case R.id.delete:
//                List<Test> removeList = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//                    removeList.add(list.get(i));
//                }
//                testBox.remove(removeList);
                KLog.i("????????????:" + getFormatMMSS(System.currentTimeMillis()));
                testBox.removeAll();
                KLog.i("????????????:" + getFormatMMSS(System.currentTimeMillis()));
                break;
            case R.id.update:
                Test test = list.get(0);
                test.name = "????????????name";
                testBox.put(test);
                break;
            case R.id.query:
                KLog.i("????????????:" + getFormatMMSS(System.currentTimeMillis()));
                List<Test> querylist = testBox.query().build().find();
                ToastUtils.showShort("?????????" + querylist.size() + "?????????");
                KLog.i("????????????:" + getFormatMMSS(System.currentTimeMillis()));
                break;
            case R.id.limit_query:
                int offset = 0;
                int limit = 10;
                List<Test> sumlist = new ArrayList<>();
                while (true){
                    List<Test> fylist = testBox.query().build().find(offset, limit);
                    sumlist.addAll(fylist);
                    offset = sumlist.size();
                    KLog.i("??????????????????????????????" + fylist.size() + ",offset = " + offset + ",?????????????????????" + sumlist.size());
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