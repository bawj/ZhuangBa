package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising;

import android.os.Bundle;

import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AdOrderInformation;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

public class BaseAdvertisingBillDetailFragment extends BaseListFragment {

    public static final String ORDER_CODES = "order_codes";

    public static BaseAdvertisingBillDetailFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        BaseAdvertisingBillDetailFragment fragment = new BaseAdvertisingBillDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        String orderCodes = getOrderCodes();
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleAdEquipmentInformation(orderCodes))
                .compose(this.<HttpResult<List<AdOrderInformation>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<AdOrderInformation>>() {
                    @Override
                    protected void onSuccess(List<AdOrderInformation> adOrderInformationList) {


                        finishRefresh();
                    }
                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        finishRefresh();
                    }
                });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_advertising_bill_detail;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_detail);
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    public String getOrderCodes() {
        if (getArguments() != null) {
            return getArguments().getString(ORDER_CODES);
        }
        return "";
    }

    @Override
    public boolean getEnableLoadMore() {
        return false;
    }
}
