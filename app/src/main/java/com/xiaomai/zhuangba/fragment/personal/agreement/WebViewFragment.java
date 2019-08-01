package com.xiaomai.zhuangba.fragment.personal.agreement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.just.agentweb.AbsAgentWebSettings;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 */
public class WebViewFragment extends BaseFragment {

    private static final String URL = "url";
    private static final String TITLE = "title";

    public static WebViewFragment newInstance(String url, String title) {
        Bundle args = new Bundle();
        args.putString(URL, url);
        args.putString(TITLE, title);
        WebViewFragment fragment = new WebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AgentWeb.with(this)
                //传入AgentWeb的父控件。
                .setAgentWebParent((ViewGroup) view.findViewById(R.id.layBaseFragment), -1,
                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                //设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
                .useDefaultIndicator(getResources().getColor(R.color.colorAccent), 3)
                //设置 IAgentWebSettings。
                .setAgentWebWebSettings(getSettings())
                //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .interceptUnkownUrl() //拦截找不到相关页面的Url AgentWeb 3.0.0 加入。
                .createAgentWeb()//创建AgentWeb。
                .ready()//设置 WebSettings。
                //WebView载入该url地址的页面并显示。
                .go(getUrl());
    }

    @Override
    public void initView() {

    }


    public IAgentWebSettings getSettings() {
        return new AbsAgentWebSettings() {
            private AgentWeb mAgentWeb;
            @Override
            protected void bindAgentWebSupport(AgentWeb agentWeb) {
                this.mAgentWeb = agentWeb;
            }
        };
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_web_view;
    }

    private String getUrl() {
        if (getArguments() != null) {
            return getArguments().getString(URL);
        }
        return "";
    }

    @Override
    protected String getActivityTitle() {
        if (getArguments() != null) {
            return getArguments().getString(TITLE);
        }
        return "";
    }
}
