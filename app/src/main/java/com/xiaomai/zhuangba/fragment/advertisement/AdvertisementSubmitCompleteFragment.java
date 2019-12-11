package com.xiaomai.zhuangba.fragment.advertisement;

import android.os.Bundle;

import com.xiaomai.zhuangba.fragment.advertisement.master.MasterAdvertisementAcceptanceFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.installation.NewSubmitCompleteFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

/**
 * @author Administrator
 * @date 2019/9/4 0004
 */
public class AdvertisementSubmitCompleteFragment extends NewSubmitCompleteFragment {

    public static AdvertisementSubmitCompleteFragment newInstance(String orderCode , String orderType , String orderStatus) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE , orderCode);
        args.putString(ConstantUtil.ORDER_TYPE , orderType);
        args.putString(ConstantUtil.ORDER_STATUS , orderStatus);
        AdvertisementSubmitCompleteFragment fragment = new AdvertisementSubmitCompleteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void startOrderDetail() {
        startFragment(MasterAdvertisementAcceptanceFragment.newInstance(getOrderCode(), getOrderType()));
    }

}
