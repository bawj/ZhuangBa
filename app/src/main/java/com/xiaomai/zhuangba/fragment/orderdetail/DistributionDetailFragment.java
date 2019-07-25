package com.xiaomai.zhuangba.fragment.orderdetail;

import android.os.Bundle;

/**
 * @author Administrator
 * @date 2019/7/15 0015
 * 分配中
 */
public class DistributionDetailFragment extends BaseOrderDetailFragment {

    public static DistributionDetailFragment newInstance() {
        Bundle args = new Bundle();
        DistributionDetailFragment fragment = new DistributionDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
