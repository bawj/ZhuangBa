package com.xiaomai.zhuangba.weight.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.toollib.base.BaseRxActivity;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.http.version.Version;
import com.example.toollib.http.version.VersionEnums;
import com.example.toollib.util.DensityUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.Util;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class VersionDialog {

    private AlertDialog alertDialog;
    private TextView tvDialogVersionContent;
    private BaseCallback baseCallback;
    private TextView tvDialogVersionClose;

    public static VersionDialog getInstance() {
        return new VersionDialog();
    }

    public VersionDialog initView(final Context mContext, final Version version) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_version, null);
        alertDialog = new AlertDialog.Builder(mContext).create();
        tvDialogVersionContent = view.findViewById(R.id.tvDialogVersionContent);
        TextView tvDialogVersionOk = view.findViewById(R.id.tvDialogVersionOk);
        tvDialogVersionClose = view.findViewById(R.id.tvDialogVersionClose);
        initData(mContext , version);
        tvDialogVersionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (baseCallback != null){
                    baseCallback.sure();
                }
            }
        });
        tvDialogVersionClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //不在提示
                notTipVersion(mContext,version.getVersionId());
            }
        });
        alertDialog.setView(view);
        //点击外部 不消失
        alertDialog.setCancelable(false);
        return this;
    }

    private void initData(Context mContext,Version version) {
        //版本说明
        String versionDesccribe = version.getVersionDesccribe();
        //是否强制更新:0否;1是
        int ifComple = version.getIfComple();
        if (ifComple == VersionEnums.APP_FORCED_UPDATES.getCode()) {
            //强制更新 “不在提示 不显示”
            tvDialogVersionClose.setVisibility(View.GONE);
        }
        Util.setParagraphSpacing(mContext, tvDialogVersionContent, versionDesccribe, 24, 3);
    }

    private void notTipVersion(Context mContext, int version) {
        RxUtils.getObservable(ServiceUrl.getUserApi().skipVersion(version))
                .compose(((BaseRxActivity) mContext).<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(mContext) {
                    @Override
                    protected void onSuccess(Object response) {
                        dismiss();
                    }
                });
    }

    private void dismiss() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    public void showDialog() {
        if (alertDialog != null) {
            alertDialog.show();
            Window window = alertDialog.getWindow();
            if (window != null) {
///                window.requestFeature(Window.FEATURE_NO_TITLE);
//                window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                window.setBackgroundDrawable(new ColorDrawable(0));
                window.getDecorView().setPadding(DensityUtil.dp2px(32), 0, DensityUtil.dp2px(32), 0);
            }
        }
    }

    public interface BaseCallback{
        /**
         * 确定
         */
        void sure();
    }

    public VersionDialog setICallBase (BaseCallback baseCallback){
        this.baseCallback = baseCallback;
        return this;
    }
}
