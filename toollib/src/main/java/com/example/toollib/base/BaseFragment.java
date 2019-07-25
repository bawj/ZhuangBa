package com.example.toollib.base;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.toollib.R;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.IBaseView;
import com.example.toollib.util.ToastUtil;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.util.QMUIViewHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.ObservableTransformer;

/**
 * @author Administrator
 * @date 2019/6/28 0028
 */
public abstract class BaseFragment<M extends IBaseModule> extends BaseRxFragment implements IBaseView {

    public M iModule;
    private Unbinder bind;
    private QMUITopBarLayout topBarBase;

    @Override
    protected View onCreateView() {
        View view;
        if (isCustomView()) {
            view = LayoutInflater.from(getActivity()).inflate(R.layout.tool_lib_fragment_base, null);
            FrameLayout layBase = view.findViewById(R.id.flayBase);
            View.inflate(getActivity(), getContentView(), layBase);
            topBarBase = view.findViewById(R.id.topBarBase);
        } else {
            view = LayoutInflater.from(getActivity()).inflate(getContentView(), null);
        }
        bind = ButterKnife.bind(this, view);
        iModule = initModule();
        if (iModule != null) {
            iModule.attachView(this, getActivity());
        }
        //返回箭头
        setBackArrow();
        //标题
        setTitle();
        //右侧标题
        setBackRightArrow();
        //右侧图标
        setRightIcon();
        initView();
        return view;
    }

    /**
     * fetch data by rule id
     *
     * @return Result<XxxxDO>
     */
    protected abstract M initModule();


    /**
     * init
     */
    public abstract void initView();

    /**
     * 主布局
     *
     * @return layoutView
     */
    public abstract @LayoutRes
    int getContentView();

    /**
     * 是否显示标题栏 默认显示
     */
    public boolean isCustomView() {
        return true;
    }

    /**
     * 是否全屏显示 没有标题栏
     *
     * @return boolean
     */
    @Override
    protected boolean translucentFull() {
        return false;
    }

    /**
     * 是否有返回箭头
     *
     * @return boolean
     */
    public boolean isBackArrow() {
        return true;
    }

    /**
     * 返回箭头
     */
    public void setBackArrow() {
        if (isBackArrow() && topBarBase != null) {
            int icon = getLeftIcon();
            topBarBase.addLeftImageButton(icon, R.id.qmui_topbar_item_left_back)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popBackStack();
                        }
                    });
        }
    }

    public int getLeftIcon() {
        return R.drawable.tool_lib_ic_left_back;
    }


    /**
     * 标题
     */
    private void setTitle() {
        if (isCustomView() && topBarBase != null) {
            topBarBase.setTitle(getActivityTitle())
                    .setTextColor(Color.BLACK);
        }
    }

    private void setRightIcon() {
        int icon = getRightIcon();
        if (topBarBase != null && icon != 0) {
            topBarBase.addRightImageButton(icon, R.id.topbar_right_about_button)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            rightIconClick(view);
                        }
                    });
        }
    }

    private void setBackRightArrow() {
        String rightTitle = getRightTitle();
        if (topBarBase != null && !TextUtils.isEmpty(rightTitle)) {
            topBarBase.addRightTextButton(rightTitle, QMUIViewHelper.generateViewId())
                    .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rightTitleClick(v);
                }
            });
        }
    }

    /**
     * bar 右侧图标
     *
     * @return drawable
     */
    public int getRightIcon() {
        return 0;
    }

    /**
     * bar 右侧图标点击事件
     *
     * @param view view
     */
    public void rightIconClick(View view) {

    }

    /**
     * bar 右侧标题
     *
     * @return String
     */
    public String getRightTitle() {
        return null;
    }

    /**
     * bar 右侧标题 点击事件
     *
     * @param v view
     */
    public void rightTitleClick(View v) {

    }

    /**
     * 标题
     *
     * @return string
     */
    protected abstract String getActivityTitle();

    public void statusBarBlack() {
        //设置状态栏字体图标 黑色
        QMUIStatusBarHelper.setStatusBarLightMode(getBaseFragmentActivity());
    }

    public void statusBarWhite() {
        //设置状态栏字体图标 白色
        QMUIStatusBarHelper.setStatusBarDarkMode(getBaseFragmentActivity());
    }

    /**
     * 退出界面之前把状态栏还原为白色字体与图标
     */
    public void onStatusBarDarkMode() {
        QMUIStatusBarHelper.setStatusBarDarkMode(getBaseFragmentActivity());
    }

    @Override
    public void showToast(String message) {
        ToastUtil.setGravity(Gravity.CENTER, 0, 0);
        ToastUtil.showShort(message);
    }

    @Override
    public ObservableTransformer bindLifecycle() {
        return bindToLifecycle();
    }

    @Override
    public void onDestroy() {
        if (bind != null) {
            bind.unbind();
        }
        super.onDestroy();
        topBarBase = null;
    }
}
