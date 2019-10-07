package com.xiaomai.zhuangba.fragment.orderdetail.employer.patrol.base;

import com.example.toollib.data.BaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpZipRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderDateList;
import com.xiaomai.zhuangba.data.bean.OrderServiceDate;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 * @date 2019/10/7 0007
 */
public class BasePatrolModule<V extends IBasePatrolView> extends BaseModule<V> implements IBasePatrolModule<V> {

    @Override
    public void requestPatrolDetail() {

        String orderCode = mViewRef.get().getOrderCode();
        String orderType = mViewRef.get().getOrderType();

        //订单详情
        Observable<HttpResult<OngoingOrdersList>> orderDetailsZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderDetails(orderCode , orderType).subscribeOn(Schedulers.io()));

        //订单时间信息
        Observable<HttpResult<List<OrderDateList>>> orderDateListZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().getOrderDateList(orderCode).subscribeOn(Schedulers.io()));

        Observable<Object> zip = Observable.zip(orderDetailsZip, orderDateListZip,
                new BiFunction<HttpResult<OngoingOrdersList>, HttpResult<List<OrderDateList>>, Object>() {
                    @Override
                    public OrderServiceDate apply(HttpResult<OngoingOrdersList> ongoingOrdersList, HttpResult<List<OrderDateList>> orderDateList) throws Exception {
                        OrderServiceDate orderServiceDate = new OrderServiceDate();
                        orderServiceDate.setOngoingOrdersList(ongoingOrdersList.getData());
                        orderServiceDate.setOrderDateLists(orderDateList.getData());
                        return orderServiceDate;
                    }
                }).compose(mViewRef.get().bindLifecycle());
        BaseHttpZipRxObserver.getInstance().httpZipObserver(zip, new BaseCallback() {
            @Override
            public void onSuccess(Object object) {
                OrderServiceDate orderServiceDate = (OrderServiceDate) object;
                mViewRef.get().requestOrderDetailSuccess(orderServiceDate);
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
