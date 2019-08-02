package com.xiaomai.zhuangba.weight.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.ServiceDialogAdapter;
import com.xiaomai.zhuangba.data.bean.ServiceData;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/9 0009
 */
public class ServiceDialog {

    private BaseCallback baseCallback;
    private QMUIDialog qmuiDialog;

    public static ServiceDialog getInstance() {
        return new ServiceDialog();
    }

    public ServiceDialog initView(Context mContext) {
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_service);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(LayoutInflater.from(mContext).inflate(R.layout.dialog_service , null));
        return this;
    }

    public ServiceDialog setData(List<ServiceData> serviceDataList , Context mContext){
        RecyclerView recyclerDialogService = qmuiDialog.findViewById(R.id.recyclerDialogService);
        recyclerDialogService.setLayoutManager(new GridLayoutManager(mContext , 3));
        ServiceDialogAdapter serviceDialogAdapter = new ServiceDialogAdapter(R.layout.item_service_dialog, serviceDataList);
        serviceDialogAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ServiceData serviceData = (ServiceData) view.findViewById(R.id.tvServiceName).getTag();
                if (baseCallback != null){
                    dismissDialog();
                    baseCallback.baseCallBack(String.valueOf(serviceData.getServiceId()) , serviceData.getServiceText());
                }
            }
        });
        recyclerDialogService.setAdapter(serviceDialogAdapter);
        ImageView ivDialogServiceClose = qmuiDialog.findViewById(R.id.ivDialogServiceClose);
        ivDialogServiceClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qmuiDialog.dismiss();
            }
        });
        return this;
    }

    public void showDialog(){
        qmuiDialog.show();
        Window window = qmuiDialog.getWindow();
        if (window != null){
            window.setGravity(Gravity.BOTTOM);
        }
    }

    public void dismissDialog(){
        if (qmuiDialog != null){
            qmuiDialog.dismiss();
        }
    }

    public ServiceDialog setICallBase (BaseCallback baseCallback){
        this.baseCallback = baseCallback;
        return this;
    }

    public interface BaseCallback{
        /**
         * callback
         * @param id 大类ID
         * @param serviceText 大类名称
         */
        void baseCallBack(String id,String serviceText);
    }

}
