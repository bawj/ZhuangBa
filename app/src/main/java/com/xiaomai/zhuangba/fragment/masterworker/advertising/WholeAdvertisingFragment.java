package com.xiaomai.zhuangba.fragment.masterworker.advertising;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.WholeAdvertisingAdapter;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.data.observable.Observer;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/30 0030
 * <p>
 * 广告单 详情
 */
public class WholeAdvertisingFragment extends BaseListFragment implements Observer {

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshLayout;

    public static WholeAdvertisingFragment newInstance(AdvertisingBillsBean advertisingBillsBean, String advertisingStatus) {
        Bundle args = new Bundle();
        args.putSerializable(ConstantUtil.ADVERTISING_BILLS, advertisingBillsBean);
        args.putString(ConstantUtil.ADVERTISING_STATUS, advertisingStatus);
        WholeAdvertisingFragment fragment = new WholeAdvertisingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_whole_advertising_detail;
    }

    @Override
    public void refresh() {
        super.refresh();
        // 覆盖 父类方法 默认不刷新
    }

    @Override
    public void update(String message, String address, Handler handler) {
        if (refreshLayout != null) {
            refreshLayout.autoRefresh();
        }
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        //刷新
        getMasterHandleAdvertisingOrderList();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        //加载
        getMasterHandleAdvertisingOrderList();
    }

    private void getMasterHandleAdvertisingOrderList() {
        AdvertisingBillsBean advertisingBillsBean = getAdvertisingBillsBean();
        String advertisingStatus = getAdvertisingStatus();
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
        hashMap.put("orderStatus", advertisingStatus);
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
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleAdvertisingOrderList(requestBody))
                .compose(this.<HttpResult<RefreshBaseList<OngoingOrdersList>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<OngoingOrdersList>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<OngoingOrdersList> response) {
                        List<OngoingOrdersList> ordersLists = response.getList();
                        if (getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            baseListAdapter.setNewData(ordersLists);
                            finishRefresh();
                        } else {
                            //加载
                            baseListAdapter.addData(ordersLists);
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

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        OngoingOrdersList ongoingOrders = (OngoingOrdersList) view.findViewById(R.id.tvItemOrdersType).getTag();
        AdvertisingStatusUtil.startMasterOrderDetail(getBaseFragmentActivity() , ongoingOrders.getOrderCode()
                , String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()) , ongoingOrders.getOrderStatus());
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        return new WholeAdvertisingAdapter();
    }

    private AdvertisingBillsBean getAdvertisingBillsBean() {
        if (getArguments() != null) {
            return (AdvertisingBillsBean) getArguments().getSerializable(ConstantUtil.ADVERTISING_BILLS);
        }
        return new AdvertisingBillsBean();
    }

    private String getAdvertisingStatus() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ADVERTISING_STATUS);
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
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }
}
