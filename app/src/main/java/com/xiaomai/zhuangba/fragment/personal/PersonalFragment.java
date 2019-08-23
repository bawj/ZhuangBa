package com.xiaomai.zhuangba.fragment.personal;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.MessageEvent;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.login.ILoginRegisteredModule;
import com.xiaomai.zhuangba.data.module.login.LoginRegisteredModule;
import com.xiaomai.zhuangba.enums.EventBusEnum;
import com.xiaomai.zhuangba.fragment.login.BaseLoginRegisteredFragment;
import com.xiaomai.zhuangba.fragment.login.LoginFragment;
import com.xiaomai.zhuangba.fragment.personal.set.SetUpFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 *
 * 个人中心
 */
public class PersonalFragment extends BaseLoginRegisteredFragment {


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
        EventBus.getDefault().register(this);
    }

    @OnClick({R.id.relHistoricalOrder, R.id.relSetUp,R.id.relServiceAgreement, R.id.btnLogout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relHistoricalOrder:
                startFragment(HistoricalOrderFragment.newInstance());
                break;
            case R.id.relSetUp:
                startFragment(SetUpFragment.newInstance());
                break;
            case R.id.relServiceAgreement:
                //服务协议
                startServiceAgreement();
                break;
            case R.id.btnLogout:
                iModule.logout();
                break;
            default:
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeChatSuccess(MessageEvent messageEvent) {
        if (messageEvent.getErrCode() == EventBusEnum.CASH_SUCCESS.getCode()){
            initView();
        }
    }

    public void startServiceAgreement() {

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


    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
