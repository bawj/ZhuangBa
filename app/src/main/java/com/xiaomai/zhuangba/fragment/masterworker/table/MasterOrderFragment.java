package com.xiaomai.zhuangba.fragment.masterworker.table;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;
import com.xiaomai.zhuangba.fragment.masterworker.AdvertisingBillsFragment;
import com.xiaomai.zhuangba.fragment.masterworker.InspectionSheetFragment;
import com.xiaomai.zhuangba.fragment.masterworker.NeedDealWithFragment;
import com.xiaomai.zhuangba.fragment.masterworker.OrderPoolFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/11/6 0006
 */
public class MasterOrderFragment extends BaseFragment {

    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    public static MasterOrderFragment newInstance() {
        Bundle args = new Bundle();
        MasterOrderFragment fragment = new MasterOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {

        List<BaseMasterEmployerContentFragment> fragmentList = new ArrayList<>();
        fragmentList.add(NeedDealWithFragment.newInstance());
        fragmentList.add(AdvertisingBillsFragment.newInstance());
        fragmentList.add(InspectionSheetFragment.newInstance());

    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_order;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isBackArrow() {
        return false;
    }
}
