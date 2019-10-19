package com.xiaomai.zhuangba.fragment.advertisement.master.beunder;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.AdvertisingEnum;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.advertisement.AdvertisementSubmitCompleteFragment;
import com.xiaomai.zhuangba.fragment.advertisement.master.having.MasterAdvertisementStartConstructionSingleFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/10/19 0019
 */
public class MasterAdvertisementNewSubmitAcceptanceSingleFragment extends MasterAdvertisementStartConstructionSingleFragment {

    public static MasterAdvertisementStartConstructionSingleFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        MasterAdvertisementStartConstructionSingleFragment fragment = new MasterAdvertisementStartConstructionSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.post_construction_photos);
    }

    @Override
    public void rightTitleClick(View v) {
        //完成 提交
        if (!TextUtils.isEmpty(requestImgUrl)) {
            HashMap<String, Object> hashMap = new HashMap<>();
            //订单编号
            hashMap.put("orderCode", getOrderCode());
            //图片地址
            hashMap.put("picturesUrl", requestImgUrl);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
            RxUtils.getObservable(ServiceUrl.getUserApi().submitAdvertisingValidation(requestBody))
                    .compose(this.<HttpResult<Object>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>() {
                        @Override
                        protected void onSuccess(Object response) {
                            startFragment(AdvertisementSubmitCompleteFragment.newInstance(getOrderCode() ,
                                    String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()) , String.valueOf(AdvertisingEnum.EMPLOYER_ACCEPTANCE.getCode())));
                        }
                    });

        } else {
            ToastUtil.showShort(getString(R.string.please_shot_img));
        }
    }

    private String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

}
