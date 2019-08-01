package com.xiaomai.zhuangba.fragment.personal.set;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.weight.SingleCountDownView;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.CodeEditText;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 */
public class UpdatePassTwoFragment extends BaseFragment {

    @BindView(R.id.tvUpdatePassHasBeenSent)
    TextView tvUpdatePassHasBeenSent;
    @BindView(R.id.singleCountDown)
    SingleCountDownView singleCountDown;
    @BindView(R.id.codeTxt)
    CodeEditText codeTxt;

    private String verificationCode;
    public static final String PHONE = "phone";
    public static final String VERIFICATION_CODE = "verification_code";

    public static UpdatePassTwoFragment newInstance(String phone, String verificationCode) {
        Bundle args = new Bundle();
        args.putString(PHONE, phone);
        args.putString(VERIFICATION_CODE, verificationCode);
        UpdatePassTwoFragment fragment = new UpdatePassTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        verificationCode =  getVerificationCode();
        tvUpdatePassHasBeenSent.setText(String.format(getString(R.string.has_been_sent), getPhone()));
        singleCountDown.setTimeColorHex("#0091FF");
        singleCountDown.startCountDown();
        singleCountDown.setSingleCountDownEndListener(new SingleCountDownView.SingleCountDownEndListener() {
            @Override
            public void onSingleCountDownEnd() {

            }
        });

        codeTxt.setOnTextChangeListener(new CodeEditText.OnTextChangeListener() {
            @Override
            public void onTextChange(String pwd) {
                codeTxt.setTag(pwd);
            }
        });
    }

    @OnClick({R.id.btnUpdatePassNext, R.id.singleCountDown})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnUpdatePassNext:
                //验证验证码输入是否正确
                Object tag = codeTxt.getTag();
                verificationCode(tag);
                break;
            case R.id.singleCountDown:
                if (!singleCountDown.isContinue()) {
                    RxUtils.getObservable(ServiceUrl.getUserApi().getAuthenticationCode(getPhone(),
                            StringTypeExplain.REGISTERED_FORGET_PASSWORD.getCode())
                            .compose(this.<HttpResult<Object>>bindLifecycle()))
                            .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                                @Override
                                protected void onSuccess(Object response) {
                                    singleCountDown.startCountDown();
                                    verificationCode = response.toString();
                                }
                            });
                }
                break;
            default:
        }
    }

    public void verificationCode(Object tag) {
        if (verificationCode.equals(tag)) {
            //修改密码
            startFragment(SetNewPassFragment.newInstance(getPhone()));
        } else {
            showToast(getString(R.string.verification_code_input_incorrectly));
        }
    }

    private String getPhone() {
        if (getArguments() != null) {
            return getArguments().getString(PHONE);
        }
        return "";
    }

    public String getVerificationCode() {
        if (getArguments() != null) {
            return getArguments().getString(VERIFICATION_CODE);
        }
        return "";
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_update_pass_two;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }
}
