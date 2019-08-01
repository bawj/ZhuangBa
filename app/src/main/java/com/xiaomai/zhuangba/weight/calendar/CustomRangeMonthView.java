package com.xiaomai.zhuangba.weight.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.RangeMonthView;

import static com.haibin.calendarview.CalendarUtil.getMonthDaysCount;
import static com.haibin.calendarview.CalendarUtil.getWeekFormCalendar;

/**
 * @author jd
 * @date 2019/5/29 20:25
 * @description 自定义月视图
 */

public class CustomRangeMonthView extends RangeMonthView {

    private int mRadius;

    public CustomRangeMonthView(Context context) {
        super(context);
    }

    @Override
    protected void onPreviewHook() {
        mRadius = Math.min(mItemWidth, mItemHeight) / 5 * 2;

    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme,
                                     boolean isSelectedPre, boolean isSelectedNext) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        int days = getMonthDaysCount(calendar.getYear(), calendar.getMonth());
        boolean isWeekSix = getWeekFormCalendar(calendar) == 6;
        boolean isWeekSeven = getWeekFormCalendar(calendar) == 0;
        boolean isfirstDay = calendar.getDay() == 1;
        boolean isLastDay = calendar.getDay() == days;
        if (isSelectedPre) {
            if (isSelectedNext) {
                mSelectedPaint.setColor(Color.parseColor("#FFEFF0"));
                if ( (isLastDay && isWeekSeven) || (isfirstDay && isWeekSix)){
                    canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
                } else if (isWeekSix || (isLastDay&&!isWeekSeven)){
                    //右边弧
                    RectF rectF = new RectF(x, cy - mRadius, x + mItemWidth/2, cy + mRadius);
                    RectF arc = new RectF(x + mItemWidth/2 - mRadius , cy - mRadius,  x + mItemWidth/2 + mRadius, cy + mRadius);
                    canvas.drawArc(arc,270,180,false,mSelectedPaint);
                    canvas.drawRect(rectF, mSelectedPaint);
                } else if (isWeekSeven || isfirstDay) {
                    //左边弧形
                    RectF rectF = new RectF(x+ mItemWidth/2, cy - mRadius, x + mItemWidth, cy + mRadius);
                    RectF arc = new RectF(x + mItemWidth/2 - mRadius , cy - mRadius,  x + mItemWidth/2 + mRadius, cy + mRadius);
                    canvas.drawArc(arc,90,270,false,mSelectedPaint);
                    canvas.drawRect(rectF, mSelectedPaint);
                } else {
                    canvas.drawRect(x, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
                }
            } else {//最后一个，the last
                mSelectedPaint.setColor(Color.parseColor("#FFEFF0"));
                if (!isWeekSeven && !isfirstDay){
                    canvas.drawRect(x, cy - mRadius, cx, cy + mRadius, mSelectedPaint);
                }
                mSelectedPaint.setColor(Color.parseColor("#E60113"));
                canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
            }
        } else {
            if (isSelectedNext) {
                mSelectedPaint.setColor(Color.parseColor("#FFEFF0"));
                if (!isWeekSix && !isLastDay){
                    canvas.drawRect(cx, cy - mRadius, x + mItemWidth, cy + mRadius, mSelectedPaint);
                }
            }
            mSelectedPaint.setColor(Color.parseColor("#E60113"));
            canvas.drawCircle(cx, cy, mRadius, mSelectedPaint);
        }
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int cy = y + mItemHeight / 2;
        canvas.drawCircle(cx, cy, mRadius, mSchemePaint);
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        float baselineY = mTextBaseLine + y;
        int cx = x + mItemWidth / 2;

        boolean isInRange = isInRange(calendar);
        boolean isEnable = !onCalendarIntercept(calendar);

        if (hasScheme) {
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSchemeTextPaint
                    );
        } else if (isSelected) {
            mSelectTextPaint.setColor(Color.parseColor("#E60113"));
            canvas.drawText(String.valueOf(calendar.getDay()),
                    cx,
                    baselineY,
                    mSelectTextPaint);
        } else {
            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
                    calendar.isCurrentDay() ? mCurDayTextPaint :
                            calendar.isCurrentMonth() && isInRange && isEnable ? mCurMonthTextPaint : mOtherMonthTextPaint);
        }
    }
}
