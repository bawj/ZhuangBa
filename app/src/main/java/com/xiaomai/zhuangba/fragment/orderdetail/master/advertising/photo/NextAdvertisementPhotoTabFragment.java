package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.BaseAdvertisementPhotoTabEntity;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;
import com.xiaomai.zhuangba.data.bean.StayUrl;

import java.util.ArrayList;
import java.util.List;

import static com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo.BaseAdvertisementPhotoFragment.DEVICE_SURFACE_INFORMATION_LIST_STRING;

public class NextAdvertisementPhotoTabFragment extends BaseAdvertisementPhotoTabFragment{

    /**
     * @param deviceSurfaceInformationListString 集合 所有面的数据
     * @param deviceSurfaceInformationString 单个 当前面的数据
     */
    public static NextAdvertisementPhotoTabFragment newInstance(String deviceSurfaceInformationListString, String deviceSurfaceInformationString) {
        Bundle args = new Bundle();
        args.putString(DEVICE_SURFACE_INFORMATION_LIST_STRING, deviceSurfaceInformationListString);
        args.putString(DEVICE_SURFACE_INFORMATION, deviceSurfaceInformationString);
        NextAdvertisementPhotoTabFragment fragment = new NextAdvertisementPhotoTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_next_advertisement_photo_tab;
    }

    @Override
    public List<BaseAdvertisementPhotoTabEntity> getList() {
        DeviceSurfaceInformation deviceSurfaceInformation = getDeviceSurfaceInformation();
        String nextIssuePhotos = deviceSurfaceInformation.getNextIssuePhotos();
        if (TextUtils.isEmpty(nextIssuePhotos)){
            return super.getList();
        }else {
            //默认图
            List<BaseAdvertisementPhotoTabEntity> stringList = new ArrayList<>();
            StayUrl stayUrl = new Gson().fromJson(nextIssuePhotos, StayUrl.class);
            stringList.add(new BaseAdvertisementPhotoTabEntity(stayUrl.getHeaderCloseRange() , getString(R.string.panorama)));
            stringList.add(new BaseAdvertisementPhotoTabEntity(stayUrl.getHeaderProspect() , getString(R.string.with_head_close_up)));
            stringList.add(new BaseAdvertisementPhotoTabEntity(stayUrl.getHeaderProspect() , getString(R.string.with_head_vision)));
            stringList.add(new BaseAdvertisementPhotoTabEntity(stayUrl.getOther() , getString(R.string.other)));
            return stringList;
        }
    }

    @Override
    public void completeSubmission(DeviceSurfaceInformation deviceSurfaceInformation, AMapLocation mapLocation, String address, String remark, StayUrl stayUrl) {
        //所有面的数据
        LiveData<LifecycleOwner> viewLifecycleOwnerLiveData = getViewLifecycleOwnerLiveData();
        //当前面的数据
        DeviceSurfaceInformation deviceSurfaceInformation1 = getDeviceSurfaceInformation();


    }
}
