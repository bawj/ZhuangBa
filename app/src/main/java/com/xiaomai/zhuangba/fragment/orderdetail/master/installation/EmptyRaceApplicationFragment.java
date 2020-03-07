package com.xiaomai.zhuangba.fragment.orderdetail.master.installation;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.DateUtil;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * @author Bawj
 * CreateDate: 2019/12/18 0018 14:24
 * 空跑申请
 */
public class EmptyRaceApplicationFragment extends BaseFragment {

    @BindView(R.id.btnConstructionSubmission)
    QMUIButton btnConstructionSubmission;
    @BindView(R.id.editFiledDescription)
    EditText editFiledDescription;
    @BindView(R.id.tvReAppointmentTime)
    TextView tvReAppointmentTime;
    @BindView(R.id.tvEmptyRaceMoney)
    TextView tvEmptyRaceMoney;

    private Date selectionDate;
    private String time;

    public static EmptyRaceApplicationFragment newInstance(String orderCode) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        EmptyRaceApplicationFragment fragment = new EmptyRaceApplicationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getEnumerate("RUNPRICE"))
                .compose(this.<HttpResult<String>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<String>(getActivity()) {
                    @Override
                    protected void onSuccess(String response) {
                        tvEmptyRaceMoney.setText(response);
                    }
                });
    }

    @OnClick({R.id.tvReAppointmentTime, R.id.btnConstructionSubmission})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvReAppointmentTime:
                timePickerView();
                break;
            case R.id.btnConstructionSubmission:
                //提交空跑申请
                commitConstruction();
                break;
            default:
        }
    }

    private void commitConstruction() {
        HashMap<String, Object> hashMap = new HashMap<>(4);
        String raceMoney = tvEmptyRaceMoney.getText().toString();
        String filedDescription = editFiledDescription.getText().toString();
        String appointmentTime = tvReAppointmentTime.getText().toString();
        if (TextUtils.isEmpty(appointmentTime)){
            ToastUtil.showShort(getString(R.string.please_fill_in_the_re_appointment_time));
        }else if (TextUtils.isEmpty(raceMoney)){
            ToastUtil.showShort(getString(R.string.application_amount_cannot_be_empty));
        }else if (TextUtils.isEmpty(raceMoney)){
            ToastUtil.showShort(getString(R.string.please_fill_in_the_application_amount));
        }else {
            //订单编号
            hashMap.put("orderCode" , getOrderCode());
            //申请金额
            hashMap.put("amount" , raceMoney);
            //申请理由
            hashMap.put("applyReason" , filedDescription);
            //在约时间
            hashMap.put("onceAgainDate" , appointmentTime);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
            RxUtils.getObservable(ServiceUrl.getUserApi().initiateAirRun(requestBody))
                    .compose(this.<HttpResult<Object>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                        @Override
                        protected void onSuccess(Object response) {
                            //提交空跑成功
                            setFragmentResult(ForResultCode.RESULT_OK.getCode() , new Intent());
                            popBackStack();
                        }
                    });
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_empty_race_application;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.empty_race_application);
    }

    private String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    private void timePickerView() {
        Calendar startDate = DateUtil.getHours(0, "yyyy-MM-dd HH:mm:ss");
        Calendar selectionCalendar = Calendar.getInstance();
        //默认选中
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
                tvReAppointmentTime.setText(time);
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
                .build().show();

    }

}
