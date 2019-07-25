package com.xiaomai.zhuangba.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/5/8 0008
 */
public class PasswordEditText extends AppCompatEditText implements TextWatcher {

    /**
     * 右侧Drawable引入
     */
    private Drawable mDrawableRight;
    private int openPasswdRes, closePasswdRes;
    /**
     * 判断当前密码打开状态，默认关闭状态
     */
    private boolean isOpen = false;

    /**
     * 构造方法 1->2->3
     */
    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText);
        openPasswdRes = typedArray.getResourceId(R.styleable.PasswordEditText_openPasswdRes, R.drawable.icon_open_eye);
        closePasswdRes = typedArray.getResourceId(R.styleable.PasswordEditText_closePasswdRes, R.drawable.icon_close_eye);
        typedArray.recycle();
        init();
        setClearIconVisible(false);
        addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
        if (isFocused()) {
            setClearIconVisible(text.length() > 0);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 初始化方法
     */
    private void init() {
        // 最开始设置不可见
        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        // Drawable顺序左上右下，0123
        // 获取DrawRight内容
        mDrawableRight = getCompoundDrawables()[2];
        if (mDrawableRight == null) {
            // 未设置默认DrawableRight
            setmDrawableRight(isOpen);
        }
    }

    /**
     * 初始化DrawableRight
     */
    public void setmDrawableRight(boolean isOpen) {
        int drawableId;
        // 通过状态设置DrawableRight的样式
        if (isOpen) {
            drawableId = openPasswdRes;
        } else {
            drawableId = closePasswdRes;
        }
        // 初始化DrawableRight
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mDrawableRight = getResources().getDrawable(drawableId, null);
        } else {
            mDrawableRight = getResources().getDrawable(drawableId);
        }

        setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[0], getCompoundDrawables()[1], mDrawableRight, getCompoundDrawables()[3]);
    }

    /**
     * 判断Drawable是否被点击，如果被点击执行点击事件 触摸事件
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                // 获取控件的DrawableRight
                Drawable drawable = getCompoundDrawables()[2];
                // 当按下的位置 在EditText的宽度 - 图标到控件右边的间距 - 图标的宽度 与 EditText的宽度 - 图标到控件右边的间距之间，算点击了图标，竖直方向不用考虑
                // getTotalPaddingRight获取右侧图标以及右侧Padding和
                // getPaddingRight获取右侧Padding值
                boolean isTouchRight = event.getX() > (getWidth() - getTotalPaddingRight()) && (event.getX() < ((getWidth() - getPaddingRight())));
                if (drawable != null && isTouchRight) {
                    // 记录状态
                    isOpen = !isOpen;
                    // 刷新DrawableRight
                    setmDrawableRight(isOpen);
                    // 执行点击事件-隐藏或显示
                    if (isOpen) {
                        setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    } else {
                        setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    }
                    // 设置光标位置
                    setSelection(TextUtils.isEmpty(getText()) ? 0 : getText().length());
                }
                break;
            default:
        }
        return super.onTouchEvent(event);
    }



    private void setClearIconVisible(final boolean visible) {
        mDrawableRight.setVisible(visible, false);
        final Drawable[] compoundDrawables = getCompoundDrawables();
        setCompoundDrawables(
                compoundDrawables[0],
                compoundDrawables[1],
                visible ? mDrawableRight : null,
                compoundDrawables[3]);
    }
}
