package com.xiaomai.zhuangba.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiaomai.zhuangba.fragment.personal.master.patrol.PatrolInspectionRecordsPhotoDetailFragment;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/10/11 0011
 */
public class PhotoPagerFragmentAdapter extends FragmentPagerAdapter {
    private List<PatrolInspectionRecordsPhotoDetailFragment> listFragment;
    private String[] mTitles;

    public PhotoPagerFragmentAdapter(FragmentManager fm, List<PatrolInspectionRecordsPhotoDetailFragment> listFragment, String[] titles) {
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
