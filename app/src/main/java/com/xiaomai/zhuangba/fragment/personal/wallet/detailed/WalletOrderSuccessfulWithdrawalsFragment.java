package com.xiaomai.zhuangba.fragment.personal.wallet.detailed;

import android.os.Bundle;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.WalletDetailBean;
import com.xiaomai.zhuangba.data.bean.WalletOrderDetailBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/10/31 0031
 */
public class WalletOrderSuccessfulWithdrawalsFragment extends WalletOrderDetailFragment{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_number)
    TextView tvNumber;

    public static WalletOrderSuccessfulWithdrawalsFragment newInstance(WalletDetailBean.ListBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("date", bean);
        WalletOrderSuccessfulWithdrawalsFragment fragment = new WalletOrderSuccessfulWithdrawalsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        tvTitle.setText(getString(R.string.wallet_was_withdraw));
    }

    @Override
    public List<WalletOrderDetailBean> getList(WalletDetailBean.ListBean bean) {
        tvNumber.setText(String.format(getString(R.string.content_money), String.valueOf(bean.getOrderAmount())));
        List<WalletOrderDetailBean> list = new ArrayList<>();
        // TODO: 2019/10/31 0031 提现时间 和 成功时间 一样
        list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_time), bean.getTimes()));
        list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_account), bean.getWithdrawalsAccount()));
        list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_status), getString(R.string.wallet_out_success)));
        list.add(new WalletOrderDetailBean(getString(R.string.wallet_out_code), bean.getAccountNumber()));
        list.add(new WalletOrderDetailBean(getString(R.string.withdrawal_success_time), bean.getTimes()));
        return list;
    }

}
