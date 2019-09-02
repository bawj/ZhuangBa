package com.xiaomai.zhuangba.data.module.selectservice;

import com.example.toollib.data.BaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.data.bean.Maintenance;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategory;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategoryProject;
import com.xiaomai.zhuangba.data.bean.Slotting;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/6 0006
 */
public class SelectServiceModule extends BaseModule<ISelectServiceView> implements ISelectServiceModule {


    @Override
    public void requestServiceData() {
        String serviceId = mViewRef.get().getServiceId();
        RxUtils.getObservable(ServiceUrl.getUserApi().getServiceSubcategory(serviceId))
                .compose(mViewRef.get().<HttpResult<List<ServiceSubcategory>>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<ServiceSubcategory>>() {
                    @Override
                    protected void onSuccess(List<ServiceSubcategory> serviceSubcategories) {
                        mViewRef.get().finishRefresh();
                        mViewRef.get().requestServiceDataSuccess(serviceSubcategories);
                    }
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        mViewRef.get().finishRefresh();
                    }
                });

    }

    @Override
    public void requestMaintenance(final ServiceSubcategoryProject serviceSubcategoryProject) {
        RxUtils.getObservable(ServiceUrl.getUserApi().getMaintenance(String.valueOf(serviceSubcategoryProject.getServiceId())))
                .compose(mViewRef.get().<HttpResult<List<Maintenance>>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<List<Maintenance>>(mContext.get()) {
                    @Override
                    protected void onSuccess(List<Maintenance> maintenanceList) {
                        mViewRef.get().requestMaintenance(serviceSubcategoryProject,maintenanceList);
                    }
                });
    }

    @Override
    public void requestSlottingAndDebug() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getSlottingAndDebug())
                .compose(mViewRef.get().<HttpResult<Slotting>>bindLifecycle())
                .subscribe(new BaseHttpRxObserver<Slotting>(mContext.get()) {
                    @Override
                    protected void onSuccess(Slotting slotting) {
                        mViewRef.get().slottingAndDebugSuccess(slotting);
                    }
                });
    }

}
