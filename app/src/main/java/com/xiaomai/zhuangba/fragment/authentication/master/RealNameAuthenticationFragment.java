package com.xiaomai.zhuangba.fragment.authentication.master;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.MasterAuthenticationInfo;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;

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
        MasterAuthenticationInfo masterAuthenticationInfo = new MasterAuthenticationInfo();
        masterAuthenticationInfo.setUserText(editAuthenticationName.getText().toString());
        String emergencyContact = editEmergencyContact.getText().toString();
        String address = editAddress.getText().toString();
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
