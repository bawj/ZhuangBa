package com.xiaomai.zhuangba.fragment.masterworker.advertising;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.google.gson.Gson;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.AllocationListAdapter;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.data.bean.DeviceOrder;
import com.xiaomai.zhuangba.data.bean.LatAndLon;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.fragment.masterworker.map.PlotDistributionFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AllocationListFragment extends BaseListFragment {

    private AllocationListAdapter allocationListAdapter;

    public static AllocationListFragment newInstance(AdvertisingBillsBean advertisingBillsBean) {
        Bundle args = new Bundle();
        AllocationListFragment fragment = new AllocationListFragment();
        args.putSerializable(ConstantUtil.ADVERTISING_BILLS, advertisingBillsBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        QMUITopBarLayout topBarBase = getTopBarBase();
        Button button = topBarBase.addRightTextButton(getString(R.string.distribution_point), QMUIViewHelper.generateViewId());
        button.setTextColor(Color.BLACK);
        Drawable drawable= getResources().getDrawable(R.drawable.ic_location_black);
        drawable.setBounds(0,0,drawable.getMinimumWidth(), drawable.getMinimumHeight());//drawable.getMinimumWidth(), drawable.getMinimumHeight()
        button.setCompoundDrawables(drawable,null,null,null);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightTitleClick();
            }
        });
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        super.onBaseRefresh(refreshLayout);
        requestAllocationList();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        super.onBaseLoadMoreRequested();
        requestAllocationList();
    }

    private void requestAllocationList() {
        AdvertisingBillsBean advertisingBillsBean = getAdvertisingBillsBean();
        HashMap<String, String> hashMap = new HashMap<>();
        //省
        hashMap.put("province", advertisingBillsBean.getProvince());
        //市
        hashMap.put("city", advertisingBillsBean.getCity());
        //区
        hashMap.put("district", advertisingBillsBean.getDistrict());
        //街道
        hashMap.put("street", advertisingBillsBean.getStreet());
        //小区
        hashMap.put("villageName", advertisingBillsBean.getVillageName());
        //订单状态
        hashMap.put("orderStatus", "");
        //页数
        hashMap.put("pageNum", String.valueOf(getPage()));
        //一页数量
        hashMap.put("pageSize", String.valueOf(StaticExplain.PAGE_NUM.getCode()));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            hashMap.forEach(new BiConsumer<String, String>() {
                @Override
                public void accept(String s, String s2) {
                    Log.e("hashMap  s = " + s + "  value = " + s2);
                }
            });
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleAdvertisingOrderListByEquipment(requestBody))
                .compose(this.<HttpResult<RefreshBaseList<DeviceOrder>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<DeviceOrder>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<DeviceOrder> response) {
                        List<DeviceOrder> ordersLists = response.getList();
                        if (getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            allocationListAdapter.setNewData(ordersLists);
                            finishRefresh();
                        } else {
                            //加载
                            allocationListAdapter.addData(ordersLists);
                        }
                        if (ordersLists.size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            loadMoreEnd();
                        } else {
                            //加载完成
                            loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        finishRefresh();
                        loadError();
                    }
                });
    }

    public void rightTitleClick() {
        AdvertisingBillsBean advertisingBillsBean = getAdvertisingBillsBean();
        HashMap<String, String> hashMap = new HashMap<>();
        //省
        hashMap.put("province", advertisingBillsBean.getProvince());
        //市
        hashMap.put("city", advertisingBillsBean.getCity());
        //区
        hashMap.put("district", advertisingBillsBean.getDistrict());
        //街道
        hashMap.put("street", advertisingBillsBean.getStreet());
        //小区
        hashMap.put("villageName", advertisingBillsBean.getVillageName());
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleAdEquipmentDistributionPoint(requestBody))
                .compose(this.<HttpResult<List<LatAndLon>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<LatAndLon>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<LatAndLon> latAndLonList) {
                        startFragment(PlotDistributionFragment.newInstance(getString(R.string.distribution_point) , latAndLonList));
                    }
                });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_allocation_list;
    }

    @Override
    protected String getActivityTitle() {
        AdvertisingBillsBean advertisingBillsBean = getAdvertisingBillsBean();
        String street = advertisingBillsBean.getStreet();
        String villageName = advertisingBillsBean.getVillageName();
        return TextUtils.isEmpty(street) ? villageName : street;
    }

    private AdvertisingBillsBean getAdvertisingBillsBean() {
        if (getArguments() != null) {
            return (AdvertisingBillsBean) getArguments().getSerializable(ConstantUtil.ADVERTISING_BILLS);
        }
        return new AdvertisingBillsBean();
    }

    @Override
    public AllocationListAdapter getBaseListAdapter() {
        allocationListAdapter = new AllocationListAdapter();
        return allocationListAdapter;
    }

}
