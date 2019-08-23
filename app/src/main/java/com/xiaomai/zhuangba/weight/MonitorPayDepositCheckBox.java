package com.xiaomai.zhuangba.weight;

import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public class MonitorPayDepositCheckBox implements CompoundButton.OnCheckedChangeListener {

    private RadioButton chkPayDepositMoney;
    private RadioButton chkPayDepositMoneyTwo;

    public MonitorPayDepositCheckBox setPayDeposit(RadioButton chkWeChatBalance) {
        this.chkPayDepositMoney = chkWeChatBalance;
        this.chkPayDepositMoney.setOnCheckedChangeListener(this);
        return this;
    }

    public MonitorPayDepositCheckBox setPayDepositTwo(RadioButton chkAliPayBalance) {
        this.chkPayDepositMoneyTwo = chkAliPayBalance;
        this.chkPayDepositMoneyTwo.setOnCheckedChangeListener(this);
        return this;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean flag) {
        /*switch (compoundButton.getId()) {
            case R.id.chkPayDepositMoney:
                if (flag) {
                    chkPayDepositMoneyTwo.setChecked(false);
                }
                break;
            case R.id.chkPayDepositMoneyTwo:
                if (flag) {
                    chkPayDepositMoney.setChecked(false);
                }
                break;
            default:
        }*/
    }
}
