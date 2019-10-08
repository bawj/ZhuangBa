package com.xiaomai.zhuangba.fragment.masterworker.inspection;

import android.os.Bundle;
import android.view.View;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.base.BasePatrolDetailFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/10/8 0008
 */
public class PatrolNewTaskDetailFragment extends BasePatrolDetailFragment {

    public static PatrolNewTaskDetailFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        PatrolNewTaskDetailFragment fragment = new PatrolNewTaskDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_patrol_new_task_detail;
    }

    @OnClick({R.id.btnPatrolCancelTask, R.id.btnPatrolTaskAcceptance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPatrolCancelTask:
                //取消订单
                break;
            case R.id.btnPatrolTaskAcceptance:
                // TODO: 2019/10/7 0007  接单

                break;
            default:
        }
    }
}
