package com.example.toollib.weight.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.toollib.R;
import com.example.toollib.util.DensityUtil;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class CommonlyDialog {

    private AlertDialog alertDialog;
    private BaseCallback baseCallback;
    private TextView tvDialogBondTips, tvDialogCommonlyContent;
    private TextView tvDialogCommonlyOk, tvDialogCommonlyClose;

    public static CommonlyDialog getInstance() {
        return new CommonlyDialog();
    }

    public CommonlyDialog initView(Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tool_lib_dialog_commonly, null);
        alertDialog = new AlertDialog.Builder(mContext).create();
        tvDialogBondTips = view.findViewById(R.id.tvDialogBondTips);
        tvDialogCommonlyContent = view.findViewById(R.id.tvDialogCommonlyContent);
        tvDialogCommonlyOk = view.findViewById(R.id.tvDialogCommonlyOk);
        tvDialogCommonlyClose = view.findViewById(R.id.tvDialogCommonlyClose);

        tvDialogCommonlyOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (baseCallback != null) {
                    baseCallback.sure();
                }
            }
        });

        tvDialogCommonlyClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        return this;
    }

    public CommonlyDialog setTvDialogCommonlyContent(String content) {
        tvDialogCommonlyContent.setText(content);
        return this;
    }

    public CommonlyDialog setTvDialogBondTips(String bondTips) {
        tvDialogBondTips.setText(bondTips);
        return this;
    }

    public CommonlyDialog isTvDialogBondTipsVisibility(int visibility){
        tvDialogBondTips.setVisibility(visibility);
        return this;
    }

    public CommonlyDialog setTvDialogCommonlyOk(String commonlyOk) {
        tvDialogCommonlyOk.setText(commonlyOk);
        return this;
    }

    public CommonlyDialog setTvDialogCommonlyOkColor(int color) {
        tvDialogCommonlyOk.setTextColor(color);
        return this;
    }

    public CommonlyDialog setTvDialogCommonlyCloseTextColor(int color) {
        tvDialogCommonlyClose.setTextColor(color);
        return this;
    }

    public CommonlyDialog setTvDialogCommonlyClose(String close) {
        tvDialogCommonlyClose.setText(close);
        return this;
    }

    public CommonlyDialog isVisibleClose(boolean flag) {
        tvDialogCommonlyClose.setVisibility(flag ? View.VISIBLE : View.GONE);
        return this;
    }

    public CommonlyDialog setTvDialogCommonlyContentTextGravity(int gravity){
        tvDialogCommonlyContent.setGravity(gravity);
        return this;
    }

    /**
     * 点击外部 不消失
     */
    public CommonlyDialog isCancelable() {
        alertDialog.setCancelable(false);
        return this;
    }

    /**
     * dialog  是否显示
     *
     * @return boolean
     */
    public boolean isShow() {
        if (alertDialog != null) {
            return alertDialog.isShowing();
        }
        return false;
    }

    public void showDialog() {
        if (alertDialog != null) {
            alertDialog.show();
            Window window = alertDialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.getDecorView().setPadding(DensityUtil.dp2px(32), 0, DensityUtil.dp2px(32), 0);
            }
        }
    }

    public interface BaseCallback {
        /**
         * 确定
         */
        void sure();
    }

    public CommonlyDialog setICallBase(BaseCallback baseCallback) {
        this.baseCallback = baseCallback;
        return this;
    }


}
