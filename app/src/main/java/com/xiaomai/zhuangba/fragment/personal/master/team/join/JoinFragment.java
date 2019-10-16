package com.xiaomai.zhuangba.fragment.personal.master.team.join;

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
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/10/15 0015
 */
public class JoinFragment extends BaseFragment {

    @BindView(R.id.editTeamPhone)
    EditText editTeamPhone;

    public static JoinFragment newInstance() {
        Bundle args = new Bundle();
        JoinFragment fragment = new JoinFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.btnCreateTeam)
    public void onViewClicked() {
        String phone = editTeamPhone.getText().toString();
        if (TextUtils.isEmpty(phone)){
            ToastUtil.showShort(getString(R.string.please_input_team_phone));
        }else {
            RxUtils.getObservable(ServiceUrl.getUserApi().insertTeamMember(phone))
                    .compose(this.<HttpResult<Object>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>() {
                        @Override
                        protected void onSuccess(Object response) {
                            startFragment(MasterWorkerFragment.newInstance());
                        }
                    });
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_join;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.join_the_team);
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

}
