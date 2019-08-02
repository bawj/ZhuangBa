package com.xiaomai.zhuangba.fragment.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.OrderListAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.data.bean.StatisticsData;
import com.xiaomai.zhuangba.data.module.masteremployer.IMasterEmployerModule;
import com.xiaomai.zhuangba.data.module.masteremployer.IMasterEmployerView;
import com.xiaomai.zhuangba.data.module.masteremployer.MasterEmployerModule;
import com.xiaomai.zhuangba.data.observable.Observer;
import com.xiaomai.zhuangba.enums.StaticExplain;

import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 * base
 * 首页 内容fragment
 */
public class BaseMasterEmployerContentFragment extends BaseFragment<IMasterEmployerModule> implements IMasterEmployerView,
        BaseQuickAdapter.OnItemClickListener, Observer {

    @BindView(R.id.rvBaseList)
    RecyclerView rvBaseList;

    private int page = 1;
    public Handler handler;
    private OrderListAdapter orderListAdapter;

    public static BaseMasterEmployerContentFragment newInstance() {
        Bundle args = new Bundle();
        BaseMasterEmployerContentFragment fragment = new BaseMasterEmployerContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IMasterEmployerModule initModule() {
        return new MasterEmployerModule();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_master_employer_content;
    }

    @Override
    public void initView() {
        rvBaseList.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderListAdapter = new OrderListAdapter();
        rvBaseList.setAdapter(orderListAdapter);
        orderListAdapter.setEmptyView(R.layout.item_not_data, rvBaseList);
        orderListAdapter.setOnItemClickListener(this);
        orderListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //上拉加载
                page++;
                onBaseLoadMoreRequested();
            }
        }, rvBaseList);
    }

    @Override
    public void update(String message, Handler handler) {
        this.handler = handler;
        page = 1;
        //刷新时 禁止上拉加载
        orderListAdapter.setEnableLoadMore(false);
    }

    @Override
    public void refreshSuccess(List<OngoingOrdersList> ordersLists) {
        //刷新成功
        stopRefresh();
        orderListAdapter.setNewData(ordersLists);
        //刷新完成 可以上拉加载
        orderListAdapter.setEnableLoadMore(true);
    }

    @Override
    public void refreshError() {
        stopRefresh();
        //停止加载
        stopLoad();
    }

    @Override
    public void loadMoreEnd() {
        orderListAdapter.loadMoreEnd();
    }

    @Override
    public void loadMoreSuccess(List<OngoingOrdersList> ongoingOrdersLists) {
        orderListAdapter.addData(ongoingOrdersLists);
    }

    @Override
    public void loadMoreComplete() {
        orderListAdapter.loadMoreComplete();
    }


    /**
     * 停止刷新
     */
    private void stopRefresh() {
        Message message = handler.obtainMessage();
        message.what = StaticExplain.STOP_REFRESH.getCode();
        handler.sendMessage(message);
    }

    /**
     * 停止加载
     */
    private void stopLoad() {
        if (getPage() > StaticExplain.PAGE_NUMBER.getCode()) {
            //加载失败 page -1
            page--;
        }
    }

    public void onMItemClick(View view, int position) {
        //item 点击事件
    }

    public void onBaseLoadMoreRequested() {
        //上拉加载
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        onMItemClick(view, position);
    }

    @Override
    public void orderStatisticsSuccess(OrderStatistics orderStatistics) {
        //订单首页统计
    }

    @Override
    public void statisticsSuccess(StatisticsData statisticsData) {

    }
    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler = null;
    }

}
