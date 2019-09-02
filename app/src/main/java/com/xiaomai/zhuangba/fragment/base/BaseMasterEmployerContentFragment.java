package com.xiaomai.zhuangba.fragment.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
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
        BaseQuickAdapter.OnItemClickListener, Observer, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.rvBaseList)
    RecyclerView rvBaseList;

    private int page = StaticExplain.PAGE_NUMBER.getCode();
    public Handler handler;
    public BaseQuickAdapter orderListAdapter;

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
        Log.d("initView 执行");
        rvBaseList.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderListAdapter = getBaseOrderAdapter();
        if (orderListAdapter != null) {
            orderListAdapter.setEmptyView(getEmptyView(), rvBaseList);
            orderListAdapter.setOnItemClickListener(this);
            rvBaseList.setAdapter(orderListAdapter);
            orderListAdapter.setOnLoadMoreListener(this, rvBaseList);
        }
        statusBarWhite();
    }

    @Override
    public void update(String message, String address, Handler handler) {
        Log.d("update 执行");
        this.handler = handler;
        page = 1;
        //刷新时 禁止上拉加载
        if (orderListAdapter != null) {
            orderListAdapter.setEnableLoadMore(false);
        }
    }

    @Override
    public void refreshSuccess(List<OngoingOrdersList> ordersLists) {
        //刷新成功
        stopRefresh();
        if (orderListAdapter != null) {
            orderListAdapter.setNewData(ordersLists);
            //刷新完成 可以上拉加载
            orderListAdapter.setEnableLoadMore(true);
        }
    }

    @Override
    public void onLoadMoreRequested() {
        //上拉加载
        page++;
        Log.d("onLoadMoreRequested 上拉加载" + page);
        onBaseLoadMoreRequested();
    }

    @Override
    public void refreshError() {
        stopRefresh();
        //停止加载
        stopLoad();
    }

    @Override
    public void loadMoreEnd() {
        if (orderListAdapter != null) {
            orderListAdapter.loadMoreEnd();
        }
    }

    @Override
    public void loadMoreSuccess(List<OngoingOrdersList> ongoingOrdersLists) {
        if (orderListAdapter != null) {
            orderListAdapter.addData(ongoingOrdersLists);
        }
    }

    public int getEmptyView() {
        return R.layout.item_not_data;
    }

    @Override
    public void loadMoreComplete() {
        if (orderListAdapter != null) {
            orderListAdapter.loadMoreComplete();
        }
    }

    public BaseQuickAdapter getBaseOrderAdapter() {
        return null;
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
        loadMoreComplete();
    }

    public void onMItemClick(View view, int position) {
        //item 点击事件
    }

    public void onBaseLoadMoreRequested() {
        //上拉加载
        Log.e("onLoadMoreRequested 上拉加载");
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
    public String getStatus() {
        return null;
    }

    @Override
    public void workingStateSwitchingSuccess() {

    }

    @Override
    public String getAddress() {
        return null;
    }

    @Override
    public void refreshAdvertisingSuccess(List<AdvertisingBillsBean> advertisingBillsBeans) {
        //刷新成功
        stopRefresh();
    }

    @Override
    public void loadMoreAdvertisingSuccess(List<AdvertisingBillsBean> advertisingBillsBeans) {

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
