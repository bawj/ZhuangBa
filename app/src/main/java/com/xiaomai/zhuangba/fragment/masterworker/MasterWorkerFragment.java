package com.xiaomai.zhuangba.fragment.masterworker;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ExamplePagerAdapter;
import com.xiaomai.zhuangba.adapter.HomeCommonNavigatorAdapter;
import com.xiaomai.zhuangba.fragment.masterworker.table.MasterHomeFragment;
import com.xiaomai.zhuangba.fragment.masterworker.table.MasterOrderFragment;
import com.xiaomai.zhuangba.fragment.personal.master.MasterPersonalFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 * <p>
 * 师傅端
 */
public class MasterWorkerFragment extends BaseFragment {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;


    public static MasterWorkerFragment newInstance() {
        Bundle args = new Bundle();
        MasterWorkerFragment fragment = new MasterWorkerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        String[] titleName = new String[]{getString(R.string.homepage), getString(R.string.order), getString(R.string.my)};
        List<String> mDataTitleName = Arrays.asList(titleName);
        //未选中
        Integer[] titleImgUnChecked = new Integer[]{R.drawable.ic_home_page_unchecked, R.drawable.ic_order_unchecked, R.drawable.ic_my_unchecked};
        List<Integer> mDataTitleImgUnChecked = Arrays.asList(titleImgUnChecked);
        //选中
        Integer[] titleImgSelection = new Integer[]{R.drawable.ic_homepage_selection, R.drawable.ic_order_selection, R.drawable.ic_my_selection};
        List<Integer> mDataTitleImgSelection = Arrays.asList(titleImgSelection);
        //fragment
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(MasterHomeFragment.newInstance());
        fragmentList.add(MasterOrderFragment.newInstance());
        fragmentList.add(MasterPersonalFragment.newInstance());
        ExamplePagerAdapter mExamplePagerAdapter = new ExamplePagerAdapter(getChildFragmentManager(), fragmentList, mDataTitleName);
        mViewPager.setAdapter(mExamplePagerAdapter);
        initMagicIndicator(mDataTitleName, mDataTitleImgUnChecked, mDataTitleImgSelection);
    }

    private void initMagicIndicator(List<String> mDataTitleName, List<Integer> mDataTitleImgUnChecked, List<Integer> mDataTitleImgSelection) {
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        HomeCommonNavigatorAdapter homeCommonNavigatorAdapter = new HomeCommonNavigatorAdapter(
                mDataTitleName, mDataTitleImgUnChecked, mDataTitleImgSelection, mViewPager);
        commonNavigator.setAdapter(homeCommonNavigatorAdapter);
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_master_worker;
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
    protected boolean translucentFull() {
        return true;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }
}

