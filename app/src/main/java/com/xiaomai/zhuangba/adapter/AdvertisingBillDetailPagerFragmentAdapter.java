package com.xiaomai.zhuangba.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiaomai.zhuangba.fragment.masterworker.advertising.WholeAdvertisingFragment;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/30 0030
 */
public class AdvertisingBillDetailPagerFragmentAdapter extends FragmentPagerAdapter {

    private List<WholeAdvertisingFragment> listFragment;
    private String[] mTitles;

    public AdvertisingBillDetailPagerFragmentAdapter(FragmentManager fm, List<WholeAdvertisingFragment> listFragment, String[] titles) {
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
