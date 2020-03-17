package com.xiaomai.zhuangba.fragment.orderdetail.employer.advertising;

import android.os.Bundle;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AdOrderInformation;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment;

/**
 * Author: Bawj
 * CreateDate: 2019/12/17 0017 10:43
 * 雇主 广告 已取消
 */
public class EmployerAdvertisementCancelledDetailFragment extends BaseAdvertisingBillDetailFragment {

    public static EmployerAdvertisementCancelledDetailFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        EmployerAdvertisementCancelledDetailFragment fragment = new EmployerAdvertisementCancelledDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setViewData(AdOrderInformation adOrderInformationList) {
        super.setViewData(adOrderInformationList);
        tvBaseAdvertisementMoney.setText(getString(R.string.content_money, String.valueOf(adOrderInformationList.getOrderAmount())));
    }
}
