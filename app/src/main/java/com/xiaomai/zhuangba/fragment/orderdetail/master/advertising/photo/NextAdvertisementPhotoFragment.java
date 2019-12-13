package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo;


import android.os.Bundle;

/**
 * 下刊拍照
 */
public class NextAdvertisementPhotoFragment extends BaseAdvertisementPhotoFragment<NextAdvertisementPhotoTabFragment>{

    public static NextAdvertisementPhotoFragment newInstance(String deviceSurfaceInformationListString) {
        Bundle args = new Bundle();
        args.putString(DEVICE_SURFACE_INFORMATION_LIST_STRING, deviceSurfaceInformationListString);
        NextAdvertisementPhotoFragment fragment = new NextAdvertisementPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public NextAdvertisementPhotoTabFragment getBaseAdvertisementPhotoTabFragment(String deviceSurfaceInformationString) {
        return NextAdvertisementPhotoTabFragment.newInstance(getDeviceSurfaceInformationS() , deviceSurfaceInformationString);
    }

    public String getDeviceSurfaceInformationS() {
        if (getArguments() != null) {
            return getArguments().getString(DEVICE_SURFACE_INFORMATION_LIST_STRING);
        }
        return "";
    }
}
