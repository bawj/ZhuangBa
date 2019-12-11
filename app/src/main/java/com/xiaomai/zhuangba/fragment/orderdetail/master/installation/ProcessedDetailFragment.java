package com.xiaomai.zhuangba.fragment.orderdetail.master.installation;

import android.os.Bundle;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseMasterOrderDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

/**
 * @author Administrator
 * @date 2019/7/16 0016
 * 待处理
 */
public class ProcessedDetailFragment extends BaseMasterOrderDetailFragment {

    public static ProcessedDetailFragment newInstance(String code) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, code);
        ProcessedDetailFragment fragment = new ProcessedDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_processed_detail;
    }

    @Override
    public String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }


}
