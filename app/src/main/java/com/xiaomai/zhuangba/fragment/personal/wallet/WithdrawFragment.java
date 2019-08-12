package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.AliPayAccountBean;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.MoneyValueFilter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/7/31 0031
 */
public class WithdrawFragment extends BaseFragment implements TextWatcher {


    @BindView(R.id.tv_withdraw_hint)
    TextView tvWithdrawHint;
    @BindView(R.id.edit_money)
    EditText editMoney;

    @BindView(R.id.rl_alipay_account)
    RelativeLayout rlAlipayAccount;
    @BindView(R.id.rl_add)
    RelativeLayout rlAdd;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_account)
    TextView tvAccount;

    @BindView(R.id.viewAlipayAccount)
    View viewAlipayAccount;
    @BindView(R.id.ivAlipayAccountRight)
    ImageView ivAlipayAccountRight;

    private double maxMoney;

    public static WithdrawFragment newInstance(double money) {
        Bundle args = new Bundle();
        args.putDouble("max", money);
        WithdrawFragment fragment = new WithdrawFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        viewAlipayAccount.setVisibility(View.GONE);
        ivAlipayAccountRight.setVisibility(View.VISIBLE);
        maxMoney = getArguments().getDouble("max");
        //默认两位小数
        editMoney.setFilters(new InputFilter[]{new MoneyValueFilter()});
        editMoney.addTextChangedListener(this);
        tvWithdrawHint.setText(String.format(getString(R.string.wallet_withdraw_money_hint), String.valueOf(maxMoney)));

        request();
    }

    private void request() {
        Observable<HttpResult<List<AliPayAccountBean>>> observable = ServiceUrl.getUserApi().getAliPayAccount();
        RxUtils.getObservable(observable)
                .compose(this.<HttpResult<List<AliPayAccountBean>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<AliPayAccountBean>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<AliPayAccountBean> response) {
                        if (response.isEmpty()) {
                            rlAlipayAccount.setVisibility(View.GONE);
                            rlAdd.setVisibility(View.VISIBLE);
                        } else {
                            tvName.setText(response.get(0).getName());
                            tvAccount.setText(response.get(0).getWithdrawalsAccount());
                        }
                    }
                });
    }

    @OnClick({R.id.rl_add, R.id.tv_withdraw, R.id.rl_alipay_account})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.rl_add:
                //添加支付宝账户
                startFragmentForResult(AddAliPayFragment.newInstance(), ForResultCode.START_FOR_RESULT_CODE.getCode());
                break;
            case R.id.rl_alipay_account:
                //支付宝账户
                startFragmentForResult(AliPayAccountFragment.newInstance(2), ForResultCode.START_FOR_RESULT_CODE_.getCode());
                break;
            case R.id.tv_withdraw:
                startToPassword();
                break;
            default:
        }
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()){
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()){
                request();
            }else if (requestCode == ForResultCode.START_FOR_RESULT_CODE_.getCode() && data != null){
                String name = data.getStringExtra("name");
                if (!TextUtils.isEmpty(name)){
                    tvName.setText(name);
                }
                String account = data.getStringExtra("account");
                if (!TextUtils.isEmpty(account)){
                    tvAccount.setText(account);
                }
            }
        }
    }

    /**
     * 跳转到校验密码
     */
    private void startToPassword() {
        String name = tvName.getText().toString();
        String account = tvAccount.getText().toString();
        double amount;

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(account)) {
            ToastUtil.showShort("请先选择提现账号");
            return;
        }
        try {
            String txt = editMoney.getText().toString();
            amount = Double.parseDouble(txt);
            if (amount <= 0) {
                ToastUtil.showShort("金额输入有误");
                return;
            }
        } catch (Exception e) {
            ToastUtil.showShort("金额输入有误");
            return;
        }
        startFragmentAndDestroyCurrent(WithdrawPasswordFragment.newInstance(name, account, amount));
    }


    @Override
    public int getContentView() {
        return R.layout.fragment_withdraw;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.wallet_withdraw);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        try {
            Double input = Double.parseDouble(str);
            if (input > maxMoney) {
                ToastUtil.showShort("您输入的金额超出提现金额");
                editMoney.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
