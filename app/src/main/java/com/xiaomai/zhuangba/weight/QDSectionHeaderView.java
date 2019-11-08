package com.xiaomai.zhuangba.weight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIResHelper;
import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.SectionHeader;

/**
 * @author Administrator
 * @date 2019/11/8 0008
 */
public class QDSectionHeaderView extends LinearLayout {

    private TextView mTitleTv;
    private ImageView mArrowView;

    private int headerHeight = QMUIDisplayHelper.dp2px(getContext(), 56);

    public QDSectionHeaderView(Context context) {
        this(context, null);
    }

    public QDSectionHeaderView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        setBackgroundColor(Color.WHITE);
        int paddingHor = QMUIDisplayHelper.dp2px(context, 24);
        mTitleTv = new TextView(getContext());
        mTitleTv.setTextSize(QMUIDisplayHelper.dp2px(context,10));
        mTitleTv.setTextColor(Color.BLACK);
        mTitleTv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        mTitleTv.setPadding(paddingHor, 0, paddingHor, 0);
        addView(mTitleTv, new LinearLayout.LayoutParams(
                0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));

        mArrowView = new AppCompatImageView(context);
        mArrowView.setMinimumHeight(QMUIDisplayHelper.dp2px(context, 7));
        mArrowView.setMinimumWidth(QMUIDisplayHelper.dp2px(context, 12));

        mArrowView.setImageDrawable(QMUIResHelper.getAttrDrawable(getContext(),
                R.attr.qmui_common_list_item_chevron));
        mArrowView.setScaleType(ImageView.ScaleType.CENTER);

        addView(mArrowView, new LinearLayout.LayoutParams(headerHeight, headerHeight));
    }

    public ImageView getArrowView() {
        return mArrowView;
    }

    public void render(SectionHeader header, boolean isFold) {
        mTitleTv.setText(header.getText());
        mArrowView.setRotation(isFold ? 0f : 90f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(headerHeight, MeasureSpec.EXACTLY));
    }
}
