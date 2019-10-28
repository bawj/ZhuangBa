package com.xiaomai.zhuangba.fragment.masterworker;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.AdvertisingBillsAdapter;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.fragment.masterworker.advertising.AdvertisingBillDetailFragment;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/30 0030
 * 广告单
 */
public class AdvertisingBillsFragment extends BaseMasterEmployerContentFragment {

    private AdvertisingBillsAdapter advertisingBillsAdapter;

    public static AdvertisingBillsFragment newInstance() {
        Bundle args = new Bundle();
        AdvertisingBillsFragment fragment = new AdvertisingBillsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void update(String code ,String address, Handler handler) {
        super.update(code ,address, handler);
        if (StringTypeExplain.REFRESH_ADVERTISING_BILLS_FRAGMENT.getCode().equals(code) && iModule != null) {
            iModule.requestAdvertisingBills();
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_advertising_bills;
    }

    @Override
    public void onBaseLoadMoreRequested() {
        if (iModule != null){
            iModule.requestAdvertisingBills();
        }
    }

    @Override
    public void refreshAdvertisingSuccess(List<AdvertisingBillsBean> advertisingBillsBeans) {
        super.refreshAdvertisingSuccess(advertisingBillsBeans);
        if (orderListAdapter != null) {
            advertisingBillsAdapter.setNewData(advertisingBillsBeans);
        }
    }

    @Override
    public void loadMoreAdvertisingSuccess(List<AdvertisingBillsBean> advertisingBillsBeans) {
        if (orderListAdapter != null) {
            advertisingBillsAdapter.addData(advertisingBillsBeans);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        AdvertisingBillsBean advertisingBillsBean = (AdvertisingBillsBean) view.findViewById(R.id.tvItemOrdersTitle).getTag();
        startFragment(AdvertisingBillDetailFragment.newInstance(advertisingBillsBean));
    }

    @Override
    public BaseQuickAdapter getBaseOrderAdapter() {
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
