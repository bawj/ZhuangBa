package com.xiaomai.zhuangba.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.toollib.base.BaseFragment;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/10/16 0016
 */
public class AssignmentTaskFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> listFragment;
    private String[] mTitles;

    public AssignmentTaskFragmentAdapter(FragmentManager fm, List<BaseFragment> listFragment, String[] titles) {
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
