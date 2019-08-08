package com.xiaomai.zhuangba.weight;

import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/8/7 0007
 */
public class ShopPayCheckBox implements CompoundButton.OnCheckedChangeListener {

    /** 微信支付 */
    private RadioButton chkWeChatBalance;
    /** 支付宝支付 */
    private RadioButton chkAliPayBalance;
    /** 钱包支付 */
    private RadioButton chkWalletBalance;

    public ShopPayCheckBox setChkWeChatBalance(RadioButton chkWeChatBalance) {
        this.chkWeChatBalance = chkWeChatBalance;
        this.chkWeChatBalance.setOnCheckedChangeListener(this);
        return this;
    }

    public ShopPayCheckBox setChkAlipayBalance(RadioButton chkAliPayBalance) {
        this.chkAliPayBalance = chkAliPayBalance;
        this.chkAliPayBalance.setOnCheckedChangeListener(this);
        return this;
    }

    public ShopPayCheckBox setChkWalletBanlance(RadioButton chkWalletBalance) {
        this.chkWalletBalance = chkWalletBalance;
        this.chkWalletBalance.setOnCheckedChangeListener(this);
        return this;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean flag) {
        switch (compoundButton.getId()) {
            case R.id.chkPaymentWeChat:
                if (flag) {
                    chkAliPayBalance.setChecked(false);
                    chkWalletBalance.setChecked(false);
                }
                break;
            case R.id.chkPaymentPlay:
                if (flag) {
                    chkWeChatBalance.setChecked(false);
                    chkWalletBalance.setChecked(false);
                }
                break;
            case R.id.chkPaymentWallet:
                if (flag) {
                    chkAliPayBalance.setChecked(false);
                    chkWeChatBalance.setChecked(false);
                }
                break;
            default:
        }
    }
}
