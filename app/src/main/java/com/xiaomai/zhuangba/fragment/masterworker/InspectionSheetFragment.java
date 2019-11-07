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
import com.xiaomai.zhuangba.adapter.InspectionSheetAdapter;
import com.xiaomai.zhuangba.data.bean.InspectionSheetBean;
import com.xiaomai.zhuangba.data.bean.RefreshBaseList;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.fragment.masterworker.inspection.InspectionSheetDetailFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/10/8 0008
 */
public class InspectionSheetFragment extends BaseListFragment<IBaseModule ,InspectionSheetAdapter> {

    private InspectionSheetAdapter inspectionSheetAdapter;

    public static InspectionSheetFragment newInstance() {
        Bundle args = new Bundle();
        InspectionSheetFragment fragment = new InspectionSheetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        requestInspectionSheet();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        requestInspectionSheet();
    }

    public void requestInspectionSheet() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getMasterHandleInspectionOrder(getPage()
                , StaticExplain.PAGE_NUM.getCode()))
                .compose(this.<HttpResult<RefreshBaseList<InspectionSheetBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<RefreshBaseList<InspectionSheetBean>>() {
                    @Override
                    protected void onSuccess(RefreshBaseList<InspectionSheetBean> response) {
                        List<InspectionSheetBean> advertisingBillsBeans = response.getList();
                        if (getPage() == StaticExplain.PAGE_NUMBER.getCode()) {
                            //刷新
                            refreshInspectionSuccess(advertisingBillsBeans);
                            finishRefresh();
                        } else {
                            //加载
                            loadInspectionSuccess(advertisingBillsBeans);
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
        return R.layout.fragment_inspection_sheet;
    }


    public void refreshInspectionSuccess(List<InspectionSheetBean> inspectionSheetBeans) {
        if (inspectionSheetAdapter != null) {
            inspectionSheetAdapter.setNewData(inspectionSheetBeans);
        }
    }

    public void loadInspectionSuccess(List<InspectionSheetBean> inspectionSheetBeans) {
        if (inspectionSheetAdapter != null) {
            inspectionSheetAdapter.addData(inspectionSheetBeans);
        }
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        InspectionSheetBean inspectionSheetBean = (InspectionSheetBean) view.findViewById(R.id.tvItemOrdersTitle).getTag();
        startFragment(InspectionSheetDetailFragment.newInstance(inspectionSheetBean));
    }

    @Override
    public InspectionSheetAdapter getBaseListAdapter() {
        inspectionSheetAdapter = new InspectionSheetAdapter();
        return inspectionSheetAdapter;
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
