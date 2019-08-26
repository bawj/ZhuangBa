package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.AdvertisingReplacementAdapter;
import com.xiaomai.zhuangba.adapter.HistoricalAdapter;
import com.xiaomai.zhuangba.adapter.MasterMaintenancePolicyAdapter;
import com.xiaomai.zhuangba.data.AdvertisingList;
import com.xiaomai.zhuangba.data.bean.AdvertisingReplacementBean;
import com.xiaomai.zhuangba.data.bean.MaintenancePolicyBean;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/22 0022
 * 广告更换
 */
public class AdvertisingReplacementFragment extends BaseListFragment {

    private AdvertisingReplacementAdapter advertisingReplacementAdapter;
    public static AdvertisingReplacementFragment newInstance() {
        Bundle args = new Bundle();
        AdvertisingReplacementFragment fragment = new AdvertisingReplacementFragment();
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
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String phoneNumber = unique.getPhoneNumber();
        RxUtils.getObservable(ServiceUrl.getUserApi().getAdvertisingList(phoneNumber,getPage(), StaticExplain.PAGE_NUM.getCode()))
                .compose(this.<HttpResult<AdvertisingReplacementBean>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<AdvertisingReplacementBean>() {
                    @Override
                    protected void onSuccess(AdvertisingReplacementBean advertisingLists) {
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            advertisingReplacementAdapter.addData(advertisingLists.getList());
                        } else {
                            //刷新
                            advertisingReplacementAdapter.setNewData(advertisingLists.getList());
                            finishRefresh();
                        }
                        if (advertisingLists.getList().size() < StaticExplain.PAGE_NUM.getCode()) {
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
        advertisingReplacementAdapter = new AdvertisingReplacementAdapter();
        return advertisingReplacementAdapter;
    }

    @Override
    public int getEmptyView() {
        return R.layout.item_empty_view;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_advertising_replacement;
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
