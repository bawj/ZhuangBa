package com.xiaomai.zhuangba.weight.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.xiaomai.zhuangba.R;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class NavigationDialog implements View.OnClickListener{

    private Context mContext;
    private QMUIDialog qmuiDialog;
    private IHeadPortraitPopupCallBack iHeadPortraitPopupCallBack;
    private TextView tvBaiDuMap, tvGoldenMap;

    public static NavigationDialog getInstance() {
        return new NavigationDialog();
    }

    public NavigationDialog setContext(Context mContext) {
        this.mContext = mContext;
        return this;
    }

    public NavigationDialog initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_navigation);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_navigation, null));

        tvBaiDuMap = qmuiDialog.findViewById(R.id.tvBaiDuMap);
        tvGoldenMap = qmuiDialog.findViewById(R.id.tvGoldenMap);
        TextView tvCancel = qmuiDialog.findViewById(R.id.tvCancel);
        tvBaiDuMap.setOnClickListener(this);
        tvGoldenMap.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        return this;
    }

    public NavigationDialog show(){
        if (qmuiDialog != null){
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
            case R.id.tvBaiDuMap:
                if (iHeadPortraitPopupCallBack != null) {
                    iHeadPortraitPopupCallBack.onBaiDuMap();
                }
                dismiss();
                break;
            case R.id.tvGoldenMap:
                if (iHeadPortraitPopupCallBack != null) {
                    iHeadPortraitPopupCallBack.onGoldenMap();
                }
                dismiss();
                break;
            case R.id.tvCancel:
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

    public NavigationDialog setTvBaiDuMap(String text) {
        if (tvBaiDuMap != null) {
            tvBaiDuMap.setText(text);
        }
        return this;
    }

    public NavigationDialog setTvGoldenMap(String text) {
        if (tvGoldenMap != null) {
            tvGoldenMap.setText(text);
        }
        return this;
    }

    public NavigationDialog setDialogCallBack(IHeadPortraitPopupCallBack iHeadPortraitPopupCallBack) {
        this.iHeadPortraitPopupCallBack = iHeadPortraitPopupCallBack;
        return this;
    }

    public interface IHeadPortraitPopupCallBack {

        /**
         * 百度地图导航
         */
        void onBaiDuMap();

        /**
         * 高德地图导航
         */
        void onGoldenMap();

    }

}
