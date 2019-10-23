package com.xiaomai.zhuangba.fragment.personal.master.assignment;

import android.content.Intent;
import android.os.Bundle;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PersonalAdvertisingBillsAdapter;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.HashMap;
import java.util.List;

import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/10/16 0016
 * <p>
 * 分配任务 广告单
 */
public class PersonalAdvertisingBillsFragment extends BaseListFragment {

    private static final String PHONE = "phone";
    private PersonalAdvertisingBillsAdapter personalAdvertisingBillsAdapter;

    public static PersonalAdvertisingBillsFragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(PHONE, phone);
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
                delAdvertisingBills(advertisingBillsBean, pos);
            }

            @Override
            public void onItemClick(AdvertisingBillsBean advertisingBillsBean) {
                //广告单详情
                startFragmentForResult(PersonalAdvertisingBillsDetailFragment.newInstance(advertisingBillsBean) , ForResultCode.START_FOR_RESULT_CODE.getCode());
            }
        });
        return personalAdvertisingBillsAdapter;
    }

    private void delAdvertisingBills(AdvertisingBillsBean advertisingBillsBean,final int pos) {
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
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
        RxUtils.getObservable(ServiceUrl.getUserApi().deleteAllOrder(requestBody))
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        List<AdvertisingBillsBean> data = personalAdvertisingBillsAdapter.getData();
                        if (pos >= 0 && pos < data.size()) {
                            data.remove(pos);
                            personalAdvertisingBillsAdapter.notifyItemRemoved(pos);
                            personalAdvertisingBillsAdapter.notifyDataSetChanged();
                        }
                    }
                });

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


    @OnClick(R.id.btnAddTask)
    public void onViewClicked() {
        //广告单添加任务
        startFragmentForResult(PersonalAdvertisingBillsAddTaskFragment.newInstance(getPhone()), ForResultCode.START_FOR_RESULT_CODE.getCode());
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()) {
                //刷新
                refresh();
            }
        }
    }

}
