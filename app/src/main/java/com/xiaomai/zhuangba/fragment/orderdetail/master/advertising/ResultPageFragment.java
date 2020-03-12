package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising;

import android.os.Bundle;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AdOrderInformation;
import com.xiaomai.zhuangba.fragment.orderdetail.master.installation.CompleteFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;
import com.xiaomai.zhuangba.util.ConstantUtil;

/**
 * @author Bawj
 * CreateDate:     2020/3/12 0012 15:28
 */
public class ResultPageFragment extends CompleteFragment {

    public static ResultPageFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        ResultPageFragment fragment = new ResultPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void startOrderDetail() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleAdEquipmentInformation(getOrderCode()))
                .compose(this.<HttpResult<AdOrderInformation>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<AdOrderInformation>(getActivity()) {
                    @Override
                    protected void onSuccess(AdOrderInformation adOrderInformationList) {
                        String orderCodes = adOrderInformationList.getOrderCodes();
                        int orderStatus = adOrderInformationList.getOrderStatus();
                        AdvertisingStatusUtil.startMasterOrderDetail(getBaseFragmentActivity(), orderCodes, orderStatus);
                    }
                });
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.submit_success);
    }

}
