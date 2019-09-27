package com.xiaomai.zhuangba.fragment.orderdetail.master.module;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.orderdetail.OrderDetailModule;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/8/3 0003
 */
public class MasterOrderDetailModule extends OrderDetailModule<IMasterOrderDetailView> implements IMasterOrderDetailModule{

    @Override
    public void requestCancelOrder() {
        String orderCode = mViewRef.get().getOrderCode();
        Observable<HttpResult<Object>> observable = ServiceUrl.getUserApi().masterCancelOrder(orderCode , "");
        RxUtils.getObservable(observable)
                .compose(mViewRef.get().<HttpResult<Object>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(mContext.get()) {
                    @Override
                    protected void onSuccess(Object response) {
                        mViewRef.get().cancelOrderSuccess();
                    }
                });
    }

    @Override
    public void requestReceiptOrder() {
        //判断是否认证了
        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        int authenticationStatue = userInfo.getAuthenticationStatue();
        //未认证
        if (authenticationStatue  != StaticExplain.CERTIFIED.getCode()){
            mViewRef.get().goAuthentication();
        }else {
            String orderCode = mViewRef.get().getOrderCode();
            RxUtils.getObservable(ServiceUrl.getUserApi().acceptOrder(orderCode))
                    .compose(mViewRef.get().<HttpResult<Object>>bindLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>(mContext.get()) {
                        @Override
                        protected void onSuccess(Object response) {
                            mViewRef.get().receiptOrderSuccess();
                        }
                    });
        }
    }

}
