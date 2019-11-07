package com.xiaomai.zhuangba.data.module.masteremployer;

import android.text.TextUtils;

import com.example.toollib.data.BaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.DensityUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.UserInfoDao;
import com.xiaomai.zhuangba.data.bean.InspectionSheetBean;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.data.bean.Orders;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.data.bean.StatisticsData;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

import io.reactivex.Observable;

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
        //订单池
        final int page = mViewRef.get().getPage();
        String address = mViewRef.get().getAddress();
        address = address.equals(mContext.get().getString(R.string.whole_country)) ? "" : address;
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterSelectOrder(String.valueOf(page)
                , String.valueOf(StaticExplain.PAGE_NUM.getCode()), address))
                .compose(mViewRef.get().<HttpResult<Orders>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Orders>() {
                    @Override
                    protected void onSuccess(Orders response) {
                        if (response != null) {
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
                String.valueOf(page), String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(mViewRef.get().<HttpResult<Orders>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Orders>() {
                    @Override
                    protected void onSuccess(Orders orders) {
                        if (orders != null) {
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
        String address = mViewRef.get().getAddress();
        if (!TextUtils.isEmpty(address) && address.equals("全国")) {
            address = "";
        }
        RxUtils.getObservable(ServiceUrl.getUserApi().getStatisticalData(TextUtils.isEmpty(address) ? "" : address))
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
        if (page == StaticExplain.PAGE_NUMBER.getCode()) {
            //刷新
            mViewRef.get().refreshSuccess(ordersLists);
        } else {
            //加载
            mViewRef.get().loadMoreSuccess(ordersLists);
        }
        if (ordersLists.size() < StaticExplain.PAGE_NUM.getCode()) {
            //加载结束
            mViewRef.get().loadMoreEnd();
        } else {
            //加载完成
            mViewRef.get().loadMoreComplete();
        }
    }

    @Override
    public void requestWorkingStateSwitching() {
        final String status = mViewRef.get().getStatus();
        Observable<HttpResult<Object>> observable = ServiceUrl.getUserApi().updateStatus(status);
        RxUtils.getObservable(observable)
                .compose(mViewRef.get().<HttpResult<Object>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(mContext.get()) {
                    @Override
                    protected void onSuccess(Object response) {
                        UserInfoDao userInfoDao = DBHelper.getInstance().getUserInfoDao();
                        UserInfo unique = userInfoDao.queryBuilder().unique();
                        unique.setStartFlag(DensityUtils.stringTypeInteger(status));
                        userInfoDao.update(unique);
                        mViewRef.get().workingStateSwitchingSuccess();
                    }
                });
    }

    @Override
    public void requestInspectionSheet() {
        final int page = mViewRef.get().getPage();
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleInspectionOrder(page
                , StaticExplain.PAGE_NUM.getCode()))
                .compose(mViewRef.get().<HttpResult<RefreshBaseList<InspectionSheetBean>>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<InspectionSheetBean>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<InspectionSheetBean> response) {
                        List<InspectionSheetBean> advertisingBillsBeans = response.getList();
                        if (page == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            mViewRef.get().refreshInspectionSuccess(advertisingBillsBeans);
                        } else {
                            //加载
                            mViewRef.get().loadInspectionSuccess(advertisingBillsBeans);
                        }
                        if (advertisingBillsBeans.size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            mViewRef.get().loadMoreEnd();
                        } else {
                            //加载完成
                            mViewRef.get().loadMoreComplete();
                        }
                    }
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        mViewRef.get().refreshError();
                    }
                });
    }

}
