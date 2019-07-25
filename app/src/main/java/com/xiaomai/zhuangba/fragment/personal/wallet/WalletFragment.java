package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.AmountUtil;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.WalletBean;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.Util;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 */
public class WalletFragment extends BaseFragment implements OnRefreshListener {

    @BindView(R.id.topBarBase)
    QMUITopBarLayout topBarBase;
    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tvTotalMoney)
    TextView tvTotalMoney;
    @BindView(R.id.tvCanWithdraw)
    TextView tvCanWithdraw;

    @BindView(R.id.tvEarnestMoney)
    TextView tvEarnestMoney;
    @BindView(R.id.tvIncome)
    TextView tvIncome;
    @BindView(R.id.tvWasWithdraw)
    TextView tvWasWithdraw;

    public static WalletFragment newInstance() {
        Bundle args = new Bundle();
        WalletFragment fragment = new WalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        //标题
        topBarBase.setTitle(getString(R.string.wallet));
        //返回
        topBarBase.addLeftImageButton(R.drawable.ic_back_white,
                com.example.toollib.R.id.qmui_topbar_item_left_back)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popBackStack();
                    }
                });
        //右侧标题
        topBarBase.addRightTextButton(getString(R.string.wallet_detail), QMUIViewHelper.generateViewId())
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //明细
                    }
                });
        //设置字体白色
        statusBarWhite();


        refreshLayout.setEnableRefresh(true);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.autoRefresh();
        refreshLayout.setHeaderInsetStart(56);
    }

    @Override
    public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
        RxUtils.getObservable(ServiceUrl.getUserApi().getWallet())
                .compose(this.<HttpResult<WalletBean>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<WalletBean>() {
                    @Override
                    protected void onSuccess(WalletBean walletBean) {
                        //账户总额 = 保证金 + 可提现
                        tvTotalMoney.setText(String.valueOf(AmountUtil.add(walletBean.getBond(),walletBean.getWithDrawableCash() , 2)));
                        //可提现金额
                        tvCanWithdraw.setText(Util.getZero(walletBean.getWithDrawableCash()));
                        //保证金
                        tvEarnestMoney.setText(getString(R.string.content_money, Util.getZero(walletBean.getBond())));
                        //收入 = 已提现 + 可提现
                        tvIncome.setText(getString(R.string.content_money,
                                String.valueOf(AmountUtil.add(walletBean.getCashAlreadyAvailable() , walletBean.getWithDrawableCash() , 2))));
                        //已提现
                        tvWasWithdraw.setText(getString(R.string.content_money, Util.getZero(walletBean.getCashAlreadyAvailable())));
                        refreshLayout.finishRefresh();
                    }
                    @Override
                    public void onError(ApiException e) {
                        super.onError(e);
                        refreshLayout.finishRefresh();
                    }
                });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_wallet;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }
}
