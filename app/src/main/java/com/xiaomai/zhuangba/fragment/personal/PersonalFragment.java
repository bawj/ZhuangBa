package com.xiaomai.zhuangba.fragment.personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.login.ILoginRegisteredModule;
import com.xiaomai.zhuangba.data.module.login.LoginRegisteredModule;
import com.xiaomai.zhuangba.fragment.login.BaseLoginRegisteredFragment;
import com.xiaomai.zhuangba.fragment.login.LoginFragment;
import com.xiaomai.zhuangba.fragment.personal.set.SetUpFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 *
 * 个人中心
 */
public class PersonalFragment extends BaseLoginRegisteredFragment {

    @BindView(R.id.ivUserHead)
    ImageView ivUserHead;
    @BindView(R.id.tvPersonalName)
    TextView tvPersonalName;

    public static PersonalFragment newInstance() {
        Bundle args = new Bundle();
        PersonalFragment fragment = new PersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected ILoginRegisteredModule initModule() {
        return new LoginRegisteredModule();
    }

    @Override
    public void initView() {
        UserInfo userInfo = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (userInfo != null){
            tvPersonalName.setText(TextUtils.isEmpty(userInfo.getUserText()) ? getString(R.string.no_certification) : userInfo.getUserText());
            GlideManager.loadCircleImage(getActivity(),userInfo.getBareHeadedPhotoUrl(), ivUserHead);
        }
    }

    @OnClick({R.id.relHistoricalOrder, R.id.relSetUp, R.id.btnLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relHistoricalOrder:
                startFragment(HistoricalOrderFragment.newInstance());
                break;
            case R.id.relSetUp:
                startFragment(SetUpFragment.newInstance());
                break;
            case R.id.btnLogout:
                iModule.logout();
                break;
            default:
        }
    }

    @Override
    public void logoutSuccess() {
        startFragmentAndDestroyCurrent(LoginFragment.newInstance());
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_personal;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.my);
    }

}
