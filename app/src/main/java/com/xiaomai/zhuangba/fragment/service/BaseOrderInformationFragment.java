package com.xiaomai.zhuangba.fragment.service;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.base.BaseCallback;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.module.orderinformation.IOrderInformationModule;
import com.xiaomai.zhuangba.data.module.orderinformation.IOrderInformationView;
import com.xiaomai.zhuangba.data.module.orderinformation.OrderInformationModule;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.util.DateUtil;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.util.Util;

import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 * <p>
 * 添加详细信息 或修改信息
 */
public class BaseOrderInformationFragment extends BaseFragment<IOrderInformationModule> implements IOrderInformationView {

    @BindView(R.id.editOrderInformationName)
    EditText editOrderInformationName;
    @BindView(R.id.editOrderInformationPhone)
    EditText editOrderInformationPhone;
    @BindView(R.id.editOrderInformationDetailedAddress)
    EditText editOrderInformationDetailedAddress;
    @BindView(R.id.btnOrderInformation)
    Button btnOrderInformation;
    @BindView(R.id.relOrderInformationTime)
    RelativeLayout relOrderInformationTime;
    @BindView(R.id.tvOrderInformationClickServiceAddress)
    TextView tvOrderInformationClickServiceAddress;
    @BindView(R.id.tvOrderInformationDate)
    TextView tvOrderInformationDate;


    private Date selectionDate;
    private Double longitude;
    private Double latitude;
    private String time;

    public static BaseOrderInformationFragment newInstance() {
        Bundle args = new Bundle();
        BaseOrderInformationFragment fragment = new BaseOrderInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        Util.setEditTextInhibitInputSpaChat(editOrderInformationDetailedAddress , 30);
        Util.setEditTextInhibitInputSpaChat(editOrderInformationName , 8);
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_order_information;
    }

    @OnClick({R.id.tvOrderInformationClickServiceAddress, R.id.ivOrderInformationLocation
            , R.id.btnOrderInformation , R.id.relOrderInformationTime})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvOrderInformationClickServiceAddress:
                applyPermission();
                break;
            case R.id.ivOrderInformationLocation:
                applyPermission();
                break;
            case R.id.btnOrderInformation:
                //提交 或 修改信息
                btnOrderInformationClick();
                break;
            case R.id.relOrderInformationTime:
                //点击选择预约时间
                timePickerView();
                break;
            default:
        }
    }


    private void applyPermission() {
        RxPermissionsUtils.applyPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION, new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        startFragmentForResult(LocationFragment.newInstance(), ForResultCode.START_FOR_RESULT_CODE.getCode());
                    }
                    @Override
                    public void onFail(Object obj) {
                        super.onFail(obj);
                        showToast(getString(R.string.positioning_authority_tip));
                    }
                });
    }


    private void timePickerView() {
        //开始时间 两小时后的时间
        Calendar startDate = DateUtil.getHours(2, "yyyy-MM-dd HH:mm:ss");
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
                tvOrderInformationDate.setText(time);
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
                .setRangDate(startDate, endDate)
                //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isCenterLabel(true)
                //是否显示为对话框样式
                .isDialog(false)
                .build().show();

    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()){
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode() && data != null){
                String name = data.getStringExtra(ForResultCode.RESULT_KEY.getExplain());
                this.longitude = data.getDoubleExtra(ForResultCode.LONGITUDE.getExplain() , 0f);
                this.latitude = data.getDoubleExtra(ForResultCode.LATITUDE.getExplain() , 0f);
                tvOrderInformationClickServiceAddress.setText(name);
            }
        }
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    protected IOrderInformationModule initModule() {
        return new OrderInformationModule();
    }

    public void btnOrderInformationClick() {
    }


    /**
     *  大类编号 修改信息使用
     * @return string
     */
    @Override
    public String getLargeClassServiceId() {
        return null;
    }

    /**
     *  大类名称 修改信息使用
     * @return string
     */
    @Override
    public String getLargeServiceText() {
        return null;
    }

    /**
     * 订单编号 修改信息使用
     * @return string
     */
    @Override
    public String getOrderCode() {
        return null;
    }

    /**
     * 订单状态 修改信息使用
     * @return string
     */
    @Override
    public String getOrderStatus() {
        return null;
    }

    @Override
    public String getPhoneNumber() {
        return editOrderInformationPhone.getText().toString();
    }

    @Override
    public String getUserName() {
        return editOrderInformationName.getText().toString();
    }

    @Override
    public String getAddress() {
        return tvOrderInformationClickServiceAddress.getText().toString();
    }

    @Override
    public String getAddressDetail() {
        return editOrderInformationDetailedAddress.getText().toString();
    }

    @Override
    public String getAppointmentTime() {
        return time;
    }

    @Override
    public String getLongitude() {
        return String.valueOf(longitude);
    }

    @Override
    public String getLatitude() {
        return String.valueOf(latitude);
    }
    @Override
    public void placeOrderSuccess(String requestBodyString) {

    }

    @Override
    public void updateOrderSuccess() {

    }
}
