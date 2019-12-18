package com.xiaomai.zhuangba.fragment.orderdetail.master.installation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.OrderPoolDetailAdapter;
import com.xiaomai.zhuangba.application.PretendApplication;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseMasterOrderDetailFragment;
import com.xiaomai.zhuangba.fragment.service.ServiceDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/3 0003
 *
 * 订单池详情
 */
public class OrderPoolDetailFragment extends BaseMasterOrderDetailFragment {

    public static OrderPoolDetailFragment newInstance(String orderCode , String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE , orderCode);
        args.putString(ConstantUtil.ORDER_TYPE , orderType);
        OrderPoolDetailFragment fragment = new OrderPoolDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_master_pool_order_detail;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        iModule.requestOrderPoolOrderDetail();
    }

    @Override
    public void orderServiceItemsSuccess(List<OrderServiceItem> orderServiceItems) {
        if (ongoingOrdersList != null) {
            OrderServiceItem orderServiceItem = new OrderServiceItem();
            orderServiceItem.setServiceText(getString(R.string.required_options));
            orderServiceItem.setSlottingStartLength(ongoingOrdersList.getSlottingStartLength());
            orderServiceItem.setSlottingEndLength(ongoingOrdersList.getSlottingEndLength());
            orderServiceItem.setDebugging(ongoingOrdersList.getDebugging());
            orderServiceItem.setMaterialsStartLength(ongoingOrdersList.getMaterialsStartLength());
            orderServiceItem.setMaterialsEndLength(ongoingOrdersList.getMaterialsEndLength());
            orderServiceItem.setAmount(ongoingOrdersList.getOrderAmount());
            double slottingPrice = ongoingOrdersList.getSlottingPrice();
            double debugPrice = ongoingOrdersList.getDebugPrice();
            double materialsPrice = ongoingOrdersList.getMaterialsPrice();
            orderServiceItem.setAmount((slottingPrice + debugPrice + materialsPrice));
            orderServiceItem.setIconUrl(PretendApplication.BASE_URL);
            orderServiceItems.add(0, orderServiceItem);
        }
        OrderPoolDetailAdapter orderPoolDetailAdapter = new OrderPoolDetailAdapter();
        recyclerServiceItems.setAdapter(orderPoolDetailAdapter);
        orderPoolDetailAdapter.setOnItemClickListener(this);
        //服务项目
        orderPoolDetailAdapter.setNewData(orderServiceItems);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        //服务项目 itemClick
        if (position != 0) {
            OrderServiceItem orderServiceItem = (OrderServiceItem) view.findViewById(R.id.tvItemServiceTotalMoney).getTag();
            startFragment(ServiceDetailFragment.newInstance(orderServiceItem.getServiceText(),
                    orderServiceItem.getServiceStandard(), orderServiceItem.getVideo(), orderServiceItem.getIconUrl()));
        }
    }

    @Override
    public void startMap() {
        //不跳转 到地图
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }
}
