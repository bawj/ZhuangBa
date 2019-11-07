package com.xiaomai.zhuangba.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.toollib.base.BaseFragment;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/11/6 0006
 */
public class ExamplePagerAdapter extends FragmentPagerAdapter {

    private List<String> mDataTitleName;
    private List<BaseFragment> mDataFragmentList;

    public ExamplePagerAdapter(FragmentManager fm, List<BaseFragment> mDataFragmentList, List<String> mDataTitleName) {
        super(fm);
        this.mDataFragmentList = mDataFragmentList;
        this.mDataTitleName = mDataTitleName;
    }

    @Override
    public int getCount() {
        return mDataTitleName == null ? 0 : mDataTitleName.size();
    }

    @Override
    public Fragment getItem(int i) {
        return mDataFragmentList.get(i);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mDataTitleName.get(position);
    }

}
