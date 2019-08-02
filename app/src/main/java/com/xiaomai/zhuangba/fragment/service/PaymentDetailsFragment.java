package com.xiaomai.zhuangba.fragment.service;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.util.AmountUtil;
import com.example.toollib.util.DensityUtils;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PaymentDetailsAdapter;
import com.xiaomai.zhuangba.data.bean.MessageEvent;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.bean.SubmissionOrder;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.pay.IPaymentDetailView;
import com.xiaomai.zhuangba.data.module.pay.IPaymentDetailsModule;
import com.xiaomai.zhuangba.data.module.pay.PaymentDetailsModule;
import com.xiaomai.zhuangba.weight.MonitorPayCheckBox;

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
public class PaymentDetailsFragment extends BaseFragment<IPaymentDetailsModule> implements PaymentDetailsAdapter.BaseCallBack, IPaymentDetailView {


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

    /**
     * 总价
     */
    private Double money = 0.0;
    /**
     * 数量
     */
    private Integer taskQuantity = 0;
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
        //总价
        for (ShopCarData shopCarData : shopCarDataList) {
            Double price = DensityUtils.stringTypeDouble(shopCarData.getMoney()) * DensityUtils.stringTypeInteger(shopCarData.getNumber());
            money = AmountUtil.add(price, money, 2);
        }
        tvPaymentMonty.setText(String.valueOf(money));
        recyclerServiceItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        PaymentDetailsAdapter paymentDetailsAdapter = new PaymentDetailsAdapter();
        paymentDetailsAdapter.setNewData(shopCarDataList);
        paymentDetailsAdapter.setBaseCallback(this);
        recyclerServiceItems.setAdapter(paymentDetailsAdapter);
        String orderData = getOrderData();
        if (orderData != null) {
            SubmissionOrder submissionOrder = new Gson().fromJson(orderData, SubmissionOrder.class);
            if (submissionOrder != null) {
                taskQuantity = submissionOrder.getNumber();
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
        new MonitorPayCheckBox().setChkWeChatBalance(chkPaymentWeChat).setChkAlipayBalance(chkPaymentPlay);
    }

    @Override
    public void onAddSuccess(Double price) {
        //重新计算价格
        money = AmountUtil.add(price, money, 2);
        //重新计算任务数量
        taskQuantity = taskQuantity + 1;
        tvPaymentTaskQuantity.setText(String.valueOf(taskQuantity));
        tvPaymentMonty.setText(String.valueOf(money));
    }

    @Override
    public void onDelSuccess(Double price) {
        //重新计算价格
        money = AmountUtil.subtract(money, price, 2);
        //重新计算任务数量
        taskQuantity = taskQuantity - 1;
        tvPaymentTaskQuantity.setText(String.valueOf(taskQuantity));
        tvPaymentMonty.setText(String.valueOf(money));
    }

    @OnClick(R.id.btnGoPayment)
    public void onViewClicked() {
        //去支付 先调用修改订单 修改成功后调用 支付
        iModule.goOrderPay();
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
            return new Gson().fromJson(orderData, SubmissionOrder.class);
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
        startFragment(SuccessfulPaymentFragment.newInstance(name , telephone , address , String.valueOf(money) ,orderCode));
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
