package com.xiaomai.zhuangba.fragment.advertisement.master;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderServiceDate;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.base.BasePatrolDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/10/24 0024
 *
 * 巡查任务 订单池详情
 */
public class PatrolPoolDetailFragment extends BasePatrolDetailFragment {

    @BindView(R.id.tvBasePatrolOrdersType)
    TextView tvBasePatrolOrdersType;

    public static PatrolPoolDetailFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        PatrolPoolDetailFragment fragment = new PatrolPoolDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void requestOrderDetailSuccess(OrderServiceDate orderServiceDate) {
        super.requestOrderDetailSuccess(orderServiceDate);
        tvBasePatrolOrdersType.setVisibility(View.GONE);
    }
}
