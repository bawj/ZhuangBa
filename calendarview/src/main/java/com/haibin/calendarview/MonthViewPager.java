/*
 * Copyright (C) 2016 huanghaibin_dev <huanghaibin_dev@163.com>
 * WebSite https://github.com/MiracleTimes-Dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haibin.calendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.util.List;


/**
 * 月份切换ViewPager，自定义适应高度
 */
public final class MonthViewPager extends ViewPager {

    private boolean isUpdateMonthView;

    private int mMonthCount;

    private CalendarViewDelegate mDelegate;

    private int mNextViewHeight, mPreViewHeight, mCurrentViewHeight;

    CalendarLayout mParentLayout;

    WeekViewPager mWeekPager;

    WeekBar mWeekBar;

    /**
     * 是否使用滚动到某一天
     */
    private boolean isUsingScrollToCalendar = false;

    public MonthViewPager(Context context) {
        this(context, null);
    }

    public MonthViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 初始化
     *
     * @param delegate delegate
     */
    void setup(CalendarViewDelegate delegate) {
        this.mDelegate = delegate;

        updateMonthViewHeight(mDelegate.getCurrentDay().getYear(),
                mDelegate.getCurrentDay().getMonth());

        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = mCurrentViewHeight;
        setLayoutParams(params);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mMonthCount = 12 * (mDelegate.getMaxYear() - mDelegate.getMinYear())
                - mDelegate.getMinYearMonth() + 1 +
                mDelegate.getMaxYearMonth();
        setAdapter(new MonthViewPagerAdapter());

    }

    /**
     * 更新月视图的高度
     *
     * @param year  year
     * @param month month
     */
    private void updateMonthViewHeight(int year, int month) {
        if (mDelegate.getMonthViewShowMode() == CalendarViewDelegate.MODE_ALL_MONTH) {//非动态高度就不需要了
            mCurrentViewHeight = 6 * mDelegate.getCalendarItemHeight();
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = mCurrentViewHeight;
            return;
        }

        mCurrentViewHeight = CalendarUtil.getMonthViewHeight(year, month,
                mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart(),
                mDelegate.getMonthViewShowMode());
        if (month == 1) {
            mPreViewHeight = CalendarUtil.getMonthViewHeight(year - 1, 12,
                    mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart(),
                    mDelegate.getMonthViewShowMode());
            mNextViewHeight = CalendarUtil.getMonthViewHeight(year, 2,
                    mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart(),
                    mDelegate.getMonthViewShowMode());
        } else {
            mPreViewHeight = CalendarUtil.getMonthViewHeight(year, month - 1,
                    mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart(),
                    mDelegate.getMonthViewShowMode());
            if (month == 12) {
                mNextViewHeight = CalendarUtil.getMonthViewHeight(year + 1, 1,
                        mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart(),
                        mDelegate.getMonthViewShowMode());
            } else {
                mNextViewHeight = CalendarUtil.getMonthViewHeight(year, month + 1,
                        mDelegate.getCalendarItemHeight(), mDelegate.getWeekStart(),
                        mDelegate.getMonthViewShowMode());
            }
        }
    }

    /**
     * 刷新
     */
    void notifyDataSetChanged() {
        mMonthCount = 12 * (mDelegate.getMaxYear() - mDelegate.getMinYear())
                - mDelegate.getMinYearMonth() + 1 +
                mDelegate.getMaxYearMonth();
        getAdapter().notifyDataSetChanged();
    }

    /**
     * 更新月视图Class
     */
    void updateMonthViewClass() {
        isUpdateMonthView = true;
        getAdapter().notifyDataSetChanged();
        isUpdateMonthView = false;
    }

    /**
     * 滚动到当前日期
     */
    void scrollToCurrent(boolean smoothScroll) {
        isUsingScrollToCalendar = true;
        int position = 12 * (mDelegate.getCurrentDay().getYear() - mDelegate.getMinYear()) +
                mDelegate.getCurrentDay().getMonth() - mDelegate.getMinYearMonth();
        int curItem = getCurrentItem();
        if (curItem == position) {
            isUsingScrollToCalendar = false;
        }

        setCurrentItem(position, smoothScroll);

        BaseMonthView view = (BaseMonthView) findViewWithTag(position);
        if (view != null) {
            view.setSelectedCalendar(mDelegate.getCurrentDay());
            view.invalidate();
        }

        if (mDelegate.mCalendarSelectListener != null && getVisibility() == VISIBLE) {
            mDelegate.mCalendarSelectListener.onCalendarSelect(mDelegate.mSelectedCalendar, false);
        }
    }

    /**
     * 更新标记日期
     */
    void updateScheme() {
        for (int i = 0; i < getChildCount(); i++) {
            BaseMonthView view = (BaseMonthView) getChildAt(i);
            view.update();
        }
    }

    /**
     * 清除选择范围
     */
    final void clearSelectRange() {
        for (int i = 0; i < getChildCount(); i++) {
            BaseMonthView view = (BaseMonthView) getChildAt(i);
            view.invalidate();
        }
    }

    /**
     * 清除单选选择
     */
    final void clearSingleSelect() {
        for (int i = 0; i < getChildCount(); i++) {
            BaseMonthView view = (BaseMonthView) getChildAt(i);
            view.mCurrentItem = -1;
            view.invalidate();
        }
    }

    /**
     * 清除单选选择
     */
    final void clearMultiSelect() {
        for (int i = 0; i < getChildCount(); i++) {
            BaseMonthView view = (BaseMonthView) getChildAt(i);
            view.mCurrentItem = -1;
            view.invalidate();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mDelegate.isMonthViewScrollable() && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDelegate.isMonthViewScrollable() && super.onInterceptTouchEvent(ev);
    }

    /**
     * 日历卡月份Adapter
     */
    public final class MonthViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mMonthCount;
        }

        @Override
        public int getItemPosition(Object object) {
            return isUpdateMonthView ? POSITION_NONE : super.getItemPosition(object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int year = (position + mDelegate.getMinYearMonth() - 1) / 12 + mDelegate.getMinYear();
            int month = (position + mDelegate.getMinYearMonth() - 1) % 12 + 1;
            BaseMonthView view;
            try {
                Constructor constructor = mDelegate.getMonthViewClass().getConstructor(Context.class);
                view = (BaseMonthView) constructor.newInstance(getContext());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
//            view.mMonthViewPager = MonthViewPager.this;
//            view.mParentLayout = mParentLayout;
            view.setup(mDelegate);
            view.setTag(position);
            view.initMonthWithDate(year, month);
            view.setSelectedCalendar(mDelegate.mSelectedCalendar);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            BaseView view = (BaseView) object;
            if (view == null) {
                return;
            }
            view.onDestroy();
            container.removeView(view);
        }
    }

}
