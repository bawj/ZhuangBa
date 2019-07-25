package com.xiaomai.zhuangba.weight;

import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/7/12 0012
 */
public class MonitorPayCheckBox implements CompoundButton.OnCheckedChangeListener {

    /** 微信支付 */
    private RadioButton chkWeChatBalance;
    /** 支付宝支付 */
    private RadioButton chkAliPayBalance;

    public MonitorPayCheckBox setChkWeChatBalance(RadioButton chkWeChatBalance) {
        this.chkWeChatBalance = chkWeChatBalance;
        this.chkWeChatBalance.setOnCheckedChangeListener(this);
        return this;
    }

    public MonitorPayCheckBox setChkAlipayBalance(RadioButton chkAliPayBalance) {
        this.chkAliPayBalance = chkAliPayBalance;
        this.chkAliPayBalance.setOnCheckedChangeListener(this);
        return this;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean flag) {
        switch (compoundButton.getId()) {
            case R.id.chkPaymentWeChat:
                if (flag) {
                    chkAliPayBalance.setChecked(false);
                }
                break;
            case R.id.chkPaymentPlay:
                if (flag) {
                    chkWeChatBalance.setChecked(false);
                }
                break;
            default:
        }
    }
}
