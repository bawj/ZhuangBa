package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.DefaultMoneyAdapter;
import com.xiaomai.zhuangba.data.bean.Claim;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

/**
 * @author Bawj
 * CreateDate:     2020/1/6 0006 16:11
 */
public class DefaultMoneyFragment extends BaseListFragment<IBaseModule, DefaultMoneyAdapter> {

    private DefaultMoneyAdapter defaultMoneyAdapter;
    public static DefaultMoneyFragment newInstance() {
        Bundle args = new Bundle();
        DefaultMoneyFragment fragment = new DefaultMoneyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(@NonNull RefreshLayout refreshLayout) {
        //刷新
        requestLiquidatedDamages();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        //加载
        requestLiquidatedDamages();
    }

    private void requestLiquidatedDamages() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getLiquidatedDamages(String.valueOf(getPage()),String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(this.<HttpResult<RefreshBaseList<Claim>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<Claim>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<Claim> claimList) {
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            defaultMoneyAdapter.addData(claimList.getList());
                        } else {
                            //刷新
                            defaultMoneyAdapter.setNewData(claimList.getList());
                            finishRefresh();
                        }
                        if (claimList.getList().size() < StaticExplain.PAGE_NUM.getCode()) {
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
    public DefaultMoneyAdapter getBaseListAdapter() {
        defaultMoneyAdapter = new DefaultMoneyAdapter();
        return defaultMoneyAdapter;
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
        return R.layout.fragment_default_money;
    }
}
