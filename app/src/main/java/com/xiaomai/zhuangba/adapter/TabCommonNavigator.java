package com.xiaomai.zhuangba.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.xiaomai.zhuangba.R;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.Arrays;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public class TabCommonNavigator extends CommonNavigatorAdapter {

    private ViewPager mViewPager;
    private List<String> dataList;

    public TabCommonNavigator(String[] title, ViewPager mViewPager) {
        dataList = Arrays.asList(title);
        this.mViewPager = mViewPager;
    }

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
        simplePagerTitleView.setText(dataList.get(index));
        simplePagerTitleView.setTextSize(18);
        simplePagerTitleView.setNormalColor(context.getResources().getColor(R.color.tool_lib_gray_777777));
        simplePagerTitleView.setSelectedColor(context.getResources().getColor(R.color.tool_lib_gray_222222));

        //字体加粗
        TextPaint paint = simplePagerTitleView.getPaint();
        paint.setFakeBoldText(true);

        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(index);
            }
        });
        return simplePagerTitleView;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        linePagerIndicator.setLineHeight(UIUtil.dip2px(context, 4));
        linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 24));
        linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3));
        linePagerIndicator.setStartInterpolator(new AccelerateInterpolator());
        linePagerIndicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        linePagerIndicator.setColors(context.getResources().getColor(R.color.tool_lib_orange_FFFF5C2B));
        return linePagerIndicator;
    }

    @Override
    public float getTitleWeight(Context context, int index) {
        if (index == 0) {
            return 1.0f;
        } else {
            return 1.0f;
        }
    }

}
