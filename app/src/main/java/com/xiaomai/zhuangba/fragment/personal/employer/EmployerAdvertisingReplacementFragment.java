package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.EmployerAdvertisingReplacementAdapter;
import com.xiaomai.zhuangba.data.bean.EmployerAdvertisingReplacement;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

/**
 * @author Administrator
 * @date 2019/8/26 0026
 */
public class EmployerAdvertisingReplacementFragment extends BaseListFragment{

    private EmployerAdvertisingReplacementAdapter employerAdvertisingReplacementAdapter;

    public static EmployerAdvertisingReplacementFragment newInstance() {
        Bundle args = new Bundle();
        EmployerAdvertisingReplacementFragment fragment = new EmployerAdvertisingReplacementFragment();
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
        RxUtils.getObservable(ServiceUrl.getUserApi().getAdvertisingElList(phoneNumber,getPage(), StaticExplain.PAGE_NUM.getCode()))
                .compose(this.<HttpResult<RefreshBaseList<EmployerAdvertisingReplacement>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<EmployerAdvertisingReplacement>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<EmployerAdvertisingReplacement> advertisingLists) {
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            employerAdvertisingReplacementAdapter.addData(advertisingLists.getList());
                        } else {
                            //刷新
                            employerAdvertisingReplacementAdapter.setNewData(advertisingLists.getList());
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        EmployerAdvertisingReplacement advertisingReplacement = (EmployerAdvertisingReplacement) view.findViewById(R.id.tvAdvertisingMoney).getTag();
        startFragment(AdvertisingReplacementDetailFragment.newInstance(advertisingReplacement.getBatchCode()));
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        employerAdvertisingReplacementAdapter = new EmployerAdvertisingReplacementAdapter();
        return employerAdvertisingReplacementAdapter;
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
        return R.layout.fragment_employer_advertising_replacement;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.advertising_replacement);
    }
}
