package com.xiaomai.zhuangba.fragment.personal.master;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.IncomePagerFragmentAdapter;
import com.xiaomai.zhuangba.adapter.TabIncomeNavigator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/24 0024
 * 持续收入
 */
public class ContinuousIncomeFragment extends BaseFragment {

    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    public static ContinuousIncomeFragment newInstance() {
        Bundle args = new Bundle();
        ContinuousIncomeFragment fragment = new ContinuousIncomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        String[] title = new String[]{getString(R.string.maintenance_policy_title),
                getString(R.string.advertising_replacement)};
        mViewPager.setAdapter(new IncomePagerFragmentAdapter(getChildFragmentManager(), title));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new TabIncomeNavigator(title, mViewPager));
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void rightIconClick(View view) {
        // TODO: 2019/8/24 0024 问号
    }

    @Override
    public int getRightIcon() {
        return R.drawable.ic_question_mark;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_continuous_income;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.continuous_income);
    }


}
