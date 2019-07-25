package com.xiaomai.zhuangba.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiaomai.zhuangba.fragment.base.BaseMasterEmployerContentFragment;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public class PagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private List<BaseMasterEmployerContentFragment> fragmentList;

    public PagerFragmentAdapter(FragmentManager fm, List<BaseMasterEmployerContentFragment> fragmentList, String[] titles) {
        super(fm);
        this.mTitles = titles;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }


}
