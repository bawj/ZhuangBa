package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo;

import android.os.Bundle;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.MasterAdvertisementAcceptanceOrdersFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.MasterAdvertisementReceivedOrdersFragment;

/**
 * Author: Bawj
 * CreateDate: 2019/12/17 0017 10:02
 * 广告单验收不通过
 */
public class MasterAdvertisementNotPassFragment extends MasterAdvertisementReceivedOrdersFragment {

    public static MasterAdvertisementNotPassFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        MasterAdvertisementNotPassFragment fragment = new MasterAdvertisementNotPassFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_advertisement_not_pass;
    }

}
