package com.xiaomai.zhuangba.fragment.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.util.Log;
import com.liulishuo.filedownloader.i.IFileDownloadIPCCallback;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.module.base.IBaseListView;
import com.xiaomai.zhuangba.enums.StaticExplain;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class BaseListFragment<M extends IBaseModule, T extends BaseQuickAdapter> extends BaseFragment<M> implements OnRefreshListener, IBaseListView,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshLayout;

    @Nullable
    @BindView(R.id.rvBaseList)
    RecyclerView rvBaseList;

    /**
     * page 默认值
     */
    private int page = StaticExplain.PAGE_NUMBER.getCode();
    public T baseListAdapter;

    @Override
    public void initView() {
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(this);
        if (rvBaseList != null) {
            rvBaseList.setLayoutManager(new LinearLayoutManager(getActivity()));
            baseListAdapter = getBaseListAdapter();
            if (baseListAdapter != null) {
                rvBaseList.setAdapter(baseListAdapter);
                View notDataView = getEmptyView();
                baseListAdapter.setEmptyView(notDataView);
                baseListAdapter.setOnItemClickListener(this);
                baseListAdapter.setOnLoadMoreListener(this, rvBaseList);
            }
        }
        refresh();
    }

    public void refresh() {
        refreshLayout.autoRefresh();
    }

    public T getBaseListAdapter() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_list;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        //刷新
        page = 1;
        if (baseListAdapter != null) {
            baseListAdapter.setEnableLoadMore(false);
        }
        onBaseRefresh(refreshLayout);
    }

    public void onBaseRefresh(RefreshLayout refreshLayout) {
        //刷新
    }

    @Override
    public void onLoadMoreRequested() {
        //上拉加载
        page++;
        //加载时 不能刷新
        refreshLayout.setEnableRefresh(false);
        onBaseLoadMoreRequested();
    }

    public void onBaseLoadMoreRequested() {
        //加载更多
    }

    @Override
    public void finishRefresh() {
        //刷新完成 可以上拉加载
        if (baseListAdapter != null) {
            baseListAdapter.setEnableLoadMore(true);
        }
        refreshLayout.finishRefresh();
    }

    @Override
    public void loadError() {
        if (getPage() > StaticExplain.PAGE_NUMBER.getCode()) {
            //加载失败 page -1
            page--;
        }
        loadMoreEnd();
    }

    @Override
    public void loadMoreEnd() {
        //加载完成 可以刷新
        if (baseListAdapter != null) {
            baseListAdapter.loadMoreEnd();
        }
        refreshLayout.setEnableRefresh(true);
    }

    @Override
    public void loadMoreComplete() {
        if (baseListAdapter != null) {
            baseListAdapter.loadMoreComplete();
        }
        refreshLayout.setEnableRefresh(true);
    }

    @Override
    public int getPage() {
        return page;
    }

    /**
     * 默认空布局
     * @return layout
     */
    public View getEmptyView() {
        View view = getLayoutInflater().inflate(R.layout.item_not_data, (ViewGroup) rvBaseList.getParent(), false);
        ImageView ivNotData = view.findViewById(R.id.ivNotData);
        if (getIvNotDataBackground() != 0){
            ivNotData.setImageResource(getIvNotDataBackground());
        }
        TextView tvNotData = view.findViewById(R.id.tvNotData);
        String tvNotData1 = getTvNotData();
        if (!TextUtils.isEmpty(tvNotData1)){
            tvNotData.setText(tvNotData1);
        }
        return view;
    }

    public int getIvNotDataBackground(){
        return 0;
    }

    public String getTvNotData(){
        return "";
    }


    @Override
    protected M initModule() {
        return null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
