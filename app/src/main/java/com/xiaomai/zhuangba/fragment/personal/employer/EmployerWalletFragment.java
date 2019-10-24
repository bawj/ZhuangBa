package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.DensityUtils;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.EmployerWalletBean;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.base.BaseListFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/8 0008
 * <p>
 * 雇主端 钱包
 */
public class EmployerWalletFragment extends BaseListFragment {

    @BindView(R.id.refreshBaseList)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.topBarBase)
    QMUITopBarLayout topBarBase;
    @BindView(R.id.tvEmployerMoney)
    TextView tvEmployerMoney;
    @BindView(R.id.rlAccountSecurity)
    RelativeLayout rlAccountSecurity;

    public static EmployerWalletFragment newInstance() {
        Bundle args = new Bundle();
        EmployerWalletFragment fragment = new EmployerWalletFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void onStart() {
        super.onStart();
        statusBarWhite();
    }

    @Override
    public void initView() {
        super.initView();
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        int roleId = unique.getRoleId();
        if (roleId == StaticExplain.SUPER_ADMINISTRATOR.getCode()){
            //超级管理员 显示 账户安全 其它不显示
            rlAccountSecurity.setVisibility(View.VISIBLE);
        }else if (roleId == StaticExplain.ADMINISTRATOR.getCode()){
            //管理员 不显示账户安全
            rlAccountSecurity.setVisibility(View.GONE);
            //显示钱包
        }else if (roleId == StaticExplain.ORDINARY_STAFF.getCode()){
            //普通员工 不显示账户安全
            rlAccountSecurity.setVisibility(View.GONE);
        }

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
                        startFragment(EmployerWalletDetailFragment.newInstance());
                    }
                });
        refreshLayout.setHeaderInsetStart(56);
    }

    @OnClick({R.id.btnRecharge, R.id.rlRechargeRecord, R.id.rlRecordsOfConsumption,
            R.id.rlAccountSecurity , R.id.tvEmployerWalletRecharge,R.id.tvEmployerWalletWithdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnRecharge:
                //充值
                startFragment(EmployerRechargeFragment.newInstance());
                break;
            case R.id.tvEmployerWalletRecharge:
                //充值
                startFragment(EmployerRechargeFragment.newInstance());
                break;
            case R.id.tvEmployerWalletWithdraw:
                //提现
                String money = tvEmployerMoney.getText().toString();
                startFragment(EmployerWalletWithdrawalFragment.newInstance(DensityUtils.stringTypeDouble(money)));
                break;
            case R.id.rlRechargeRecord:
                //充值记录
                startFragment(RechargeRecordFragment.newInstance());
                break;
            case R.id.rlRecordsOfConsumption:
                //消费记录
                startFragment(RecordsOfConsumptionFragment.newInstance());
                break;
            case R.id.rlAccountSecurity:
                //账户安全
                startFragment(AccountSecurityFragment.newInstance());
                break;
            default:
        }
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String phoneNumber = unique.getPhoneNumber();
        RxUtils.getObservable(ServiceUrl.getUserApi().selectWalletBalance(phoneNumber))
                .compose(this.<HttpResult<EmployerWalletBean>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<EmployerWalletBean>() {
                    @Override
                    protected void onSuccess(EmployerWalletBean employerWalletBean) {
                        tvEmployerMoney.setText(String.valueOf(employerWalletBean.getWithDrawableCash()));
                        finishRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        finishRefresh();
                    }
                });

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_wallet;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.wallet);
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }
}
