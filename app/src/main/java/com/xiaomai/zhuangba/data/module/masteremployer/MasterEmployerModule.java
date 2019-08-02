package com.xiaomai.zhuangba.data.module.masteremployer;

import com.example.toollib.data.BaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.data.bean.Orders;
import com.xiaomai.zhuangba.data.bean.StatisticsData;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public class MasterEmployerModule extends BaseModule<IMasterEmployerView> implements IMasterEmployerModule {


    @Override
    public void requestOrderStatistics() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getOrderStatistics())
                .compose(mViewRef.get().<HttpResult<OrderStatistics>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<OrderStatistics>() {
                    @Override
                    protected void onSuccess(OrderStatistics orderStatistics) {
                        mViewRef.get().orderStatisticsSuccess(orderStatistics);
                    }
                });
    }

    @Override
    public void requestMasterNewTaskOrderList() {
        //新任务
        final int page = mViewRef.get().getPage();
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterOrderList(String.valueOf(page)
                , String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(mViewRef.get().<HttpResult<Orders>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Orders>() {
                    @Override
                    protected void onSuccess(Orders response) {
                        if (response != null){
                            success(response, page);
                        }
                    }
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        mViewRef.get().refreshError();
                    }
                });
    }

    @Override
    public void requestOngoingOrders() {
        //需处理
        final int page = mViewRef.get().getPage();
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleOrderList(String.valueOf(page)
                , String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(mViewRef.get().<HttpResult<Orders>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Orders>() {
                    @Override
                    protected void onSuccess(Orders response) {
                        if (response != null){
                            success(response, page);
                        }
                    }
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        mViewRef.get().refreshError();
                    }
                });
    }

    @Override
    public void employerOrderList() {
        final int page = mViewRef.get().getPage();
        RxUtils.getObservable(ServiceUrl.getUserApi().getOrderList(
                 String.valueOf(page),String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(mViewRef.get().<HttpResult<Orders>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Orders>() {
                    @Override
                    protected void onSuccess(Orders orders) {
                        if (orders != null){
                            success(orders, page);
                        }
                    }
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        mViewRef.get().refreshError();
                    }
                });
    }

    @Override
    public void requestStatisticsData() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getStatisticalData())
                .compose(mViewRef.get().<HttpResult<StatisticsData>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<StatisticsData>() {
                    @Override
                    protected void onSuccess(StatisticsData statisticsData) {
                        mViewRef.get().statisticsSuccess(statisticsData);
                    }
                });

    }

    private void success(Orders response, int page) {
        List<OngoingOrdersList> ordersLists = response.getList();
        if (page == 1){
            //刷新
            mViewRef.get().refreshSuccess(ordersLists);
        }else{
            //加载
            mViewRef.get().loadMoreSuccess(ordersLists);
        }
        if (ordersLists.size() < StaticExplain.PAGE_NUM.getCode()){
            //加载结束
            mViewRef.get().loadMoreEnd();
        }else {
            //加载完成
            mViewRef.get().loadMoreComplete();
        }
    }
}
