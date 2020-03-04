package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;

import butterknife.BindView;
import io.reactivex.annotations.Nullable;

/**
 * @author Bawj
 * CreateDate:     2020/3/3 0003 13:50
 * 设备面规则
 */
public class EquipmentSurfaceRulesFragment extends BaseFragment implements OnRefreshListener {

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshLayout;

    @Nullable
    @BindView(R.id.rvBaseList)
    RecyclerView rvBaseList;
    @BindView(R.id.tvSurfaceRules)
    TextView tvSurfaceRules;

    public static final String SERVICE_ID = "service_id";

    public static EquipmentSurfaceRulesFragment newInstance() {
        Bundle args = new Bundle();
        EquipmentSurfaceRulesFragment fragment = new EquipmentSurfaceRulesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        refreshLayout.setOnRefreshListener(this);
        rvBaseList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rvBaseList.addItemDecoration(new GridSpacingItemDecoration(3, 11, false));
        //初始化适配器

        refreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_equipment_surface_rules;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.equipment_surface_rules);
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

}
