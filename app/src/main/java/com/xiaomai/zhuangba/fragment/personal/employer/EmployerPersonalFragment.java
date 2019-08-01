package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.personal.PersonalFragment;
import com.xiaomai.zhuangba.fragment.personal.agreement.WebViewFragment;
import com.xiaomai.zhuangba.util.ConstantUtil;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 *
 * 雇主端个人中心
 */
public class EmployerPersonalFragment extends PersonalFragment {

    public static EmployerPersonalFragment newInstance() {
        Bundle args = new Bundle();
        EmployerPersonalFragment fragment = new EmployerPersonalFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void startServiceAgreement() {
        startFragment(WebViewFragment.newInstance(ConstantUtil.EMPLOYER_FABRICATING_USER_SERVICE_PROTOCOL,
                getString(R.string.fabricating_user_service_protocol)));
    }

}
