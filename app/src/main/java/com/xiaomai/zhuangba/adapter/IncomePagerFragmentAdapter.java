package com.xiaomai.zhuangba.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiaomai.zhuangba.fragment.personal.employer.AdvertisingReplacementFragment;
import com.xiaomai.zhuangba.fragment.personal.master.MasterMaintenancePolicyFragment;

/**
 * @author Administrator
 * @date 2019/8/24 0024
 */
public class IncomePagerFragmentAdapter extends FragmentPagerAdapter {

    private String[] mTitles;

    public IncomePagerFragmentAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return MasterMaintenancePolicyFragment.newInstance();
        } else if (position == 1) {
            return AdvertisingReplacementFragment.newInstance();
        }
        return null;
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
