package com.xiaomai.zhuangba.weight.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.xiaomai.zhuangba.R;

/**
 * @author Bawj
 * CreateDate 2019/12/17 0017 11:39
 * 申请空跑 dialog
 */
public class ApplyForARunDialog {
    private QMUIDialog qmuiDialog;

    private TextView tvApplicationAmount;
    private TextView tvDialogContent;
    private TextView tvReAppointmentTime;

    private BaseCallback baseCallback;

    public static ApplyForARunDialog getInstance() {
        return new ApplyForARunDialog();
    }

    public ApplyForARunDialog initView(final Context mContext) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_apply_for_run);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_apply_for_run, null));

        //申请金额
        tvApplicationAmount = qmuiDialog.findViewById(R.id.tvApplicationAmount);
        //申请理由
        tvDialogContent = qmuiDialog.findViewById(R.id.tvDialogContent);
        //再约时间
        tvReAppointmentTime = qmuiDialog.findViewById(R.id.tvReAppointmentTime);

        TextView tvDialogOk = qmuiDialog.findViewById(R.id.tvDialogOk);
        TextView tvDialogClose = qmuiDialog.findViewById(R.id.tvDialogClose);

        tvDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qmuiDialog.dismiss();
                if (baseCallback != null){
                    baseCallback.applicationForApproval();
                }
            }
        });
        tvDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qmuiDialog.dismiss();
                if (baseCallback != null){
                    baseCallback.applicationFailed();
                }
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

    public ApplyForARunDialog setApplicationAmount(String applicationAmount){
        tvApplicationAmount.setText(applicationAmount);
        return this;
    }
    public ApplyForARunDialog setDialogContent(String dialogContent){
        tvDialogContent.setText(dialogContent);
        return this;
    }
    public ApplyForARunDialog setReAppointmentTime(String reAppointmentTime){
        tvReAppointmentTime.setText(reAppointmentTime);
        return this;
    }

    public ApplyForARunDialog setICallBase(BaseCallback baseCallback) {
        this.baseCallback = baseCallback;
        return this;
    }


    public interface BaseCallback {
        /**
         * 申请通过
         */
        void applicationForApproval();

        /**
         * 申请不通过
         */
        void applicationFailed();
    }

}
