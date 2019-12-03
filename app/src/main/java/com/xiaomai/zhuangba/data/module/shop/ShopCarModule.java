package com.xiaomai.zhuangba.data.module.shop;

import android.text.TextUtils;

import com.example.toollib.data.BaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.AmountUtil;
import com.example.toollib.util.DensityUtils;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.ShopCarDataDao;
import com.xiaomai.zhuangba.data.bean.OrderAddress;
import com.xiaomai.zhuangba.data.bean.OrderServicesBean;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.bean.db.ShopAuxiliaryMaterialsDB;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.QiNiuUtil;
import com.xiaomai.zhuangba.util.ShopCarUtil;
import com.xiaomai.zhuangba.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/9/27 0027
 */
public class ShopCarModule extends BaseModule<IShopCarView> implements IShopCarModule {

    private HashMap<String, Object> hashMap;

    @Override
    public void submitOrder() {
        String orderAddressGson = mViewRef.get().getOrderAddressGson();
        OrderAddress orderAddress = new Gson().fromJson(orderAddressGson , OrderAddress.class);
        //串联请求 先上传图片
        List<String> mediaSelectorFiles = orderAddress.getImgList();
        List<String> uriList = new ArrayList<>(mediaSelectorFiles);
        uriList.remove(uriList.size() - 1);
//        List<MultipartBody.Part> parts = new ArrayList<>();
//        for (int i = 0; i < uriList.size(); i++) {
//            Uri compressPath = Uri.parse(uriList.get(i));
//            try {
//                File file = new File(new URI(compressPath.toString()));
//                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
//                parts.add(body);
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//        }
        hashMap = getSubmissionOrder(orderAddress);
        if (hashMap != null && uriList.isEmpty()) {
            postGenerateOrder();
        } else if (hashMap != null) {
            //postImgGenerateOrder(parts);
            postImgGenerateOrder(uriList);
        }
    }

    /**
     * 带图片
     */
    private void postImgGenerateOrder(List<String> uriList /*List<MultipartBody.Part> parts*/) {
        RxUtils.getObservable(QiNiuUtil.newInstance().getObservable(uriList))
                .compose(mViewRef.get().<List<String>>bindLifecycle())
                .doOnNext(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) {
                    }
                }).concatMap(new Function<List<String>, ObservableSource<HttpResult<String>>>() {
            @Override
            public ObservableSource<HttpResult<String>> apply(List<String> imgUrlList){
                hashMap.put("picturesUrl", new Gson().toJson(imgUrlList));
                String requestBodyString = new Gson().toJson(hashMap);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), requestBodyString);
                return RxUtils.getObservable(ServiceUrl.getUserApi().postGenerateOrder(requestBody));
            }
        }).subscribe(new BaseHttpRxObserver<String>(mContext.get()) {
            @Override
            protected void onSuccess(String response) {
                hashMap.put("orderCode", response);
                hashMap.put("orderType", String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()));
                mViewRef.get().placeOrderSuccess(new Gson().toJson(hashMap));
            }
        });

        /*Observable<HttpResult<Object>> httpResultObservable1 = ServiceUrl.getUserApi().uploadFiles(parts);
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
                .subscribe(new BaseHttpRxObserver<String>(mContext.get()) {
                    @Override
                    protected void onSuccess(String response) {
                        hashMap.put("orderCode", response);
                        hashMap.put("orderType", String.valueOf(StaticExplain.INSTALLATION_LIST.getCode()));
                        mViewRef.get().placeOrderSuccess(new Gson().toJson(hashMap));
                    }
                });*/
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

    /**
     * 提交订单
     *
     * @return hashMap hashMap
     */
    private HashMap<String, Object> getSubmissionOrder(OrderAddress orderAddress) {
        //任务总数量
        int number = 0;
        //订单总金额
        Double orderAmount = 0.0;
        //服务项目
        List<OrderServicesBean> orderServicesBeans = ShopCarUtil.getOrderServicesBean();
        for (OrderServicesBean orderServicesBean : orderServicesBeans) {
            number = number + orderServicesBean.getNumber();
            orderAmount = AmountUtil.add(orderAmount, orderServicesBean.getAmount(), 2);
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        //大类ID
        hashMap.put("serviceId", mViewRef.get().getServiceId());
        //服务名称
        hashMap.put("serviceText", mViewRef.get().getServiceText());
        //总数量
        hashMap.put("number", String.valueOf(number));
        //姓名
        String userText = orderAddress.getUserText();
        if (TextUtils.isEmpty(userText)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.please_input_name));
            return null;
        }
        hashMap.put("name", userText);
        //电话
        String phoneNumber = orderAddress.getPhoneNumber();
        if (TextUtils.isEmpty(phoneNumber)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.please_input_telephone));
            return null;
        }
        hashMap.put("telephone", phoneNumber);
        //地址
        String province = orderAddress.getProvince();
        String city = orderAddress.getCity();
        String area = orderAddress.getArea();
        //详细地址
        String addressDetail = orderAddress.getAddressDetail();
        String address = Util.getProvinceCityArea(province , city , area , addressDetail);
        if (TextUtils.isEmpty(address)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.please_input_address));
            return null;
        }
        hashMap.put("address", address);
        //预约时间
        String appointmentTime = orderAddress.getAppointmentTime();
        if (TextUtils.isEmpty(appointmentTime)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.please_input_appointment_time));
            return null;
        }
        String date = DateUtil.dateToStr(appointmentTime, "yyyy-MM-dd HH:mm:ss");
        Long aLong = DateUtil.dateToCurrentTimeMillis(date, "yyyy-MM-dd HH:mm:ss");
        if (!DateUtil.isDateCompare(aLong)) {
            mViewRef.get().showToast(mContext.get().getString(R.string.reservation_time_prompt));
            return null;
        }
        hashMap.put("appointmentTime", date);
        //经纬度
        hashMap.put("longitude", orderAddress.getLongitude());
        hashMap.put("latitude", orderAddress.getLatitude());
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
        hashMap.put("employerDescribe", orderAddress.getEmployerDescribe());
        setAuxiliaryMaterials(hashMap);
        //总金额
        hashMap.put("orderAmount", String.valueOf(ShopCarUtil.getTotalMoney()));
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

}
