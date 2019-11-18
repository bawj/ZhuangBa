package com.xiaomai.zhuangba.fragment.authentication.employer;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.BaseCallback;
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
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.master.CertificationSuccessfulFragment;
import com.xiaomai.zhuangba.fragment.service.LocationFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class EmployerInformationFragment extends BaseFragment {

    @BindView(R.id.editPhone)
    public EditText editPhone;
    @BindView(R.id.editEmployerName)
    public EditText editEmployerName;
    @BindView(R.id.editAddress)
    public TextView editAddress;
    @BindView(R.id.editAddressDetail)
    public EditText editAddressDetail;
    @BindView(R.id.editEnterpriseName)
    public EditText editEnterpriseName;

    public static final String BUSINESS_LICENSE_URL = "business_license_url";

    public static EmployerInformationFragment newInstance(String businessLicenseUrl) {
        Bundle args = new Bundle();
        args.putString(BUSINESS_LICENSE_URL, businessLicenseUrl);
        EmployerInformationFragment fragment = new EmployerInformationFragment();
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

    @OnClick({R.id.btnUpload, R.id.relAddress})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnUpload:
                upload();
                break;
            case R.id.relAddress:
                applyPermission();
                break;
            default:
        }
    }

    public void upload() {
        String employerName = editEmployerName.getText().toString();
        String phone = editPhone.getText().toString();
        String address = editAddress.getText().toString();
        String enterpriseName = editEnterpriseName.getText().toString();
        if (TextUtils.isEmpty(employerName)) {
            ToastUtil.showShort(getString(R.string.please_enter_the_employer_name));
        } else if (TextUtils.isEmpty(phone)) {
            ToastUtil.showShort(getString(R.string.please_enter_your_contact_information));
        } else if (TextUtils.isEmpty(address)) {
            ToastUtil.showShort(getString(R.string.please_check_service_address));
        } else if (TextUtils.isEmpty(enterpriseName)) {
            ToastUtil.showShort(getString(R.string.please_input_enterprise_name));
        } else {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserText(editEmployerName.getText().toString());
            userInfo.setEmergencyContact(editPhone.getText().toString());
            userInfo.setRole(String.valueOf(StaticExplain.EMPLOYER.getCode()));
            userInfo.setBusinessLicense(getBusinessLicenseUrl());
            userInfo.setAddress(editAddress.getText().toString());
            userInfo.setCompanyName(editEnterpriseName.getText().toString());
            userInfo.setContactAddress(editAddressDetail.getText().toString());
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(userInfo));
            Observable<HttpResult<UserInfo>> observable = ServiceUrl.getUserApi().certification(requestBody);
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

    private void applyPermission() {
        RxPermissionsUtils.applyPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, new BaseCallback<String>() {
            @Override
            public void onSuccess(String obj) {
                startFragmentForResult(LocationFragment.newInstance(), ForResultCode.START_FOR_RESULT_CODE.getCode());
            }

            @Override
            public void onFail(Object obj) {
                super.onFail(obj);
                showToast(getString(R.string.positioning_authority_tip));
            }
        });
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()) {
                //地址选择成功返回
                String name = data.getStringExtra(ForResultCode.RESULT_KEY.getExplain());
                editAddress.setText(name);
            }
        }
    }


    private String getBusinessLicenseUrl() {
        if (getArguments() != null) {
            return getArguments().getString(BUSINESS_LICENSE_URL);
        }
        return "";
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_employer_information;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.employer_information);
    }
}
