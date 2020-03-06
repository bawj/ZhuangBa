package com.xiaomai.zhuangba.weight.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.EquipmentSurfaceRules;
import com.xiaomai.zhuangba.http.ServiceUrl;

/**
 * @author Bawj
 * CreateDate:     2020/3/5 0005 17:14
 * 设备面规则 弹窗
 */
public class EquipmentSurfaceDialog<T extends BaseFragment> implements View.OnClickListener {

    private T t;
    private String serviceId;
    private Context mContext;
    private QMUIDialog qmuiDialog;

    public static EquipmentSurfaceDialog getInstance() {
        return new EquipmentSurfaceDialog();
    }

    public EquipmentSurfaceDialog setContext(Context mContext, T t , String serviceId) {
        this.t = t;
        this.serviceId = serviceId;
        this.mContext = mContext;
        return this;
    }

    public EquipmentSurfaceDialog initView(EquipmentSurfaceRules equipmentSurfaceRules) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_equipment_surface);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_equipment_surface, null));

        ImageView ivEquipmentSurfaceRules = qmuiDialog.findViewById(R.id.ivEquipmentSurfaceRules);
        TextView tvSurfaceRules = qmuiDialog.findViewById(R.id.tvSurfaceRules);
        String pictUrl = equipmentSurfaceRules.getPictUrl();
        String notice = equipmentSurfaceRules.getNotice();
        GlideManager.loadImage(mContext , pictUrl , ivEquipmentSurfaceRules);
        tvSurfaceRules.setText(notice);
        QMUIButton btnGotIt = qmuiDialog.findViewById(R.id.btnGotIt);
        btnGotIt.setOnClickListener(this);
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGotIt:
                //点击我知道了
                gotIt();
                break;
            default:
        }
    }

    private void gotIt() {
        RxUtils.getObservableZip(ServiceUrl.getUserApi().savePopup(serviceId))
                .compose(t.<HttpResult<Object>>bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<Object>(mContext) {
                    @Override
                    protected void onSuccess(Object response) {
                        dismiss();
                    }
                });
    }

    public EquipmentSurfaceDialog show() {
        if (qmuiDialog != null) {
            qmuiDialog.show();
            Window window = qmuiDialog.getWindow();
            if (window != null) {
                window.setGravity(Gravity.BOTTOM);
            }
        }
        return this;
    }


    private void dismiss() {
        if (qmuiDialog != null) {
            qmuiDialog.dismiss();
        }
    }
}
