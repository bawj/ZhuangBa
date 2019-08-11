package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.DensityUtils;
import com.example.toollib.util.Log;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.PayData;
import com.xiaomai.zhuangba.data.bean.PlayModule;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.MonitorPayCheckBox;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 * <p>
 * 充值
 */
public class EmployerRechargeFragment extends BaseFragment {

    @BindView(R.id.editRecharge)
    EditText editRecharge;
    @BindView(R.id.chkPaymentWeChat)
    RadioButton chkPaymentWeChat;
    @BindView(R.id.chkPaymentPlay)
    RadioButton chkPaymentPlay;

    public static EmployerRechargeFragment newInstance() {
        Bundle args = new Bundle();
        EmployerRechargeFragment fragment = new EmployerRechargeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        new MonitorPayCheckBox()
                .setChkWeChatBalance(chkPaymentWeChat)
                .setChkAlipayBalance(chkPaymentPlay);
        statusBarBlack();
    }

    @OnClick(R.id.btnEmployerConfirmationRecharge)
    public void onViewClicked() {
        String money = editRecharge.getText().toString();
        if (TextUtils.isEmpty(money)) {
            ToastUtil.showShort(getString(R.string.please_input_recharge));
        } else {
            Observable<HttpResult<PayData>> httpResultObservable;
            //确认充值
            if (chkPaymentPlay.isChecked()) {
                //支付宝 充值
                httpResultObservable = ServiceUrl.getUserApi().walletPay(money, StringTypeExplain.A_ALIPAY_PAYMENT.getCode());
            } else {
                //微信充值
                httpResultObservable = ServiceUrl.getUserApi().walletPay(money, StringTypeExplain.WE_CHAT_PAYMENT.getCode());
            }
            RxUtils.getObservable(httpResultObservable)
                    .compose(this.<HttpResult<PayData>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<PayData>(getActivity()) {
                        @Override
                        protected void onSuccess(PayData payData) {
                            Log.e("subscribe onSuccess = " + payData.toString());
                            if (!TextUtils.isEmpty(payData.getAliPay())) {
                                new PlayModule().aplipayOrderPayment(getActivity(), payData, new BaseCallback() {
                                    @Override
                                    public void onSuccess(Object obj) {
                                        //充值成功
                                    }
                                });
                            } else {
                                new PlayModule().weChatOrderPayment(getActivity(), payData);
                            }
                        }
                    });
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_recharge;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.recharge);
    }

    @Override
    public boolean isInSwipeBack() {
        statusBarWhite();
        return super.isInSwipeBack();
    }

    @Override
    protected void popBackStack() {
        statusBarWhite();
        super.popBackStack();
    }

}
