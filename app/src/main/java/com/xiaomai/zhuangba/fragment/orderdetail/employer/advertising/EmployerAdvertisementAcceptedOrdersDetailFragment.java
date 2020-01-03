package com.xiaomai.zhuangba.fragment.orderdetail.employer.advertising;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AdOrderInformation;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;
import com.xiaomai.zhuangba.data.bean.MessageEvent;
import com.xiaomai.zhuangba.enums.AdvertisingEnum;
import com.xiaomai.zhuangba.enums.EventBusEnum;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.MasterAdvertisementReceivedOrdersFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.dialog.CompensateDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Bawj
 * CreateDate: 2019/12/17 0017 10:32
 * 广告单 已接单
 */
public class EmployerAdvertisementAcceptedOrdersDetailFragment extends BaseAdvertisingBillDetailFragment {

    @BindView(R.id.ivEmployerDetailMasterHeader)
    ImageView ivEmployerDetailMasterHeader;
    @BindView(R.id.tvEmployerDetailMasterName)
    TextView tvEmployerDetailMasterName;
    @BindView(R.id.relNewTaskOrderDetailBottom)
    RelativeLayout relNewTaskOrderDetailBottom;

    private List<DeviceSurfaceInformation> list;

    public static EmployerAdvertisementAcceptedOrdersDetailFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        EmployerAdvertisementAcceptedOrdersDetailFragment fragment = new EmployerAdvertisementAcceptedOrdersDetailFragment();
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
        list = adOrderInformationList.getList();
        relNewTaskOrderDetailBottom.setVisibility(View.VISIBLE);
        //师傅名称
        String userText = adOrderInformationList.getUserText();
        tvEmployerDetailMasterName.setText(userText);
        String bareheadedPhotoUrl = adOrderInformationList.getBareheadedPhotoUrl();
        GlideManager.loadCircleImage(getActivity(), bareheadedPhotoUrl, ivEmployerDetailMasterHeader, R.drawable.bg_def_head);
    }

    @OnClick({R.id.layCompensate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layCompensate:
                //发起索赔
                new CompensateDialog<EmployerAdvertisementAcceptedOrdersDetailFragment>()
                        .initView(getActivity(), this)
                        .setCompensate(getActivity(), list)
                        .showDialog();
                break;
            default:
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_advertisement_acceted_orders_detail;
    }
}
