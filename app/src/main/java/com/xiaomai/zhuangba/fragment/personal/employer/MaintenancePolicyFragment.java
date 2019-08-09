package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.DensityUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.MaintenancePolicyAdapter;
import com.xiaomai.zhuangba.data.bean.MaintenanceBean;
import com.xiaomai.zhuangba.data.bean.MaintenancePolicyBean;
import com.xiaomai.zhuangba.data.bean.OrderServiceItem;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 */
public class MaintenancePolicyFragment extends BaseListFragment implements MaintenancePolicyAdapter.IMaintenance {

    private MaintenancePolicyAdapter maintenancePolicyAdapter;

    public static MaintenancePolicyFragment newInstance() {
        Bundle args = new Bundle();
        MaintenancePolicyFragment fragment = new MaintenancePolicyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void rightIconClick(View view) {
        // TODO: 2019/8/8 0008 问号
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        super.onBaseRefresh(refreshLayout);
        requestEmployerMaintenance();
    }

    @Override
    public void onLoadMoreRequested() {
        super.onLoadMoreRequested();

    }

    private void requestEmployerMaintenance() {
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String phoneNumber = unique.getPhoneNumber();
        RxUtils.getObservable(ServiceUrl.getUserApi().selectEmployerMaintenance(phoneNumber, getPage(), StaticExplain.PAGE_NUM.getCode()))
                .compose(this.<HttpResult<MaintenanceBean>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<MaintenanceBean>() {
                    @Override
                    protected void onSuccess(MaintenanceBean maintenanceBean) {
                        List<MaintenancePolicyBean> maintenanceBeanList = maintenanceBean.getList();
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            maintenancePolicyAdapter.addData(maintenanceBeanList);
                        } else {
                            //刷新
                            maintenancePolicyAdapter.setNewData(maintenanceBeanList);
                            finishRefresh();
                        }
                        if (maintenanceBeanList.size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            loadMoreEnd();
                        } else {
                            //加载完成
                            loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        finishRefresh();
                        loadError();
                    }
                });
    }

    @Override
    public void continuedMaintenance(MaintenancePolicyBean item) {
        //续维保
        List<OrderServiceItem> orderServiceItems = new ArrayList<>();
        OrderServiceItem orderServiceItem = new OrderServiceItem();
        orderServiceItem.setOrderCode(item.getOrderCode());
        orderServiceItem.setServiceId(DensityUtils.stringTypeInteger(item.getServiceId()));
        orderServiceItem.setServiceText(item.getServiceName());
        orderServiceItem.setAmount(item.getAmount());
        orderServiceItem.setIconUrl(item.getServiceImg());
        orderServiceItem.setMonthNumber(item.getNumber());
        orderServiceItem.setNumber(item.getServiceNumber());
        orderServiceItems.add(orderServiceItem);
        DBHelper.getInstance().getOrderServiceItemDao().deleteAll();
        DBHelper.getInstance().getOrderServiceItemDao().insertInTx(orderServiceItems);
        startFragment(ContinuedMaintenanceFragment.newInstance());
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        maintenancePolicyAdapter = new MaintenancePolicyAdapter();
        maintenancePolicyAdapter.setMaintenance(this);
        return maintenancePolicyAdapter;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_maintenance_policy;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.maintenance_policy_title);
    }

    @Override
    public int getRightIcon() {
        return R.drawable.ic_question_mark;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

}
