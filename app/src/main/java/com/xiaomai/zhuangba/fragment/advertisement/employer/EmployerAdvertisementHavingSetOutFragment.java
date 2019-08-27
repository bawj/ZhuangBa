package com.xiaomai.zhuangba.fragment.advertisement.employer;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.fragment.advertisement.base.BaseAdvertisementFragment;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/27 0027
 *
 * 广告单已出发
 */
public class EmployerAdvertisementHavingSetOutFragment extends BaseAdvertisementFragment {

    @BindView(R.id.ivEmployerDetailMasterHeader)
    ImageView ivEmployerDetailMasterHeader;
    @BindView(R.id.tvEmployerDetailMasterName)
    TextView tvEmployerDetailMasterName;

    public static EmployerAdvertisementHavingSetOutFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        EmployerAdvertisementHavingSetOutFragment fragment = new EmployerAdvertisementHavingSetOutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_advertisement_accepted_orders;
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

    @Override
    public void setOngoingOrder(OngoingOrdersList ongoingOrdersList, List<OrderDateList> orderDateLists) {
        super.setOngoingOrder(ongoingOrdersList, orderDateLists);
        GlideManager.loadImage(getActivity() , ongoingOrdersList.getBareheadedPhotoUrl() , ivEmployerDetailMasterHeader , R.drawable.bg_def_head);
        tvEmployerDetailMasterName.setText(ongoingOrdersList.getUserText());
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
