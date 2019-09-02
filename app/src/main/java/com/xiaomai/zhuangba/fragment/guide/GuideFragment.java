package com.xiaomai.zhuangba.fragment.guide;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.login.LoginFragment;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/7/4 0004
 *
 * 广告页
 */
public class GuideFragment extends BaseFragment {

    @BindView(R.id.ivGuide)
    ImageView ivGuide;
    @BindView(R.id.btnGuideGo)
    QMUIRoundButton btnGuideGo;

    public static final String IMG = "img";

    public static GuideFragment newInstance(int img) {
        Bundle args = new Bundle();
        args.putInt(IMG, img);
        GuideFragment fragment = new GuideFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected IBaseModule initModule() {
        return null;
    }
    @Override
    public void initView() {
        ivGuide.setImageResource(getBackImg());
        if (getBackImg() == R.drawable.bg_guide_four) {
            btnGuideGo.setVisibility(View.VISIBLE);
            btnGuideGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startFragment(LoginFragment.newInstance());
                }
            });
        }
    }

    private int getBackImg() {
        if (getArguments() != null) {
            return getArguments().getInt(IMG);
        }
        return 0;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_guide;
    }

    @Override
    public boolean isCustomView() {
        return false;
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
