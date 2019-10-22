package com.xiaomai.zhuangba.fragment.masterworker.inspection;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.base.BasePatrolDetailFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
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
                //巡查任务取消订单
                RxUtils.getObservable(ServiceUrl.getUserApi().masterCancelInspectionOrder(getOrderCode()))
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                            @Override
                            protected void onSuccess(Object response) {
                                setFragmentResult(ForResultCode.START_FOR_RESULT_CODE.getCode() , new Intent());
                                popBackStack();
                            }
                        });
                break;
            case R.id.btnPatrolTaskAcceptance:
                //巡查任务接单
                RxUtils.getObservable(ServiceUrl.getUserApi().acceptInspectionOrder(getOrderCode()))
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                            @Override
                            protected void onSuccess(Object response) {
                                setFragmentResult(ForResultCode.START_FOR_RESULT_CODE.getCode() , new Intent());
                                popBackStack();
                            }
                        });
                break;
            default:
        }
    }
}
