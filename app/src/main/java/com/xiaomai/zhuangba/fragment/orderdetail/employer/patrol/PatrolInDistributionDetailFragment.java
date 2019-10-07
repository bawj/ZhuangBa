package com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol;

import android.os.Bundle;
import android.view.View;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.base.BasePatrolDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/10/7 0007
 */
public class PatrolInDistributionDetailFragment extends BasePatrolDetailFragment {


    public static PatrolInDistributionDetailFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        PatrolInDistributionDetailFragment fragment = new PatrolInDistributionDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_patrol_indistribution_detail;
    }


    @OnClick({R.id.btnPatrolTask, R.id.relBaseOrderDetailLocation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relBaseOrderDetailLocation:
                //定位
                break;
            case R.id.btnPatrolTask:
                // TODO: 2019/10/7 0007  取消订单

                break;
            default:
        }
    }
}
