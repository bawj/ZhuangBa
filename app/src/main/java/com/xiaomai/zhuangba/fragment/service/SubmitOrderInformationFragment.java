package com.xiaomai.zhuangba.fragment.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.DensityUtils;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.OrderAddress;
import com.xiaomai.zhuangba.data.bean.ServiceData;
import com.xiaomai.zhuangba.fragment.SelectServiceFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.DateUtil;

import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 * 提交订单信息
 */
public class SubmitOrderInformationFragment extends BaseOrderInformationFragment {

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

    public static SubmitOrderInformationFragment newInstance() {
        Bundle args = new Bundle();
        SubmitOrderInformationFragment fragment = new SubmitOrderInformationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        btnOrderInformation.setText(getString(R.string.next));
    }

    @Override
    public void btnOrderInformationClick() {
        //姓名
        final String userText = getUserName();
        //电话
        final String phoneNumber = getPhoneNumber();
        //地址
        final String address = getAddress();
        //详细地址
        final String addressDetail = getAddressDetail();
        //预约时间
        final String appointmentTime = getAppointmentTime();
        String date = DateUtil.dateToStr(appointmentTime, "yyyy-MM-dd HH:mm:ss");
        Long aLong = DateUtil.dateToCurrentTimeMillis(date, "yyyy-MM-dd HH:mm:ss");
        if (TextUtils.isEmpty(userText)) {
            showToast(getString(R.string.please_input_name));
        }else if (TextUtils.isEmpty(phoneNumber)) {
            showToast(getString(R.string.please_input_telephone));
        }else if (TextUtils.isEmpty(address)) {
            showToast(getString(R.string.please_input_address));
        }else if (TextUtils.isEmpty(appointmentTime)) {
            showToast(getString(R.string.please_input_appointment_time));
        }else if (!DateUtil.isDateCompare(aLong)) {
            showToast(getString(R.string.reservation_time_prompt));
        }else {
            //雇主描述
            final String employerDescribe = getEmployerDescribe();
            //经纬度
            final String longitude = getLongitude();
            final String latitude = getLatitude();
            RxUtils.getObservable(ServiceUrl.getUserApi().getServiceCategory())
                    .compose(this.<HttpResult<List<ServiceData>>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<List<ServiceData>>(getActivity()) {
                        @Override
                        protected void onSuccess(List<ServiceData> serviceDataList) {
                            if (serviceDataList != null && !serviceDataList.isEmpty()) {
                                OrderAddress orderAddress = new OrderAddress();
                                orderAddress.setUserText(userText);
                                orderAddress.setPhoneNumber(phoneNumber);
                                orderAddress.setProvince(province);
                                orderAddress.setCity(city);
                                orderAddress.setArea(area);
                                orderAddress.setLongitude(DensityUtils.stringTypeLong(longitude));
                                orderAddress.setLatitude(DensityUtils.stringTypeLong(latitude));
                                orderAddress.setAddress(address);
                                orderAddress.setAddressDetail(addressDetail);
                                orderAddress.setAppointmentTime(appointmentTime);
                                orderAddress.setEmployerDescribe(employerDescribe);
                                orderAddress.setImgList(getMediaSelectorFiles());
                                String orderAddressGson = new Gson().toJson(orderAddress);
                                ServiceData serviceData = serviceDataList.get(0);
                                startFragment(SelectServiceFragment.newInstance(
                                        String.valueOf(serviceData.getServiceId()), serviceData.getServiceText()
                                        ,orderAddressGson));
                            }
                        }
                    });
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_order_information;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.add_information);
    }



}
