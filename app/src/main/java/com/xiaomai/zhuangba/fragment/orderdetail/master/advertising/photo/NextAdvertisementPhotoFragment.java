package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo;


import android.os.Bundle;

import com.xiaomai.zhuangba.R;

import static com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment.ORDER_CODES;


/**
 * 下刊拍照
 */
public class NextAdvertisementPhotoFragment extends BaseAdvertisementPhotoFragment<NextAdvertisementPhotoTabFragment> {

    /**
     * @param orderCodes                         订单编号
     * @param serviceId                          serviceId
     * @param serviceSample                      默认图
     * @param deviceSurfaceInformationListString 集合 所有面的数据
     * @return
     */
    public static NextAdvertisementPhotoFragment newInstance(String orderCodes,String serviceId, String serviceSample,String previousPublicatioList, String deviceSurfaceInformationListString) {
        Bundle args = new Bundle();
        args.putString(DEVICE_SURFACE_INFORMATION_LIST_STRING, deviceSurfaceInformationListString);
        args.putString(PREVIOUS_PUBLICATIO_LIST , previousPublicatioList);
        args.putString(PhotoStyleFragment.SERVICE_ID, serviceId);
        args.putString(SERVICE_SAMPLE, serviceSample);
        args.putString(ORDER_CODES , orderCodes);
        NextAdvertisementPhotoFragment fragment = new NextAdvertisementPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public NextAdvertisementPhotoTabFragment getBaseAdvertisementPhotoTabFragment(String deviceSurfaceInformationString) {
        return NextAdvertisementPhotoTabFragment.newInstance(getOrderCodes(),getServiceSample(),getDeviceSurfaceInformationS(),deviceSurfaceInformationString);
    }

    public String getDeviceSurfaceInformationS() {
        if (getArguments() != null) {
            return getArguments().getString(DEVICE_SURFACE_INFORMATION_LIST_STRING);
        }
        return "";
    }

    @Override
    public String getTitle() {
        return getString(R.string.photo_taken_in_next_issue);
    }
}
