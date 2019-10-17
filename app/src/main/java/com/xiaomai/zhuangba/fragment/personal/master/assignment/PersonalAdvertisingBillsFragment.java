package com.xiaomai.zhuangba.fragment.personal.master.assignment;

import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PersonalAdvertisingBillsAdapter;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

/**
 * @author Administrator
 * @date 2019/10/16 0016
 *
 * 分配任务 广告单
 */
public class PersonalAdvertisingBillsFragment extends BaseListFragment {

    private static final String PHONE = "phone";
    private PersonalAdvertisingBillsAdapter personalAdvertisingBillsAdapter;

    public static PersonalAdvertisingBillsFragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(PHONE , phone);
        PersonalAdvertisingBillsFragment fragment = new PersonalAdvertisingBillsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_personal_addvertising_bills;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        requestOrderListByStaff();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        requestOrderListByStaff();
    }

    private void requestOrderListByStaff() {
        String phone = getPhone();
        RxUtils.getObservable(ServiceUrl.getUserApi().getAdvertisingOrderByStaff(phone
                , String.valueOf(getPage()), String.valueOf(StaticExplain.PAGE_NUM.getCode())))
                .compose(this.<HttpResult<RefreshBaseList<AdvertisingBillsBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<AdvertisingBillsBean>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<AdvertisingBillsBean> ongoingOrdersLists) {
                        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
                            //加载
                            personalAdvertisingBillsAdapter.addData(ongoingOrdersLists.getList());
                        } else {
                            //刷新
                            personalAdvertisingBillsAdapter.setNewData(ongoingOrdersLists.getList());
                            finishRefresh();
                        }
                        if (ongoingOrdersLists.getList().size() < StaticExplain.PAGE_NUM.getCode()) {
                            //加载结束
                            loadMoreEnd();
                        } else {
                            //加载完成
                            loadMoreComplete();
                        }
                    }

                    @Override
                    public void onError(ApiException apiException) {
                        super.onError(apiException);
                        finishRefresh();
                        loadError();
                    }
                });

    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        personalAdvertisingBillsAdapter = new PersonalAdvertisingBillsAdapter();
        personalAdvertisingBillsAdapter.setOnDelListener(new PersonalAdvertisingBillsAdapter.IOnSwipeListener() {
            @Override
            public void onDel(AdvertisingBillsBean advertisingBillsBean, int pos) {
                delAdvertisingBills(advertisingBillsBean , pos);
            }

            @Override
            public void onItemClick(AdvertisingBillsBean advertisingBillsBean) {
                //广告单详情
                startFragment(PersonalAdvertisingBillsDetailFragment.newInstance(advertisingBillsBean));
            }
        });
        return personalAdvertisingBillsAdapter;
    }

    private void delAdvertisingBills(AdvertisingBillsBean advertisingBillsBean, int pos) {
        // TODO: 2019/10/17 0017 删除广告单
    }

    private String getPhone() {
        if (getArguments() != null) {
            return getArguments().getString(PHONE);
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
    public boolean isCustomView() {
        return false;
    }
}
