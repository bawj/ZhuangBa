package com.xiaomai.zhuangba.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

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
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.AdvertisingStatusUtil;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/10/18 0018
 */
public class PersonalAdvertisingBillsAddTaskDetail extends BaseListFragment {

    /**
     * 全选
     */
    @BindView(R.id.chkTaskAllElection)
    CheckBox chkTaskAllElection;

    @BindView(R.id.relInstallationAssignmentTask)
    RelativeLayout relInstallationAssignmentTask;
    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshLayout;

    private static final String PHONE = "phone";
    private PersonalAdvertisingBillsAddTaskDetailAdapter personalAdvertisingBillsAddTaskDetailAdapter;

    public static PersonalAdvertisingBillsAddTaskDetail newInstance(AdvertisingBillsBean advertisingBillsBean, String phone) {
        Bundle args = new Bundle();
        args.putString(PHONE, phone);
        args.putSerializable(ConstantUtil.ADVERTISING_BILLS, advertisingBillsBean);
        PersonalAdvertisingBillsAddTaskDetail fragment = new PersonalAdvertisingBillsAddTaskDetail();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_whole_advertising_detail;
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
        hashMap.put("orderStatus", "");
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
        RxUtils.getObservable(ServiceUrl.getUserApi().getAdvertisingOrderListByStaff(requestBody))
                .compose(this.<HttpResult<RefreshBaseList<OngoingOrdersList>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<OngoingOrdersList>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<OngoingOrdersList> response) {
                        List<OngoingOrdersList> ordersLists = response.getList();
                        if (getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            personalAdvertisingBillsAddTaskDetailAdapter.setNewData(ordersLists);
                            finishRefresh();
                            if (ordersLists != null && ordersLists.size() > 0) {
                                relInstallationAssignmentTask.setVisibility(View.VISIBLE);
                            } else {
                                relInstallationAssignmentTask.setVisibility(View.GONE);
                            }
                        } else {
                            //加载
                            personalAdvertisingBillsAddTaskDetailAdapter.addData(ordersLists);
                        }
                        if (ordersLists != null && ordersLists.size() < StaticExplain.PAGE_NUM.getCode()) {
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
        AdvertisingStatusUtil.startMasterOrderDetail(getBaseFragmentActivity(), ongoingOrders.getOrderCode()
                , String.valueOf(StaticExplain.ADVERTISING_BILLS.getCode()), ongoingOrders.getOrderStatus());
    }

    @Override
    public BaseQuickAdapter getBaseListAdapter() {
        personalAdvertisingBillsAddTaskDetailAdapter = new PersonalAdvertisingBillsAddTaskDetailAdapter();
        personalAdvertisingBillsAddTaskDetailAdapter.setOnCheckedChangeListener(new PersonalAdvertisingBillsAddTaskDetailAdapter.IOnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isChecked) {
                //查询是否所有的 都选中了
                boolean check = true;
                List<OngoingOrdersList> data = personalAdvertisingBillsAddTaskDetailAdapter.getData();
                for (OngoingOrdersList datum : data) {
                    if (!datum.isCheck()) {
                        check = false;
                        break;
                    }
                }
                chkTaskAllElection.setChecked(check);
            }
        });
        return personalAdvertisingBillsAddTaskDetailAdapter;
    }

    @OnClick({R.id.btnFinishAdding, R.id.chkTaskAllElection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnFinishAdding:
                //订单编号集合
                List<String> orderList = new ArrayList<>();
                List<OngoingOrdersList> data = personalAdvertisingBillsAddTaskDetailAdapter.getData();
                for (OngoingOrdersList datum : data) {
                    boolean check = datum.isCheck();
                    if (check) {
                        String orderCode = datum.getOrderCode();
                        orderList.add(orderCode);
                    }
                }
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("staff", getPhone());
                hashMap.put("orderCodes", orderList);
                hashMap.put("orderType", "3");
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
                RxUtils.getObservable(ServiceUrl.getUserApi().addOrder(requestBody))
                        .compose(this.<HttpResult<Object>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                            @Override
                            protected void onSuccess(Object response) {
                                refresh();
                                setFragmentResult(ForResultCode.RESULT_OK.getCode(), new Intent());
                            }
                        });
                break;
            case R.id.chkTaskAllElection:
                List<OngoingOrdersList> ongoingOrdersLists = personalAdvertisingBillsAddTaskDetailAdapter.getData();
                for (OngoingOrdersList datum : ongoingOrdersLists) {
                    datum.setCheck(chkTaskAllElection.isChecked());
                }
                personalAdvertisingBillsAddTaskDetailAdapter.notifyDataSetChanged();
                break;
            default:
        }
    }

    private AdvertisingBillsBean getAdvertisingBillsBean() {
        if (getArguments() != null) {
            return (AdvertisingBillsBean) getArguments().getSerializable(ConstantUtil.ADVERTISING_BILLS);
        }
        return new AdvertisingBillsBean();
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
    protected String getActivityTitle() {
        return getString(R.string.assignment_task);
    }

}
