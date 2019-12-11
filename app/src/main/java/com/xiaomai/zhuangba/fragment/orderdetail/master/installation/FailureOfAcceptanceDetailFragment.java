package com.xiaomai.zhuangba.fragment.orderdetail.master.installation;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 验收不通过
 */
public class FailureOfAcceptanceDetailFragment extends MasterCompleteFragment{

    @BindView(R.id.relNewTaskOrderDetailBottom)
    RelativeLayout relNewTaskOrderDetailBottom;

    public static FailureOfAcceptanceDetailFragment newInstance(String orderCode , String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        FailureOfAcceptanceDetailFragment fragment = new FailureOfAcceptanceDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        relNewTaskOrderDetailBottom.setVisibility(View.GONE);
    }

    @Override
    public void requestOrderDetailSuccess(Object object) {
        super.requestOrderDetailSuccess(object);
        relNewTaskOrderDetailBottom.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btnSubmitForAcceptance)
    public void onViewBeUnderConstructionClicked() {
        //提交验收
        startFragment(NewSubmitAcceptanceFragment.newInstance(getOrderCode() , getOrderType()));
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_failure_of_acceptance_detail;
    }
}
