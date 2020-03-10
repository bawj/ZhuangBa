package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.google.gson.Gson;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AdOrderInformation;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;
import com.xiaomai.zhuangba.data.bean.MessageEvent;
import com.xiaomai.zhuangba.enums.AdvertisingEnum;
import com.xiaomai.zhuangba.enums.EventBusEnum;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo.LastAdvertisementPhotoFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo.NextAdvertisementPhotoFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 广告单已接单
 */
public class MasterAdvertisementReceivedOrdersFragment extends BaseAdvertisingBillDetailFragment {

    @BindView(R.id.relNewTaskOrderDetailBottom)
    RelativeLayout relNewTaskOrderDetailBottom;
    /**
     * 可能是下刊拍照 也可能是上刊拍照
     */
    @BindView(R.id.btnPhotoNextOrLastAdvertisement)
    QMUIButton btnPhotoNextOrLastAdvertisement;
    /**
     * 1：下刊 2：上刊
     */
    private String operating;
    /**
     * 广告面
     */
    private List<DeviceSurfaceInformation> deviceSurfaceInformationList;
    private Integer serviceId;
    private String serviceSample;

    private List<DeviceSurfaceInformation> list;
    public static MasterAdvertisementReceivedOrdersFragment newInstance(String orderCodes) {
        Bundle args = new Bundle();
        args.putString(ORDER_CODES, orderCodes);
        MasterAdvertisementReceivedOrdersFragment fragment = new MasterAdvertisementReceivedOrdersFragment();
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
        return R.layout.fragment_master_received_orders;
    }

    @Override
    public void setViewData(AdOrderInformation adOrderInformationList) {
        super.setViewData(adOrderInformationList);
        list = adOrderInformationList.getList();
        relNewTaskOrderDetailBottom.setVisibility(View.VISIBLE);
        serviceId = adOrderInformationList.getServiceId();
        operating = adOrderInformationList.getOperating();
        serviceSample = adOrderInformationList.getServiceSample();
        deviceSurfaceInformationList = adOrderInformationList.getList();
        if (operating.equals(String.valueOf(AdvertisingEnum.MASTER_PENDING_DISPOSAL_NEXT_ISSUE.getCode()))) {
            //下刊
            btnPhotoNextOrLastAdvertisement.setText(getString(R.string.photo_taken_in_next_issue));
        } else if (operating.equals(String.valueOf(AdvertisingEnum.MASTER_PENDING_DISPOSAL_LAST_ISSUE.getCode()))) {
            //上刊
            btnPhotoNextOrLastAdvertisement.setText(getString(R.string.photo_taken_in_last_issue));
        }
    }

    @OnClick({R.id.btnCancelAdvertisementOrder, R.id.btnPhotoNextOrLastAdvertisement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancelAdvertisementOrder:
                //取消订单
                RxUtils.getObservable(ServiceUrl.getUserApi().masterCancelAdOrder(getOrderCodes()))
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                            @Override
                            protected void onSuccess(Object response) {
                                //刷新广告单列表
                                EventBus.getDefault().post(new MessageEvent(EventBusEnum.ALLOCATION_LIST_REFRESH.getCode()));
                                popBackStack();
                            }
                        });
                break;
            case R.id.btnPhotoNextOrLastAdvertisement:
                if (operating.equals(String.valueOf(AdvertisingEnum.MASTER_PENDING_DISPOSAL_NEXT_ISSUE.getCode()))) {
                    //下刊拍照
                    nextAdvertisement();
                } else if (operating.equals(String.valueOf(AdvertisingEnum.MASTER_PENDING_DISPOSAL_LAST_ISSUE.getCode()))) {
                    //上刊
                    lastAdvertisement();
                }
                break;
            default:
        }
    }

    /**
     * 下刊拍照
     */
    private void nextAdvertisement() {
        List<DeviceSurfaceInformation> deviceSurfaceInformationList = getNextDeviceSurfaceInformationList();
        startFragment(NextAdvertisementPhotoFragment.newInstance(getOrderCodes(),String.valueOf(serviceId), serviceSample, new Gson().toJson(deviceSurfaceInformationList)));
    }

    /**
     * 上刊拍照
     */
    private void lastAdvertisement() {
        List<DeviceSurfaceInformation> deviceSurfaceInformationList = getLastNextDeviceSurfaceInformationList();
        startFragment(LastAdvertisementPhotoFragment.newInstance(getOrderCodes(),String.valueOf(serviceId), serviceSample, new Gson().toJson(deviceSurfaceInformationList)));
    }


    /**
     * 返回下刊list
     *
     * @return list
     */
    private List<DeviceSurfaceInformation> getNextDeviceSurfaceInformationList() {
        List<DeviceSurfaceInformation> informationList = new ArrayList<>();
        for (DeviceSurfaceInformation deviceSurfaceInformation : deviceSurfaceInformationList) {
            //1.上下刊 3.下刊
            String type = deviceSurfaceInformation.getType();
            if (type.equals(String.valueOf(AdvertisingEnum.MASTER_PENDING_DISPOSAL_LAST_ISSUE_OR_NEXT_NOODLES.getCode())) ||
                    type.equals(String.valueOf(AdvertisingEnum.MASTER_PENDING_DISPOSAL_LAST_ISSUE_NOODLES.getCode()))) {
                //下刊
                informationList.add(deviceSurfaceInformation);
            }
        }
        return informationList;
    }


    /**
     * 返回上刊
     *
     * @return list
     */
    private List<DeviceSurfaceInformation> getLastNextDeviceSurfaceInformationList() {
        List<DeviceSurfaceInformation> informationList = new ArrayList<>();
        for (DeviceSurfaceInformation deviceSurfaceInformation : deviceSurfaceInformationList) {
            //1.上下刊；2.上刊；3.下刊
            String type = deviceSurfaceInformation.getType();
            if (type.equals(String.valueOf(AdvertisingEnum.MASTER_PENDING_DISPOSAL_LAST_ISSUE_OR_NEXT_NOODLES.getCode())) ||
                    type.equals(String.valueOf(AdvertisingEnum.MASTER_PENDING_DISPOSAL_NEXT_ISSUE_NOODLES.getCode()))) {
                //上刊
                informationList.add(deviceSurfaceInformation);
            }
        }
        return informationList;
    }

}
