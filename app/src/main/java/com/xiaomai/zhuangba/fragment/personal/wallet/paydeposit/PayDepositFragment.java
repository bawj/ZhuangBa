package com.xiaomai.zhuangba.fragment.personal.wallet.paydeposit;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.MessageEvent;
import com.xiaomai.zhuangba.data.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.personal.agreement.WebViewFragment;
import com.xiaomai.zhuangba.fragment.personal.wallet.PaymentSuccessFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.weight.MonitorPayCheckBox;
import com.xiaomai.zhuangba.weight.MonitorPayDepositCheckBox;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 * 缴纳保证金
 */
public class PayDepositFragment extends BaseFragment<IPayDepositModule> implements IPayDepositView {

    @BindView(R.id.chkPaymentWeChat)
    RadioButton chkPaymentWeChat;
    @BindView(R.id.chkPaymentPlay)
    RadioButton chkPaymentPlay;

    @BindView(R.id.tvDepositTitle)
    TextView tvDepositTitle;
    @BindView(R.id.tvPayDepositMoney)
    TextView tvPayDepositMoney;
    @BindView(R.id.tvPayDepositMsg)
    TextView tvPayDepositMsg;
    @BindView(R.id.chkPayDepositMoney)
    RadioButton chkPayDepositMoney;

    @BindView(R.id.tvDepositTitleTwo)
    TextView tvDepositTitleTwo;
    @BindView(R.id.tvPayDepositMoneyTwo)
    TextView tvPayDepositMoneyTwo;
    @BindView(R.id.tvPayDepositMsgTwo)
    TextView tvPayDepositMsgTwo;
    @BindView(R.id.chkPayDepositMoneyTwo)
    RadioButton chkPayDepositMoneyTwo;

    @BindView(R.id.layPayDepositMoney)
    LinearLayout layPayDepositMoney;

    private UserInfo userInfo;

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

        new MonitorPayCheckBox()
                .setChkWeChatBalance(chkPaymentWeChat)
                .setChkAlipayBalance(chkPaymentPlay);
        new MonitorPayDepositCheckBox()
                .setPayDeposit(chkPayDepositMoney)
                .setPayDepositTwo(chkPayDepositMoneyTwo);

        if (userInfo.getMasterRankId().equals(String.valueOf(StaticExplain.INTERNSHIP.getCode()))) {
            //实习师傅
            layPayDepositMoney.setVisibility(View.GONE);
            chkPayDepositMoneyTwo.setChecked(true);
        }
        iModule.requestPayDeposit();
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
    public void setTvPayDepositMoney(String s) {
        tvPayDepositMoney.setText(s);
    }

    @Override
    public void setTvDepositTitle(String masterRankName) {
        tvDepositTitle.setText(masterRankName);
    }

    @Override
    public void setTvPayDepositMsg(String explain) {
        tvPayDepositMsg.setText(explain);
    }

    @Override
    public void setTvPayDepositMoneyTwo(String bond) {
        tvPayDepositMoneyTwo.setText(bond);
    }

    @Override
    public void setTvDepositTitleTwo(String masterRankName) {
        tvDepositTitleTwo.setText(masterRankName);
    }

    @Override
    public void setTvPayDepositMsgTwo(String explain) {
        tvPayDepositMsgTwo.setText(explain);
    }

    @Override
    public String getMoney() {
        String money;
        if (chkPayDepositMoney.isChecked()) {
            money = tvPayDepositMoney.getText().toString();
        } else {
            money = tvPayDepositMoneyTwo.getText().toString();
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
                        startFragmentAndDestroyCurrent(PaymentSuccessFragment.newInstance());
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
