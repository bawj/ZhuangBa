package com.xiaomai.zhuangba.fragment.personal.set;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.login.LoginFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 */
public class SetNewPassFragment extends BaseFragment {

    @BindView(R.id.editUpdatePass)
    EditText editUpdatePass;
    @BindView(R.id.editUpdatePassTwoPass)
    EditText editUpdatePassTwoPass;

    public static final String PHONE = "phone";

    public static SetNewPassFragment newInstance(String phone) {
        Bundle args = new Bundle();
        args.putString(PHONE, phone);
        SetNewPassFragment fragment = new SetNewPassFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.btnUpdatePassTwo)
    public void onViewClicked() {
        String passwordOne = editUpdatePass.getText().toString();
        String passwordTwo = editUpdatePassTwoPass.getText().toString();
        if (TextUtils.isEmpty(passwordOne)) {
            ToastUtil.showShort(getString(R.string.please_input_pass));
        } else if (TextUtils.isEmpty(passwordTwo)) {
            ToastUtil.showShort(getString(R.string.please_input_pass));
        } else if (!passwordOne.equals(passwordTwo)) {
            ToastUtil.showShort(getString(R.string.password_mismatch_entered_twice));
        } else if (passwordOne.length() < 7) {
            ToastUtil.showShort(getString(R.string.min_pass_length));
        } else if (passwordOne.length() > 20) {
            ToastUtil.showShort(getString(R.string.max_pass_length));
        } else {
            Observable<HttpResult<Object>> register = ServiceUrl.getUserApi()
                    .forgetPassword(getPhone(), passwordOne);
            RxUtils.getObservable(register).compose(this.<HttpResult<Object>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>() {
                        @Override
                        protected void onSuccess(Object response) {
                            showToast(getString(R.string.pass_update_success));
                            //修改成功 重新登录
                            startFragment(LoginFragment.newInstance());
                        }
                    });

        }

    }


    @Override
    public int getContentView() {
        return R.layout.fragment_set_new_pass;
    }

    public String getPhone() {
        if (getArguments() != null) {
            return getArguments().getString(PHONE);
        }
        return "";
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }
}
