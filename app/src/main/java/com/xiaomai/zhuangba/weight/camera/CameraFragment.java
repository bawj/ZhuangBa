package com.xiaomai.zhuangba.weight.camera;

import android.os.Bundle;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;

/**
 * @author Administrator
 * @date 2019/10/12 0012
 */
public class CameraFragment extends BaseFragment{

    public static CameraFragment newInstance() {
        Bundle args = new Bundle();
        CameraFragment fragment = new CameraFragment();
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
        return 0;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }
    
    
}
