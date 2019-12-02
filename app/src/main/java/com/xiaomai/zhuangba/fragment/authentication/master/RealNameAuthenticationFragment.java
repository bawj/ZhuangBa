package com.xiaomai.zhuangba.fragment.authentication.master;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.MasterAuthenticationInfo;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/21 0021
 */
public class RealNameAuthenticationFragment extends BaseFragment {

    @BindView(R.id.editAuthenticationName)
    EditText editAuthenticationName;
    @BindView(R.id.editEmergencyContact)
    EditText editEmergencyContact;
    @BindView(R.id.editAddress)
    EditText editAddress;
    @BindView(R.id.btnAuthenticationNext)
    Button btnAuthenticationNext;

    public static RealNameAuthenticationFragment newInstance() {
        Bundle args = new Bundle();
        RealNameAuthenticationFragment fragment = new RealNameAuthenticationFragment();
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

    @OnClick(R.id.btnAuthenticationNext)
    public void onViewClicked() {
        String address = editAddress.getText().toString();
        if (TextUtils.isEmpty(address)){
            ToastUtil.showShort(getString(R.string.please_fill_in_the_address_));
            return;
        }
        String emergencyContact = editEmergencyContact.getText().toString();
        if (TextUtils.isEmpty(emergencyContact)){
            ToastUtil.showShort(getString(R.string.please_fill_in_the_emergency_contact_number));
            return;
        }
        MasterAuthenticationInfo masterAuthenticationInfo = new MasterAuthenticationInfo();
        masterAuthenticationInfo.setUserText(editAuthenticationName.getText().toString());

        masterAuthenticationInfo.setEmergencyContact(emergencyContact);
        masterAuthenticationInfo.setContactAddress(address);
        startFragment(BareheadedFragment.newInstance(new Gson().toJson(masterAuthenticationInfo)));
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_real_name_authentication;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.real_authentication);
    }


}
