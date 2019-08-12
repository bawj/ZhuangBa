package com.xiaomai.zhuangba.fragment.employer;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.OngoingOrdersAdapter;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.util.OrderStatusUtil;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 * <p>
 * 进行中的订单 列表
 */
public class OngoingOrdersFragment extends BaseMasterEmployerContentFragment {

    public static OngoingOrdersFragment newInstance() {
        Bundle args = new Bundle();
        OngoingOrdersFragment fragment = new OngoingOrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void update(String code, String address, Handler handler) {
        super.update(code, address, handler);
        if (code.equals(StringTypeExplain.REFRESH_NEW_TASK_FRAGMENT.getCode())) {
            iModule.employerOrderList();
        }
    }

    @Override
    public void onMItemClick(View view, int position) {
        super.onMItemClick(view, position);
        //进入 订单详情
        OngoingOrdersList ongoingOrdersList = (OngoingOrdersList) view.findViewById(R.id.tvItemOrdersTitle).getTag();
        OrderStatusUtil.startEmployerOrderDetail(getBaseFragmentActivity(), ongoingOrdersList.getOrderCode(),
                ongoingOrdersList.getOrderStatus());
    }

    @Override
    public void refreshSuccess(List<OngoingOrdersList> ordersLists) {
        super.refreshSuccess(ordersLists);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_ongoing_orders;
    }

    @Override
    public BaseQuickAdapter getBaseOrderAdapter() {
        return new OngoingOrdersAdapter();
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        statusBarWhite();
    }
}
