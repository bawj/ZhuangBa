package com.xiaomai.zhuangba.fragment.test;

import android.os.Bundle;
import android.view.View;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.xiaomai.zhuangba.R;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/6/28 0028
 */
public class NotBarFragment extends BaseFragment {


    @BindView(R.id.topBarBase)
    QMUITopBarLayout topBarBase;

    public static NotBarFragment newInstance() {
        Bundle args = new Bundle();
        NotBarFragment fragment = new NotBarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        topBarBase.setTitle("全屏");
        topBarBase.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_not_bar;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

}
