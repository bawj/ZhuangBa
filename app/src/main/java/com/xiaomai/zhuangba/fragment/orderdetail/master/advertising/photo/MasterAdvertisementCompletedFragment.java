package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo;

import android.os.Bundle;

import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.MasterAdvertisementAcceptanceOrdersFragment;

/**
 * Author: Bawj
 * CreateDate: 2019/12/17 0017 09:46
 * 广告单已完成
 *
 */
public class MasterAdvertisementCompletedFragment extends BaseAdvertisingBillDetailFragment {

    public static MasterAdvertisementAcceptanceOrdersFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        MasterAdvertisementAcceptanceOrdersFragment fragment = new MasterAdvertisementAcceptanceOrdersFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
