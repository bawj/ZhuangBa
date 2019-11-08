package com.xiaomai.zhuangba.weight;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUILoadingView;

/**
 * @author Administrator
 * @date 2019/11/8 0008
 */
public class QDLoadingItemView extends FrameLayout {

    private QMUILoadingView mLoadingView;

    public QDLoadingItemView(@NonNull Context context) {
        this(context, null);
    }

    public QDLoadingItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mLoadingView = new QMUILoadingView(context,
                QMUIDisplayHelper.dp2px(context, 24), Color.LTGRAY);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        addView(mLoadingView, lp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(
                QMUIDisplayHelper.dp2px(getContext(), 48), MeasureSpec.EXACTLY));
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mLoadingView.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mLoadingView.stop();
    }
}
