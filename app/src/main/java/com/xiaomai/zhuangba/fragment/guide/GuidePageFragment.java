package com.xiaomai.zhuangba.fragment.guide;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.GuidePageAdapter;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 * <p>
 * 第一次进入广告页
 */
public class GuidePageFragment extends BaseFragment {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    public static GuidePageFragment newInstance() {
        Bundle args = new Bundle();
        GuidePageFragment fragment = new GuidePageFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected IBaseModule initModule() {
        return null;
    }
    @Override
    public void initView() {
        mViewPager.setAdapter(new GuidePageAdapter(getChildFragmentManager(),
                R.drawable.bg_guide_one, R.drawable.bg_guide_two, R.drawable.bg_guide_three, R.drawable.bg_guide_four));
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_guide_page;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }
}
