package com.xiaomai.zhuangba.fragment.personal.master;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.MasterFrozenAmountAdapter;
import com.xiaomai.zhuangba.data.bean.FrozenAmountBean;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/10/18 0018
 *
 * 冻结金额
 */
public class MasterPersonalFrozenAmount extends BaseListFragment {

    private MasterFrozenAmountAdapter masterFrozenAmountAdapter;

    public static MasterPersonalFrozenAmount newInstance() {
        Bundle args = new Bundle();
        MasterPersonalFrozenAmount fragment = new MasterPersonalFrozenAmount();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        requestSelectFreezeOrder();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        requestSelectFreezeOrder();
    }

    private void requestSelectFreezeOrder() {
        //冻结金额
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String phoneNumber = unique.getPhoneNumber();
        RxUtils.getObservable(ServiceUrl.getUserApi().selectFreezeOrder(phoneNumber))
                .compose(this.<HttpResult<List<FrozenAmountBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<FrozenAmountBean>>() {
                    @Override
                    protected void onSuccess(List<FrozenAmountBean> frozenAmountBeanList) {
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            masterFrozenAmountAdapter.addData(frozenAmountBeanList);
                        } else {
                            //刷新
                            masterFrozenAmountAdapter.setNewData(frozenAmountBeanList);
                            finishRefresh();
                        }
                        if (frozenAmountBeanList.size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            loadMoreEnd();
                        } else {
                            //加载完成
                            loadMoreComplete();
                        }
                    }
                });
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        masterFrozenAmountAdapter = new MasterFrozenAmountAdapter();
        return masterFrozenAmountAdapter;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_personal_frozen;
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
    protected String getActivityTitle() {
        return getString(R.string.frozen_amount_title);
    }
}
