package com.xiaomai.zhuangba.fragment.personal.master;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.personal.PersonalFragment;
import com.xiaomai.zhuangba.fragment.personal.wallet.WalletFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 *
 * 师傅端 个人中心
 */
public class MasterPersonalFragment extends PersonalFragment {

    @BindView(R.id.tvPersonalTodayNumbers)
    TextView tvPersonalTodayNumbers;
    @BindView(R.id.tvPersonalTodayIncome)
    TextView tvPersonalTodayIncome;

    /** 师傅端显示 今日接单 */
    public static final String ORDER_TODAY = "order_today";
    /** 师傅端显示 今日收入 */
    public static final String TODAY_FLOWING_RMB = "today_flowing_rmb";

    public static MasterPersonalFragment newInstance(String orderToday , String todayFlowingRmb) {
        Bundle args = new Bundle();
        args.putString(ORDER_TODAY , orderToday);
        args.putString(TODAY_FLOWING_RMB , todayFlowingRmb);
        MasterPersonalFragment fragment = new MasterPersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        super.initView();
        //今日收入
        tvPersonalTodayIncome.setText(getTodayFlowingRmb());
        //今日单数
        tvPersonalTodayNumbers.setText(getOrderToday());
    }

    @OnClick({R.id.relWallet})
    public void onMasterViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relWallet:
                //钱包
                startFragment(WalletFragment.newInstance());
                break;
            default:
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_personal;
    }

    /** 今日单数 */
    private String getOrderToday(){
        if (getArguments() != null){
            return getArguments().getString(ORDER_TODAY);
        }
        return "0";
    }

    /** 今日收入 */
    private String getTodayFlowingRmb(){
        if (getArguments() != null){
            return getArguments().getString(TODAY_FLOWING_RMB);
        }
        return "0";
    }
}
