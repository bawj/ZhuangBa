package com.xiaomai.zhuangba.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/11/6 0006
 */
public class HomeCommonNavigatorAdapter extends CommonNavigatorAdapter {

    private List<String> mDataTitleName;
    private List<Integer> mDataTitleImgUnChecked;
    private List<Integer> mDataTitleImgSelection;
    private ViewPager mViewPager;

    public HomeCommonNavigatorAdapter(List<String> mDataTitleName, List<Integer> mDataTitleImgUnChecked,
                                      List<Integer> mDataTitleImgSelection, ViewPager mViewPager) {
        this.mDataTitleName = mDataTitleName;
        this.mDataTitleImgUnChecked = mDataTitleImgUnChecked;
        this.mDataTitleImgSelection = mDataTitleImgSelection;
        this.mViewPager = mViewPager;
    }

    @Override
    public int getCount() {
        return mDataTitleName.size();
    }

    @Override
    public IPagerTitleView getTitleView(final Context context,final int index) {
        CommonPagerTitleView commonPagerTitleView = new CommonPagerTitleView(context);
        View customLayout = LayoutInflater.from(context).inflate(R.layout.simple_pager_title_layout, null);
        final ImageView titleImg = customLayout.findViewById(R.id.title_img);
        final TextView titleText = customLayout.findViewById(R.id.title_text);
        titleImg.setImageResource(mDataTitleImgSelection.get(index));
        titleText.setText(mDataTitleName.get(index));
        commonPagerTitleView.setContentView(customLayout);

        commonPagerTitleView.setOnPagerTitleChangeListener(new CommonPagerTitleView.OnPagerTitleChangeListener() {

            @Override
            public void onSelected(int index, int totalCount) {
                titleText.setTextColor(context.getResources().getColor(R.color.tool_lib_red_EF2B2B));
                titleImg.setImageResource(mDataTitleImgSelection.get(index));
            }

            @Override
            public void onDeselected(int index, int totalCount) {
                titleText.setTextColor(context.getResources().getColor(R.color.tool_lib_gray_777777));
                titleImg.setImageResource(mDataTitleImgUnChecked.get(index));
            }

            @Override
            public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
                titleImg.setScaleX(1.3f + (0.8f - 1.3f) * leavePercent);
                titleImg.setScaleY(1.3f + (0.8f - 1.3f) * leavePercent);
            }

            @Override
            public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
                titleImg.setScaleX(0.8f + (1.3f - 0.8f) * enterPercent);
                titleImg.setScaleY(0.8f + (1.3f - 0.8f) * enterPercent);
            }
        });

        commonPagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(index);
            }
        });

        return commonPagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        return null;
    }
}
