package com.xiaomai.zhuangba.fragment.personal.master;

import android.os.Bundle;
import android.widget.EditText;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.SkillAddressCondition;
import com.xiaomai.zhuangba.data.bean.SkillList;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.fragment.authentication.master.ScopeOfServiceFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class UpdateScopeOfServiceFragment extends ScopeOfServiceFragment {

    @BindView(R.id.editAddressDetail)
    EditText editAddressDetail;

    public static UpdateScopeOfServiceFragment newInstance() {
        Bundle args = new Bundle();
        UpdateScopeOfServiceFragment fragment = new UpdateScopeOfServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void submission() {
        final String address = getAddress() + editAddressDetail.getText().toString();
        List<SkillList> skillLists = getSkillList();
        SkillAddressCondition skillAddressCondition = new SkillAddressCondition();
        skillAddressCondition.setAddress(address);
        skillAddressCondition.setLatitude(0f);
        skillAddressCondition.setLongitude(0f);
        skillAddressCondition.setSkills(skillLists);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(skillAddressCondition));
        Observable<HttpResult<Object>> httpResultObservable = ServiceUrl.getUserApi().userAuthenticationAddress(requestBody);
        RxUtils.getObservable(httpResultObservable)
                .compose(this.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
                        unique.setAddress(address);
                        DBHelper.getInstance().getUserInfoDao().update(unique);
                        ToastUtil.showShort(getString(R.string.update_success));
                        getBaseFragmentActivity().popBackStack();
                    }
                });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_update_scope_of_service;
    }

}
