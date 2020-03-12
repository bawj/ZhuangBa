package com.xiaomai.zhuangba.fragment.personal.feedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.EmployerAddProjectAdapter;
import com.xiaomai.zhuangba.data.DryRunOrder;
import com.xiaomai.zhuangba.data.bean.EmployerAddProjectData;
import com.xiaomai.zhuangba.data.bean.MessageEvent;
import com.xiaomai.zhuangba.data.bean.OrderServiceCondition;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.weight.PayPassView;
import com.xiaomai.zhuangba.weight.dialog.PayApplyForArunDialog;
import com.xiaomai.zhuangba.weight.dialog.PayPassDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Bawj
 * CreateDate:     2020/3/12 0012 09:00
 * 新增项目
 */
public class EmployerAddProjectFragment extends BaseListFragment<IOrderFeedBackModule, EmployerAddProjectAdapter> implements IOrderFeedBackView , PayApplyForArunDialog.IHeadPortraitPopupCallBack{

    @BindView(R.id.relEmployerProject)
    RelativeLayout relEmployerProject;
    @BindView(R.id.tvEmployerTotal)
    TextView tvEmployerTotal;
    public static final String DRY_RUN_ORDER = "dry_run_order";

    public static EmployerAddProjectFragment newInstance(String dryRunOrder) {
        Bundle args = new Bundle();
        args.putString(DRY_RUN_ORDER, dryRunOrder);
        EmployerAddProjectFragment fragment = new EmployerAddProjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        relEmployerProject.setVisibility(View.GONE);
    }

    @OnClick({R.id.btnNoPassage, R.id.btnAuditPass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnNoPassage:
                iModule.failIncreaseDecrease(getDryRunOrder());
                break;
            case R.id.btnAuditPass:
                iModule.customFailIncreaseDecrease(getDryRunOrder());
                break;
            default:
        }
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        iModule.requestIncreaseDecrease(getDryRunOrder().getOrderCode());
    }

    @Override
    public void increaseDecreaseSuccess(EmployerAddProjectData employerAddProjectData,List<OrderServiceCondition> orderServiceList) {
        relEmployerProject.setVisibility(View.VISIBLE);
        baseListAdapter.setNewData(orderServiceList);
        tvEmployerTotal.setText(getString(R.string.content_money , String.valueOf(employerAddProjectData.getTotalAmount())));
    }

    private String playStatus;
    @Override
    public void dryRunOrderAdoptSuccess(boolean flag,final String playStatus) {
        this.playStatus = playStatus;
        //确认支付
        PayApplyForArunDialog.getInstance().initView(getActivity())
                .isPaymentMonthlyAccount(flag)
                .setDialogCallBack(this)
                .show();
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
                        iModule.requestPay(getDryRunOrder().getId(), StringTypeExplain.WE_WALLET.getCode()
                                , passContent , playStatus , getDryRunOrder());
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
                        iModule.requestPay(getDryRunOrder().getId(), StringTypeExplain.MONTHLY_KNOTS.getCode(), passContent , playStatus , getDryRunOrder());
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
        iModule.requestPay(getDryRunOrder().getId(), StringTypeExplain.WE_CHAT_PAYMENT.getCode(),
                "" , playStatus, getDryRunOrder());
    }

    @Override
    public void aliPay() {
        //支付宝
        iModule.requestPay(getDryRunOrder().getId(), StringTypeExplain.A_ALIPAY_PAYMENT.getCode(),
                "" , playStatus, getDryRunOrder());
    }

    @Override
    public void viewRefresh() {
        //跳转页面刷新
        setFragmentResult(ForResultCode.RESULT_OK.getCode() , new Intent());
        popBackStack();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeChatSuccess(MessageEvent messageEvent) {
        switch (messageEvent.getErrCode()) {
            case 0:
                //微信支付成功
                viewRefresh();
                break;
            default:
        }
    }

    @Override
    public EmployerAddProjectAdapter getBaseListAdapter() {
        return new EmployerAddProjectAdapter();
    }

    private DryRunOrder getDryRunOrder() {
        if (getArguments() != null) {
            return new Gson().fromJson(getArguments().getString(DRY_RUN_ORDER) , DryRunOrder.class);
        }
        return new DryRunOrder();
    }

    @Override
    protected IOrderFeedBackModule initModule() {
        return new OrderFeedBackModule();
    }

    @Override
    public boolean getEnableLoadMore() {
        return false;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_add_project;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.add_project);
    }

    @Override
    public void setNewData(List<DryRunOrder> dryRunOrderList) {

    }

    @Override
    public void addData(List<DryRunOrder> dryRunOrderList) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
