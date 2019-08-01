package com.xiaomai.zhuangba.weight.popup;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.toollib.util.ToastUtil;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.xiaomai.zhuangba.R;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @date 2019/7/31 0031
 * 日历筛选
 */
public class TimePopupWindow extends PopupWindow implements CalendarView.OnCalendarRangeSelectListener, View.OnClickListener {

    private Activity mContext;
    private OnTimeSelectListener onSelectListener;

    CalendarView calendarView;
    TextView tvSelectTime;
    TextView tvFinish;

    Calendar startCalendar, endCalendar;
    Map<String, Calendar> map = new HashMap<>();
    StringBuilder builder;

    private View popupView;

    @Override
    public void onClick(View v) {
        if (onSelectListener != null && startCalendar != null && endCalendar != null) {
            onSelectListener.onTimeSelect(startCalendar, endCalendar);
        }
        dismiss();
    }

    public interface OnTimeSelectListener {
        /**
         * 范围选择
         *
         * @param startCalendar
         * @param endCalendar
         */
        void onTimeSelect(Calendar startCalendar, Calendar endCalendar);
    }

    public TimePopupWindow(Activity context, OnTimeSelectListener onSelectListener) {
        this.mContext = context;
        this.onSelectListener = onSelectListener;
        builder = new StringBuilder();
        init();
    }

    private void init() {

        popupView = View.inflate(mContext, R.layout.popup_time_select, null);

        calendarView = popupView.findViewById(R.id.calendarView);
        tvSelectTime = popupView.findViewById(R.id.tv_select_time);
        tvFinish = popupView.findViewById(R.id.tv_finish);

        tvFinish.setOnClickListener(this);

        calendarInit(calendarView);


        //背景颜色变暗
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = 0.4f;
        mContext.getWindow().setAttributes(lp);

        this.setContentView(popupView);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.popup_bottom_in);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));

        showAtLocation(getContentView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    /**
     * 初始化日历
     *
     * @param calendarView
     */
    private void calendarInit(CalendarView calendarView) {
        //滑动至当前日期
        calendarView.getMonthViewPager()
                .scrollToCalendar(calendarView.getCurYear(), calendarView.getCurMonth(), 1, false);
        //最小/最大选择范围
        calendarView.setSelectRange(2, 30);

        calendarView.setOnCalendarRangeSelectListener(this);
    }

    @Override
    public void onCalendarSelectOutOfRange(Calendar calendar) {

    }

    @Override
    public void onSelectOutOfRange(Calendar calendar, boolean isOutOfMinRange) {
        ToastUtil.showShort(isOutOfMinRange ? "最少选择2天" : "最多选择30天");
    }

    @Override
    public void onCalendarRangeSelect(Calendar calendar, boolean isEnd) {

        if (!isEnd) {
            //选择起始时间
            if (!map.isEmpty()) {
                map.clear();
                builder.delete(0, builder.length());
            }
            startCalendar = calendar;
            builder.append(calendar.getYear())
                    .append("-")
                    .append(calendar.getMonth())
                    .append("-")
                    .append(calendar.getDay())
                    .append("～");
        } else {
            //选择结束时间
            builder.append(calendar.getYear())
                    .append("-")
                    .append(calendar.getMonth())
                    .append("-")
                    .append(calendar.getDay());
            endCalendar = calendar;
        }

        tvSelectTime.setText(builder.toString());
        map.put(getSchemeCalendar(calendar.getYear(), calendar.getMonth(), calendar.getDay(), Color.parseColor("#E60113"), "start").toString(),
                getSchemeCalendar(calendar.getYear(), calendar.getMonth(), calendar.getDay(), Color.parseColor("#E60113"), "start"));
        calendarView.setSchemeDate(map);
        calendarView.invalidate();

    }

    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "");
        calendar.addScheme(0xFF008800, "");
        return calendar;
    }

    @Override
    public void dismiss() {
        super.dismiss();
        //背景颜色
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = 1f;
        mContext.getWindow().setAttributes(lp);
    }
}
