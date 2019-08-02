package com.xiaomai.zhuangba.fragment.employer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.data.bean.ServiceData;
import com.xiaomai.zhuangba.fragment.SelectServiceFragment;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerFragment;
import com.xiaomai.zhuangba.fragment.personal.employer.EmployerPersonalFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 * <p>
 * 雇主端
 */
public class EmployerFragment extends BaseMasterEmployerFragment {

    @BindView(R.id.tvUnallocatedOrders)
    TextView tvUnallocatedOrders;
    @BindView(R.id.tvPendingOrders)
    TextView tvPendingOrders;

    public static EmployerFragment newInstance() {
        Bundle args = new Bundle();
        EmployerFragment fragment = new EmployerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @OnClick(R.id.relEmployerRelease)
    public void onViewClicked() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getServiceCategory())
                .compose(this.<HttpResult<List<ServiceData>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<ServiceData>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<ServiceData> serviceDataList) {
                        serviceCategorySuccess(serviceDataList);
                    }
                });
    }

    public void serviceCategorySuccess(List<ServiceData> serviceDataList) {
        if (serviceDataList != null && !serviceDataList.isEmpty()){
            ServiceData serviceData = serviceDataList.get(0);
            startFragment(SelectServiceFragment.newInstance(String.valueOf(serviceData.getServiceId()), serviceData.getServiceText()));
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
    }

    @Override
    public void orderStatisticsSuccess(OrderStatistics orderStatistics) {
        if (orderStatistics != null){
            //未分配
            tvUnallocatedOrders.setText(String.valueOf(orderStatistics.getDistribution()));
            //待处理
            tvPendingOrders.setText(String.valueOf(orderStatistics.getPendingDisposal()));
        }
    }

    @Override
    public void startPersonal() {
        startFragment(EmployerPersonalFragment.newInstance());
    }

    @Override
    public List<BaseMasterEmployerContentFragment> getListFragment() {
        List<BaseMasterEmployerContentFragment> fragmentList = new ArrayList<>();
        fragmentList.add(OngoingOrdersFragment.newInstance());
        return fragmentList;
    }

    @Override
    public String[] getTabTitle() {
        return new String[]{getString(R.string.ongoing_orders)};
    }

}
