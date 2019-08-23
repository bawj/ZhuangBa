package com.xiaomai.zhuangba.fragment.service;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;

import butterknife.BindView;

import static com.xiaomai.zhuangba.fragment.SelectServiceFragment.SERVICE_ID;
import static com.xiaomai.zhuangba.fragment.SelectServiceFragment.SERVICE_TEXT;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 * 提交订单信息
 */
public class SubmitOrderInformationFragment extends BaseOrderInformationFragment{

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

    public static SubmitOrderInformationFragment newInstance(String largeClassServiceId, String serviceText) {
        Bundle args = new Bundle();
        args.putString(SERVICE_ID, largeClassServiceId);
        args.putString(SERVICE_TEXT, serviceText);
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
        //下一步
        iModule.submitOrder();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_order_information;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.add_information);
    }

    @Override
    public String getLargeClassServiceId() {
        if (getArguments() != null) {
            return getArguments().getString(SERVICE_ID);
        }
        return "";
    }

    @Override
    public String getLargeServiceText() {
        if (getArguments() != null) {
            return getArguments().getString(SERVICE_TEXT);
        }
        return "";
    }

    @Override
    public void placeOrderSuccess(String requestBodyString) {
        //提交成功
        startFragment(PaymentDetailsFragment.newInstance(requestBodyString));
    }


}
