package com.xiaomai.zhuangba.data.module.orderdetail;

import android.annotation.SuppressLint;

import com.example.toollib.data.BaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpZipRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.data.bean.DeliveryContent;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.data.bean.OrderServiceDate;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;
import java.util.Observer;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class OrderDetailModule<V extends IOrderDetailView> extends BaseModule<V> implements IOrderDetailModule<V> {

    /**
     * 订单详情
     */
    @SuppressLint("CheckResult")
    @Override
    public void requestOrderDetail() {
        String orderCode = mViewRef.get().getOrderCode();
        String orderType = mViewRef.get().getOrderType();
        //订单详情
        Observable<HttpResult<OngoingOrdersList>> orderDetailsZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderDetails(orderCode , orderType).subscribeOn(Schedulers.io()));
        //服务项目
        Observable<HttpResult<List<OrderServiceItem>>> orderServiceListZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderServiceList(orderCode).subscribeOn(Schedulers.io()));
        //订单时间信息
        Observable<HttpResult<List<OrderDateList>>> orderDateListZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderDateList(orderCode).subscribeOn(Schedulers.io()));

        //师傅提交的信息
        Observable<HttpResult<List<DeliveryContent>>> observableZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderValidation(orderCode, orderType).subscribeOn(Schedulers.io()));

        Observable<Object> zip = Observable.zip(orderDetailsZip, orderServiceListZip, orderDateListZip,
                observableZip, new Function4<HttpResult<OngoingOrdersList>, HttpResult<List<OrderServiceItem>>,
                        HttpResult<List<OrderDateList>>, HttpResult<List<DeliveryContent>>, Object>() {
                    @Override
                    public Object apply(HttpResult<OngoingOrdersList> ongoingOrdersList, HttpResult<List<OrderServiceItem>> orderServiceItemList,
                                        HttpResult<List<OrderDateList>> orderDateList, HttpResult<List<DeliveryContent>> deliveryContent) throws Exception {
                        OrderServiceDate orderServiceDate = new OrderServiceDate();
                        orderServiceDate.setOngoingOrdersList(ongoingOrdersList.getData());
                        orderServiceDate.setOrderServiceItems(orderServiceItemList.getData());
                        orderServiceDate.setOrderDateLists(orderDateList.getData());
                        orderServiceDate.setDeliveryContents(deliveryContent.getData());
                        return orderServiceDate;
                    }
                }).compose(mViewRef.get().bindLifecycle());
        BaseHttpZipRxObserver.getInstance().httpZipObserver(zip, new BaseCallback() {
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

    /**
     * 订单池
     */
    @SuppressLint("CheckResult")
    @Override
    public void requestOrderPoolOrderDetail() {
        String orderCode = mViewRef.get().getOrderCode();
        String orderType = mViewRef.get().getOrderType();
        //订单详情
        Observable<HttpResult<OngoingOrdersList>> orderDetailsZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderByOrderCode(orderCode , orderType).subscribeOn(Schedulers.io()));
        //服务项目
        Observable<HttpResult<List<OrderServiceItem>>> orderServiceListZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderServiceList(orderCode).subscribeOn(Schedulers.io()));
        //订单时间信息
        Observable<HttpResult<List<OrderDateList>>> orderDateListZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderDateList(orderCode).subscribeOn(Schedulers.io()));

        Observable<Object> zip = Observable.zip(orderDetailsZip, orderServiceListZip, orderDateListZip,
                new Function3<HttpResult<OngoingOrdersList>,
                        HttpResult<List<OrderServiceItem>>,
                        HttpResult<List<OrderDateList>>, Object>() {
                    @Override
                    public Object apply(HttpResult<OngoingOrdersList> ongoingOrdersList,
                                        HttpResult<List<OrderServiceItem>> orderServiceItemList,
                                        HttpResult<List<OrderDateList>> orderDateList) throws Exception {
                        OrderServiceDate orderServiceDate = new OrderServiceDate();
                        orderServiceDate.setOngoingOrdersList(ongoingOrdersList.getData());
                        orderServiceDate.setOrderServiceItems(orderServiceItemList.getData());
                        orderServiceDate.setOrderDateLists(orderDateList.getData());
                        return orderServiceDate;
                    }
                }).compose(mViewRef.get().bindLifecycle());
        BaseHttpZipRxObserver.getInstance().httpZipObserver(zip, new BaseCallback() {
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
