package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo;


import android.os.Bundle;

import com.xiaomai.zhuangba.R;


/**
 * 下刊拍照
 */
public class NextAdvertisementPhotoFragment extends BaseAdvertisementPhotoFragment<NextAdvertisementPhotoTabFragment>{

    /**
     * @param serviceId serviceId
     * @param deviceSurfaceInformationListString 集合 所有面的数据
     */
    public static NextAdvertisementPhotoFragment newInstance(String serviceId , String deviceSurfaceInformationListString) {
        Bundle args = new Bundle();
        args.putString(DEVICE_SURFACE_INFORMATION_LIST_STRING, deviceSurfaceInformationListString);
        args.putString(PhotoStyleFragment.SERVICE_ID , serviceId);
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

    @Override
    public String getTitle() {
        return getString(R.string.photo_taken_in_next_issue);
    }
}
