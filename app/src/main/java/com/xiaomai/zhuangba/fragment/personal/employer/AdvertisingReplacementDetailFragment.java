package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.AdvertisingReplacementDetailAdapter;
import com.xiaomai.zhuangba.data.bean.AdvertisingReplacementDetailBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

/**
 * @author Administrator
 * @date 2019/8/26 0026
 */
public class AdvertisingReplacementDetailFragment extends BaseListFragment {

    public static final String BATCH_CODE = "batch_code";
    private AdvertisingReplacementDetailAdapter advertisingReplacementDetailAdapter;

    public static AdvertisingReplacementDetailFragment newInstance(String batchCode) {
        Log.e("batchCode = " + batchCode);
        Bundle args = new Bundle();
        args.putString(BATCH_CODE, batchCode);
        AdvertisingReplacementDetailFragment fragment = new AdvertisingReplacementDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(@NonNull RefreshLayout refreshLayout) {
        //刷新
        requestAdvertisingReplacement();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        //加载
        requestAdvertisingReplacement();
    }

    private void requestAdvertisingReplacement() {
        String batchCode = getBatchCode();
        Log.e("batchCode = " + batchCode);
        RxUtils.getObservable(ServiceUrl.getUserApi().getAdvertisingElDetails(getBatchCode(), getPage(), StaticExplain.PAGE_NUM.getCode()))
                .compose(this.<HttpResult<RefreshBaseList<AdvertisingReplacementDetailBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<AdvertisingReplacementDetailBean>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<AdvertisingReplacementDetailBean> advertisingReplacementDetailBeanRefreshBaseList) {
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            advertisingReplacementDetailAdapter.addData(advertisingReplacementDetailBeanRefreshBaseList.getList());
                        } else {
                            //刷新
                            advertisingReplacementDetailAdapter.setNewData(advertisingReplacementDetailBeanRefreshBaseList.getList());
                            finishRefresh();
                        }
                        if (advertisingReplacementDetailBeanRefreshBaseList.getList().size() < StaticExplain.PAGE_NUM.getCode()) {
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

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        advertisingReplacementDetailAdapter = new AdvertisingReplacementDetailAdapter();
        return advertisingReplacementDetailAdapter;
    }

    private String getBatchCode() {
        if (getArguments() != null) {
            return getArguments().getString(BATCH_CODE);
        }
        return "";
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
    public int getContentView() {
        return R.layout.fragment_employer_advertising_replacement_detail;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.advertising_replacement);
    }
}
