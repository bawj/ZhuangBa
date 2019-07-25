package com.xiaomai.zhuangba.fragment.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.util.Util;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/7/10 0010
 */
public class ServiceDetailFragment extends BaseFragment {

    @BindView(R.id.tvServiceDetailTitle)
    TextView tvServiceDetailTitle;
    @BindView(R.id.tvServiceDetailContent)
    TextView tvServiceDetailContent;

    public static final String TITLE = "title";
    public static final String CONTENT = "content";

    public static ServiceDetailFragment newInstance(String title, String content) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(CONTENT, content);
        ServiceDetailFragment fragment = new ServiceDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void initView() {
        tvServiceDetailTitle.setText(getContentTitle());
        String content = getContent();
        if (getActivity() != null && !TextUtils.isEmpty(content)){
            Util.setParagraphSpacing(getActivity() , tvServiceDetailContent , content,24 , 3);
        }
    }

    private String getContentTitle() {
        if (getArguments() != null) {
            return getArguments().getString(TITLE);
        }
        return "";
    }

    private String getContent() {
        if (getArguments() != null) {
            return getArguments().getString(CONTENT);
        }
        return "";
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_service_detail;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.service_acceptance_criteria);
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }
}
