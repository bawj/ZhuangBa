package com.xiaomai.zhuangba.fragment.masterworker;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.AdvertisingBillsAdapter;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.data.bean.LatAndLon;
import com.xiaomai.zhuangba.data.bean.OuterLayerAdvertisingBills;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.fragment.masterworker.advertising.AllocationListFragment;
import com.xiaomai.zhuangba.fragment.masterworker.map.PlotDistributionFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/30 0030
 * 广告单
 */
public class AdvertisingBillsFragment extends BaseListFragment<IBaseModule, AdvertisingBillsAdapter> {

    /**
     * 筛选条件
     */
    private List<String> teamList = new ArrayList<>();
    /**
     * 筛选条件
     */
    private List<String> equipmentList = new ArrayList<>();
    /**
     * 筛选条件
     */
    private List<String> batchCodeList = new ArrayList<>();
    private AdvertisingBillsAdapter advertisingBillsAdapter;

    @BindView(R.id.tvAdvertisingNumber)
    TextView tvAdvertisingNumber;
    @BindView(R.id.tvPlotDistribution)
    TextView tvPlotDistribution;

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
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pageNum", getPage());
        hashMap.put("pageSize", StaticExplain.PAGE_NUM.getCode());
//        hashMap.put("teamList", getTeamList());
//        hashMap.put("equipmentList", getEquipmentList());
//        hashMap.put("batchCodeList", getBatchCodeList());

        List<String> teamList = getTeamList();
        StringBuilder team = new StringBuilder();
        for (int i = 0; i < teamList.size(); i++) {
            team.append(teamList.get(i));
            if (i < teamList.size() - 1) {
                team.append(",");
            }
        }

        List<String> equipmentList = getEquipmentList();
        StringBuilder equipment = new StringBuilder();
        for (int i = 0; i < equipmentList.size(); i++) {
            equipment.append(equipmentList.get(i));
            if (i < equipmentList.size() - 1) {
                equipment.append(",");
            }
        }
        List<String> batchCodeList = getBatchCodeList();
        StringBuilder batchCode = new StringBuilder();
        for (int i = 0; i < batchCodeList.size(); i++) {
            batchCode.append(batchCodeList.get(i));
            if (i < batchCodeList.size() - 1) {
                batchCode.append(",");
            }
        }

        hashMap.put("teams", team.toString());
        hashMap.put("equipments", equipment.toString());
        hashMap.put("batchCodes", batchCode.toString());


        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleOrder(requestBody))
                .compose(this.<HttpResult<OuterLayerAdvertisingBills>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<OuterLayerAdvertisingBills>() {
                    @Override
                    protected void onSuccess(OuterLayerAdvertisingBills response) {
                        int num = response.getNum();
                        if (num == 0) {
                            tvPlotDistribution.setVisibility(View.GONE);
                            tvAdvertisingNumber.setVisibility(View.GONE);
                        } else {
                            tvPlotDistribution.setVisibility(View.VISIBLE);
                            tvAdvertisingNumber.setVisibility(View.VISIBLE);
                            tvAdvertisingNumber.setText(getString(R.string.total_quantity, String.valueOf(num)));
                        }
                        List<AdvertisingBillsBean> advertisingBillsBeans = response.getList().getList();
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

    @OnClick(R.id.tvPlotDistribution)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvPlotDistribution:
                //小区分布
                RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleAdvertisingOrderLatAndLon())
                        .compose(this.<HttpResult<List<LatAndLon>>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<List<LatAndLon>>(getActivity()) {
                            @Override
                            protected void onSuccess(List<LatAndLon> latAndLonList) {
                                startFragment(PlotDistributionFragment.newInstance(getString(R.string.plot_distribution) , latAndLonList));
                            }
                        });
                break;
            default:
        }
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
        //startFragment(AdvertisingBillDetailFragment.newInstance(advertisingBillsBean));
        // TODO: 2019/12/9 0009 dev-1.7.0 修改
        startFragment(AllocationListFragment.newInstance(advertisingBillsBean));
    }

    @Override
    public AdvertisingBillsAdapter getBaseListAdapter() {
        advertisingBillsAdapter = new AdvertisingBillsAdapter();
        return advertisingBillsAdapter;
    }


    public List<String> getTeamList() {
        if (teamList == null) {
            return new ArrayList<>();
        }
        return teamList;
    }

    public void setTeamList(List<String> teamList) {
        this.teamList = teamList;
    }

    public List<String> getEquipmentList() {
        if (equipmentList == null) {
            return new ArrayList<>();
        }
        return equipmentList;
    }

    public void setEquipmentList(List<String> equipmentList) {
        this.equipmentList = equipmentList;
    }

    public List<String> getBatchCodeList() {
        if (batchCodeList == null) {
            return new ArrayList<>();
        }
        return batchCodeList;
    }

    public void setBatchCodeList(List<String> batchCodeList) {
        this.batchCodeList = batchCodeList;
    }

    public void refrshAdvertisingBills() {
        refresh();
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    public boolean isBackArrow() {
        return false;
    }


    @Override
    protected boolean translucentFull() {
        return true;
    }
}
