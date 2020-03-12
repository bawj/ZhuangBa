package com.xiaomai.zhuangba.fragment.personal.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.OrderFeedBackAdapter;
import com.xiaomai.zhuangba.data.DryRunOrder;
import com.xiaomai.zhuangba.data.bean.EmployerAddProjectData;
import com.xiaomai.zhuangba.data.bean.MessageEvent;
import com.xiaomai.zhuangba.data.bean.OrderServiceCondition;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.weight.PayPassView;
import com.xiaomai.zhuangba.weight.dialog.ApplyForARunDialog;
import com.xiaomai.zhuangba.weight.dialog.PayApplyForArunDialog;
import com.xiaomai.zhuangba.weight.dialog.PayPassDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * 订单反馈
 *
 * @author Bawj
 * CreateDate:     2019/12/19 0019 11:23
 */
public class OrderFeedBackFragment extends BaseListFragment<IOrderFeedBackModule, OrderFeedBackAdapter> implements PayApplyForArunDialog.IHeadPortraitPopupCallBack, IOrderFeedBackView {

    private String playStatus;
    private DryRunOrder dryRunOrder;
    private OrderFeedBackAdapter orderFeedBackAdapter;

    public static OrderFeedBackFragment newInstance() {
        Bundle args = new Bundle();
        OrderFeedBackFragment fragment = new OrderFeedBackFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        iModule.requestSelectAirRun();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        iModule.requestSelectAirRun();
    }

    @Override
    public void setNewData(List<DryRunOrder> dryRunOrderList) {
        orderFeedBackAdapter.setNewData(dryRunOrderList);
    }

    @Override
    public void addData(List<DryRunOrder> dryRunOrderList) {
        orderFeedBackAdapter.addData(dryRunOrderList);
    }

    @Override
    public void viewRefresh() {
        refresh();
    }

    @Override
    public void dryRunOrderAdoptSuccess(boolean flag,String playStatus) {
        this.playStatus = playStatus;
        //确认支付
        PayApplyForArunDialog.getInstance().initView(getActivity())
                .isPaymentMonthlyAccount(flag)
                .setDialogCallBack(OrderFeedBackFragment.this)
                .show();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        dryRunOrder = (DryRunOrder) view.findViewById(R.id.tvMemberPhone).getTag();
        String orderType = dryRunOrder.getOrderType();
        if (orderType.equals(StringTypeExplain.ADD_PROJECT.getCode())){
            //新增项目
            startFragmentForResult(EmployerAddProjectFragment.newInstance(new Gson().toJson(dryRunOrder)) , ForResultCode.START_FOR_RESULT_CODE.getCode());
        }else if (orderType.equals(StringTypeExplain.CUSTOM_PROJECT.getCode())){
            //自定义项
            customProject(dryRunOrder);
        }else if (orderType.equals(StringTypeExplain.DRY_RUN_PROJECT.getCode())){
            //空跑
            dryRunProject();
        }
    }

    private void customProject(final DryRunOrder dryRunOrder) {
        ApplyForARunDialog.getInstance().initView(getActivity())
                .setApplicationAmount(dryRunOrder.getAmount())
                .setDialogContent(dryRunOrder.getApplyReason())
                .setTvProblemFeedback(getString(R.string.site_replenishment))
                .isReAppointmentTime(View.GONE)
                .setICallBase(new ApplyForARunDialog.BaseCallback() {
                    @Override
                    public void applicationForApproval() {
                        //现场补充申请通过
                        iModule.customForApproval(dryRunOrder);
                    }
                    @Override
                    public void applicationFailed() {
                        //现场补充申请不通过
                        iModule.customApplicationFailed(dryRunOrder);
                    }
                })
                .showDialog();
    }

    private void dryRunProject() {
        //弹出申请空跑
        ApplyForARunDialog.getInstance().initView(getActivity())
                .setApplicationAmount(dryRunOrder.getAmount())
                .setDialogContent(dryRunOrder.getApplyReason())
                .setReAppointmentTime(getString(R.string.re_appointment_time_, dryRunOrder.getOnceAgainDate()))
                .setICallBase(new ApplyForARunDialog.BaseCallback() {
                    @Override
                    public void applicationForApproval() {
                        //申请通过
                        iModule.dryRunOrderAdopt();
                    }

                    @Override
                    public void applicationFailed() {
                        //申请不通过
                        iModule.dryRunOrderApplicationFailed(dryRunOrder.getId());
                    }
                })
                .showDialog();
    }


    @Override
    public void walletPay() {
        //钱包
        final PayPassDialog dialog = new PayPassDialog(getActivity());
        dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        dialog.dismiss();
                        iModule.requestPay(dryRunOrder.getId(), StringTypeExplain.WE_WALLET.getCode(), passContent , playStatus , dryRunOrder);
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

    @Override
    public void paymentMonthlyAccount() {
        //月结
        final PayPassDialog dialog = new PayPassDialog(getActivity());
        dialog.getPayViewPass()
                .setPayClickListener(new PayPassView.OnPayClickListener() {
                    @Override
                    public void onPassFinish(String passContent) {
                        dialog.dismiss();
                        iModule.requestPay(dryRunOrder.getId(), StringTypeExplain.MONTHLY_KNOTS.getCode(), passContent , playStatus , dryRunOrder);
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

    @Override
    public void weChatPay() {
        //微信
        iModule.requestPay(dryRunOrder.getId(), StringTypeExplain.WE_CHAT_PAYMENT.getCode(), "" , playStatus, dryRunOrder);
    }

    @Override
    public void aliPay() {
        //支付宝
        iModule.requestPay(dryRunOrder.getId(), StringTypeExplain.A_ALIPAY_PAYMENT.getCode(), "" , playStatus, dryRunOrder);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeChatSuccess(MessageEvent messageEvent) {
        switch (messageEvent.getErrCode()) {
            case 0:
                //微信支付成功
                refresh();
                break;
            default:
        }
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()){
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()){
                refresh();
            }
        }
    }

    @Override
    public OrderFeedBackAdapter getBaseListAdapter() {
        return orderFeedBackAdapter = new OrderFeedBackAdapter();
    }

    @Override
    protected IOrderFeedBackModule initModule() {
        return new OrderFeedBackModule();
    }

    @Override
    public int getIvNotDataBackground() {
        return R.drawable.bg_search_empty;
    }

    @Override
    public String getTvNotData() {
        return getString(R.string.search_empty);
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.order_feedback);
    }

    @Override
    public void increaseDecreaseSuccess(EmployerAddProjectData employerAddProjectData, List<OrderServiceCondition> orderServiceList) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
