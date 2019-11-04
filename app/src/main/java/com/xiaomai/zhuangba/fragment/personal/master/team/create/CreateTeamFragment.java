package com.xiaomai.zhuangba.fragment.personal.master.team.create;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

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
 * @date 2019/10/14 0014
 * <p>
 * 创建团队
 */
public class CreateTeamFragment extends BaseFragment {

    @BindView(R.id.tvTeamName)
    TextView tvTeamName;
    @BindView(R.id.editTeamName)
    EditText editTeamName;

    public static CreateTeamFragment newInstance() {
        Bundle args = new Bundle();
        CreateTeamFragment fragment = new CreateTeamFragment();
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

    @Override
    public int getContentView() {
        return R.layout.fragment_create_team;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.create_team);
    }


    @OnClick(R.id.btnCreateTeam)
    public void onViewClicked() {
        String nameTeam = editTeamName.getText().toString();
        if (TextUtils.isEmpty(nameTeam)) {
            ToastUtil.showShort(getString(R.string.please_input_team_name));
        } else {
            //创建团队
            RxUtils.getObservable(ServiceUrl.getUserApi().saveTeam(nameTeam))
                    .compose(this.<HttpResult<Object>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                        @Override
                        public void onNext(HttpResult<Object> httpResult) {
                            super.onNext(httpResult);
                            ToastUtil.showShort(httpResult.getMsg());
                            startFragment(MasterWorkerFragment.newInstance());
                        }
                        @Override
                        protected void onSuccess(Object response) {
                        }
                    });
        }
    }
}
