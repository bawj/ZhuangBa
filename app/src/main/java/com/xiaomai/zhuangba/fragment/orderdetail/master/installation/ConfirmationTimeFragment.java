package com.xiaomai.zhuangba.fragment.orderdetail.master.installation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OngoingOrdersList;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.Util;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 确认时间
 */
public class ConfirmationTimeFragment extends BaseFragment {

    public static final String MORE_TIME = "more_time";
    public static final String TIME = "time";

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvDate)
    TextView tvDate;
    private Date selectionDate;
    private String time;

    public static ConfirmationTimeFragment newInstance(OngoingOrdersList ongoingOrdersList , String time) {
        Bundle args = new Bundle();
        args.putParcelable(MORE_TIME, ongoingOrdersList);
        args.putString(TIME, time);
        ConfirmationTimeFragment fragment = new ConfirmationTimeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        OngoingOrdersList ongoingOrdersList = getOngoingOrdersList();
        if (ongoingOrdersList != null) {
            tvName.setText(ongoingOrdersList.getName());
            tvPhone.setText(ongoingOrdersList.getTelephone());
            tvAddress.setText(ongoingOrdersList.getAddress());
            //产品 需要 让师傅 必须选一次 确认时间
            //tvDate.setText(ongoingOrdersList.getAppointmentTime());
        }
    }

    @OnClick({R.id.btnData, R.id.relTime, R.id.tvPhone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnData:
                upload();
                break;
            case R.id.relTime:
                timePickerView();
                break;
            case R.id.tvPhone:
                String phone = tvPhone.getText().toString();
                if (getActivity() != null && !TextUtils.isEmpty(phone)) {
                    Util.callPhone(getActivity(), phone);
                }
                break;
            default:
        }
    }

    private void upload() {
        OngoingOrdersList ongoingOrdersList = getOngoingOrdersList();
        if (ongoingOrdersList != null) {
            //最后修改时间
            String appointmentTime = ongoingOrdersList.getModifyTime();
            //发布时间 如果发布时间为空 则用最后修改时间
            String time = getTime();
            Long aLong;
            if (!TextUtils.isEmpty(time)){
                aLong = DateUtil.dateToCurrentTimeMilli(time, "yyyy-MM-dd HH:mm:ss");
            }else {
                aLong = DateUtil.dateToCurrentTimeMilli(appointmentTime, "yyyy-MM-dd HH:mm:ss");
            }
            //确认时间
            String date = tvDate.getText().toString();
            Long aLong1 = DateUtil.dateToCurrentTimeMilli(date, "yyyy-MM-dd HH:mm:ss");
            if (aLong1 < aLong) {
                ToastUtil.showShort(getString(R.string.order_time_tip));
                return;
            }
            String orderCode = ongoingOrdersList.getOrderCode();
            Observable<HttpResult<Object>> confirmationOrder = ServiceUrl.getUserApi().getConfirmationOrder(orderCode, date);
            RxUtils.getObservable(confirmationOrder)
                    .compose(this.<HttpResult<Object>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                @Override
                protected void onSuccess(Object response) {
                    //成功
                    setFragmentResult(ForResultCode.RESULT_OK.getCode(), new Intent());
                    popBackStack();
                }
            });
        }
    }

    private void timePickerView() {
        //开始时间 两小时后的时间
        Calendar startDate = DateUtil.getHours(0, "yyyy-MM-dd HH:mm:ss");
        //结束 一个月内的时间
        Calendar endDate = DateUtil.getMonth(1, "yyyy-MM-dd HH:mm:ss");

        Calendar selectionCalendar = Calendar.getInstance();
        if (selectionDate != null) {
            selectionCalendar.setTime(selectionDate);
        } else {
            selectionCalendar = startDate;
        }
        new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                //选中事件回调
                DateTime dateTime = new DateTime(date);
                selectionDate = date;
                int hour = dateTime.getHourOfDay();
                int year = dateTime.getYear();
                int day = dateTime.getDayOfMonth();
                int month = dateTime.getMonthOfYear();
                time = String.valueOf(year)
                        + "-" + String.valueOf(month)
                        + "-" + String.valueOf(day)
                        + " " + String.valueOf(hour)
                        + ":00" + ":00";
                tvDate.setText(time);
            }
        }).setType(new boolean[]{true, true, true, true, false, false})
                //取消按钮文字
                .setCancelText(getString(R.string.close))
                //确认按钮文字
                .setSubmitText(getString(R.string.ok))
                //滚轮文字大小
                .setContentTextSize(18)
                //标题文字大小
                .setTitleSize(14)
                //标题文字
                .setTitleText(getString(R.string.check_time_date))
                //点击屏幕，点在控件外部范围时，是否取消显示
                .setOutSideCancelable(true)
                //是否循环滚动
                .isCyclic(false)
                .setDate(selectionCalendar)
                //确定按钮文字颜色
                .setSubmitColor(getResources().getColor(R.color.tool_lib_red_EF2B2B))
                //取消按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.tool_lib_red_EF2B2B))
                //起始终止年月日设定
                .setRangDate(startDate, null)
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(true)
                //是否显示为对话框样式
                .isDialog(false)
                .build()
                .show();

    }

    private OngoingOrdersList getOngoingOrdersList() {
        if (getArguments() != null) {
            return getArguments().getParcelable(MORE_TIME);
        }
        return null;
    }

    private String getTime(){
        if (getArguments() != null) {
            return getArguments().getString(TIME);
        }
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_confirmation_time;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.confirmation_time);
    }


}
