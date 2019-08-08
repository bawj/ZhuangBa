package com.xiaomai.zhuangba.fragment.personal.wallet.detailed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;

import com.example.toollib.data.BaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.haibin.calendarview.Calendar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.grouping.WalletDetailAdapter;
import com.xiaomai.zhuangba.data.bean.WalletDetailBean;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.WalletOrderTypeEnum;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.weight.popup.TimePopupWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/7/27 0027
 * <p>
 * 钱包明细
 */
public class BaseWalletDetailedFragment extends BaseListFragment implements ExpandableListView.OnChildClickListener, OnLoadMoreListener {

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @BindView(R.id.linSearchEmpty)
    LinearLayout linSearchEmpty;

    private WalletDetailAdapter walletDetailAdapter;

    /**
     * 开始时间
     */
    public String startDate = "";
    /**
     * 结束时间
     */
    public String endDate = "";

    public static BaseWalletDetailedFragment newInstance() {
        Bundle args = new Bundle();
        BaseWalletDetailedFragment fragment = new BaseWalletDetailedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        super.initView();
        expandableListView.setOnChildClickListener(this);
        expandableListView.setGroupIndicator(null);
        refreshLayout.setEnableLoadMore(true);
        refreshLayout.setOnLoadMoreListener(this);
    }


    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        //刷新
        requestWalletDetailed();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        onLoadMoreRequested();
        //加载
        requestWalletDetailed();
    }

    private void requestWalletDetailed() {
        Observable<HttpResult<WalletDetailBean>> observable = getObservable();
        if (observable != null) {
            RxUtils.getObservable(observable)
                    .compose(this.<HttpResult<WalletDetailBean>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<WalletDetailBean>() {
                        @Override
                        protected void onSuccess(WalletDetailBean response) {
                            requestSuccess(response);
                        }
                        @Override
                        public void onError(ApiException e) {
                            super.onError(e);
                            finishRefresh();
                            loadError();
                        }
                    });
        }
    }

    private void requestSuccess(WalletDetailBean response) {
        List<WalletDetailBean.ListBean> list = response.getList();
        if (getPage() != StaticExplain.PAGE_NUMBER.getCode()) {
            //加载
            loadSuccess(list);
        } else {
            refreshSuccess(list);
            finishRefresh();
        }
        if (list != null && list.size() < StaticExplain.PAGE_NUM.getCode()) {
            //加载结束
            refreshLayout.finishLoadMoreWithNoMoreData();
        } else {
            //加载完成
            refreshLayout.finishLoadMore();
        }
    }

    private void loadSuccess(List<WalletDetailBean.ListBean> list) {
        List<String> groupList = getTitleHead(list);
        List<List<WalletDetailBean.ListBean>> childrenList = getChildrenList(list , groupList);
        if (walletDetailAdapter != null){
            walletDetailAdapter.loadNotifyDataChanged(groupList , childrenList);
        }
        expandGroup(groupList);
    }

    private void refreshSuccess(List<WalletDetailBean.ListBean> list) {
        if (list != null && !list.isEmpty()){
            List<String> groupList = getTitleHead(list);
            List<List<WalletDetailBean.ListBean>> childrenList = getChildrenList(list , groupList);
            walletDetailAdapter =  new WalletDetailAdapter(getActivity(), groupList, childrenList, getType());
            expandableListView.setAdapter(walletDetailAdapter);
            expandGroup(groupList);
            expandableListView.setVisibility(View.VISIBLE);
            linSearchEmpty.setVisibility(View.GONE);
        }else {
            linSearchEmpty.setVisibility(View.VISIBLE);
            expandableListView.setVisibility(View.GONE);
        }
    }

    private void expandGroup(List<String> groupList){
        //展开所有group
        for (int i = 0; i < groupList.size(); i++) {
            expandableListView.expandGroup(i, true);
        }
    }

    private List<String> getTitleHead(List<WalletDetailBean.ListBean> listBeans) {
        List<String> groupList = new ArrayList<>();
        int type = getType();
        for (WalletDetailBean.ListBean listBean : listBeans) {
            String time = type == WalletOrderTypeEnum.DETAIL_OUT.getCode() ? listBean.getTimes() : listBean.getModifyTime();
            String title = DateUtil.dateToStr(time , "yyyy-MM");
            if (!groupList.contains(title)) {
                groupList.add(title);
            }
        }
        return groupList;
    }

    private List<List<WalletDetailBean.ListBean>> getChildrenList(List<WalletDetailBean.ListBean> listBean , List<String> groupList){
        List<List<WalletDetailBean.ListBean>> childrenList = new ArrayList<>();
        int type = getType();
        for (int i = 0; i < groupList.size(); i++) {
            String s = groupList.get(i);
            List<WalletDetailBean.ListBean> list = new ArrayList<>();
            for (int j = 0; j < listBean.size(); j++) {
                WalletDetailBean.ListBean listBean1 = listBean.get(j);
                String time = type == WalletOrderTypeEnum.DETAIL_OUT.getCode() ? listBean1.getTimes() : listBean1.getModifyTime();
                if (time.contains(s)) {
                    list.add(listBean1);
                }
            }
            childrenList.add(list);
        }
        return childrenList;
    }

    @Override
    public void loadMoreEnd() {
        super.loadMoreEnd();
        //加载完成
        refreshLayout.finishLoadMore(false);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
        WalletDetailBean.ListBean listBean = (WalletDetailBean.ListBean) view.findViewById(R.id.tv_order_time).getTag();
        startFragment(WalletOrderDetailFragment.newInstance(listBean, getType()));
        return false;
    }

    public Observable<HttpResult<WalletDetailBean>> getObservable() {
        return null;
    }

    public int getType() {
        return WalletOrderTypeEnum.DETAIL_ALL.getCode();
    }

    @Override
    public void rightTitleClick(View v) {
        new TimePopupWindow(getActivity(),new TimePopupWindow.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Calendar startCalendar, Calendar endCalendar) {
                startDate = startCalendar.getYear() + "-" + startCalendar.getMonth() + "-" + startCalendar.getDay();
                endDate = endCalendar.getYear() + "-" + endCalendar.getMonth() + "-" + endCalendar.getDay();
                refreshLayout.autoRefresh();
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_wallet_detailed;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.wallet_detail);
    }

    @Override
    public String getRightTitle() {
        return getString(R.string.wallet_detail_query);
    }
}
