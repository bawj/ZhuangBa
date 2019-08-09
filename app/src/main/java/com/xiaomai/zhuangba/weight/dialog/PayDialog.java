package com.xiaomai.zhuangba.weight.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/8/9 0009
 * 维保 支付
 */
public class PayDialog implements View.OnClickListener {

    private QMUIDialog qmuiDialog;
    private TextView tvDialogPayWallet;
    private TextView tvDialogPayWeChat;
    private TextView tvDialogPayAli;
    private ImageView ivDialogPayClose;
    private IHeadPortraitPopupCallBack iHeadPortraitPopupCallBack;

    public static PayDialog getInstance() {
        return new PayDialog();
    }

    public PayDialog initView(Context mContext) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_pay);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_pay, null));
        tvDialogPayWallet = qmuiDialog.findViewById(R.id.tvDialogPayWallet);
        tvDialogPayWeChat = qmuiDialog.findViewById(R.id.tvDialogPayWeChat);
        tvDialogPayAli = qmuiDialog.findViewById(R.id.tvDialogPayAli);
        ivDialogPayClose = qmuiDialog.findViewById(R.id.ivDialogPayClose);

        tvDialogPayWallet.setOnClickListener(this);
        tvDialogPayWeChat.setOnClickListener(this);
        tvDialogPayAli.setOnClickListener(this);
        ivDialogPayClose.setOnClickListener(this);

        return this;
    }

    public PayDialog show() {
        if (qmuiDialog != null) {
            qmuiDialog.show();
            Window window = qmuiDialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.BOTTOM);
            }
        }
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDialogPayWallet:
                //钱包支付
                if (iHeadPortraitPopupCallBack != null) {
                    dismiss();
                    iHeadPortraitPopupCallBack.walletPay();
                }
                break;
            case R.id.tvDialogPayWeChat:
                //微信支付
                if (iHeadPortraitPopupCallBack != null) {
                    dismiss();
                    iHeadPortraitPopupCallBack.weChatPay();
                }
                break;
            case R.id.tvDialogPayAli:
                //支付宝支付
                if (iHeadPortraitPopupCallBack != null) {
                    dismiss();
                    iHeadPortraitPopupCallBack.aliPay();
                }
                break;
            case R.id.ivDialogPayClose:
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

    public PayDialog setDialogCallBack(IHeadPortraitPopupCallBack iHeadPortraitPopupCallBack) {
        this.iHeadPortraitPopupCallBack = iHeadPortraitPopupCallBack;
        return this;
    }

    public interface IHeadPortraitPopupCallBack {

        /**
         * 钱包支付
         */
        void walletPay();

        /**
         * 微信支付
         */
        void weChatPay();


        /**
         * 支付宝
         */
        void aliPay();

    }

}
