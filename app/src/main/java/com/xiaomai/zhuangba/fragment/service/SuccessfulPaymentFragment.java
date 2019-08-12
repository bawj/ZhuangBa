package com.xiaomai.zhuangba.fragment.service;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.employer.EmployerFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.DistributionDetailFragment;
import com.xiaomai.zhuangba.fragment.orderdetail.employer.EmployerDistributionFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/13 0013
 * 支付成功
 */
public class SuccessfulPaymentFragment extends BaseFragment {

    @BindView(R.id.tvPaymentName)
    TextView tvPaymentName;
    @BindView(R.id.tvSuccessfulPhone)
    TextView tvSuccessfulPhone;
    @BindView(R.id.tvSuccessfulAddress)
    TextView tvSuccessfulAddress;
    @BindView(R.id.tvSuccessfulActualPayment)
    TextView tvSuccessfulActualPayment;

    public static final String NAME = "name";
    public static final String PHONE = "phone";
    public static final String ADDRESS = "address";
    public static final String ORDER_AMOUNT = "order_amount";
    public static final String ORDER_CODE = "order_code";

    public static SuccessfulPaymentFragment newInstance(String name, String phone, String address
            , String orderAmount, String orderCode) {
        Bundle args = new Bundle();
        args.putString(NAME, name);
        args.putString(PHONE, phone);
        args.putString(ADDRESS, address);
        args.putString(ORDER_AMOUNT, orderAmount);
        args.putString(ORDER_CODE, orderCode);
        SuccessfulPaymentFragment fragment = new SuccessfulPaymentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        statusBarWhite();
    }

    @Override
    public void initView() {
        tvPaymentName.setText(getName());
        tvSuccessfulPhone.setText(getPhone());
        tvSuccessfulAddress.setText(getAddress());
        tvSuccessfulActualPayment.setText(String.valueOf(getOrderAmount()));

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_successful_payment;
    }

    @OnClick({R.id.btnSuccessfulMissionDetails, R.id.btnSuccessfulBackHome})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSuccessfulMissionDetails:
                startFragment(EmployerDistributionFragment.newInstance(getOrderCode()));
                break;
            case R.id.btnSuccessfulBackHome:
                startFragment(EmployerFragment.newInstance());
                break;
            default:
        }
    }

    /**
     * 联系人姓名
     *
     * @return string
     */
    private String getName() {
        if (getArguments() != null) {
            return getArguments().getString(NAME);
        }
        return "";
    }

    /**
     * 联系人电话
     *
     * @return string
     */
    private String getPhone() {
        if (getArguments() != null) {
            return getArguments().getString(PHONE);
        }
        return "";
    }

    /**
     * 联系人地址
     *
     * @return string
     */
    private String getAddress() {
        if (getArguments() != null) {
            return getArguments().getString(ADDRESS);
        }
        return "";
    }

    /**
     * 总金额
     *
     * @return string
     */
    private String getOrderAmount() {
        if (getArguments() != null) {
            return getArguments().getString(ORDER_AMOUNT);
        }
        return "";
    }

    /**
     * 订单编号
     *
     * @return string
     */
    private String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ORDER_CODE);
        }
        return "";
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isInSwipeBack() {
        return true;
    }
}
