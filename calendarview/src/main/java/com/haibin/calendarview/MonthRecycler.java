package com.haibin.calendarview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.haibin.calendarview.BaseMonthView;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarUtil;
import com.haibin.calendarview.CalendarViewDelegate;
import com.haibin.calendarview.MonthViewPager;
import com.haibin.calendarview.WeekBar;
import com.haibin.calendarview.WeekViewPager;

import java.lang.reflect.Constructor;
import java.util.List;

public class MonthRecycler extends RecyclerView {

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

    public MonthRecycler(Context context) {
        this(context, null);
    }

    public MonthRecycler(Context context, @Nullable AttributeSet attrs) {
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
        mCurrentViewHeight = 8 * mDelegate.getCalendarItemHeight();
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
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(new MonthRecyclerAdapter());

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
//        int curItem = getCurrentItem();
//        if (curItem == position) {
//            isUsingScrollToCalendar = false;
//        }

        scrollToPosition(position);

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

    /**
     * 滚动到指定日期
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    public void scrollToCalendar(int year, int month, int day, boolean smoothScroll) {
        isUsingScrollToCalendar = true;
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setCurrentDay(calendar.equals(mDelegate.getCurrentDay()));
        LunarCalendar.setupLunarCalendar(calendar);
        mDelegate.mIndexCalendar = calendar;
        mDelegate.mSelectedCalendar = calendar;
        mDelegate.updateSelectCalendarScheme();
        int y = calendar.getYear() - mDelegate.getMinYear();
        int position = 12 * y + calendar.getMonth() - mDelegate.getMinYearMonth();

        scrollToPosition(position);

        BaseMonthView view = (BaseMonthView) findViewWithTag(position);
        if (view != null) {
            view.setSelectedCalendar(mDelegate.mIndexCalendar);
            view.invalidate();
            if (mParentLayout != null) {
                mParentLayout.updateSelectPosition(view.getSelectedIndex(mDelegate.mIndexCalendar));
            }
        }
        if (mParentLayout != null) {
            int week = CalendarUtil.getWeekFromDayInMonth(calendar, mDelegate.getWeekStart());
            mParentLayout.updateSelectWeek(week);
        }

        if (mDelegate.mCalendarSelectListener != null) {
            mDelegate.mCalendarSelectListener.onCalendarSelect(calendar, false);
        }
        if (mDelegate.mInnerListener != null) {
            mDelegate.mInnerListener.onMonthDateSelected(calendar, false);
        }

        updateSelected();
    }

    /**
     * 更新选择效果
     */
    void updateSelected() {
        for (int i = 0; i < getChildCount(); i++) {
            BaseMonthView view = (BaseMonthView) getChildAt(i);
            view.setSelectedCalendar(mDelegate.mSelectedCalendar);
            view.invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mDelegate.isMonthViewScrollable() && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDelegate.isMonthViewScrollable() && super.onInterceptTouchEvent(ev);
    }


    class MonthRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            try {
                Constructor constructor = mDelegate.getMonthViewClass().getConstructor(Context.class);
                return new ViewHolder((BaseMonthView) constructor.newInstance(getContext()));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            int year = (position + mDelegate.getMinYearMonth() - 1) / 12 + mDelegate.getMinYear();
            int month = (position + mDelegate.getMinYearMonth() - 1) % 12 + 1;
            BaseMonthView view = (BaseMonthView) holder.itemView;

            view.setup(mDelegate);
            view.setTag(position);
            view.initMonthWithDate(year, month);
            view.setSelectedCalendar(mDelegate.mSelectedCalendar);

        }

        @Override
        public int getItemCount() {
            return mMonthCount;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        FrameLayout frameLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.fl);
        }
    }
}
