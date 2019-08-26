package com.xiaomai.zhuangba.data.module.orderinformation;

import android.net.Uri;
import android.text.TextUtils;

import com.example.toollib.data.BaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.function.BaseHttpConsumer;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.AmountUtil;
import com.example.toollib.util.DensityUtils;
import com.example.toollib.util.Log;
import com.example.toollib.util.RegexUtils;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.ShopCarDataDao;
import com.xiaomai.zhuangba.data.bean.OrderServicesBean;
import com.xiaomai.zhuangba.data.bean.PayData;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.bean.db.ShopAuxiliaryMaterialsDB;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.OrdersEnum;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.ShopCarUtil;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public class OrderInformationModule extends BaseModule<IOrderInformationView> implements IOrderInformationModule {

    private HashMap<String, Object> hashMap;

    @Override
    public void submitOrder() {
        //串联请求 先上传图片
        List<Uri> mediaSelectorFiles = mViewRef.get().getMediaSelectorFiles();
        List<Uri> uriList = new ArrayList<>(mediaSelectorFiles);
        uriList.remove(uriList.size() - 1);
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (int i = 0; i < uriList.size(); i++) {
            Uri compressPath = uriList.get(i);
            try {
                File file = new File(new URI(compressPath.toString()));
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
                parts.add(body);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        hashMap = getSubmissionOrder();
        if (hashMap != null && parts.isEmpty()) {
            postGenerateOrder();
        } else if (hashMap != null) {
            postImgGenerateOrder(parts);
        }
    }

    /**
     * 带图片
     */
    private void postImgGenerateOrder(List<MultipartBody.Part> parts) {
        Observable<HttpResult<Object>> httpResultObservable1 = ServiceUrl.getUserApi().uploadFiles(parts);
        RxUtils.getObservable(httpResultObservable1)
                .compose(mViewRef.get().<HttpResult<Object>>bindLifecycle())
                .doOnNext(new BaseHttpConsumer<Object>() {
                    @Override
                    public void httpConsumerAccept(HttpResult<Object> httpResult) {
                        hashMap.put("picturesUrl", httpResult.getData().toString());
                    }
                })
                .concatMap(new Function<HttpResult<Object>, ObservableSource<HttpResult<String>>>() {
                    @Override
                    public ObservableSource<HttpResult<String>> apply(HttpResult<Object> httpResult) {
                        Log.e("flatMap 1 " + httpResult.toString());
                        String requestBodyString = new Gson().toJson(hashMap);
                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestBodyString);
                        return RxUtils.getObservable(ServiceUrl.getUserApi().postGenerateOrder(requestBody));
                    }
                })
                .subscribe(new BaseHttpRxObserver(mContext.get()) {
                    @Override
                    protected void onSuccess(Object response) {
                        hashMap.put("orderCode", response);
                        hashMap.put("orderType", String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()));
                        mViewRef.get().placeOrderSuccess(new Gson().toJson(hashMap));
                    }
                });
    }

    /**
     * 不带图片
     */
    private void postGenerateOrder() {
        if (hashMap != null) {
            String requestBodyString = new Gson().toJson(hashMap);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestBodyString);
            Observable<HttpResult<String>> httpResultObservable = ServiceUrl.getUserApi().postGenerateOrder(requestBody);
            RxUtils.getObservable(httpResultObservable).compose(mViewRef.get().<HttpResult<String>>bindLifecycle())
                    .subscribe(new BaseHttpRxObserver<String>(mContext.get()) {
                        @Override
                        protected void onSuccess(String orderCode) {
                            hashMap.put("orderCode", orderCode);
                            hashMap.put("orderType", String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()));
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
        if (!isDateCompare(aLong)) {
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
     * @return hashMap hashMap
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
        hashMap.put("serviceId", mViewRef.get().getLargeClassServiceId());
        //服务名称
        hashMap.put("serviceText", mViewRef.get().getLargeServiceText());
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
        if (!isDateCompare(aLong)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.reservation_time_prompt));
            return null;
        }
        hashMap.put("appointmentTime", date);
        //经纬度
        hashMap.put("longitude", DensityUtils.stringTypeFloat(mViewRef.get().getLongitude()));
        hashMap.put("latitude", DensityUtils.stringTypeFloat(mViewRef.get().getLatitude()));
        //是否有维保 0：没有维保；1：有维保
        List<ShopCarData> list = DBHelper.getInstance().getShopCarDataDao().queryBuilder()
                .where(ShopCarDataDao.Properties.MaintenanceId.notEq(String.valueOf(ConstantUtil.DEF_MAINTENANCE)))
                .list();
        hashMap.put("maintenanceFlag", list.isEmpty() ? 0 : 1);
        //计算 维保总金额
        Double maintenanceMoney = 0d;
        for (ShopCarData shopCarData : list) {
            maintenanceMoney += (DensityUtils.stringTypeDouble(shopCarData.getMaintenanceMoney()) * DensityUtils.stringTypeInteger(shopCarData.getNumber()));
        }
        hashMap.put("maintenanceAmount", maintenanceMoney);
        //服务项目信息
        hashMap.put("orderServices", orderServicesBeans);
        //雇主描述
        hashMap.put("employerDescribe", mViewRef.get().getEmployerDescribe());
        setAuxiliaryMaterials(hashMap);
        return hashMap;
    }

    private void setAuxiliaryMaterials(HashMap<String, Object> hashMap) {
        //没有 添加图片 和 备注
        ShopAuxiliaryMaterialsDB unique = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao().queryBuilder().unique();
        //开槽
        hashMap.put("slottingStartLength", unique.getSlottingStartLength());
        hashMap.put("slottingEndLength", unique.getSlottingEndLength());
        hashMap.put("slottingPrice", unique.getSlottingSlottingPrice());
        //是否调试 0 调试 1 不调试
        hashMap.put("debugging", unique.getDebuggingPrice() == 0 ? 0 : 1);
        hashMap.put("debugPrice", unique.getDebuggingPrice());
        //辅材
        hashMap.put("materialsStartLength", unique.getMaterialsStartLength());
        hashMap.put("materialsEndLength", unique.getMaterialsEndLength());
        hashMap.put("materialsPrice", unique.getMaterialsSlottingPrice());
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
            double orderAmount = ShopCarUtil.getTotalMoneys(number, DensityUtils.stringTypeDouble(shopCarData.getMoney())
                    , DensityUtils.stringTypeDouble(shopCarData.getMoney2()), DensityUtils.stringTypeDouble(shopCarData.getMoney3())
                    , DensityUtils.stringTypeDouble(shopCarData.getMaintenanceMoney()));
            //订单服务项目
            OrderServicesBean orderServicesBean = new OrderServicesBean();
            //服务项目ID
            orderServicesBean.setServiceId(shopCarData.getServiceId());
            //服务项目数量
            orderServicesBean.setNumber(number);
            //服务项目总金额
            orderServicesBean.setAmount(orderAmount);

            //如果有维保 则ID != -1
            if (shopCarData.getMaintenanceId() != ConstantUtil.DEF_MAINTENANCE) {
                //维保时间 单位（月）
                orderServicesBean.setMonthNumber(DensityUtils.stringTypeInteger(shopCarData.getMaintenanceTime()));
                //维保 金额 * 项目数量
                double maintenanceAmount = DensityUtils.stringTypeDouble(shopCarData.getMaintenanceMoney())
                        * DensityUtils.stringTypeInteger(shopCarData.getNumber());
                orderServicesBean.setMaintenanceAmount(maintenanceAmount);
            }
            orderServicesBeans.add(orderServicesBean);
        }
        return orderServicesBeans;
    }

    private boolean isDateCompare(Long appointmentTime) {
        long l = System.currentTimeMillis();
        String date2String = DateUtil.getDate2String(l, "yyyy-MM-dd HH");
        Long aLong = DateUtil.dateToCurrentTimeMilli(date2String, "yyyy-MM-dd HH");
        return appointmentTime - aLong >= 7200000;
    }
}
