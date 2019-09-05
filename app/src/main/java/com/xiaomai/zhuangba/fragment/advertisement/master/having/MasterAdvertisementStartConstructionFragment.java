package com.xiaomai.zhuangba.fragment.advertisement.master.having;

import android.net.Uri;
import android.os.Bundle;

import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.function.BaseHttpConsumer;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseAutographFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/28 0028
 * <p>
 * 师傅 广告单 开始施工
 */
public class MasterAdvertisementStartConstructionFragment extends BaseAutographFragment {


    public static MasterAdvertisementStartConstructionFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        MasterAdvertisementStartConstructionFragment fragment = new MasterAdvertisementStartConstructionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void beforeInstallation() {
        List<Uri> uriList = new ArrayList<>(mediaSelectorFiles);
        uriList.remove(uriList.size() - 1);
        if (uriList.isEmpty()) {
            ToastUtil.showShort(getString(R.string.job_site_font_installation_img));
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
                                return RxUtils.getObservable(ServiceUrl.getUserApi()
                                        .startTaskAdvertisingOrder(getOrderCode(), httpResult.getData().toString()));
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
    public int getContentView() {
        return R.layout.fragment_master_advertisement_start_construction;
    }

    @Override
    public String getAutographImgTip() {
        return getString(R.string.advertisement_start_construction);
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.pre_construction_photos);
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }
}