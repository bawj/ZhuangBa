package com.xiaomai.zhuangba.fragment.personal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.HistoricalAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.Orders;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;
import com.xiaomai.zhuangba.util.OrderStatusUtil;

import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 */
public class HistoricalOrderFragment extends BaseListFragment<IBaseModule, HistoricalAdapter> {

    private HistoricalAdapter historicalAdapter;

    public static HistoricalOrderFragment newInstance() {
        Bundle args = new Bundle();
        HistoricalOrderFragment fragment = new HistoricalOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(@NonNull RefreshLayout refreshLayout) {
        //刷新
        requestOrderList();
    }


    @Override
    public void onBaseLoadMoreRequested() {
        //加载
        requestOrderList();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        OngoingOrdersList ongoingOrdersList = (OngoingOrdersList)
                view.findViewById(R.id.tvItemHistoricalOrdersMoney).getTag();
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String orderType = ongoingOrdersList.getOrderType();
        if (unique.getRole().equals(String.valueOf(StaticExplain.EMPLOYER.getCode()))) {
            // 安装单
            if (ongoingOrdersList.getOrderType().equals(String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()))) {
                OrderStatusUtil.startEmployerOrderDetail(getBaseFragmentActivity(), ongoingOrdersList.getOrderCode()
                        , ongoingOrdersList.getOrderType(), ongoingOrdersList.getOrderStatus());
            } else if (orderType.equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))) {
                //广告单
                AdvertisingStatusUtil.startEmployerAdvertisingBills(getBaseFragmentActivity(), ongoingOrdersList.getOrderCode()
                        ,ongoingOrdersList.getOrderType(), ongoingOrdersList.getOrderStatus());
            }
            //师傅端
        } else if (unique.getRole().equals(String.valueOf(StaticExplain.FU_FU_SHI.getCode()))) {
            // 安装单
            if (orderType.equals(String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()))){
                OrderStatusUtil.startMasterOrderDetail(getBaseFragmentActivity() , ongoingOrdersList.getOrderCode() , ongoingOrdersList.getOrderType(),
                        ongoingOrdersList.getOrderStatus() , ongoingOrdersList.getExpireTime());
                //广告单
            }else if (orderType.equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))){
                AdvertisingStatusUtil.startMasterOrderDetail(getBaseFragmentActivity() ,ongoingOrdersList.getOrderCode() , ongoingOrdersList.getOrderType(),
                        ongoingOrdersList.getOrderStatus() );
            }
        }
    }

    private void requestOrderList() {
        Observable<HttpResult<Orders>> historyOrderList = ServiceUrl.getUserApi().getHistoryOrderList(String.valueOf(getPage()),
                String.valueOf(StaticExplain.PAGE_NUM.getCode()));
        RxUtils.getObservable(historyOrderList)
                .compose(this.<HttpResult<Orders>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Orders>() {
                    @Override
                    protected void onSuccess(Orders orders) {
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            historicalAdapter.addData(orders.getList());
                        } else {
                            //刷新
                            historicalAdapter.setNewData(orders.getList());
                            finishRefresh();
                        }
                        if (orders.getList().size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            loadMoreEnd();
                        } else {
                            //加载完成
                            loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        finishRefresh();
                        loadError();
                    }
                });
    }

    @Override
    public HistoricalAdapter getBaseListAdapter() {
        historicalAdapter = new HistoricalAdapter();
        return historicalAdapter;
    }

    @Override
    public int getEmptyView() {
        return R.layout.item_empty_view;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_historical_order;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.historical_order);
    }
}
