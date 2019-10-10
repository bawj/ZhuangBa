package com.xiaomai.zhuangba.fragment.masterworker.inspection;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.InspectionSheetDetailAdapter;
import com.xiaomai.zhuangba.data.bean.InspectionSheetBean;
import com.xiaomai.zhuangba.data.bean.InspectionSheetDetailBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.PatrolStatusUtil;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/10/8 0008
 */
public class InspectionSheetDetailFragment extends BaseListFragment<IBaseModule, InspectionSheetDetailAdapter> {

    private InspectionSheetDetailAdapter inspectionSheetDetailAdapter;
    private static final String INSPECTION_SHEET = "inspection_sheet";

    public static InspectionSheetDetailFragment newInstance(InspectionSheetBean inspectionSheetBean) {
        Bundle args = new Bundle();
        args.putParcelable(INSPECTION_SHEET , inspectionSheetBean);
        InspectionSheetDetailFragment fragment = new InspectionSheetDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public InspectionSheetDetailAdapter getBaseListAdapter() {
        inspectionSheetDetailAdapter = new InspectionSheetDetailAdapter();
        return inspectionSheetDetailAdapter;
    }

    @Override
    public void onBaseRefresh(@NonNull RefreshLayout refreshLayout) {
        //刷新
        requestOrderList();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        //加载
        requestOrderList();
    }

    private void requestOrderList() {
        InspectionSheetBean inspectionSheetBean = getInspectionSheetBean();
        HashMap<String, String> hashMap = new HashMap<>();
        //省
        hashMap.put("province", inspectionSheetBean.getProvince());
        //市
        hashMap.put("city", inspectionSheetBean.getCity());
        //区
        hashMap.put("district", inspectionSheetBean.getDistrict());
        //街道
        hashMap.put("street", inspectionSheetBean.getStreet());
        //小区
        hashMap.put("villageName", inspectionSheetBean.getVillageName());
        //页数
        hashMap.put("pageNum", String.valueOf(getPage()));
        //一页数量
        hashMap.put("pageSize", String.valueOf(StaticExplain.PAGE_NUM.getCode()));
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleInspectionOrderList(requestBody))
                .compose(this.<HttpResult<RefreshBaseList<InspectionSheetDetailBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<InspectionSheetDetailBean>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<InspectionSheetDetailBean> refreshBaseList) {
                        List<InspectionSheetDetailBean> inspectionSheetDetailBeans = refreshBaseList.getList();
                        //刷新
                        if (getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            inspectionSheetDetailAdapter.setNewData(inspectionSheetDetailBeans);
                            finishRefresh();
                        } else {
                            //加载
                            inspectionSheetDetailAdapter.addData(inspectionSheetDetailBeans);
                        }
                        if (inspectionSheetDetailBeans.size() < StaticExplain.PAGE_NUM.getCode()) {
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
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        super.onItemClick(adapter, view, position);
        InspectionSheetDetailBean inspectionSheetDetailBean = (InspectionSheetDetailBean) view.findViewById(R.id.tvItemInspectionSheetTitle).getTag();
        PatrolStatusUtil.startMasterPatrol(getBaseFragmentActivity(), inspectionSheetDetailBean.getOrderCode()
                ,inspectionSheetDetailBean.getOrderType(), inspectionSheetDetailBean.getOrderStatus());
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
        return R.layout.fragment_inspection_sheet_detail;
    }

    @Override
    protected String getActivityTitle() {
        String villageName = getInspectionSheetBean().getVillageName();
        if (TextUtils.isEmpty(villageName)) {
            villageName = getInspectionSheetBean().getStreet();
        }
        return villageName;
    }

    private InspectionSheetBean getInspectionSheetBean(){
        if (getArguments() != null){
            return getArguments().getParcelable(INSPECTION_SHEET);
        }
        return new InspectionSheetBean();
    }
}
