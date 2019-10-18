package com.xiaomai.zhuangba.fragment.personal.master.assignment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.AssignmentTaskFragmentAdapter;
import com.xiaomai.zhuangba.adapter.TabIncomeNavigator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/10/16 0016
 * <p>
 * 分配任务
 */
public class AssignmentTaskFragment extends BaseFragment {

    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private static final String PHONE = "phone";

    public static AssignmentTaskFragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(PHONE, phone);
        AssignmentTaskFragment fragment = new AssignmentTaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        String[] tabTitle = getTabTitle();
        List<BaseFragment> fragmentList = getListFragment();
        mViewPager.setAdapter(new AssignmentTaskFragmentAdapter(getChildFragmentManager(), fragmentList, tabTitle));

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new TabIncomeNavigator(tabTitle, mViewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    private List<BaseFragment> getListFragment() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(PersonalNeedDealWithFragment.newInstance(getPhone()));
        fragmentList.add(PersonalAdvertisingBillsFragment.newInstance(getPhone()));
        return fragmentList;
    }

    private String[] getTabTitle() {
        return new String[]{getString(R.string.installation_list), getString(R.string.advertising_bills)};
    }

    private String getPhone(){
        if (getArguments() != null){
            return getArguments().getString(PHONE);
        }
        return "";
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_assignment_task;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.assignment_task);
    }
}
