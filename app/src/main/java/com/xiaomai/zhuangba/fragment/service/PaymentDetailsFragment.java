package com.xiaomai.zhuangba.fragment.service;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.util.DensityUtils;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PaymentDetailsAdapter;
import com.xiaomai.zhuangba.data.bean.MessageEvent;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.bean.SubmissionOrder;
import com.xiaomai.zhuangba.data.bean.db.ShopAuxiliaryMaterialsDB;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.pay.IPaymentDetailView;
import com.xiaomai.zhuangba.data.module.pay.IPaymentDetailsModule;
import com.xiaomai.zhuangba.data.module.pay.PaymentDetailsModule;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.util.ShopCarUtil;
import com.xiaomai.zhuangba.weight.PayPassView;
import com.xiaomai.zhuangba.weight.ShopPayCheckBox;
import com.xiaomai.zhuangba.weight.dialog.PayPassDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/12 0012
 */
public class PaymentDetailsFragment extends BaseFragment<IPaymentDetailsModule> implements IPaymentDetailView {


    @BindView(R.id.tvPaymentItemOrdersTitle)
    TextView tvPaymentItemOrdersTitle;
    @BindView(R.id.tvPaymentTaskQuantity)
    TextView tvPaymentTaskQuantity;
    @BindView(R.id.tvPaymentName)
    TextView tvPaymentName;
    @BindView(R.id.tvPaymentPhone)
    TextView tvPaymentPhone;
    @BindView(R.id.tvPaymentAppointment)
    TextView tvPaymentAppointment;
    @BindView(R.id.tvPaymentLocation)
    TextView tvPaymentLocation;
    @BindView(R.id.tvPaymentMonty)
    TextView tvPaymentMonty;
    @BindView(R.id.tvPaymentTaskOrderCode)
    TextView tvPaymentTaskOrderCode;
    @BindView(R.id.recyclerServiceItems)
    RecyclerView recyclerServiceItems;
    @BindView(R.id.chkPaymentWeChat)
    RadioButton chkPaymentWeChat;
    @BindView(R.id.chkPaymentPlay)
    RadioButton chkPaymentPlay;
    @BindView(R.id.chkPaymentWallet)
    RadioButton chkPaymentWallet;
    @BindView(R.id.chkPaymentMonthlyAccount)
    RadioButton chkPaymentMonthlyAccount;

    /**
     * 支付密码
     */
    private String password;
    private static final String ORDER_DATA = "order_data";

    public static PaymentDetailsFragment newInstance(String orderGson) {
        Bundle args = new Bundle();
        args.putString(ORDER_DATA, orderGson);
        PaymentDetailsFragment fragment = new PaymentDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Subscribe
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initView() {
        //服务项目
        List<ShopCarData> shopCarDataList = DBHelper.getInstance().getShopCarDataDao().queryBuilder().list();

        tvPaymentMonty.setText(String.valueOf(ShopCarUtil.getTotalMoney()));
        recyclerServiceItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        PaymentDetailsAdapter paymentDetailsAdapter = new PaymentDetailsAdapter();
        paymentDetailsAdapter.addHeaderView(getRequiredOptions());
        paymentDetailsAdapter.setNewData(shopCarDataList);
        recyclerServiceItems.setAdapter(paymentDetailsAdapter);
        String orderData = getOrderData();
        if (orderData != null) {
            SubmissionOrder submissionOrder = new Gson().fromJson(orderData, SubmissionOrder.class);
            if (submissionOrder != null) {
                Integer taskQuantity = submissionOrder.getNumber();
                //服务项目
                tvPaymentItemOrdersTitle.setText(submissionOrder.getServiceText());
                //数量
                tvPaymentTaskQuantity.setText(String.valueOf(taskQuantity));
                //联系人名称
                tvPaymentName.setText(submissionOrder.getName());
                //联系人电话
                tvPaymentPhone.setText(submissionOrder.getTelephone());
                //预约时间
                tvPaymentAppointment.setText(submissionOrder.getAppointmentTime());
                //地址 + 详细地址
                String addressDetail = submissionOrder.getAddress() + submissionOrder.getAddressDetail();
                tvPaymentLocation.setText(addressDetail);
                //订单编号
                tvPaymentTaskOrderCode.setText(submissionOrder.getOrderCode());
            }
        }

        new ShopPayCheckBox()
                .setChkWeChatBalance(chkPaymentWeChat)
                .setChkAlipayBalance(chkPaymentPlay)
                .setChkWalletBanlance(chkPaymentWallet)
                .setChkPaymentMonthlyAccount(chkPaymentMonthlyAccount);
    }

    private View getRequiredOptions() {
        ShopAuxiliaryMaterialsDB unique = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao().queryBuilder().unique();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_payment_required_options , null);
        //开槽
        TextView tvSlotting = view.findViewById(R.id.tvSlotting);
        if (DensityUtils.stringTypeInteger(unique.getSlottingEndLength()) > 0) {
            tvSlotting.setVisibility(View.VISIBLE);
            tvSlotting.setText(getString(R.string.slotting_start_end_length,
                    unique.getSlottingStartLength(), unique.getSlottingEndLength()));
        } else {
            tvSlotting.setVisibility(View.GONE);
        }
        //调试
        TextView tvDebugging = view.findViewById(R.id.tvDebugging);
        double debuggingPrice = unique.getDebuggingPrice();
        if (debuggingPrice != 0) {
            tvDebugging.setText(getString(R.string.debugging));
            tvDebugging.setVisibility(View.VISIBLE);
        } else {
            tvDebugging.setVisibility(View.GONE);
        }
        //辅材
        TextView tvAuxiliaryMaterials = view.findViewById(R.id.tvAuxiliaryMaterials);
        String materialsEndLength = unique.getMaterialsEndLength();
        if (DensityUtils.stringTypeInteger(materialsEndLength) > 0) {
            tvAuxiliaryMaterials.setVisibility(View.VISIBLE);
            tvSlotting.setText(getString(R.string.slotting_start_end_length,
                    unique.getMaterialsStartLength(), unique.getMaterialsEndLength()));
        } else {
            tvAuxiliaryMaterials.setVisibility(View.GONE);
        }
        return view;
    }

    @OnClick({R.id.btnGoPayment, R.id.ivPaymentReplace})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnGoPayment:
                //去支付 先调用修改订单 修改成功后调用 支付
                if (chkPaymentWallet.isChecked()){
                    inputPassword();
                }else {
                    iModule.goOrderPay();
                }
                break;
            case R.id.ivPaymentReplace:
                RxPermissionsUtils.applyPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, new BaseCallback<String>() {
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
                break;
            default:
        }
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()) {
                //地址选择成功返回
                String name = data.getStringExtra(ForResultCode.RESULT_KEY.getExplain());
                tvPaymentLocation.setText(name);
            }
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_payment_details;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.payment_details);
    }

    private String getOrderData() {
        if (getArguments() != null) {
            return getArguments().getString(ORDER_DATA);
        }
        return null;
    }

    @Override
    protected IPaymentDetailsModule initModule() {
        return new PaymentDetailsModule();
    }


    @Override
    public SubmissionOrder getSubmissionOrder() {
        String orderData = getOrderData();
        if (getOrderData() != null) {
            SubmissionOrder submissionOrder = new Gson().fromJson(orderData, SubmissionOrder.class);
            submissionOrder.setAddress(tvPaymentLocation.getText().toString());
            return submissionOrder;
        }
        return null;
    }

    @Override
    public boolean getChkPaymentWeChat() {
        return chkPaymentWeChat.isChecked();
    }

    @Override
    public boolean getChkPaymentPlay() {
        return chkPaymentPlay.isChecked();
    }

    @Override
    public boolean getChkPaymentWallet() {
        return chkPaymentWallet.isChecked();
    }

    @Override
    public boolean getMonthlyAccount() {
        return chkPaymentMonthlyAccount.isChecked();
    }

    @Override
    public String getWalletPassword() {
        return password;
    }

    public void inputPassword() {
        final PayPassDialog dialog = new PayPassDialog(getActivity());
        dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        dialog.dismiss();
                        password = passContent;
                        iModule.goOrderPay();
                    }
                    @Override
                    public void onPayClose() {
                        dialog.dismiss();
                    }
                    @Override
                    public void onPayForget() {
                        dialog.dismiss();
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeChatSuccess(MessageEvent messageEvent) {
        switch (messageEvent.getErrCode()) {
            case 0:
                //微信支付成功
                showToast(getString(R.string.payment_success));
                paymentSuccess();
                break;
            default:
        }
    }

    @Override
    public void paymentSuccess() {
        SubmissionOrder submissionOrder = getSubmissionOrder();
        String address = submissionOrder.getAddress();
        String telephone = submissionOrder.getTelephone();
        String name = submissionOrder.getName();
        String orderCode = submissionOrder.getOrderCode();
        String orderType = submissionOrder.getOrderType();
        Double money = ShopCarUtil.getTotalMoney();
        startFragment(SuccessfulPaymentFragment.newInstance(name, telephone, address, String.valueOf(money), orderCode ,orderType));
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
