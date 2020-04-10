package com.xiaomai.zhuangba.fragment.authentication.employer;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
import com.xiaomai.zhuangba.adapter.EmployerRealNameAuthenticationAdapter;
import com.xiaomai.zhuangba.data.bean.BusinessNeeds;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.fragment.authentication.master.CertificationSuccessfulFragment;
import com.xiaomai.zhuangba.fragment.service.LocationFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class EmployerInformationFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

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

    @BindView(R.id.tvSecurity)
    TextView tvSecurity;
    @BindView(R.id.tvMedia)
    TextView tvMedia;
    @BindView(R.id.recyclerMediaSecurity)
    RecyclerView recyclerMediaSecurity;

    private List<BusinessNeeds> securityList = new ArrayList<>();
    private List<BusinessNeeds> mediaList = new ArrayList<>();

    private int itemPosition;
    private EmployerRealNameAuthenticationAdapter employerRealNameAuthenticationAdapter;
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
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        editEmployerName.setText(unique.getUserText());
        editPhone.setText(unique.getEmergencyContact());
        editAddress.setText(unique.getAddress());
        editEnterpriseName.setText(unique.getCompanyName());
        editAddressDetail.setText(unique.getContactAddress());

        recyclerMediaSecurity.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerMediaSecurity.addItemDecoration(new GridSpacingItemDecoration(2, 16, false));
        employerRealNameAuthenticationAdapter = new EmployerRealNameAuthenticationAdapter();
        employerRealNameAuthenticationAdapter.setOnItemClickListener(this);

        RxUtils.getObservable(ServiceUrl.getUserApi().getBusinessNeeds())
                .compose(this.<HttpResult<List<BusinessNeeds>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<BusinessNeeds>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<BusinessNeeds> response) {
                        if (response != null && response.size() > 0) {
                            for (int i = 0; i < response.size(); i++) {
                                if (i == 0) {
                                    BusinessNeeds businessNeeds = response.get(i);
                                    String name = businessNeeds.getName();
                                    securityList = businessNeeds.getChildDemand();
                                    tvSecurity.setText(name);
                                    recyclerMediaSecurity.setAdapter(employerRealNameAuthenticationAdapter);
                                    employerRealNameAuthenticationAdapter.setNewData(securityList);
                                    isCheck(businessNeeds.getFlag(), tvSecurity);
                                }
                                if (i == 1) {
                                    BusinessNeeds businessNeeds = response.get(i);
                                    String name = businessNeeds.getName();
                                    mediaList = businessNeeds.getChildDemand();
                                    tvMedia.setText(name);
                                    isCheck(businessNeeds.getFlag(), tvMedia);
                                    if (businessNeeds.getFlag() == StaticExplain.EMPLOYER_IS_CHECK.getCode()){
                                        employerRealNameAuthenticationAdapter.setNewData(mediaList);
                                    }
                                }
                            }
                        }
                    }
                });
    }

    @OnClick({R.id.btnUpload, R.id.relAddress, R.id.tvSecurity, R.id.tvMedia})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnUpload:
                upload();
                break;
            case R.id.relAddress:
                applyPermission();
                break;
            case R.id.tvSecurity:
                itemPosition = 0;
                isCheck(StaticExplain.EMPLOYER_IS_CHECK.getCode(), tvSecurity);
                employerRealNameAuthenticationAdapter.setNewData(securityList);
                tvMedia.setBackground(getResources().getDrawable(R.drawable.bg_gray));
                tvMedia.setTextColor(getResources().getColor(R.color.tool_lib_gray_777777));
                break;
            case R.id.tvMedia:
                itemPosition = 1;
                //媒体企业 默认选中
                defaultSelection();
                isCheck(StaticExplain.EMPLOYER_IS_CHECK.getCode(), tvMedia);
                employerRealNameAuthenticationAdapter.setNewData(mediaList);
                tvSecurity.setBackground(getResources().getDrawable(R.drawable.bg_gray));
                tvSecurity.setTextColor(getResources().getColor(R.color.tool_lib_gray_777777));
                break;
            default:
        }
    }

    private void defaultSelection() {
        if (mediaList != null && mediaList.size() > 0) {
            boolean flag = false;
            for (BusinessNeeds needs : mediaList) {
                if (needs.getFlag() == StaticExplain.EMPLOYER_IS_CHECK.getCode()) {
                    flag = true;
                    break;
                } else {
                    flag = false;
                }
            }
            int flagOne = mediaList.get(0).getFlag();
            if (flag && flagOne != StaticExplain.EMPLOYER_IS_CHECK.getCode()) {
                mediaList.get(0).setFlag(StaticExplain.EMPLOYER_NOT_CHECK.getCode());
            } else {
                mediaList.get(0).setFlag(StaticExplain.EMPLOYER_IS_CHECK.getCode());
            }
        }
    }

    private void isCheck(int flag, TextView tv) {
        if (flag == StaticExplain.EMPLOYER_NOT_CHECK.getCode()) {
            tv.setBackground(getResources().getDrawable(R.drawable.bg_gray));
            tv.setTextColor(getResources().getColor(R.color.tool_lib_gray_777777));
        } else if (flag == StaticExplain.EMPLOYER_IS_CHECK.getCode()) {
            tv.setBackground(getResources().getDrawable(R.drawable.bg_red));
            tv.setTextColor(getResources().getColor(R.color.tool_lib_color_E74C3C));
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (itemPosition == 0) {
            BusinessNeeds businessNeeds = securityList.get(position);
            businessNeeds.setFlag(businessNeeds.getFlag() == StaticExplain.EMPLOYER_NOT_CHECK.getCode()
                    ? StaticExplain.EMPLOYER_IS_CHECK.getCode() : StaticExplain.EMPLOYER_NOT_CHECK.getCode());
            employerRealNameAuthenticationAdapter.setNewData(securityList);
        } else if (itemPosition == 1) {
            BusinessNeeds businessNeeds = mediaList.get(position);
            businessNeeds.setFlag(businessNeeds.getFlag() == StaticExplain.EMPLOYER_NOT_CHECK.getCode()
                    ? StaticExplain.EMPLOYER_IS_CHECK.getCode() : StaticExplain.EMPLOYER_NOT_CHECK.getCode());
            employerRealNameAuthenticationAdapter.setNewData(mediaList);
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
            userInfo.setIdStr(getIdStr());
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

    public String getIdStr() {
        StringBuilder idStr = new StringBuilder();
        if (itemPosition == 0) {
            for (BusinessNeeds businessNeeds : securityList) {
                int flag = businessNeeds.getFlag();
                if (flag == StaticExplain.EMPLOYER_IS_CHECK.getCode()) {
                    idStr.append(businessNeeds.getId());
                    idStr.append(",");
                }
            }
            idStr.append("-2");
        } else if (itemPosition == 1) {
            for (BusinessNeeds businessNeeds : mediaList) {
                int flag = businessNeeds.getFlag();
                if (flag == StaticExplain.EMPLOYER_IS_CHECK.getCode()) {
                    idStr.append(businessNeeds.getId());
                    idStr.append(",");
                }
            }
            idStr.append("-1");
        }
        return idStr.toString();
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
