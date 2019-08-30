package com.xiaomai.zhuangba.fragment.masterworker;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.util.Log;
import com.qmuiteam.qmui.arch.QMUIFragmentActivity;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.NeedDealWithAdapter;
import com.xiaomai.zhuangba.adapter.OrderListAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.OrdersEnum;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.ProcessedDetailFragment;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;
import com.xiaomai.zhuangba.util.OrderStatusUtil;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 *
 * 安装单
 */
public class NeedDealWithFragment extends BaseMasterEmployerContentFragment {

    public static NeedDealWithFragment newInstance() {
        Bundle args = new Bundle();
        NeedDealWithFragment fragment = new NeedDealWithFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_need_deal_with;
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
    public void update(String code ,String address, Handler handler) {
        super.update(code ,address, handler);
        if (StringTypeExplain.REFRESH_NEED_DEAL_WITH_FRAGMENT.getCode().equals(code)){
            iModule.requestOngoingOrders();
        }
    }

    @Override
    public void onMItemClick(View view, int position) {
        OngoingOrdersList ongoingOrdersList = (OngoingOrdersList) view.findViewById(R.id.tvItemOrdersTitle).getTag();
        String orderType = ongoingOrdersList.getOrderType();
        if (orderType.equals(String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()))){
            OrderStatusUtil.startMasterOrderDetail(getBaseFragmentActivity() , ongoingOrdersList.getOrderCode() , ongoingOrdersList.getOrderType(),
                    ongoingOrdersList.getOrderStatus() , ongoingOrdersList.getExpireTime());
        }else if (orderType.equals(String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()))){
            AdvertisingStatusUtil.startMasterOrderDetail(getBaseFragmentActivity() ,ongoingOrdersList.getOrderCode() , ongoingOrdersList.getOrderType(),
                    ongoingOrdersList.getOrderStatus() );
        }
    }

    @Override
    public void onBaseLoadMoreRequested() {
        Log.e("NeedDealWithFragment 上拉加载" );
        iModule.requestOngoingOrders();
    }

    @Override
    public BaseQuickAdapter getBaseOrderAdapter() {
        return new NeedDealWithAdapter();
    }

    @Override
    public void refreshSuccess(List<OngoingOrdersList> ordersLists) {
        super.refreshSuccess(ordersLists);

    }
}
