package com.xiaomai.zhuangba.fragment.personal.set;

import android.os.Bundle;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.xiaomai.zhuangba.http.ServiceUrl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 * <p>
 * 修改密码
 */
public class UpdatePassFragment extends BaseFragment {

    @BindView(R.id.tvUpdatePassPhone)
    TextView tvUpdatePassPhone;

    public static UpdatePassFragment newInstance() {
        Bundle args = new Bundle();
        UpdatePassFragment fragment = new UpdatePassFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        String phoneNumber = unique.getPhoneNumber();
        tvUpdatePassPhone.setText(phoneNumber);
    }

    @OnClick(R.id.btnUpdatePassCode)
    public void onViewClicked() {
        //获取验证码 下一步
        final String phone = tvUpdatePassPhone.getText().toString();
        RxUtils.getObservable(ServiceUrl.getUserApi().getAuthenticationCode(phone,
                StringTypeExplain.REGISTERED_FORGET_PASSWORD.getCode())
                .compose(this.<HttpResult<Object>>bindLifecycle()))
                .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                    @Override
                    protected void onSuccess(Object response) {
                        startFragment(UpdatePassTwoFragment.newInstance(phone,
                                response.toString()));
                    }
                });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_update_pass;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.reset_password);
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }
}
