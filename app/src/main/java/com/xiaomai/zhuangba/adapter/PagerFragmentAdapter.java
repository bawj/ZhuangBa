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

    private List<BaseMasterEmployerContentFragment> listFragment;
    private String[] mTitles;

    public PagerFragmentAdapter(FragmentManager fm, List<BaseMasterEmployerContentFragment> listFragment, String[] titles) {
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
