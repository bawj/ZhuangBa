package com.xiaomai.zhuangba.fragment.personal.about;

import android.os.Bundle;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.util.Utils;
import com.xiaomai.zhuangba.R;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/7/19 0019
 *
 * 关于
 */
public class AboutFragment extends BaseFragment {

    @BindView(R.id.tvAboutEdition)
    TextView tvAboutEdition;

    public static AboutFragment newInstance() {
        Bundle args = new Bundle();
        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        tvAboutEdition.setText(Utils.getAppVersionName(getActivity()));
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_about;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.about);
    }
}
