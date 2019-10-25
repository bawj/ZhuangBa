package com.xiaomai.zhuangba.fragment.masterworker;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.InspectionSheetAdapter;
import com.xiaomai.zhuangba.data.bean.InspectionSheetBean;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.fragment.masterworker.inspection.InspectionSheetDetailFragment;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/10/8 0008
 */
public class InspectionSheetFragment extends BaseMasterEmployerContentFragment {

    private InspectionSheetAdapter inspectionSheetAdapter;

    public static InspectionSheetFragment newInstance() {
        Bundle args = new Bundle();
        InspectionSheetFragment fragment = new InspectionSheetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_inspection_sheet;
    }

    @Override
    public void update(String code, String address, Handler handler) {
        super.update(code, address, handler);
        if (StringTypeExplain.INSPECTION_SHEET_BILLS_FRAGMENT.getCode().equals(code) && iModule != null) {
            //刷新
            iModule.requestInspectionSheet();
        }
    }

    @Override
    public void onBaseLoadMoreRequested() {
        if (iModule != null) {
            iModule.requestAdvertisingBills();
        }
    }

    @Override
    public void refreshInspectionSuccess(List<InspectionSheetBean> inspectionSheetBeans) {
        super.refreshInspectionSuccess(inspectionSheetBeans);
        if (inspectionSheetAdapter != null) {
            inspectionSheetAdapter.setNewData(inspectionSheetBeans);
        }
    }

    @Override
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
    public BaseQuickAdapter getBaseOrderAdapter() {
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
