package com.xiaomai.zhuangba.fragment.personal.master;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.MasterMaintenancePolicyAdapter;
import com.xiaomai.zhuangba.data.bean.MaintenanceBean;
import com.xiaomai.zhuangba.data.bean.MaintenancePolicyBean;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/9 0009
 *
 * 师傅端 我的维保单
 */
public class MasterMaintenancePolicyFragment extends BaseListFragment {

    private MasterMaintenancePolicyAdapter masterMaintenancePolicyAdapter;

    public static MasterMaintenancePolicyFragment newInstance() {
        Bundle args = new Bundle();
        MasterMaintenancePolicyFragment fragment = new MasterMaintenancePolicyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        super.onBaseRefresh(refreshLayout);
        requestEmployerMaintenance();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        requestEmployerMaintenance();
    }

    private void requestEmployerMaintenance() {
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String phoneNumber = unique.getPhoneNumber();
        RxUtils.getObservable(ServiceUrl.getUserApi().selectOvermanMaintenance(phoneNumber, getPage(), StaticExplain.PAGE_NUM.getCode()))
                .compose(this.<HttpResult<MaintenanceBean>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<MaintenanceBean>() {
                    @Override
                    protected void onSuccess(MaintenanceBean maintenanceBean) {
                        List<MaintenancePolicyBean> maintenanceBeanList = maintenanceBean.getList();
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            masterMaintenancePolicyAdapter.addData(maintenanceBeanList);
                        } else {
                            //刷新
                            masterMaintenancePolicyAdapter.setNewData(maintenanceBeanList);
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
    public BaseQuickAdapter getBaseListAdapter() {
        masterMaintenancePolicyAdapter = new MasterMaintenancePolicyAdapter();
        return masterMaintenancePolicyAdapter;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_maintenance_policy;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    public int getIvNotDataBackground() {
        return R.drawable.bg_search_empty;
    }

    @Override
    public String getTvNotData() {
        return getString(R.string.search_empty);
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }
}
