package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.Log;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.EquipmentSurfaceRules;
import com.xiaomai.zhuangba.http.ServiceUrl;
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

    @BindView(R.id.ivEquipmentSurfaceRules)
    ImageView ivEquipmentSurfaceRules;
    @BindView(R.id.tvSurfaceRules)
    TextView tvSurfaceRules;

    public static final String SERVICE_ID = "service_id";

    public static EquipmentSurfaceRulesFragment newInstance(String serviceId) {
        Bundle args = new Bundle();
        args.putString(SERVICE_ID, serviceId);
        EquipmentSurfaceRulesFragment fragment = new EquipmentSurfaceRulesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        refreshLayout.setOnRefreshListener(this);
        //初始化适配器

        refreshLayout.autoRefresh();
    }

    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        String serviceId = getServiceId();
        Log.e("serviceId = " + serviceId);
        RxUtils.getObservable(ServiceUrl.getUserApi().getDeviceSurfaceRules(getServiceId() , 0))
                .compose(this.<HttpResult<EquipmentSurfaceRules>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<EquipmentSurfaceRules>() {
                    @Override
                    protected void onSuccess(EquipmentSurfaceRules equipmentSurfaceRules) {
                        String pictUrl = equipmentSurfaceRules.getPictUrl();
                        String notice = equipmentSurfaceRules.getNotice();
                        GlideManager.loadImage(getActivity() , pictUrl , ivEquipmentSurfaceRules);
                        tvSurfaceRules.setText(notice);
                        refreshLayout.finishRefresh();
                    }
                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        refreshLayout.finishRefresh();
                    }
                });
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

    private String getServiceId() {
        if (getArguments() != null) {
            return getArguments().getString(SERVICE_ID);
        }
        return "";
    }

}
