package com.xiaomai.zhuangba.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.toollib.base.BaseFragment;

import java.util.List;

public class BaseViewPagerAdapter<T extends BaseFragment> extends FragmentPagerAdapter {

    private List<T> listFragment;
    private String[] mTitles;

    public BaseViewPagerAdapter(FragmentManager fm, List<T> listFragment, String[] titles) {
        super(fm);
        this.mTitles = titles;
        this.listFragment = listFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
