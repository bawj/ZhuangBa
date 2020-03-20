package com.xiaomai.zhuangba.weight.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.util.ToastUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.DialogNoPassAdapter;
import com.xiaomai.zhuangba.data.bean.Cause;
import com.xiaomai.zhuangba.util.Util;

import java.util.List;

/**
 * @Author: Bawj
 * @CreateDate: 2019/12/17 0017 11:39
 * 验收不通过 dialog
 */
public class EditTextDialogNoPassBuilder {
    private QMUIDialog qmuiDialog;

    private TextView tvProblemFeedback;
    private  TextView tvDialogOk;
    private BaseCallback baseCallback;
    private EditText editDialogContent;
    private String tip;
    private DialogNoPassAdapter noPassAdapter;
    private Cause cause;
    public static EditTextDialogNoPassBuilder getInstance() {
        return new EditTextDialogNoPassBuilder();
    }

    public EditTextDialogNoPassBuilder initView(final Context mContext) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_exittext_no_pass);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_exittext_no_pass, null));

        tvProblemFeedback = qmuiDialog.findViewById(R.id.tvProblemFeedback);
        tvDialogOk = qmuiDialog.findViewById(R.id.tvDialogOk);
        TextView tvDialogClose = qmuiDialog.findViewById(R.id.tvDialogClose);
        editDialogContent = qmuiDialog.findViewById(R.id.editDialogContent);
        tip = mContext.getString(R.string.please_input_content);
        Util.setEditTextInhibitInputSpaChat(editDialogContent,50);

        RecyclerView recyclerReason = qmuiDialog.findViewById(R.id.recyclerReason);
        recyclerReason.setLayoutManager(new LinearLayoutManager(mContext));
        noPassAdapter = new DialogNoPassAdapter();
        recyclerReason.setAdapter(noPassAdapter);

        noPassAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                cause = (Cause) view.findViewById(R.id.tvReason).getTag();
                noPassAdapter.setCheck(position);
            }
        });

        tvDialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (baseCallback != null){
                    qmuiDialog.dismiss();
                    baseCallback.ok(cause,editDialogContent.getText().toString());
                }else{
                    ToastUtil.showShort(tip);
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

    public EditTextDialogNoPassBuilder setICallBase(BaseCallback baseCallback) {
        this.baseCallback = baseCallback;
        return this;
    }

    public EditTextDialogNoPassBuilder setTitle(String s){
        tvProblemFeedback.setText(s);
        return this;
    }
    public EditTextDialogNoPassBuilder setDialogOk(String s){
        tvDialogOk.setText(s);
        return this;
    }
    public EditTextDialogNoPassBuilder setDialogOkColor(int color){
        tvDialogOk.setTextColor(color);
        return this;
    }
    public TextView getTvDialogOk(){
        return tvDialogOk;
    }

    public EditTextDialogNoPassBuilder setContent(String s){
        editDialogContent.setText(s);
        return this;
    }
    public void getTip(String notTip){
        tip = notTip;
    }

    public EditTextDialogNoPassBuilder setNoPassAdapter(List<Cause> causeList){
        if (noPassAdapter != null){
            noPassAdapter.setNewData(causeList);
        }
        return this;
    }

    public EditText geteditDialogContent(){
        return editDialogContent;
    }

    public interface BaseCallback {
        /**
         * 不通过原因
         *
         * @param cause   理由
         * @param content string
         */
        void ok(Cause cause, String content);
    }

}
