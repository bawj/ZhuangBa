package com.xiaomai.zhuangba.fragment.masterworker.inspection;

import android.os.Bundle;

import com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.PatrolInDistributionDetailFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.base.BasePatrolDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

/**
 * @author Administrator
 * @date 2019/10/8 0008
 */
public class PatrolHaveHandDetailFragment extends BasePatrolDetailFragment {

    public static PatrolHaveHandDetailFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        PatrolHaveHandDetailFragment fragment = new PatrolHaveHandDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
