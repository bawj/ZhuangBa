package com.xiaomai.zhuangba.data.module.advertisement;

import com.example.toollib.data.BaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpZipRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.data.bean.OrderServiceDate;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 * @date 2019/8/27 0027
 */
public class BaseAdvertisementModule extends BaseModule<IBaseAdvertisementView> implements IBaseAdvertisementModule {

    @Override
    public void requestAdvertisementDetail() {
        String orderCode = mViewRef.get().getOrderCode();
        String orderType = mViewRef.get().getOrderType();

        //订单详情
        Observable<HttpResult<OngoingOrdersList>> orderDetailsZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderDetails(orderCode, orderType).subscribeOn(Schedulers.io()));

        //师傅提交的信息
        Observable<HttpResult<List<DeliveryContent>>> observableZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderValidation(orderCode, orderType).subscribeOn(Schedulers.io()));

        //订单时间信息
        Observable<HttpResult<List<OrderDateList>>> orderDateListZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderDateList(orderCode).subscribeOn(Schedulers.io()));
        Observable<Object> compose = Observable.zip(orderDetailsZip, observableZip, orderDateListZip, new Function3<HttpResult<OngoingOrdersList>,
                HttpResult<List<DeliveryContent>>, HttpResult<List<OrderDateList>>, Object>() {
            @Override
            public Object apply(HttpResult<OngoingOrdersList> ongoingOrdersListHttpResult,
                                HttpResult<List<DeliveryContent>> listHttpResult,
                                HttpResult<List<OrderDateList>> listHttpResult2) throws Exception {
                OrderServiceDate orderServiceDate = new OrderServiceDate();
                orderServiceDate.setOngoingOrdersList(ongoingOrdersListHttpResult.getData());
                orderServiceDate.setOrderDateLists(listHttpResult2.getData());
                orderServiceDate.setDeliveryContents(listHttpResult.getData());
                return orderServiceDate;
            }
        }).compose(mViewRef.get().bindLifecycle());
        BaseHttpZipRxObserver.getInstance().httpZipObserver(compose, new BaseCallback() {
            @Override
            public void onSuccess(Object object) {
                mViewRef.get().requestOrderDetailSuccess(object);
                mViewRef.get().finishRefresh();
            }

            @Override
            public void onFail(Object obj) {
                mViewRef.get().showToast(obj.toString());
                mViewRef.get().finishRefresh();
            }
        });
    }

    @Override
    public void requestAdvertisementOrderPoolOrderDetail() {
        String orderCode = mViewRef.get().getOrderCode();
        String orderType = mViewRef.get().getOrderType();

        //订单池详情
        Observable<HttpResult<OngoingOrdersList>> orderDetailsZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderByOrderCode(orderCode, orderType).subscribeOn(Schedulers.io()));

        //师傅提交的信息
        Observable<HttpResult<List<DeliveryContent>>> observableZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderValidation(orderCode, orderType).subscribeOn(Schedulers.io()));

        //订单时间信息
        Observable<HttpResult<List<OrderDateList>>> orderDateListZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderDateList(orderCode).subscribeOn(Schedulers.io()));
        Observable<Object> compose = Observable.zip(orderDetailsZip, observableZip, orderDateListZip, new Function3<HttpResult<OngoingOrdersList>,
                HttpResult<List<DeliveryContent>>, HttpResult<List<OrderDateList>>, Object>() {
            @Override
            public Object apply(HttpResult<OngoingOrdersList> ongoingOrdersListHttpResult,
                                HttpResult<List<DeliveryContent>> listHttpResult,
                                HttpResult<List<OrderDateList>> listHttpResult2) throws Exception {
                OrderServiceDate orderServiceDate = new OrderServiceDate();
                orderServiceDate.setOngoingOrdersList(ongoingOrdersListHttpResult.getData());
                orderServiceDate.setOrderDateLists(listHttpResult2.getData());
                orderServiceDate.setDeliveryContents(listHttpResult.getData());
                return orderServiceDate;
            }
        }).compose(mViewRef.get().bindLifecycle());
        BaseHttpZipRxObserver.getInstance().httpZipObserver(compose, new BaseCallback() {
            @Override
            public void onSuccess(Object object) {
                mViewRef.get().requestOrderDetailSuccess(object);
                mViewRef.get().finishRefresh();
            }

            @Override
            public void onFail(Object obj) {
                mViewRef.get().showToast(obj.toString());
                mViewRef.get().finishRefresh();
            }
        });
    }

}
