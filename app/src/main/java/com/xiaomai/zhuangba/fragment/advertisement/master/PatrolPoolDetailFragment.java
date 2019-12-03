package com.xiaomai.zhuangba.fragment.advertisement.master;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderServiceDate;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.base.BasePatrolDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.MapUtils;

import butterknife.BindView;
import butterknife.OnClick;

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
    public int getContentView() {
        return R.layout.fragment_base_patrol_pool_detail;
    }

    @OnClick({R.id.relBaseOrderDetailLocation})
    public void onViewBaseClicked(View view) {
        switch (view.getId()) {
            case R.id.relBaseOrderDetailLocation:
                break;
            default:
        }
    }

    @Override
    public void requestOrderDetailSuccess(OrderServiceDate orderServiceDate) {
        super.requestOrderDetailSuccess(orderServiceDate);
        tvBasePatrolOrdersType.setVisibility(View.GONE);
    }
}
