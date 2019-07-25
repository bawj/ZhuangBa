package com.xiaomai.zhuangba.weight;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * @author Administrator
 * @date 2019/7/6 0006
 */
public class MyToggleButton extends ToggleButton {

    public MyToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyToggleButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyToggleButton(Context context) {
        super(context);
        init();
    }

    private int start = 0;
    private int end = 1;
    public void init() {
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    end = 0;
                    start = 1;
                }
                ValueAnimator animator = ValueAnimator.ofFloat(start, end);
                animator.setDuration(200);
                animator.start();
            }
        });
    }

}
