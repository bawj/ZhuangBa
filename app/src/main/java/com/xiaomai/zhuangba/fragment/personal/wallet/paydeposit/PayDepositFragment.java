package com.xiaomai.zhuangba.fragment.personal.wallet.paydeposit;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.PayDepositAdapter;
import com.xiaomai.zhuangba.data.bean.MessageEvent;
import com.xiaomai.zhuangba.data.bean.PayDepositBean;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.fragment.personal.agreement.WebViewFragment;
import com.xiaomai.zhuangba.fragment.personal.wallet.PaymentSuccessFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.weight.MonitorPayCheckBox;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 * 缴纳保证金
 */
public class PayDepositFragment extends BaseFragment<IPayDepositModule> implements IPayDepositView,
        BaseQuickAdapter.OnItemClickListener, PayDepositAdapter.IPayDepositInterface {

    @BindView(R.id.chkPaymentWeChat)
    RadioButton chkPaymentWeChat;
    @BindView(R.id.chkPaymentPlay)
    RadioButton chkPaymentPlay;
    @BindView(R.id.rvPayDeposit)
    RecyclerView rvPayDeposit;

    private UserInfo userInfo;
    private String money = "";
    private PayDepositBean payDepositBean;
    private PayDepositAdapter payDepositAdapter;
    private List<PayDepositBean> depositBeanList;

    public static PayDepositFragment newInstance() {
        Bundle args = new Bundle();
        PayDepositFragment fragment = new PayDepositFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IPayDepositModule initModule() {
        return new PayDepositModule();
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();

        rvPayDeposit.setLayoutManager(new LinearLayoutManager(getActivity()));
        payDepositAdapter = new PayDepositAdapter();
        payDepositAdapter.setOnItemClickListener(this);
        payDepositAdapter.setCallBack(this);
        rvPayDeposit.setAdapter(payDepositAdapter);

        new MonitorPayCheckBox()
                .setChkWeChatBalance(chkPaymentWeChat)
                .setChkAlipayBalance(chkPaymentPlay);

        iModule.requestPayDeposit();
    }

    @Override
    public void requestPayDeposit(List<PayDepositBean> depositBeanList) {
        this.depositBeanList = depositBeanList;
        payDepositAdapter.setNewData(depositBeanList);
        if (!depositBeanList.isEmpty()) {
            int masterRankId = depositBeanList.get(0).getMasterRankId();
            payDepositBean = depositBeanList.get(0);
            payDepositAdapter.notifyPayDeposit(masterRankId);
        }
    }

    @Override
    public void callBack(PayDepositBean item) {
        payDepositBean = item;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (!depositBeanList.isEmpty()) {
            int masterRankId = depositBeanList.get(position).getMasterRankId();
            payDepositAdapter.notifyPayDeposit(masterRankId);
        }
    }

    @OnClick({R.id.btnDepositToPay, R.id.tvRule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnDepositToPay:
                iModule.payDeposit();
                break;
            case R.id.tvRule:
                startFragment(WebViewFragment.newInstance(ConstantUtil.MASTER_FABRICATING_USER_SERVICE_PROTOCOL,
                        getString(R.string.fabricating_user_service_protocol)));
                break;
            default:
        }

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_pay_deposit;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.pay_deposit);
    }

    @Override
    public String getMoney() {
        if (payDepositBean != null) {
            return String.valueOf(payDepositBean.getBond());
        }
        return money;
    }

    @Override
    public boolean chkPaymentPlayIsChecked() {
        return chkPaymentPlay.isChecked();
    }

    @Override
    public boolean chkPaymentWeChatIsChecked() {
        return chkPaymentWeChat.isChecked();
    }

    @Override
    public void playSuccess() {
        RxUtils.getObservable(ServiceUrl.getUserApi().getUser())
                .compose(this.<HttpResult<UserInfo>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<UserInfo>(getActivity()) {
                    @Override
                    protected void onSuccess(UserInfo response) {
                        DBHelper.getInstance().getUserInfoDao().deleteAll();
                        DBHelper.getInstance().getUserInfoDao().insert(userInfo);
                        startFragment(PaymentSuccessFragment.newInstance());
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeChatSuccess(MessageEvent messageEvent) {
        switch (messageEvent.getErrCode()) {
            case 0:
                //微信支付成功
                showToast(getString(R.string.payment_success));
                playSuccess();
                break;
            case -2:
                break;
            default:
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
