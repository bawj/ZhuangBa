package com.xiaomai.zhuangba.data.module.orderdetail;

import android.annotation.SuppressLint;

import com.example.toollib.data.BaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpZipRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.data.DeliveryContent;
import com.xiaomai.zhuangba.data.OngoingOrdersList;
import com.xiaomai.zhuangba.data.OrderDateList;
import com.xiaomai.zhuangba.data.OrderServiceDate;
import com.xiaomai.zhuangba.data.OrderServiceItem;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function4;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class OrderDetailModule extends BaseModule<IOrderDetailView> implements IOrderDetailModule {

    @SuppressLint("CheckResult")
    @Override
    public void requestOrderDetail() {
        String orderCode = mViewRef.get().getOrderCode();
        //订单详情
        Observable<HttpResult<OngoingOrdersList>> orderDetailsZip = RxUtils.getObservableZip(ServiceUrl.getUserApi().getOrderDetails(orderCode));
        //服务项目
        Observable<HttpResult<List<OrderServiceItem>>> orderServiceListZip = RxUtils.getObservableZip(ServiceUrl.getUserApi().getOrderServiceList(orderCode));
        //订单时间信息
        Observable<HttpResult<List<OrderDateList>>> orderDateListZip = RxUtils.getObservableZip(ServiceUrl.getUserApi().getOrderDateList(orderCode));

        //任务开始前的现场照 和 任务完成后的现场照 雇主评价
        Observable<HttpResult<List<DeliveryContent>>> orderValidationZip = RxUtils.getObservableZip(ServiceUrl.getUserApi().getOrderValidation(orderCode));

        Observable<Object> zip = Observable.zip(orderDetailsZip, orderServiceListZip, orderDateListZip, orderValidationZip,
                new Function4<HttpResult<OngoingOrdersList>, HttpResult<List<OrderServiceItem>>,
                        HttpResult<List<OrderDateList>>, HttpResult<List<DeliveryContent>>, Object>() {
                    @Override
                    public Object apply(HttpResult<OngoingOrdersList> ongoingOrdersList, HttpResult<List<OrderServiceItem>> orderServiceItemList,
                                        HttpResult<List<OrderDateList>> orderDateList, HttpResult<List<DeliveryContent>> deliveryContent) {
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
}
