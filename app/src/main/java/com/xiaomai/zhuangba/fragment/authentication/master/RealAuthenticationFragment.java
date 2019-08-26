package com.xiaomai.zhuangba.fragment.authentication.master;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.toollib.base.BaseFragment;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.MasterAuthenticationInfo;
import com.xiaomai.zhuangba.data.module.authentication.IMasterAuthenticationModule;
import com.xiaomai.zhuangba.data.module.authentication.IMasterAuthenticationView;
import com.xiaomai.zhuangba.data.module.authentication.MasterAuthenticationModule;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 * <p>
 * 师傅端实名认证
 */
public class RealAuthenticationFragment extends BaseFragment<IMasterAuthenticationModule> implements IMasterAuthenticationView {

    @BindView(R.id.editAuthenticationName)
    EditText editAuthenticationName;
    @BindView(R.id.editEmergencyContact)
    EditText editEmergencyContact;
    @BindView(R.id.editAddress)
    EditText editAddress;
    @BindView(R.id.editAuthenticationIdCard)
    EditText editAuthenticationIdCard;
    @BindView(R.id.editAuthenticationTermOfValidity)
    EditText editAuthenticationTermOfValidity;
    @BindView(R.id.btnAuthenticationNext)
    Button btnAuthenticationNext;

    private MasterAuthenticationInfo masterAuthenticationInfo;
    public static final String MASTER_AUTHENTICATION_INFO = "master_authentication_info";

    public static RealAuthenticationFragment newInstance(String masterAuthenticationInfo) {
        Bundle args = new Bundle();
        args.putString(MASTER_AUTHENTICATION_INFO , masterAuthenticationInfo);
        RealAuthenticationFragment fragment = new RealAuthenticationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IMasterAuthenticationModule initModule() {
        return new MasterAuthenticationModule();
    }

    @Override
    public void initView() {
        if (getArguments() != null){
            String s = getArguments().getString(MASTER_AUTHENTICATION_INFO);
            masterAuthenticationInfo = new Gson().fromJson(s , MasterAuthenticationInfo.class);
            editAuthenticationName.setText(masterAuthenticationInfo.getUserText());
            editAuthenticationIdCard.setText(masterAuthenticationInfo.getIdentityCard());
            editAuthenticationTermOfValidity.setText(masterAuthenticationInfo.getValidityData());
        }
    }


    @OnClick({R.id.btnAuthenticationNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAuthenticationNext:
                iModule.requestIdCardImg();
                break;
            default:
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_real_authentication;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.real_authentication);
    }

    @Override
    public String getUserText() {
        return editAuthenticationName.getText().toString();
    }

    @Override
    public String getIdentityCard() {
        return editAuthenticationIdCard.getText().toString();
    }

    @Override
    public String getIdCardFrontPhoto() {
        if (masterAuthenticationInfo != null){
            return masterAuthenticationInfo.getIdCardFrontPhoto();
        }
        return null;
    }

    @Override
    public String getIdCardBackPhoto() {
        if (masterAuthenticationInfo != null){
            return masterAuthenticationInfo.getIdCardBackPhoto();
        }
        return null;
    }

    @Override
    public String getValidityData() {
        return editAuthenticationTermOfValidity.getText().toString();
    }

    @Override
    public String getEmergencyContact() {
        return editEmergencyContact.getText().toString();
    }

    @Override
    public String getAddress() {
        return editAddress.getText().toString();
    }

    @Override
    public void uploadSuccess() {
        startFragment(MasterWorkerFragment.newInstance());
    }

}
