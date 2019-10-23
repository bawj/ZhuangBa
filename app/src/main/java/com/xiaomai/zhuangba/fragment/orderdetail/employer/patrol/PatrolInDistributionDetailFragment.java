package com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.base.BasePatrolDetailFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
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
                RxUtils.getObservable(ServiceUrl.getUserApi().cancelInspectionOrder(getOrderCode()))
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                            @Override
                            protected void onSuccess(Object response) {
                                setFragmentResult(ForResultCode.START_FOR_RESULT_CODE.getCode() , new Intent());
                                popBackStack();
                            }
                            @Override
                            public void onError(ApiException apiException) {
                                super.onError(apiException);
                                if (apiException.getStatus() == StaticExplain.REFUND.getCode()){
                                    setFragmentResult(ForResultCode.START_FOR_RESULT_CODE.getCode() , new Intent());
                                    popBackStack();
                                }
                            }
                        });
                break;
            default:
        }
    }
}
