package com.xiaomai.zhuangba.fragment.personal.employer.base;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.EmployerWalletDetailAdapter;
import com.xiaomai.zhuangba.data.bean.EmployerWalletDetailBean;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/8/9 0009
 */
public class BaseEmployerWalletDetailFragment<T extends BaseQuickAdapter> extends BaseListFragment {

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshLayout;

    public static BaseEmployerWalletDetailFragment newInstance() {
        Bundle args = new Bundle();
        BaseEmployerWalletDetailFragment fragment = new BaseEmployerWalletDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        //刷新
        requestRunningAccount();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        requestRunningAccount();
    }

    private void requestRunningAccount() {
        Observable<HttpResult<EmployerWalletDetailBean>> runningAccountDetail = getObservable();
        RxUtils.getObservable(runningAccountDetail)
                .compose(this.<HttpResult<EmployerWalletDetailBean>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<EmployerWalletDetailBean>() {
                    @Override
                    protected void onSuccess(EmployerWalletDetailBean employerWalletDetailBeanList) {
                        List<EmployerWalletDetailBean.ListBean> employerWalletDetailBeanListList = employerWalletDetailBeanList.getList();
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            baseListAdapter.addData(employerWalletDetailBeanListList);
                        } else {
                            //刷新
                            baseListAdapter.setNewData(employerWalletDetailBeanListList);
                            finishRefresh();
                        }
                        if (employerWalletDetailBeanListList.size() < StaticExplain.PAGE_NUM.getCode()) {
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
        return new EmployerWalletDetailAdapter();
    }

    public Observable<HttpResult<EmployerWalletDetailBean>> getObservable() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_employer_wallet_detail;
    }

    @Override
    public int getIvNotDataBackground() {
        return R.drawable.bg_search_empty;
    }

    @Override
    public String getTvNotData() {
        return getString(R.string.search_empty);
    }
}
