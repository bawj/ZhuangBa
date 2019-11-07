package com.xiaomai.zhuangba.fragment.masterworker;

import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.AdvertisingBillsAdapter;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.fragment.masterworker.advertising.AdvertisingBillDetailFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/30 0030
 * 广告单
 */
public class AdvertisingBillsFragment extends BaseListFragment<IBaseModule, AdvertisingBillsAdapter> {

    private AdvertisingBillsAdapter advertisingBillsAdapter;

    public static AdvertisingBillsFragment newInstance() {
        Bundle args = new Bundle();
        AdvertisingBillsFragment fragment = new AdvertisingBillsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        requestAdvertisingBills();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        requestAdvertisingBills();
    }

    public void requestAdvertisingBills() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleOrder(getPage()
                , StaticExplain.PAGE_NUM.getCode()))
                .compose(this.<HttpResult<RefreshBaseList<AdvertisingBillsBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<AdvertisingBillsBean>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<AdvertisingBillsBean> response) {
                        List<AdvertisingBillsBean> advertisingBillsBeans = response.getList();
                        if (getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            refreshAdvertisingSuccess(advertisingBillsBeans);
                            finishRefresh();
                        } else {
                            //加载
                            loadMoreAdvertisingSuccess(advertisingBillsBeans);
                        }
                        if (advertisingBillsBeans.size() < StaticExplain.PAGE_NUM.getCode()) {
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
    public int getContentView() {
        return R.layout.fragment_advertising_bills;
    }

    public void refreshAdvertisingSuccess(List<AdvertisingBillsBean> advertisingBillsBeans) {
        if (advertisingBillsAdapter != null) {
            advertisingBillsAdapter.setNewData(advertisingBillsBeans);
        }
    }

    public void loadMoreAdvertisingSuccess(List<AdvertisingBillsBean> advertisingBillsBeans) {
        if (advertisingBillsAdapter != null) {
            advertisingBillsAdapter.addData(advertisingBillsBeans);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        AdvertisingBillsBean advertisingBillsBean = (AdvertisingBillsBean) view.findViewById(R.id.tvItemOrdersTitle).getTag();
        startFragment(AdvertisingBillDetailFragment.newInstance(advertisingBillsBean));
    }

    @Override
    public AdvertisingBillsAdapter getBaseListAdapter() {
        advertisingBillsAdapter = new AdvertisingBillsAdapter();
        return advertisingBillsAdapter;
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
