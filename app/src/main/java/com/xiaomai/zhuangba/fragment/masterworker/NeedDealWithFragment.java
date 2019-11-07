package com.xiaomai.zhuangba.fragment.masterworker;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.NeedDealWithAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.Orders;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.OrderStatusUtil;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 * <p>
 * 安装单
 */
public class NeedDealWithFragment extends BaseListFragment<IBaseModule, NeedDealWithAdapter> {

    private NeedDealWithAdapter needDealWithAdapter;

    public static NeedDealWithFragment newInstance() {
        Bundle args = new Bundle();
        NeedDealWithFragment fragment = new NeedDealWithFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        requestOngoingOrders();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        requestOngoingOrders();
    }

    private void requestOngoingOrders() {
        //需处理
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleOrderList(String.valueOf(getPage())
                , String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(this.<HttpResult<Orders>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Orders>() {
                    @Override
                    protected void onSuccess(Orders orders) {
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            needDealWithAdapter.addData(orders.getList());
                        } else {
                            //刷新
                            needDealWithAdapter.setNewData(orders.getList());
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        OngoingOrdersList ongoingOrdersList = (OngoingOrdersList) view.findViewById(R.id.tvItemOrdersTitle).getTag();
        OrderStatusUtil.startMasterOrderDetail(getBaseFragmentActivity(), ongoingOrdersList.getOrderCode(), ongoingOrdersList.getOrderType(),
                ongoingOrdersList.getOrderStatus(), ongoingOrdersList.getExpireTime());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_need_deal_with;
    }

    @Override
    public NeedDealWithAdapter getBaseListAdapter() {
        needDealWithAdapter = new NeedDealWithAdapter();
        return needDealWithAdapter;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }
}
