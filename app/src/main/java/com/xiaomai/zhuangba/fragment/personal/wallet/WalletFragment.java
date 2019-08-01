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
import com.example.toollib.util.DensityUtils;
import com.example.toollib.util.ToastUtil;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.MessageEvent;
import com.xiaomai.zhuangba.data.WalletBean;
import com.xiaomai.zhuangba.enums.EventBusEnum;
import com.xiaomai.zhuangba.enums.WalletOrderTypeEnum;
import com.xiaomai.zhuangba.fragment.personal.wallet.detailed.CashWithdrawalFragment;
import com.xiaomai.zhuangba.fragment.personal.wallet.detailed.IncomeDetailedFragment;
import com.xiaomai.zhuangba.fragment.personal.wallet.detailed.WalletDetailFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.Util;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

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

    private WalletBean walletBeans;

    public static WalletFragment newInstance() {
        Bundle args = new Bundle();
        WalletFragment fragment = new WalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
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
                        startFragment(WalletDetailFragment.newInstance());
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
                        walletBeans = walletBean;
                        //账户总额 = 保证金 + 可提现
                        tvTotalMoney.setText(String.valueOf(AmountUtil.add(walletBean.getBond(), walletBean.getWithDrawableCash(), 2)));
                        //可提现金额
                        tvCanWithdraw.setText(Util.getZero(walletBean.getWithDrawableCash()));
                        //保证金
                        tvEarnestMoney.setText(getString(R.string.content_money, Util.getZero(walletBean.getBond())));
                        //收入 = 已提现 + 可提现
                        tvIncome.setText(getString(R.string.content_money,
                                String.valueOf(AmountUtil.add(walletBean.getCashAlreadyAvailable(), walletBean.getWithDrawableCash(), 2))));
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

    @OnClick({R.id.btnWithdraw, R.id.tvAccountManager, R.id.tvAccountSafe, R.id.rlEarNest,R.id.rlIncome, R.id.rlAlreadyWithdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnWithdraw:
                //提现
                startToWithdraw();
                break;
            case R.id.tvAccountManager:
                //提现账号管理
                startFragment(AliPayAccountFragment.newInstance(1));
                break;
            case R.id.tvAccountSafe:
                //账号安全
                if (walletBeans != null) {
                    startFragment(TradePhoneFragment.newInstance(walletBeans.getPresentationPassword()));
                }
                break;
            case R.id.rlEarNest:
                //保证金
                startToEarNest();
                break;
            case R.id.rlIncome:
                //收入
                startFragment(IncomeDetailedFragment.newInstance());
                break;
            case R.id.rlAlreadyWithdraw:
                //已提现
                startFragment(CashWithdrawalFragment.newInstance());
                break;
            default:
        }
    }


    /**
     * 保证金
     */
    private void startToEarNest() {
        if (walletBeans != null) {
            if (walletBeans.getBond() <= 0) {
                ToastUtil.showShort(getString(R.string.wallet_earnest_zero));
            } else if (walletBeans.getPresentationPassword().equals("2")) {
                ToastUtil.showShort(getString(R.string.please_wallet_set_trade_success));
            }else {
                startFragment(EarnestFragment.newInstance());
            }
        }
    }

    /**
     * 提现
     */
    private void startToWithdraw() {
        String canWithdraw = tvCanWithdraw.getText().toString();
        Double i = DensityUtils.stringTypeDouble(canWithdraw);
        if (i == 0) {
            ToastUtil.showShort(getString(R.string.wallet_can_withdraw_zero));
        } else if (walletBeans != null && walletBeans.getPresentationPassword().equals(String.valueOf(WalletOrderTypeEnum.NULL_PRESENTATION_PASSWORD.getCode()))) {
            CommonlyDialog.getInstance().initView(getActivity())
                    .setTvDialogCommonlyContent(getString(R.string.set_transaction))
                    .setTvDialogCommonlyOk(getString(R.string.go_set_transaction))
                    .setICallBase(new CommonlyDialog.BaseCallback() {
                        @Override
                        public void sure() {
                            startFragment(TradePhoneFragment.newInstance(walletBeans.getPresentationPassword()));
                        }
                    }).showDialog();
        } else if (walletBeans != null) {
            startFragment(WithdrawFragment.newInstance(walletBeans.getWithDrawableCash()));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeChatSuccess(MessageEvent messageEvent) {
        if (messageEvent.getErrCode() == EventBusEnum.WITHDRAWAL_PASSWORD.getCode()) {
            if (walletBeans != null) {
                walletBeans.setPresentationPassword(String.valueOf(WalletOrderTypeEnum.YES_PRESENTATION_PASSWORD.getCode()));
            }
        }else if (messageEvent.getErrCode() == EventBusEnum.CASH_SUCCESS.getCode()){
            refreshLayout.autoRefresh();
        }
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

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
