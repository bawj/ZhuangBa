package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AdOrderInformation;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.installation.CompleteFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment.ORDER_CODES;

/**
 * Author: Bawj
 * CreateDate: 2019/12/16 0016 20:04
 * 提交成功
 */
public class AdvertisementPhotoSuccessFragment extends CompleteFragment {

    @BindView(R.id.ivComplete)
    ImageView ivComplete;
    @BindView(R.id.tvCompleteTip)
    TextView tvCompleteTip;

    public static AdvertisementPhotoSuccessFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        AdvertisementPhotoSuccessFragment fragment = new AdvertisementPhotoSuccessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    @OnClick({R.id.btnCompleteMissionDetails, R.id.btnCompleteBackHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCompleteMissionDetails:
                //查看已接单详情
                String orderCodes = getOrderCodes();
                RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleAdEquipmentInformation(orderCodes))
                        .compose(this.<HttpResult<AdOrderInformation>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<AdOrderInformation>(getActivity()) {
                            @Override
                            protected void onSuccess(AdOrderInformation adOrderInformationList) {
                                AdvertisingStatusUtil.startMasterOrderDetail(getBaseFragmentActivity()
                                        , adOrderInformationList.getOrderCodes(), adOrderInformationList.getOrderStatus());
                            }
                        });
                break;
            case R.id.btnCompleteBackHome:
                //首页
                startFragmentAndDestroyCurrent(MasterWorkerFragment.newInstance());
                break;
            default:
        }
    }

    public String getOrderCodes() {
        if (getArguments() != null) {
            return getArguments().getString(ORDER_CODES);
        }
        return "";
    }
}
