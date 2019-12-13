package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AdOrderInformation;
import com.xiaomai.zhuangba.data.bean.MessageEvent;
import com.xiaomai.zhuangba.enums.EventBusEnum;
import com.xiaomai.zhuangba.http.ServiceUrl;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class MasterAdvertisementNewTaskFragment extends BaseAdvertisingBillDetailFragment {

    @BindView(R.id.relNewTaskOrderDetailBottom)
    RelativeLayout relNewTaskOrderDetailBottom;

    public static MasterAdvertisementNewTaskFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        MasterAdvertisementNewTaskFragment fragment = new MasterAdvertisementNewTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        relNewTaskOrderDetailBottom.setVisibility(View.GONE);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_advertisement_new_task;
    }

    @Override
    public void setViewData(AdOrderInformation adOrderInformationList) {
        super.setViewData(adOrderInformationList);
        relNewTaskOrderDetailBottom.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.btnCancelAdvertisementOrder, R.id.btnAcceptanceReceiptAdvertisement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancelAdvertisementOrder:
                //取消订单
                RxUtils.getObservable(ServiceUrl.getUserApi().adOrderCancelAdOrder(getOrderCodes()))
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<Object>() {
                            @Override
                            protected void onSuccess(Object response) {

                            }
                        });
                break;
            case R.id.btnAcceptanceReceiptAdvertisement:
                //接单
                final String orderCodes = getOrderCodes();
                RxUtils.getObservable(ServiceUrl.getUserApi().adOrderAcceptOrder(orderCodes))
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                            @Override
                            protected void onSuccess(Object response) {
                                //通知列表刷新
                                EventBus.getDefault().post(new MessageEvent(EventBusEnum.ALLOCATION_LIST_REFRESH.getCode()));
                                startFragmentAndDestroyCurrent(MasterAdvertisementReceivedOrdersFragment.newInstance(orderCodes));
                            }
                        });
                break;
            default:
        }
    }
}
