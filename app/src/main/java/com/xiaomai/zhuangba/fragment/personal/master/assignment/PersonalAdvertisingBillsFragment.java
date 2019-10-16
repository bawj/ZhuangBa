package com.xiaomai.zhuangba.fragment.personal.master.assignment;

import android.os.Bundle;

import com.xiaomai.zhuangba.fragment.base.BaseListFragment;

/**
 * @author Administrator
 * @date 2019/10/16 0016
 *
 * 分配任务 广告单
 */
public class PersonalAdvertisingBillsFragment extends BaseListFragment {

    public static PersonalAdvertisingBillsFragment newInstance() {
        Bundle args = new Bundle();
        PersonalAdvertisingBillsFragment fragment = new PersonalAdvertisingBillsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }
}
