package com.xiaomai.zhuangba.fragment.test;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.http.ServiceUrl;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/6/29 0029
 */
public class YesBarFragment extends BaseFragment {

    @BindView(R.id.tvTest)
    TextView tvTest;

    public static YesBarFragment newInstance() {
        Bundle args = new Bundle();
        YesBarFragment fragment = new YesBarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        RxUtils.getObservable(ServiceUrl.getUserApi().login("13757188697", "a123456")
                .compose(this.<HttpResult<UserInfo>>bindToLifecycle()))
                .subscribe(new BaseHttpRxObserver<UserInfo>(getActivity()) {
                    @Override
                    protected void onSuccess(UserInfo response) {
                        tvTest.setText(response.toString());
                    }
                });
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_yes_bar;
    }

    @Override
    protected String getActivityTitle() {
        return "标题";
    }

    @Override
    public int getRightIcon() {
        return R.drawable.qmui_icon_checkbox_checked;
    }

    @Override
    public void rightIconClick(View view) {
        Log.e("右侧图标 点击事件");
    }

    @Override
    public String getRightTitle() {
        return "右侧标题";
    }

    @Override
    public void rightTitleClick(View v) {
        Log.e("右侧标题点击事件");
    }

}
