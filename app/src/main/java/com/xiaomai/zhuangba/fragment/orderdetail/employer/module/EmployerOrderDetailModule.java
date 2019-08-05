package com.xiaomai.zhuangba.fragment.orderdetail.employer.module;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.data.module.orderdetail.OrderDetailModule;
import com.xiaomai.zhuangba.http.ServiceUrl;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 */
public class EmployerOrderDetailModule extends OrderDetailModule<IEmployerOrderDetailView> implements IEmployerOrderDetailModule {


    @Override
    public void obtainCancelTask() {
        String orderCode = mViewRef.get().getOrderCode();
        RxUtils.getObservable(ServiceUrl.getUserApi().cancelOrder(orderCode))
                .compose(mViewRef.get().<HttpResult<Object>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(mContext.get()) {
                    @Override
                    protected void onSuccess(Object response) {
                        mViewRef.get().cancelOrderSuccess();
                    }
                });
    }

}
