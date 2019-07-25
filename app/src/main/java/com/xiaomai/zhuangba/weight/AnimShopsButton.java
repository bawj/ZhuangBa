package com.xiaomai.zhuangba.weight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.module.IOnAddDelListeners;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class AnimShopsButton extends RelativeLayout implements View.OnClickListener {

    /**
     * 当前数量
     */
    private int mCount = 0;
    /**
     * 点击回调
     */
    private IOnAddDelListeners mOnAddDelListener;

    /**
     * 动画的基准值 动画：减 0~1, 加 1~0 ,  普通状态下都显示时是0
     */
    protected ValueAnimator mAnimAdd, mAniDel;
    protected float mAnimFraction;
    protected static final int DEFAULT_DURATION = 350;
    protected int mPerAnimDuration = DEFAULT_DURATION;

    private ImageView ivViewAnimShopDel, ivViewAnimShopAdd;
    private TextView tvViewAnimShopNumber;

    public AnimShopsButton(Context context) {
        super(context);
    }

    public AnimShopsButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AnimShopsButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_anim_shop_button, this, true);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivViewAnimShopDel = findViewById(R.id.ivViewAnimShopDel);
        tvViewAnimShopNumber = findViewById(R.id.tvViewAnimShopNumber);
        ivViewAnimShopAdd = findViewById(R.id.ivViewAnimShopAdd);
        ivViewAnimShopDel.setOnClickListener(this);
        ivViewAnimShopAdd.setOnClickListener(this);
        //展开动画

        //动画 +
        mAnimAdd = ValueAnimator.ofFloat(1, 0);
        mAnimAdd.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                 mAnimFraction = (float) animation.getAnimatedValue();
            }
        });
        mAnimAdd.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                add();
            }
        });
        mAnimAdd.setDuration(mPerAnimDuration);

        //动画 -
        mAniDel = ValueAnimator.ofFloat(0, 1);
        mAniDel.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimFraction = (float) animation.getAnimatedValue();
            }
        });
        //1-0的动画
        mAniDel.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                del();
            }
        });
        mAniDel.setDuration(mPerAnimDuration);
    }

    private void del() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ivViewAnimShopAdd.setBackground(getResources().getDrawable(R.drawable.shop_circular_bg));
        }
        ivViewAnimShopDel.setVisibility(GONE);
        tvViewAnimShopNumber.setVisibility(GONE);
    }

    private void add() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ivViewAnimShopAdd.setBackground(getResources().getDrawable(R.drawable.shop_add_bg));
        }
        ivViewAnimShopDel.setVisibility(VISIBLE);
        tvViewAnimShopNumber.setVisibility(VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivViewAnimShopAdd:
                //add
                onAddClick();
                break;
            case R.id.ivViewAnimShopDel:
                //del
                onDelClick();
                break;
            default:
        }
    }

    protected void onAddClick() {
        mCount = mCount + 1;
        tvViewAnimShopNumber.setText(String.valueOf(mCount));
        onCountAddSuccess();
        if (null != mOnAddDelListener) {
            mOnAddDelListener.onAddSuccess(mCount);
        }
    }

    protected void onDelClick() {
        if (mCount > 0) {
            if (!flag && mCount == 1) {
                return;
            }
            mCount = mCount - 1;
            onCountDelSuccess();
            tvViewAnimShopNumber.setText(String.valueOf(mCount));
            if (null != mOnAddDelListener) {
                mOnAddDelListener.onDelSuccess(mCount);
            }
        }
    }

    /**
     * 设置加减监听器
     *
     * @param iOnAddDelListener 设置加减监听器
     */
    public void setOnAddDelListener(IOnAddDelListeners iOnAddDelListener) {
        mOnAddDelListener = iOnAddDelListener;
    }

    /**
     * 数量增加成功后，使用者回调以执行动画。
     */
    public void onCountAddSuccess() {
        if (mCount > 0 && tvViewAnimShopNumber.getVisibility() == GONE) {
            mAnimAdd.start();
        }
    }

    /**
     * 数量减去成功后，使用者回调以执行动画。
     */
    private void onCountDelSuccess() {
        if (mCount == 0) {
            mAniDel.start();
        }
    }

    public int getMCount() {
        return mCount;
    }

    public void setIsDefStatus() {
        this.mCount = 0;
        onCountDelSuccess();
    }

    public void setIsStatus(int count) {
        this.mCount = count;
        tvViewAnimShopNumber.setText(String.valueOf(mCount));
        onCountAddSuccess();
    }

    /**
     * 数量减到1 是否还可以 减
     */
    private boolean flag = true;

    public void setFlag(boolean falg) {
        this.flag = falg;
    }

}
