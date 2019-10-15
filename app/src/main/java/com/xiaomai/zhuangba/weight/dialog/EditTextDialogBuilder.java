package com.xiaomai.zhuangba.weight.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toollib.util.ToastUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.util.Util;

/**
 * @author Administrator
 * @date 2019/5/24 0024
 * <p>
 * 输入框
 */
public class EditTextDialogBuilder {

    private QMUIDialog qmuiDialog;

    private TextView tvProblemFeedback;
    private  TextView tvDialogOk;
    private BaseCallback baseCallback;
    private EditText editDialogContent;
    public static EditTextDialogBuilder getInstance() {
        return new EditTextDialogBuilder();
    }

    public EditTextDialogBuilder initView(final Context mContext) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_edittext);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_edittext, null));
        tvProblemFeedback = qmuiDialog.findViewById(R.id.tvProblemFeedback);
        tvDialogOk = qmuiDialog.findViewById(R.id.tvDialogOk);
        TextView tvDialogClose = qmuiDialog.findViewById(R.id.tvDialogClose);
        editDialogContent = qmuiDialog.findViewById(R.id.editDialogContent);
        Util.setEditTextInhibitInputSpaChat(editDialogContent,50);
        tvDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = editDialogContent.getText().toString();
                if (baseCallback != null && !TextUtils.isEmpty(s)){
                    qmuiDialog.dismiss();
                    baseCallback.ok(editDialogContent.getText().toString());
                }else{
                    ToastUtil.showShort(mContext.getString(R.string.please_input_content));
                }
            }
        });
        tvDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qmuiDialog.dismiss();
            }
        });

        return this;
    }

    public void showDialog() {
        if (qmuiDialog != null) {
            qmuiDialog.show();
            Window window = qmuiDialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.getDecorView().setPadding(com.example.toollib.util.DensityUtil.dp2px(32), 0, com.example.toollib.util.DensityUtil.dp2px(32), 0);
            }
        }
    }

    public EditTextDialogBuilder setICallBase(BaseCallback baseCallback) {
        this.baseCallback = baseCallback;
        return this;
    }

    public EditTextDialogBuilder setTitle(String s){
        tvProblemFeedback.setText(s);
        return this;
    }
    public EditTextDialogBuilder setDialogOk(String s){
        tvDialogOk.setText(s);
        return this;
    }
    public EditTextDialogBuilder setDialogOkColor(int color){
        tvDialogOk.setTextColor(color);
        return this;
    }
    public EditTextDialogBuilder setContent(String s){
        editDialogContent.setText(s);
        return this;
    }

    public interface BaseCallback {
        /**
         * 不通过原因
         *
         * @param content string
         */
        void ok(String content);
    }

}
