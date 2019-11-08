package com.xiaomai.zhuangba.fragment.masterworker;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.GuaranteeAdapter;
import com.xiaomai.zhuangba.data.bean.MaintenanceOverman;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.fragment.masterworker.guarantee.GuaranteeDetailFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/11/7 0007
 * 维保单
 */
public class GuaranteeFragment extends BaseListFragment<IBaseModule , GuaranteeAdapter> {

    private GuaranteeAdapter guaranteeAdapter;

    public static GuaranteeFragment newInstance() {
        Bundle args = new Bundle();
        GuaranteeFragment fragment = new GuaranteeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        Log.e("维保单刷新");
        getMasterMaintenanceOrderList();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        getMasterMaintenanceOrderList();
    }

    private void getMasterMaintenanceOrderList() {
        Log.e("维保单刷新");
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterMaintenanceOrderList(String.valueOf(getPage())
                , String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(this.<HttpResult<RefreshBaseList<MaintenanceOverman>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<MaintenanceOverman>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<MaintenanceOverman> object) {
                        List<MaintenanceOverman> list = object.getList();
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            guaranteeAdapter.addData(list);
                        } else {
                            //刷新
                            guaranteeAdapter.setNewData(list);
                            finishRefresh();
                        }
                        if (list.size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            loadMoreEnd();
                        } else {
                            //加载完成
                            loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        finishRefresh();
                        loadError();
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        MaintenanceOverman maintenanceOverman = (MaintenanceOverman) view.findViewById(R.id.tvItemOrdersMoney).getTag();
        String orderCode = maintenanceOverman.getOrderCode();
        String orderType = maintenanceOverman.getOrderType();
        startFragment(GuaranteeDetailFragment.newInstance(orderCode , orderType));
    }

    @Override
    public GuaranteeAdapter getBaseListAdapter() {
        guaranteeAdapter = new GuaranteeAdapter();
        return guaranteeAdapter;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_guarantee;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

}