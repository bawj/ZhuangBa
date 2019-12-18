package com.xiaomai.zhuangba.data.module.orderinformation;

import android.text.TextUtils;

import com.example.toollib.data.BaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.DensityUtils;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.DateUtil;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public class OrderInformationModule extends BaseModule<IOrderInformationView> implements IOrderInformationModule {


    @Override
    public void requestUpdateOrder() {
        HashMap<String, Object> hashMap = new HashMap<>();
        String userText = mViewRef.get().getUserName();
        if (TextUtils.isEmpty(userText)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.please_input_name));
            return;
        }
        hashMap.put("name", userText);
        //电话
        String phoneNumber = mViewRef.get().getPhoneNumber();
        if (TextUtils.isEmpty(phoneNumber)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.please_input_telephone));
            return;
        }
        hashMap.put("telephone", phoneNumber);
        //地址
        String address = mViewRef.get().getAddress();
        //详细地址
        String addressDetail = mViewRef.get().getAddressDetail();
        if (TextUtils.isEmpty(address)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.please_input_address));
            return;
        }
        hashMap.put("address", address + addressDetail);
        //预约时间
        String appointmentTime = mViewRef.get().getAppointmentTime();
        if (TextUtils.isEmpty(appointmentTime)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.please_input_appointment_time));
            return;
        }
        String date = DateUtil.dateToStr(appointmentTime, "yyyy-MM-dd HH:mm:ss");
        Long aLong = DateUtil.dateToCurrentTimeMillis(date, "yyyy-MM-dd HH:mm:ss");
        if (!DateUtil.isDateCompare(aLong)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.reservation_time_prompt));
            return;
        }
        hashMap.put("appointmentTime", date);
        //经纬度
        String longitude = mViewRef.get().getLongitude();
        String latitude = mViewRef.get().getLatitude();
        hashMap.put("longitude", DensityUtils.stringTypeFloat(longitude));
        hashMap.put("latitude", DensityUtils.stringTypeFloat(latitude));
        //订单编号
        hashMap.put("orderCode", mViewRef.get().getOrderCode());

        //合同编号
        hashMap.put("contractNo" , mViewRef.get().getContractNo());
        // 客户经理
        hashMap.put("accountManager" , mViewRef.get().getAccountManager());
        // 项目名称
        hashMap.put("projectName" , mViewRef.get().getProjectName());
        // 项目特点
        hashMap.put("projectFeatures" , mViewRef.get().getProjectFeatures());
        // 店铺名称
        hashMap.put("shopName" , mViewRef.get().getShopName());
        // 第三方订单编号
        hashMap.put("orderNumber" , mViewRef.get().getOrderNumber());

        String s = new Gson().toJson(hashMap);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), s);


        Observable<HttpResult<String>> httpResultObservable = ServiceUrl.getUserApi().updateOrderAddress(requestBody);
        RxUtils.getObservable(httpResultObservable)
                .compose(mViewRef.get().<HttpResult<String>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<String>(mContext.get()) {
                    @Override
                    protected void onSuccess(String response) {
                        mViewRef.get().updateOrderSuccess();
                    }
                });
    }

}
