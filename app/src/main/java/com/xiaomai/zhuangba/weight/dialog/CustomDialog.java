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

/**
 * @author Bawj
 * CreateDate:     2020/1/3 0003 13:46
 * <p>
 * 自定义补充 dialog
 */
public class CustomDialog implements View.OnClickListener {

    private QMUIDialog qmuiDialog;

    private Context mContext;
    private EditText editSupplementaryAmount, editCancellation;

    public static CustomDialog getInstance() {
        return new CustomDialog();
    }

    public CustomDialog initView(final Context mContext) {
        this.mContext = mContext;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_custom);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_custom, null));

        TextView tvOk = qmuiDialog.findViewById(R.id.tvOk);
        TextView tvClose = qmuiDialog.findViewById(R.id.tvClose);
        editSupplementaryAmount = qmuiDialog.findViewById(R.id.editSupplementaryAmount);
        editCancellation = qmuiDialog.findViewById(R.id.editCancellation);

        tvOk.setOnClickListener(this);
        tvClose.setOnClickListener(this);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvOk:
                //提交
                String supplementaryAmount = editSupplementaryAmount.getText().toString();
                String cancellation = editCancellation.getText().toString();
                if (TextUtils.isEmpty(supplementaryAmount)) {
                    ToastUtil.showShort(mContext.getString(R.string.please_fill_in_the_amount));
                } else if (TextUtils.isEmpty(cancellation)) {
                    ToastUtil.showShort(mContext.getString(R.string.please_fill_in_the_reason_for_cancellation));
                } else if (iCustomDialogCallBack != null) {
                    iCustomDialogCallBack.ok(supplementaryAmount , cancellation);
                    dismiss();
                }
                break;
            case R.id.tvClose:
                //取消
                dismiss();
                break;
            default:
        }
    }


    private void dismiss() {
        if (qmuiDialog != null) {
            qmuiDialog.dismiss();
        }
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

    private ICustomDialogCallBack iCustomDialogCallBack;
    public interface ICustomDialogCallBack {

        void ok(String supplementaryAmount, String cancellation);

    }

    public CustomDialog setCustomDialogCallBack(ICustomDialogCallBack iCustomDialogCallBack){
        this.iCustomDialogCallBack = iCustomDialogCallBack;
        return this;
    }
}
