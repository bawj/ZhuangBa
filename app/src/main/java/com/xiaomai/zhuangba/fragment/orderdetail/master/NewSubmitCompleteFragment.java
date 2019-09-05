package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.orderdetail.CompleteFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 */
public class NewSubmitCompleteFragment extends CompleteFragment {

    @BindView(R.id.tvCompleteTip)
    TextView tvCompleteTip;

    public static NewSubmitCompleteFragment newInstance(String orderCode , String orderType , String orderStatus) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE , orderCode);
        args.putString(ConstantUtil.ORDER_TYPE , orderType);
        args.putString(ConstantUtil.ORDER_STATUS , orderStatus);
        NewSubmitCompleteFragment fragment = new NewSubmitCompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        tvCompleteTip.setText(getString(R.string.application_for_acceptance_has_been_submitted));
    }

    @Override
    public void startOrderDetail() {
        //到 完成 详情
        startFragment(MasterCompleteFragment.newInstance(getOrderCode() , getOrderType()));
    }

    @Override
    public int getImageResource() {
        return R.drawable.bg_success;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.submit_successfully);
    }

}
