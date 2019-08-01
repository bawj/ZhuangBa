package com.xiaomai.zhuangba.fragment.personal.wallet;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.MessageEvent;
import com.xiaomai.zhuangba.enums.EventBusEnum;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.CodeEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/7/31 0031
 * <p>
 * 设置交易密码
 */
public class SetTradePasswordFragment extends BaseFragment {

    @BindView(R.id.codeTxt)
    CodeEditText codeTxt;

    public String password;

    public static SetTradePasswordFragment newInstance() {
        Bundle args = new Bundle();
        SetTradePasswordFragment fragment = new SetTradePasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initView() {
        codeTxt.setOnTextChangeListener(new CodeEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String pwd) {
                password = pwd;
            }
        });
    }

    @OnClick(R.id.btnFinish)
    public void onFinishClick() {
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showShort(getString(R.string.input_wallet_trade_password_null));
        } else {
            submission();
        }
    }

    public void submission() {
        Observable<HttpResult<String>> observable = ServiceUrl.getUserApi().setTradePassword(password);
        RxUtils.getObservable(observable)
                .compose(this.<HttpResult<String>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<String>(getActivity()) {
                    @Override
                    protected void onSuccess(String response) {
                        EventBus.getDefault().post(new MessageEvent(EventBusEnum.WITHDRAWAL_PASSWORD.getCode()));
                        ToastUtil.showShort(getString(R.string.wallet_set_trade_success));
                        popBackStack();
                    }
                });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_set_trade_password;
    }

    @Override
    protected String getActivityTitle() {
        return "";
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }
}
