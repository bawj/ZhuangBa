package com.xiaomai.zhuangba.fragment.masterworker.guarantee;

import android.os.Bundle;

/**
 * @author Administrator
 * @date 2019/11/9 0009
 *
 * 维保单进行中
 */
public class HaveInHandGuaranteeFragment extends BaseGuaranteeDetailFragment{

    public static HaveInHandGuaranteeFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODE, orderCode);
        args.putString(ORDER_TYPE, orderType);
        HaveInHandGuaranteeFragment fragment = new HaveInHandGuaranteeFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
