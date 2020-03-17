package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising;

import android.os.Bundle;

import com.xiaomai.zhuangba.R;

/**
 * Author: Bawj
 * CreateDate: 2019/12/16 0016 21:06
 * 验收中
 */
public class MasterAdvertisementAcceptanceOrdersFragment extends BaseAdvertisingBillDetailFragment{


    public static MasterAdvertisementAcceptanceOrdersFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        MasterAdvertisementAcceptanceOrdersFragment fragment = new MasterAdvertisementAcceptanceOrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_advertisement_acceptance_orders;
    }
}
