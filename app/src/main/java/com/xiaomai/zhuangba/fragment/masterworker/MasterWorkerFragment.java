package com.xiaomai.zhuangba.fragment.masterworker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderStatistics;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerFragment;
import com.xiaomai.zhuangba.fragment.personal.master.MasterPersonalFragment;
import com.xiaomai.zhuangba.util.Util;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 * <p>
 * 师傅端
 */
public class MasterWorkerFragment extends BaseMasterEmployerFragment {

    @BindView(R.id.tvTodayFlowingRMB)
    TextView tvTodayFlowingRMB;
    @BindView(R.id.tvOrderToday)
    TextView tvOrderToday;
    @BindView(R.id.tvFinishToday)
    TextView tvFinishToday;

    public static MasterWorkerFragment newInstance() {
        Bundle args = new Bundle();
        MasterWorkerFragment fragment = new MasterWorkerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_worker;
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
    }

    @Override
    public void orderStatisticsSuccess(OrderStatistics orderStatistics) {
        if (orderStatistics != null){
            tvTodayFlowingRMB.setText(Util.getZero(orderStatistics.getMasterOrderAmount()));
            tvOrderToday.setText(String.valueOf(orderStatistics.getPendingDisposal()));
            tvFinishToday.setText(String.valueOf(orderStatistics.getComplete()));
        }
    }

    @Override
    public void startPersonal() {
        String orderToday = tvOrderToday.getText().toString();
        String todayFlowingRMB = tvTodayFlowingRMB.getText().toString();
        startFragment(MasterPersonalFragment.newInstance(orderToday , todayFlowingRMB));
    }

    @Override
    public List<BaseMasterEmployerContentFragment> getListFragment() {
        List<BaseMasterEmployerContentFragment> fragmentList = new ArrayList<>();
        fragmentList.add(NewTaskFragment.newInstance());
        fragmentList.add(NeedDealWithFragment.newInstance());
        return fragmentList;
    }

    @Override
    public String[] getTabTitle() {
        return new String[]{getString(R.string.new_task), getString(R.string.need_to_be_dealt_with)};
    }

}

