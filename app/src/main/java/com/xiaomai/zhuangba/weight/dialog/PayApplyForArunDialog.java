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
 * @author Bawj
 * CreateDate:     2019/12/19 0019 14:57
 * 空跑支付
 */
public class PayApplyForArunDialog implements View.OnClickListener {

    private QMUIDialog qmuiDialog;
    private View view;
    private TextView tvDialogPaymentMonthlyAccount;
    private IHeadPortraitPopupCallBack iHeadPortraitPopupCallBack;

    public static PayApplyForArunDialog getInstance() {
        return new PayApplyForArunDialog();
    }

    public PayApplyForArunDialog initView(Context mContext) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_pay_for_arun);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_pay_for_arun, null));
        TextView tvDialogPayWallet = qmuiDialog.findViewById(R.id.tvDialogPayWallet);
        TextView tvDialogPayWeChat = qmuiDialog.findViewById(R.id.tvDialogPayWeChat);
        tvDialogPaymentMonthlyAccount = qmuiDialog.findViewById(R.id.tvDialogPaymentMonthlyAccount);
        view = qmuiDialog.findViewById(R.id.viewDialogPaymentMonthlyAccount);
        TextView tvDialogPayAli = qmuiDialog.findViewById(R.id.tvDialogPayAli);
        ImageView ivDialogPayClose = qmuiDialog.findViewById(R.id.ivDialogPayClose);

        tvDialogPayWallet.setOnClickListener(this);
        tvDialogPayWeChat.setOnClickListener(this);
        tvDialogPayAli.setOnClickListener(this);
        tvDialogPaymentMonthlyAccount.setOnClickListener(this);
        ivDialogPayClose.setOnClickListener(this);

        return this;
    }

    public PayApplyForArunDialog show() {
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
            case R.id.tvDialogPaymentMonthlyAccount:
                //月结
                if (iHeadPortraitPopupCallBack != null) {
                    dismiss();
                    iHeadPortraitPopupCallBack.paymentMonthlyAccount();
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

    public PayApplyForArunDialog setDialogCallBack(IHeadPortraitPopupCallBack iHeadPortraitPopupCallBack) {
        this.iHeadPortraitPopupCallBack = iHeadPortraitPopupCallBack;
        return this;
    }

    public PayApplyForArunDialog isPaymentMonthlyAccount(Boolean response) {
        if (response){
            view.setVisibility(View.VISIBLE);
            tvDialogPaymentMonthlyAccount.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
            tvDialogPaymentMonthlyAccount.setVisibility(View.GONE);
        }
        return this;
    }

    public interface IHeadPortraitPopupCallBack {

        /**
         * 钱包支付
         */
        void walletPay();

        /**
         * 月结
         */
        void paymentMonthlyAccount();

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
