package com.xiaomai.zhuangba.weight.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.util.DensityUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/8/22 0022
 */
public class AuthenticationDialog implements View.OnClickListener {

    private ImageView ivAuthenticationLog;
    private TextView tvAuthenticationTitle, tvAuthenticationContent;
    private TextView tvDialogVersionOk, tvDialogVersionClose;
    private View viewVerticalLine;
    private QMUIDialog qmuiDialog;
    private BaseCallback baseCallback;

    public static AuthenticationDialog getInstance() {
        return new AuthenticationDialog();
    }

    public AuthenticationDialog initView(Context mContext) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_authentication);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_authentication, null));
        ImageView ivAuthenticationClose = qmuiDialog.findViewById(R.id.ivAuthenticationClose);
        ivAuthenticationLog = qmuiDialog.findViewById(R.id.ivAuthenticationLog);
        tvAuthenticationTitle = qmuiDialog.findViewById(R.id.tvAuthenticationTitle);
        tvAuthenticationContent = qmuiDialog.findViewById(R.id.tvAuthenticationContent);
        tvDialogVersionOk = qmuiDialog.findViewById(R.id.tvDialogVersionOk);
        tvDialogVersionClose = qmuiDialog.findViewById(R.id.tvDialogVersionClose);
        viewVerticalLine = qmuiDialog.findViewById(R.id.viewVerticalLine);
        ivAuthenticationClose.setOnClickListener(this);
        tvDialogVersionClose.setOnClickListener(this);
        tvDialogVersionOk.setOnClickListener(this);
        return this;
    }

    public AuthenticationDialog setIvAuthenticationLog(int img) {
        ivAuthenticationLog.setBackgroundResource(img);
        return this;
    }

    public AuthenticationDialog setTvAuthenticationTitle(String title) {
        tvAuthenticationTitle.setText(title);
        return this;
    }

    public AuthenticationDialog setTvAuthenticationContent(String content) {
        tvAuthenticationContent.setText(content);
        return this;
    }

    public AuthenticationDialog setTvDialogVersionOk(String ok) {
        tvDialogVersionOk.setText(ok);
        return this;
    }

    public AuthenticationDialog isTvDialogVersionClose() {
        viewVerticalLine.setVisibility(View.GONE);
        tvDialogVersionClose.setVisibility(View.GONE);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAuthenticationClose:
                qmuiDialog.dismiss();
                break;
            case R.id.tvDialogVersionClose:
                qmuiDialog.dismiss();
                break;
            case R.id.tvDialogVersionOk:
                qmuiDialog.dismiss();
                if (baseCallback != null) {
                    baseCallback.ok();
                }
                break;
            default:
        }
    }

    public void showDialog() {
        if (qmuiDialog != null) {
            qmuiDialog.show();
            Window window = qmuiDialog.getWindow();
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
        void ok();
    }

    public AuthenticationDialog setICallBase(BaseCallback baseCallback) {
        this.baseCallback = baseCallback;
        return this;
    }

}
