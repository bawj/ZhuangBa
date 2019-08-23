package com.xiaomai.zhuangba.fragment.authentication.employer;

import android.os.Bundle;
import android.text.TextUtils;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.example.toollib.util.spf.SPUtils;
import com.example.toollib.util.spf.SpfConst;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.master.CertificationSuccessfulFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.dialog.AuthenticationDialog;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/21 0021
 */
public class EmployerRealNameAuthenticationFragment extends EmployerInformationFragment {

    public static EmployerRealNameAuthenticationFragment newInstance() {
        Bundle args = new Bundle();
        EmployerRealNameAuthenticationFragment fragment = new EmployerRealNameAuthenticationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void upload() {
        AuthenticationDialog.getInstance()
                .initView(getActivity())
                .setTvAuthenticationTitle(getString(R.string.selection_of_employer_account_number))
                .setTvAuthenticationContent(getString(R.string.do_you_want_to_continue))
                .setICallBase(new AuthenticationDialog.BaseCallback() {
                    @Override
                    public void ok() {
                        requestUpload();
                    }
                })
                .showDialog();
    }

    private void requestUpload() {
        String employerName = editEmployerName.getText().toString();
        String phone = editPhone.getText().toString();
        String address = editAddress.getText().toString();
        if (TextUtils.isEmpty(employerName)){
            ToastUtil.showShort(getString(R.string.please_enter_the_employer_name));
        }else if (TextUtils.isEmpty(phone)){
            ToastUtil.showShort(getString(R.string.please_enter_your_contact_information));
        }else if (TextUtils.isEmpty(address)){
            ToastUtil.showShort(getString(R.string.please_check_service_address));
        }else {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserText(employerName);
            userInfo.setEmergencyContact(phone);
            userInfo.setRole(String.valueOf(StaticExplain.EMPLOYER.getCode()));
            userInfo.setAddress(address);
            userInfo.setContactAddress(editAddressDetail.getText().toString());
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(userInfo));
            Observable<HttpResult<UserInfo>> observable = ServiceUrl.getUserApi().updateRegistrationInformation(requestBody);
            RxUtils.getObservable(observable)
                    .compose(this.<HttpResult<UserInfo>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<UserInfo>(getActivity()) {
                        @Override
                        protected void onSuccess(UserInfo userInfo) {
                            String token = userInfo.getToken();
                            if (!TextUtils.isEmpty(token)) {
                                SPUtils.getInstance().put(SpfConst.TOKEN, token);
                            }
                            DBHelper.getInstance().getUserInfoDao().deleteAll();
                            DBHelper.getInstance().getUserInfoDao().insert(userInfo);
                            startFragment(CertificationSuccessfulFragment.newInstance());
                        }
                    });
        }
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.employer_information);
    }
}
