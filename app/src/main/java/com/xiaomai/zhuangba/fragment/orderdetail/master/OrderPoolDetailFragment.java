package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseMasterOrderDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

/**
 * @author Administrator
 * @date 2019/8/3 0003
 *
 * 订单池详情
 */
public class OrderPoolDetailFragment extends BaseMasterOrderDetailFragment {

    public static OrderPoolDetailFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE , orderCode);
        OrderPoolDetailFragment fragment = new OrderPoolDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_master_order_detail;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        iModule.requestOrderPoolOrderDetail();
    }

    @Override
    public void startMap() {
        //不跳转 到地图
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

    @Override
    public void onDestroyView() {
        //恢复状态栏颜色
        statusBarWhite();
        super.onDestroyView();
    }
}
