package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;

import com.xiaomai.zhuangba.fragment.personal.wallet.WithdrawFragment;

/**
 * @author Administrator
 * @date 2019/9/25 0025
 * 提现
 */
public class EmployerWalletWithdrawalFragment extends WithdrawFragment {

    public static EmployerWalletWithdrawalFragment newInstance() {
        Bundle args = new Bundle();
        EmployerWalletWithdrawalFragment fragment = new EmployerWalletWithdrawalFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
