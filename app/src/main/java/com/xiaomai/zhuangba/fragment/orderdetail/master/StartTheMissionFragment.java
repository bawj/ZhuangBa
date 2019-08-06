package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderServiceDate;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseMasterOrderDetailFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 已接单  或 确认时间
 */
public class StartTheMissionFragment extends BaseMasterOrderDetailFragment {

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshBaseList;
    @BindView(R.id.btnConfirmationTime)
    QMUIButton btnConfirmationTime;
    @BindView(R.id.tvBaseOrderConfirmationTime)
    TextView tvBaseOrderConfirmationTime;
    @BindView(R.id.tvBaseOrderConfirmationTime_)
    TextView tvBaseOrderConfirmationTime_;
    private OngoingOrdersList ongoingOrdersList;

    public static StartTheMissionFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        StartTheMissionFragment fragment = new StartTheMissionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_start_the_mission;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }


    @OnClick({R.id.btnNewTaskCancelTheOrder, R.id.btnConfirmationTime})
    public void onViewStartTheMissionClicked(View view) {
        switch (view.getId()) {
            case R.id.btnNewTaskCancelTheOrder:
                //取消任务
                CommonlyDialog.getInstance().initView(getActivity())
                        .setTvDialogCommonlyContent(getString(R.string.cancel_order_tip))
                        .setICallBase(new CommonlyDialog.BaseCallback() {
                            @Override
                            public void sure() {
                                iModule.requestCancelOrder();
                            }
                        }).showDialog();
                break;
            case R.id.btnConfirmationTime:
                if (TextUtils.isEmpty(ongoingOrdersList.getConfirmationTime())) {
                    //确认时间
                    startFragmentForResult(ConfirmationTimeFragment.newInstance(ongoingOrdersList), ForResultCode.START_FOR_RESULT_CODE.getCode());
                } else {
                    //现在出发
                    requestWeLeaveNow();
                }
                break;
            default:
        }
    }

    private void requestWeLeaveNow() {
        Observable<HttpResult<Object>> httpResultObservable = ServiceUrl.getUserApi().nowWeLeave(ongoingOrdersList.getOrderCode());
        RxUtils.getObservable(httpResultObservable)
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        //现在出发
                        startFragment(MasterWorkerFragment.newInstance());
                    }
                });
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()) {
                if (refreshBaseList != null) {
                    refreshBaseList.autoRefresh();
                }
            }
        }
    }

    @Override
    public void requestOrderDetailSuccess(Object object) {
        super.requestOrderDetailSuccess(object);
        if (object != null) {
            OrderServiceDate orderServiceDate = (OrderServiceDate) object;
            ongoingOrdersList = orderServiceDate.getOngoingOrdersList();
            if (ongoingOrdersList != null) {
                String confirmationTime = ongoingOrdersList.getConfirmationTime();
                if (!TextUtils.isEmpty(confirmationTime)) {
                    //现在出发
                    btnConfirmationTime.setText(getString(R.string.we_leave_now));
                    tvBaseOrderConfirmationTime.setVisibility(View.VISIBLE);
                    tvBaseOrderConfirmationTime_.setVisibility(View.VISIBLE);
                    tvBaseOrderConfirmationTime_.setText(confirmationTime);
                } else {
                    //确认时间
                    btnConfirmationTime.setText(getString(R.string.confirmation_time));
                    tvBaseOrderConfirmationTime.setVisibility(View.GONE);
                    tvBaseOrderConfirmationTime_.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public boolean isInSwipeBack() {
        statusBarWhite();
        return super.isInSwipeBack();
    }
}
