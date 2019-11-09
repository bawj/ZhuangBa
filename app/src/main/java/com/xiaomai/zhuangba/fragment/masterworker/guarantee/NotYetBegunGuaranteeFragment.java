package com.xiaomai.zhuangba.fragment.masterworker.guarantee;

import android.os.Bundle;

/**
 * @author Administrator
 * @date 2019/11/9 0009
 * 未开始 维保单
 */
public class NotYetBegunGuaranteeFragment extends BaseGuaranteeDetailFragment{

    public static NotYetBegunGuaranteeFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODE, orderCode);
        args.putString(ORDER_TYPE, orderType);
        NotYetBegunGuaranteeFragment fragment = new NotYetBegunGuaranteeFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
