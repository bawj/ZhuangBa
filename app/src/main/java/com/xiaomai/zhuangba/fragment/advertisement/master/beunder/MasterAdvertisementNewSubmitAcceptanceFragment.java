package com.xiaomai.zhuangba.fragment.advertisement.master.beunder;

import android.net.Uri;
import android.os.Bundle;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.function.BaseHttpConsumer;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseAutographFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/28 0028
 *
 * 师傅广告单 提交验收
 */
public class MasterAdvertisementNewSubmitAcceptanceFragment extends BaseAutographFragment {

    public static MasterAdvertisementNewSubmitAcceptanceFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        MasterAdvertisementNewSubmitAcceptanceFragment fragment = new MasterAdvertisementNewSubmitAcceptanceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void beforeInstallation() {
        List<Uri> uriList = new ArrayList<>(mediaSelectorFiles);
        uriList.remove(uriList.size() - 1);
        if (uriList.isEmpty()) {
            ToastUtil.showShort(getString(R.string.job_site_img));
        } else {
            try {
                //确认提交
                List<MultipartBody.Part> parts = new ArrayList<>();
                for (int i = 0; i < uriList.size(); i++) {
                    Uri compressPath = uriList.get(i);
                    File file = new File(new URI(compressPath.toString()));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                    parts.add(body);
                }
                RxUtils.getObservable(RxUtils.getObservable(ServiceUrl.getUserApi().uploadFiles(parts)))
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .doOnNext(new BaseHttpConsumer<Object>() {
                            @Override
                            public void httpConsumerAccept(HttpResult<Object> httpResult) {
                                Log.e("httpConsumerAccept httpResult = " + httpResult.toString());
                            }
                        })
                        .concatMap(new Function<HttpResult<Object>, ObservableSource<HttpResult<Object>>>() {
                            @Override
                            public ObservableSource<HttpResult<Object>> apply(HttpResult<Object> httpResult) throws Exception {
                                Log.e("flatMap 1 " + httpResult.toString());
                                HashMap<String, Object> hashMap = new HashMap<>();
                                //订单编号
                                hashMap.put("orderCode", getOrderCode());
                                //图片地址
                                hashMap.put("picturesUrl", httpResult.getData().toString());
                                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
                                return RxUtils.getObservable(ServiceUrl.getUserApi()
                                        .submitAdvertisingValidation(requestBody));
                            }
                        })
                        .subscribe(new BaseHttpRxObserver(getActivity()) {
                            @Override
                            protected void onSuccess(Object response) {
                                startFragment(MasterWorkerFragment.newInstance());
                            }
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.post_construction_photos);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_advertisement_new_submit_acceptance;
    }

}
