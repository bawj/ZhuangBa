package com.xiaomai.zhuangba.fragment.authentication.master;

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
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.spf.SPUtils;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ScopeOfServiceAdapter;
import com.xiaomai.zhuangba.data.bean.MasterAuthenticationInfo;
import com.xiaomai.zhuangba.data.bean.SkillList;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.example.toollib.util.spf.SpfConst;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.xiaomai.zhuangba.fragment.authentication.master.BareheadedFragment.BAREHEADED;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class ScopeOfServiceFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.recyclerScopeServiceSkills)
    RecyclerView recyclerScopeServiceSkills;
    @BindView(R.id.tvScopeServiceSkillsClickServiceAddress)
    TextView tvScopeServiceSkillsClickServiceAddress;
    @BindView(R.id.editAddressDetail)
    EditText editAddressDetail;

    private UserInfo userInfo;
    private List<SkillList> skillLists = new ArrayList<>();
    private ScopeOfServiceAdapter scopeOfServiceAdapter;

    public static ScopeOfServiceFragment newInstance(String masterAuthenticationInfo) {
        Bundle args = new Bundle();
        args.putString(BAREHEADED, masterAuthenticationInfo);
        ScopeOfServiceFragment fragment = new ScopeOfServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        recyclerScopeServiceSkills.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        scopeOfServiceAdapter = new ScopeOfServiceAdapter();
        recyclerScopeServiceSkills.setAdapter(scopeOfServiceAdapter);
        scopeOfServiceAdapter.setOnItemClickListener(this);

        requestSkillList();
    }

    @OnClick({R.id.relScopeServiceSkillsAddress, R.id.btnScopeServiceUpload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relScopeServiceSkillsAddress:
                //查询接口
                startFragmentForResult(ProvincialFragment.newInstance(), ForResultCode.START_FOR_RESULT_CODE.getCode());
                break;
            case R.id.btnScopeServiceUpload:
                //提交
                submission();
                break;
            default:
        }
    }

    public void submission() {
        String address = getAddress();
        if (TextUtils.isEmpty(address)) {
            showToast(getString(R.string.please_check_service_address));
        } else {
            address = address + editAddressDetail.getText().toString();
            MasterAuthenticationInfo masterAuthenticationInfo = getMasterAuthenticationInfo();
            if (masterAuthenticationInfo != null) {
                masterAuthenticationInfo.setAddress(address);
            }
            List<SkillList> skillLists = getSkillList();
            if (skillLists.isEmpty()) {
                showToast(getString(R.string.please_check_skill_list));
            }else if (masterAuthenticationInfo != null){
                UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
                userInfo.setUserText(masterAuthenticationInfo.getUserText());
                userInfo.setIdentityCard(masterAuthenticationInfo.getIdentityCard());
                userInfo.setValidityData(masterAuthenticationInfo.getValidityData());
                userInfo.setIdCardFrontPhoto(masterAuthenticationInfo.getIdCardFrontPhoto());
                userInfo.setIdCardBackPhoto(masterAuthenticationInfo.getIdCardBackPhoto());
                userInfo.setBareHeadedPhotoUrl(masterAuthenticationInfo.getPhotoPath());
                userInfo.setLongitude(masterAuthenticationInfo.getLongitude());
                userInfo.setLatitude(masterAuthenticationInfo.getLatitude());
                userInfo.setEmergencyContact(masterAuthenticationInfo.getEmergencyContact());
                userInfo.setContactAddress(masterAuthenticationInfo.getContactAddress());
                userInfo.setAddress(masterAuthenticationInfo.getAddress());
                userInfo.setRole(String.valueOf(StaticExplain.FU_FU_SHI.getCode()));
                userInfo.setSkillLists(skillLists);
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(userInfo));
                Observable<HttpResult<UserInfo>> observable = ServiceUrl.getUserApi().updateRegistrationInformation(requestBody);
                RxUtils.getObservable(observable)
                        .compose(this.<HttpResult<UserInfo>>bindToLifecycle())
                        .subscribe(new BaseHttpRxObserver<UserInfo>(getActivity()) {
                            @Override
                            protected void onSuccess(UserInfo response) {
                                String token = response.getToken();
                                if (!TextUtils.isEmpty(token)) {
                                    SPUtils.getInstance().put(SpfConst.TOKEN, token);
                                }
                                response.setAuthenticationStatue(StaticExplain.IN_AUDIT.getCode());
                                DBHelper.getInstance().getUserInfoDao().deleteAll();
                                DBHelper.getInstance().getUserInfoDao().insert(response);
                                startFragment(CertificationSuccessfulFragment.newInstance());
                            }
                        });
            }
        }
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()) {
                //地址选择成功返回
                String name = data.getStringExtra(ForResultCode.RESULT_KEY.getExplain());
                tvScopeServiceSkillsClickServiceAddress.setText(name);
            }
        }
    }

    /**
     * 获取技能列表
     */
    private void requestSkillList() {
        Observable<HttpResult<List<SkillList>>> skillList = ServiceUrl.getUserApi().getSkillList();
        RxUtils.getObservable(skillList)
                .compose(this.<HttpResult<List<SkillList>>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<SkillList>>(getActivity()) {
                    @Override
                    protected void onSuccess(List<SkillList> skillListList) {
                        skillLists = skillListList;
                        scopeOfServiceAdapter.setNewData(skillListList);
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        SkillList skillList1 = (SkillList) view.findViewById(R.id.tvItemScopeOfService).getTag();
        String phoneNumber = userInfo.getPhoneNumber();
        String phoneNumber1 = skillList1.getPhoneNumber();
        if (phoneNumber1 == null) {
            skillList1.setPhoneNumber(phoneNumber);
        } else {
            skillList1.setPhoneNumber(null);
        }
        scopeOfServiceAdapter.notifyDataSetChanged();
    }

    public List<SkillList> getSkillList() {
        List<SkillList> skillList = new ArrayList<>();
        if (skillLists != null && !skillLists.isEmpty()) {
            for (SkillList list : skillLists) {
                if (!TextUtils.isEmpty(list.getPhoneNumber())) {
                    skillList.add(list);
                }
            }
        }
        return skillList;
    }

    public String getAddress() {
        return tvScopeServiceSkillsClickServiceAddress.getText().toString();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_scope_of_service;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.scope_of_service_set);
    }

    private MasterAuthenticationInfo getMasterAuthenticationInfo() {
        if (getArguments() != null) {
            String masterAuthenticationInfo = getArguments().getString(BAREHEADED);
            return new Gson().fromJson(masterAuthenticationInfo, MasterAuthenticationInfo.class);
        }
        return null;
    }
}
