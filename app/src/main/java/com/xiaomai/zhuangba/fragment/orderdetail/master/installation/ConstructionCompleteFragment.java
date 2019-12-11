package com.xiaomai.zhuangba.fragment.orderdetail.master.installation;

import android.os.Bundle;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.util.ConstantUtil;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 */
public class ConstructionCompleteFragment extends CompleteFragment {

    public static ConstructionCompleteFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        ConstructionCompleteFragment fragment = new ConstructionCompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void startOrderDetail() {
        //到 施工中
        startFragment(BeUnderConstructionFragment.newInstance(getOrderCode(), getOrderType()));
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
