package com.xiaomai.zhuangba.fragment.advertisement.employer;

import android.os.Bundle;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.advertisement.base.BaseAdvertisementFragment;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;

import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/27 0027
 * <p>
 * 雇主广告单详情 分配中
 */
public class EmployerAdvertisementDistributionFragment extends BaseAdvertisementFragment {

    public static EmployerAdvertisementDistributionFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        EmployerAdvertisementDistributionFragment fragment = new EmployerAdvertisementDistributionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_advertisement_distribution;
    }

    @OnClick(R.id.btnCancelTask)
    public void onViewClicked() {
        CommonlyDialog.getInstance().initView(getActivity())
                .setTvDialogCommonlyContent(getString(R.string.cancel_order_employer_msg))
                .setICallBase(new CommonlyDialog.BaseCallback() {
                    @Override
                    public void sure() {
                        cancelTask();
                    }
                }).showDialog();

    }

    private void cancelTask() {
        RxUtils.getObservable(ServiceUrl.getUserApi().cancelAdvertisingOrderOrder(getOrderCode()))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        startFragment(EmployerFragment.newInstance());
                    }
                });
    }
}
