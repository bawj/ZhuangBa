package com.xiaomai.zhuangba.fragment.personal.employer;

import android.os.Bundle;

import com.xiaomai.zhuangba.fragment.personal.PersonalFragment;

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

}
