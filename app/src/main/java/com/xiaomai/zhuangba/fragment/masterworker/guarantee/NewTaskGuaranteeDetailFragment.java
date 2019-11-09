package com.xiaomai.zhuangba.fragment.masterworker.guarantee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.GuaranteeDeatil;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.http.ServiceUrl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/11/9 0009
 * 新任务
 */
public class NewTaskGuaranteeDetailFragment extends BaseGuaranteeDetailFragment {

    @BindView(R.id.relGuaranteeBottom)
    RelativeLayout relGuaranteeBottom;

    public static NewTaskGuaranteeDetailFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODE, orderCode);
        args.putString(ORDER_TYPE, orderType);
        NewTaskGuaranteeDetailFragment fragment = new NewTaskGuaranteeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_new_task_guarantee_detail;
    }

    @Override
    public void initView() {
        super.initView();
        relGuaranteeBottom.setVisibility(View.GONE);
    }

    @Override
    public void setOrderInfo(GuaranteeDeatil guaranteeDeatil) {
        relGuaranteeBottom.setVisibility(View.VISIBLE);
        super.setOrderInfo(guaranteeDeatil);
    }

    @OnClick({R.id.btnCancelTask, R.id.btnReceiveTask})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancelTask:
                //取消任务
                RxUtils.getObservable(ServiceUrl.getUserApi().masterCancelAdvertisingMaintenanceOrder(getOrderCode()))
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                            @Override
                            protected void onSuccess(Object response) {
                                setFragmentResult(ForResultCode.START_FOR_RESULT_CODE.getCode() , new Intent());
                                popBackStack();
                            }
                        });
                break;
            case R.id.btnReceiveTask:
                //接任务
                RxUtils.getObservable(ServiceUrl.getUserApi().acceptAdvertisingMaintenanceOrder(getOrderCode()))
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
