package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;
import com.xiaomai.zhuangba.data.bean.ServiceSampleEntity;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.QiNiuUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment.ORDER_CODES;
import static com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo.BaseAdvertisementPhotoFragment.DEVICE_SURFACE_INFORMATION_LIST_STRING;
import static com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo.BaseAdvertisementPhotoFragment.SERVICE_SAMPLE;

/**
 * 下刊
 */
public class NextAdvertisementPhotoTabFragment extends BaseAdvertisementPhotoTabFragment {

    /**
     * @param serviceSample                      默认样图
     * @param deviceSurfaceInformationListString 集合 所有面的数据
     * @param deviceSurfaceInformationString     单个 当前面的数据
     * @return
     */
    public static NextAdvertisementPhotoTabFragment newInstance(String orderCodes,String serviceSample, String deviceSurfaceInformationListString, String deviceSurfaceInformationString) {
        Bundle args = new Bundle();
        args.putString(DEVICE_SURFACE_INFORMATION_LIST_STRING, deviceSurfaceInformationListString);
        args.putString(DEVICE_SURFACE_INFORMATION, deviceSurfaceInformationString);
        args.putString(SERVICE_SAMPLE, serviceSample);
        args.putString(ORDER_CODES, orderCodes);
        NextAdvertisementPhotoTabFragment fragment = new NextAdvertisementPhotoTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_next_advertisement_photo_tab;
    }

    @Override
    public List<ServiceSampleEntity> getServiceSampleEntities() {
        //拍过得图 回显下刊图
        DeviceSurfaceInformation deviceSurfaceInformation = getDeviceSurfaceInformation();
        String nextIssuePhotos = deviceSurfaceInformation.getNextIssuePhotos();
        try {
            if (!TextUtils.isEmpty(nextIssuePhotos)) {
                return new Gson().fromJson(nextIssuePhotos, new TypeToken<List<ServiceSampleEntity>>() {
                }.getType());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * @param submitted 已经提交
     * @param notSubmitted 未提交
     * @param hashMap 待提交的参数
     */
    @Override
    public void submitAdvertisementPhoto(final List<ServiceSampleEntity> submitted, List<ServiceSampleEntity> notSubmitted,
                                         final HashMap<String, Object> hashMap) {
        for (ServiceSampleEntity serviceSampleEntity : notSubmitted) {
            Log.e(serviceSampleEntity.getPicUrl());
        }
        RxUtils.getObservable(QiNiuUtil.newInstance().getAdvertisementPhotoObservable(notSubmitted))
                .compose(this.<List<ServiceSampleEntity>>bindToLifecycle())
                .doOnNext(new Consumer<List<ServiceSampleEntity>>() {
                    @Override
                    public void accept(List<ServiceSampleEntity> strings) throws Exception {
                    }
                }).concatMap(new Function<List<ServiceSampleEntity>, ObservableSource<HttpResult<Object>>>() {
            @Override
            public ObservableSource<HttpResult<Object>> apply(List<ServiceSampleEntity> imgUrlList){
                hashMap.put("pictureUrl" ,new Gson().toJson(submitted));
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
                return RxUtils.getObservable(ServiceUrl.getUserApi().nextIssuePicture(requestBody));
            }
        }).subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
            @Override
            protected void onSuccess(Object response) {
                ToastUtil.showShort(response.toString());
                //startFragmentAndDestroyCurrent(AdvertisementPhotoSuccessFragment.newInstance(getOrderCodes()));
            }
        });
    }

}
