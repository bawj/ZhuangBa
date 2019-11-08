package com.xiaomai.zhuangba.fragment.masterworker.table;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.MasterOrderAdapter;
import com.xiaomai.zhuangba.adapter.TabCommonNavigator;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.fragment.masterworker.AdvertisingBillsFragment;
import com.xiaomai.zhuangba.fragment.masterworker.GuaranteeFragment;
import com.xiaomai.zhuangba.fragment.masterworker.InspectionSheetFragment;
import com.xiaomai.zhuangba.fragment.masterworker.NeedDealWithFragment;
import com.xiaomai.zhuangba.weight.dialog.ScreenDialog;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/11/6 0006
 */
public class MasterOrderFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tvScreen)
    TextView tvScreen;

    public static MasterOrderFragment newInstance() {
        Bundle args = new Bundle();
        MasterOrderFragment fragment = new MasterOrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        List<BaseListFragment> fragmentList = new ArrayList<>();
        //安装单
        fragmentList.add(NeedDealWithFragment.newInstance());
        //维保单
        fragmentList.add(GuaranteeFragment.newInstance());
        //广告单
        fragmentList.add(AdvertisingBillsFragment.newInstance());
        //巡查单
        fragmentList.add(InspectionSheetFragment.newInstance());
        //title
        String[] strTitle = getTabTitle();
        mViewPager.setAdapter(new MasterOrderAdapter(getChildFragmentManager(), fragmentList, strTitle));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new TabCommonNavigator(strTitle, mViewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
        mViewPager.addOnPageChangeListener(this);
    }

    public String[] getTabTitle() {
        return new String[]{getString(R.string.need_to_be_dealt_with), getString(R.string.maintenance_policy_title)
                , getString(R.string.advertising_bills), getString(R.string.inspection_sheet)};
    }

    @Override
    public void onPageSelected(int i) {
        if (i == 1 || i == 2) {
            tvScreen.setVisibility(View.VISIBLE);
        } else {
            tvScreen.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tvScreen)
    public void onViewClicked() {
        ScreenDialog.getInstance().showRightDialog(getActivity());
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
    public boolean isBackArrow() {
        return false;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }
}
