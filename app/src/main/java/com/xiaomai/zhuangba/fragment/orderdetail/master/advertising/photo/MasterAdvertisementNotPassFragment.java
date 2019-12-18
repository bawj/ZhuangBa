package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo;

import android.os.Bundle;

import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.MasterAdvertisementAcceptanceOrdersFragment;

/**
 * Author: Bawj
 * CreateDate: 2019/12/17 0017 10:02
 * 广告单验收不通过
 */
public class MasterAdvertisementNotPassFragment extends BaseAdvertisingBillDetailFragment {
    public static MasterAdvertisementAcceptanceOrdersFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        MasterAdvertisementAcceptanceOrdersFragment fragment = new MasterAdvertisementAcceptanceOrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
