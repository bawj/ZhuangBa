package com.xiaomai.zhuangba.weight.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.util.DensityUtil;
import com.example.toollib.util.DensityUtils;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.DialogRVChoosingAdapter;
import com.xiaomai.zhuangba.data.bean.Maintenance;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.util.ConstantUtil;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/7 0007
 */
public class ShopCarDialog {

    private AlertDialog alertDialog;
    private BaseCallback baseCallback;

    private RecyclerView rvChoosingGoods;
    private TextView tvChoosingMoney;
    private Maintenance maintenance;

    public static ShopCarDialog getInstance() {
        return new ShopCarDialog();
    }

    public ShopCarDialog initView(final ShopCarData shopCarData, final Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_shop_car, null);
        alertDialog = new AlertDialog.Builder(mContext).create();
        //关闭
        ImageView ivChoosingGoodsClose = view.findViewById(R.id.ivChoosingGoodsClose);
        //维保 类型
        rvChoosingGoods = view.findViewById(R.id.rvChoosingGoods);
        rvChoosingGoods.setLayoutManager(new LinearLayoutManager(mContext));
        //选择的价格(维保价格)
        tvChoosingMoney = view.findViewById(R.id.tvChoosingMoney);
        //确认添加
        QMUIButton btnShopCarOk = view.findViewById(R.id.btnShopCarOk);
        btnShopCarOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if (baseCallback != null) {
                    baseCallback.sure(maintenance);
                }
            }
        });

        ivChoosingGoodsClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
            }
        });
        //默认值
        maintenance = new Maintenance();
        //有选择维保
        if (shopCarData != null) {
            if (shopCarData.getMaintenanceId() == ConstantUtil.DEF_MAINTENANCE) {
                //选择了 商品 没有选择维保
                maintenance.setId(-1);
                maintenance.setNotChoosingMaintenance(mContext.getString(R.string.not_choosing_maintenance));
            } else {
                maintenance.setId(shopCarData.getMaintenanceId());
                maintenance.setAmount(DensityUtils.stringTypeDouble(shopCarData.getMaintenanceMoney()));
                maintenance.setNumber(DensityUtils.stringTypeInteger(shopCarData.getMaintenanceTime()));
            }
            String maintenanceMoney = "0";
            if (!TextUtils.isEmpty(shopCarData.getMaintenanceMoney()) && !shopCarData.getMaintenanceMoney().equals("null")){
                maintenanceMoney = shopCarData.getMaintenanceMoney();
            }
            tvChoosingMoney.setText(maintenanceMoney);
        } else {
            //没有选择维保
            maintenance.setId(-1);
            maintenance.setNotChoosingMaintenance(mContext.getString(R.string.not_choosing_maintenance));
            tvChoosingMoney.setText(mContext.getString(R.string.content_money, String.valueOf(0)));
        }
        alertDialog.setView(view);
        return this;
    }


    public ShopCarDialog setRvChoosingGoods(final Context mContext, final List<Maintenance> maintenanceList) {
        Maintenance defMaintenance = new Maintenance();
        defMaintenance.setId(ConstantUtil.DEF_MAINTENANCE);
        maintenanceList.add(0, defMaintenance);
        final DialogRVChoosingAdapter dialogRVChoosingAdapter = new DialogRVChoosingAdapter();
        dialogRVChoosingAdapter.setNewData(maintenanceList);
        dialogRVChoosingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                maintenance = maintenanceList.get(position);
                dialogRVChoosingAdapter.checkItem(maintenance.getId());
                tvChoosingMoney.setText(mContext.getString(R.string.content_money, String.valueOf(maintenance.getAmount())));
            }
        });
        rvChoosingGoods.setAdapter(dialogRVChoosingAdapter);
        dialogRVChoosingAdapter.checkItem(maintenance.getId());
        return this;
    }

    public void showDialog() {
        if (alertDialog != null) {
            alertDialog.show();
            Window window = alertDialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.getDecorView().setPadding(DensityUtil.dp2px(32), 0, DensityUtil.dp2px(32), 0);
            }
        }
    }

    public interface BaseCallback {
        /**
         * 确定
         *
         * @param maintenance 商品
         */
        void sure(Maintenance maintenance);
    }

    public ShopCarDialog setICallBase(BaseCallback baseCallback) {
        this.baseCallback = baseCallback;
        return this;
    }

}
