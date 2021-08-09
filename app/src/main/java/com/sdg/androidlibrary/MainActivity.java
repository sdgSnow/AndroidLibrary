package com.sdg.androidlibrary;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dimeno.commons.toolbar.impl.Toolbar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.sdg.adapter.GridSpacesItemDecoration;
import com.sdg.adapter.interf.IEmpty;
import com.sdg.adapter.test.LibraryAdapter;
import com.sdg.adapter.test.LibraryBean;
import com.sdg.androidlibrary.adapter.AdapterActivity;
import com.sdg.androidlibrary.api.ApiActivity;
import com.sdg.androidlibrary.baidumap.BaiduMapActivity;
import com.sdg.androidlibrary.common.CommonActivity;
import com.sdg.androidlibrary.db.DbActivity;
import com.sdg.androidlibrary.dialog.DialogActivity;
import com.sdg.androidlibrary.download.DownloadActivity;
import com.sdg.androidlibrary.exception.ExceptionActivity;
import com.sdg.androidlibrary.oss.OssActivity;
import com.sdg.androidlibrary.permission.PermissionActivity;
import com.sdg.androidlibrary.record.RecordActivity;
import com.sdg.androidlibrary.regex.RegexActivity;
import com.sdg.androidlibrary.thread.ThreadActivity;
import com.sdg.androidlibrary.update.UpdateActivity;
import com.sdg.androidlibrary.utils.UtilsActivity;
import com.sdg.common.base.BaseActivity;
import com.sdg.common.base.BasePresenter;
import com.sdg.common.toolbar.TitleToolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView rcyLibrary;
    private List<LibraryBean> list;
    public SmartRefreshLayout refresh;
    private LibraryAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        refresh = findViewById(R.id.refresh);
        refresh.setPrimaryColorsId(R.color.color_f5f4f4, R.color.color_262626);
        refresh.setBackgroundColor(getResources().getColor(R.color.color_f5f4f4));
        refresh.setRefreshHeader(new ClassicsHeader(mContext));
        refresh.setRefreshFooter(new ClassicsFooter(mContext).setSpinnerStyle(SpinnerStyle.Scale));
        rcyLibrary = findViewById(R.id.rcyLibrary);
    }

    @Override
    public Toolbar createToolbar() {
        return new TitleToolbar(this,"库总结");
    }

    @Override
    protected void initData() {
        initRcyData();
        initRcy();
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refresh.setNoMoreData(false);//恢复没有更多数据的原始状态 1.0.5
                adapter.setNewData(list);
            }
        });
        refresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(final RefreshLayout refreshlayout) {
                adapter.setNewData(list);
            }
        });
    }

    private void initRcyData() {
        list = new ArrayList<>();
        list.add(new LibraryBean("adapter","适配器的使用",1));
        list.add(new LibraryBean("common","基础库的各基类",1));
        list.add(new LibraryBean("api","接口的封装",1));
        list.add(new LibraryBean("permission","权限的使用",0));
        list.add(new LibraryBean("record","录音的使用",0));
        list.add(new LibraryBean("update","升级的使用",0));
        list.add(new LibraryBean("dialog","弹出框的使用",0));
        list.add(new LibraryBean("utils","工具类的使用",0));
        list.add(new LibraryBean("baidumap","百度地图的使用",2));
        list.add(new LibraryBean("thread","线程的使用",0));
        list.add(new LibraryBean("exception","各异常的登记，跳转浏览器",0));
        list.add(new LibraryBean("regex","正则表达式",0));
        list.add(new LibraryBean("oss","oss上传",2));
        list.add(new LibraryBean("download","文件下载",0));
        list.add(new LibraryBean("objectbox","数据库使用",0));
    }

    private void initRcy() {
        List<LibraryBean> emptyList = new ArrayList<>();
        adapter = new LibraryAdapter(mContext,emptyList);
//        adapter.addHeaderView(LayoutInflater.from(mContext).inflate(R.layout.header, null, false));
        if(rcyLibrary.getItemDecorationCount() == 0) {
            rcyLibrary.addItemDecoration(new GridSpacesItemDecoration(this, 2, 10));
        }
        rcyLibrary.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
//        adapter.setEmptyView(LayoutInflater.from(mContext).inflate(R.layout.empty, null, false));
        adapter.setDefaultEmpty(new IEmpty() {
            @Override
            public void retry() {
                adapter.setNewData(list);
            }
        });
        rcyLibrary.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                switch (position){
                    case LibraryType.ADAPTER:
                        gotoActivity(AdapterActivity.class);
                        break;
                    case LibraryType.COMMON:
                        gotoActivity(CommonActivity.class);
                        break;
                    case LibraryType.API:
                        gotoActivity(ApiActivity.class);
                        break;
                    case LibraryType.PERMISSION:
                        gotoActivity(PermissionActivity.class);
                        break;
                    case LibraryType.RECORD:
                        gotoActivity(RecordActivity.class);
                        break;
                    case LibraryType.UPDATE:
                        gotoActivity(UpdateActivity.class);
                        break;
                    case LibraryType.DIALOG:
                        gotoActivity(DialogActivity.class);
                        break;
                    case LibraryType.UTILS:
                        gotoActivity(UtilsActivity.class);
                        break;
                    case LibraryType.BAIDUMAP:
                        gotoActivity(BaiduMapActivity.class);
                        break;
                    case LibraryType.THREAD:
                        gotoActivity(ThreadActivity.class);
                        break;
                    case LibraryType.EXCEPTION:
                        gotoActivity(ExceptionActivity.class);
                        break;
                    case LibraryType.REGEX:
                        gotoActivity(RegexActivity.class);
                        break;
                    case LibraryType.OSS:
                        gotoActivity(OssActivity.class);
                        break;
                    case LibraryType.DOWNLOAD:
                        gotoActivity(DownloadActivity.class);
                        break;
                    case LibraryType.DB:
                        gotoActivity(DbActivity.class);
                        break;
                }
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()){
                    case R.id.title:
                        ToastUtils.showShort(list.get(position).title);
                        break;
                    case R.id.desc:
                        ToastUtils.showShort(list.get(position).desc);
                        break;
                }
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}