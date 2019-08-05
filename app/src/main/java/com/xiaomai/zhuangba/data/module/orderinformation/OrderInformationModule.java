package com.xiaomai.zhuangba.data.module.orderinformation;

import android.text.TextUtils;

import com.example.toollib.data.BaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.AmountUtil;
import com.example.toollib.util.DensityUtils;
import com.example.toollib.util.RegexUtils;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderServicesBean;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.OrdersEnum;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.DateUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public class OrderInformationModule extends BaseModule<IOrderInformationView> implements IOrderInformationModule {


    @Override
    public void submitOrder() {
        final HashMap<String, Object> hashMap = getSubmissionOrder();
        if (hashMap != null) {
            String requestBodyString = new Gson().toJson(hashMap);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestBodyString);
            Observable<HttpResult<String>> httpResultObservable = ServiceUrl.getUserApi().postGenerateOrder(requestBody);
            RxUtils.getObservable(httpResultObservable).compose(mViewRef.get().<HttpResult<String>>bindLifecycle())
                    .subscribe(new BaseHttpRxObserver<String>(mContext.get()) {
                        @Override
                        protected void onSuccess(String orderCode) {
                            hashMap.put("orderCode", orderCode);
                            mViewRef.get().placeOrderSuccess(new Gson().toJson(hashMap));
                        }
                    });
        }
    }

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
        } else if (!RegexUtils.isMobileSimple(phoneNumber)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.incorrect_format_of_mobile_phone_number));
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
        if (!dateCompare(aLong)) {
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

    /**
     * 提交订单
     *
     * @return hashMap
     */
    private HashMap<String, Object> getSubmissionOrder() {
        //任务总数量
        int number = 0;
        //订单总金额
        Double orderAmount = 0.0;
        //服务项目
        List<OrderServicesBean> orderServicesBeans = getOrderServicesBean();
        for (OrderServicesBean orderServicesBean : orderServicesBeans) {
            number = number + orderServicesBean.getNumber();
            orderAmount = AmountUtil.add(orderAmount, orderServicesBean.getAmount(), 2);
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        //大类ID
        String largeClassServiceId = mViewRef.get().getLargeClassServiceId();
        hashMap.put("serviceId", largeClassServiceId);
        //总数量
        hashMap.put("number", String.valueOf(number));
        //总金额
        hashMap.put("orderAmount", String.valueOf(orderAmount));
        //姓名
        String userText = mViewRef.get().getUserName();
        if (TextUtils.isEmpty(userText)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.please_input_name));
            return null;
        }
        hashMap.put("name", userText);
        //电话
        String phoneNumber = mViewRef.get().getPhoneNumber();
        if (TextUtils.isEmpty(phoneNumber)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.please_input_telephone));
            return null;
        } else if (!RegexUtils.isMobileSimple(phoneNumber)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.incorrect_format_of_mobile_phone_number));
            return null;
        }
        hashMap.put("telephone", phoneNumber);
        //地址
        String address = mViewRef.get().getAddress();
        //详细地址
        String addressDetail = mViewRef.get().getAddressDetail();
        if (TextUtils.isEmpty(address)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.please_input_address));
            return null;
        }
        hashMap.put("address", address + addressDetail);
        //预约时间
        String appointmentTime = mViewRef.get().getAppointmentTime();
        if (TextUtils.isEmpty(appointmentTime)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.please_input_appointment_time));
            return null;
        }
        String date = DateUtil.dateToStr(appointmentTime, "yyyy-MM-dd HH:mm:ss");
        Long aLong = DateUtil.dateToCurrentTimeMillis(date, "yyyy-MM-dd HH:mm:ss");
        if (!dateCompare(aLong)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.reservation_time_prompt));
            return null;
        }
        hashMap.put("appointmentTime", date);
        //经纬度
        String longitude = mViewRef.get().getLongitude();
        String latitude = mViewRef.get().getLatitude();
        hashMap.put("longitude", DensityUtils.stringTypeFloat(longitude));
        hashMap.put("latitude", DensityUtils.stringTypeFloat(latitude));
        //服务项目信息
        hashMap.put("orderServices", orderServicesBeans);
        return hashMap;
    }


    public static List<OrderServicesBean> getOrderServicesBean() {
        //服务项目{ 小类服务ID 数量 和 总价格}
        List<ShopCarData> shopCarDataList = DBHelper.getInstance().getShopCarDataDao().queryBuilder().list();
        List<OrderServicesBean> orderServicesBeans = new ArrayList<>();
        for (ShopCarData shopCarData : shopCarDataList) {
            //大类服务ID
            //任务总数量
            int number = DensityUtils.stringTypeInteger(shopCarData.getNumber());
            //订单总金额
            double orderAmount = DensityUtils.stringTypeDouble(String.valueOf(number)) * DensityUtils.stringTypeDouble(shopCarData.getMoney());
            //订单服务项目
            OrderServicesBean orderServicesBean = new OrderServicesBean();
            //服务项目ID
            orderServicesBean.setServiceId(shopCarData.getServiceId());
            //服务项目数量
            orderServicesBean.setNumber(DensityUtils.stringTypeInteger(shopCarData.getNumber()));
            //服务项目总金额
            orderServicesBean.setAmount(orderAmount);
            orderServicesBeans.add(orderServicesBean);
        }
        return orderServicesBeans;
    }

    private boolean dateCompare(Long appointmentTime) {
        long l = System.currentTimeMillis();
        String date2String = DateUtil.getDate2String(l, "yyyy-MM-dd HH");
        Long aLong = DateUtil.dateToCurrentTimeMilli(date2String, "yyyy-MM-dd HH");
        if (appointmentTime - aLong >= 7200000) {
            return true;
        }
        return false;
    }
}
