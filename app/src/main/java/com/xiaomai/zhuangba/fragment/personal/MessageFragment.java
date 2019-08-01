package com.xiaomai.zhuangba.fragment.personal;

import android.os.Bundle;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 */
public class MessageFragment extends BaseFragment {

    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
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
        return R.layout.fragment_message;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }
}
