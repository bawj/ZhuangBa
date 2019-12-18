package com.xiaomai.zhuangba.fragment.orderdetail.employer.advertising;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AdOrderInformation;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Bawj
 * CreateDate: 2019/12/17 0017 10:18
 * 雇主 广告 分配中
 */
public class EmployerAdvertisementDistributionDetailFragment extends BaseAdvertisingBillDetailFragment {

    @BindView(R.id.relNewTaskOrderDetailBottom)
    RelativeLayout relNewTaskOrderDetailBottom;

    public static EmployerAdvertisementDistributionDetailFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        EmployerAdvertisementDistributionDetailFragment fragment = new EmployerAdvertisementDistributionDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        relNewTaskOrderDetailBottom.setVisibility(View.GONE);
    }

    @Override
    public void setViewData(AdOrderInformation adOrderInformationList) {
        super.setViewData(adOrderInformationList);
        relNewTaskOrderDetailBottom.setVisibility(View.VISIBLE);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_advertisement_distribution_detail;
    }
    @OnClick({R.id.btnCancelOrder})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancelOrder:
                //取消订单
                RxUtils.getObservable(ServiceUrl.getUserApi().adOrderCancelAdOrder(getOrderCodes()))
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<Object>() {
                            @Override
                            protected void onSuccess(Object response) {

                            }
                        });
                break;
            default:
        }
    }
}
