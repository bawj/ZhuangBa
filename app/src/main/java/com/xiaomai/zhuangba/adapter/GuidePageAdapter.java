package com.xiaomai.zhuangba.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiaomai.zhuangba.fragment.guide.GuideFragment;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 */
public class GuidePageAdapter extends FragmentPagerAdapter {
    private int[] img;

    public GuidePageAdapter(FragmentManager fm, int... img) {
        super(fm);
        this.img = img;
    }

    @Override
    public Fragment getItem(int position) {
        return GuideFragment.newInstance(img[position]);
    }

    @Override
    public int getCount() {
        return img.length;
    }

}
