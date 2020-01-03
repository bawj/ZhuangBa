package com.xiaomai.zhuangba.weight.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.CompensateDialogAdapter;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author Bawj
 * CreateDate:     2020/1/3 0003 13:46
 * <p>
 * 索赔dialog
 */
public class CompensateDialog<T extends BaseFragment> implements View.OnClickListener , BaseQuickAdapter.OnItemClickListener {

    private QMUIDialog qmuiDialog;

    private T t;
    private EditText editClaimAmount, editClaimReason;
    private RecyclerView recyclerCompensate;
    private CompensateDialogAdapter compensateDialogAdapter;
    private String orderCode;

    public static CompensateDialog getInstance() {
        return new CompensateDialog();
    }

    public CompensateDialog initView(final Context mContext , T t) {
        this.t = t;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_compensate);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_compensate, null));

        TextView tvOk = qmuiDialog.findViewById(R.id.tvOk);
        TextView tvClose = qmuiDialog.findViewById(R.id.tvClose);
        editClaimAmount = qmuiDialog.findViewById(R.id.editClaimAmount);
        editClaimReason = qmuiDialog.findViewById(R.id.editClaimReason);
        recyclerCompensate = qmuiDialog.findViewById(R.id.recyclerCompensate);

        compensateDialogAdapter = new CompensateDialogAdapter();
        compensateDialogAdapter.setOnItemClickListener(this);
        tvOk.setOnClickListener(this);
        tvClose.setOnClickListener(this);
        return this;
    }

    /**
     * 设置recycleView
     *
     * @param mContext   ctx
     * @param compensate 设备面
     */
    public CompensateDialog setCompensate(Context mContext, List<DeviceSurfaceInformation> compensate) {
        recyclerCompensate.setLayoutManager(new GridLayoutManager(mContext, 3));
        recyclerCompensate.addItemDecoration(new GridSpacingItemDecoration(3, 32, false));
        recyclerCompensate.setAdapter(compensateDialogAdapter);
        compensateDialogAdapter.setNewData(compensate);

        if (compensate != null && compensate.size() > 0){
            orderCode = compensate.get(0).getOrderCode();
            compensateDialogAdapter.isCheck(0);
        }
        return this;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DeviceSurfaceInformation deviceSurfaceInformation = (DeviceSurfaceInformation) view.findViewById(R.id.tvEmployerReal).getTag();
        compensateDialogAdapter.isCheck(position);
        orderCode = deviceSurfaceInformation.getOrderCode();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvOk:
                //提交
                upload();
                break;
            case R.id.tvClose:
                //取消
                dismiss();
                break;
            default:
        }
    }

    private void upload() {
        String claimAmount = editClaimAmount.getText().toString();
        String claimReason = editClaimReason.getText().toString();
        if (TextUtils.isEmpty(claimAmount)){
            ToastUtil.showShort(t.getContext().getString(R.string.please_fill_in_the_amount));
        }else if (TextUtils.isEmpty(claimReason)){
            ToastUtil.showShort(t.getContext().getString(R.string.please_fill_in_the_claim_reason));
        }else {
            HashMap<String , String> hashMap = new HashMap<>(4);
            //选中面的订单编号
            hashMap.put("orderCode" , orderCode);
            hashMap.put("source" , "1");
            //申请赔偿金额
            hashMap.put("price" , claimAmount);
            //雇主发起理赔原因
            hashMap.put("reason" , claimReason);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), new Gson().toJson(hashMap));
            RxUtils.getObservable(ServiceUrl.getUserApi().initiateClaim(requestBody))
                    .compose(t.<HttpResult<Object>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>(t.getContext()) {
                        @Override
                        protected void onSuccess(Object response) {
                            dismiss();
                            ToastUtil.showShort(t.getContext().getString(R.string.submit_successfully));
                        }
                    });
        }
    }

    private void dismiss() {
        if (qmuiDialog != null){
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
}
