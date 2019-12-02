package com.xiaomai.zhuangba.fragment.advertisement.master.beunder;

import android.os.Bundle;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.master.base.BaseAutographFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.QiNiuUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
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
        List<String> uriList = new ArrayList<>(mediaSelectorFiles);
        uriList.remove(uriList.size() - 1);
        if (uriList.isEmpty()) {
            ToastUtil.showShort(getString(R.string.job_site_img));
        } else {
            RxUtils.getObservable(QiNiuUtil.newInstance().getObservable(uriList))
                    .compose(this.<List<String>>bindToLifecycle())
                    .doOnNext(new Consumer<List<String>>() {
                        @Override
                        public void accept(List<String> strings) throws Exception {
                        }
                    }).concatMap(new Function<List<String>, ObservableSource<HttpResult<Object>>>() {
                @Override
                public ObservableSource<HttpResult<Object>> apply(List<String> imgUrlList){
                    HashMap<String, Object> hashMap = new HashMap<>();
                    //订单编号
                    hashMap.put("orderCode", getOrderCode());
                    //图片地址
                    hashMap.put("picturesUrl", new Gson().toJson(imgUrlList));
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
                    return RxUtils.getObservable(ServiceUrl.getUserApi().submitAdvertisingValidation(requestBody));
                }
            }).subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                @Override
                protected void onSuccess(Object response) {
                    //跳转到待开工
                    startFragment(MasterWorkerFragment.newInstance());
                }
            });
        }
    }

    private String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    @Override
    public String getAutographImgTip() {
        return getString(R.string.please_upload_clear_photos_of_last_issue);
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
