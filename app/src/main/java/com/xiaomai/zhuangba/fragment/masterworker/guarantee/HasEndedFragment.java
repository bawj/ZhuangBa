package com.xiaomai.zhuangba.fragment.masterworker.guarantee;

import android.os.Bundle;

/**
 * @author Bawj
 * CreateDate:     2020/3/10 0010 09:42
 * 维保单 已结束
 */
public class HasEndedFragment extends BaseGuaranteeDetailFragment{

    public static HasEndedFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODE, orderCode);
        args.putString(ORDER_TYPE, orderType);
        HasEndedFragment fragment = new HasEndedFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
