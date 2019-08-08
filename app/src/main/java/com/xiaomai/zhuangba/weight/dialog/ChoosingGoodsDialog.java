package com.xiaomai.zhuangba.weight.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.util.DensityUtil;
import com.example.toollib.util.DensityUtils;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.DialogRVChoosingAdapter;
import com.xiaomai.zhuangba.data.bean.Maintenance;
import com.xiaomai.zhuangba.data.bean.ServiceSubcategoryProject;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.module.IOnAddDelListeners;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.ShopCarUtil;
import com.xiaomai.zhuangba.weight.AnimShopsButton;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/6 0006
 * <p>
 * 选择服务-->选择规格
 */
public class ChoosingGoodsDialog {

    private AlertDialog alertDialog;
    private BaseCallback baseCallback;

    private AnimShopsButton animChoosingShopBtn;
    private RecyclerView rvChoosingGoods;
    private TextView tvChoosingGoodsName, tvChoosingMoney, tvChoosingMaintenanceTime;
    private Maintenance maintenance;

    public static ChoosingGoodsDialog getInstance() {
        return new ChoosingGoodsDialog();
    }

    public ChoosingGoodsDialog initView(final ServiceSubcategoryProject serviceSubcategoryProject, final Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_choosing_goods, null);
        alertDialog = new AlertDialog.Builder(mContext).create();
        //关闭
        ImageView ivChoosingGoodsClose = view.findViewById(R.id.ivChoosingGoodsClose);
        //添加 商品
        animChoosingShopBtn = view.findViewById(R.id.animChoosingShopBtn);
        //维保 类型
        rvChoosingGoods = view.findViewById(R.id.rvChoosingGoods);
        rvChoosingGoods.setLayoutManager(new LinearLayoutManager(mContext));
        //商品名称
        tvChoosingGoodsName = view.findViewById(R.id.tvChoosingGoodsName);
        //选择的价格(商品价格 +  维保价格)
        tvChoosingMoney = view.findViewById(R.id.tvChoosingMoney);
        //选择的维保时间
        tvChoosingMaintenanceTime = view.findViewById(R.id.tvChoosingMaintenanceTime);
        //确认添加
        LinearLayout laySelectAdd = view.findViewById(R.id.laySelectAdd);
        animChoosingShopBtn.setOnAddDelListener(new IOnAddDelListeners() {
            @Override
            public void onAddSuccess(int mCount) {
                Log.e("增加数量 = " + mCount);
                //计算 总价
                setTvChoosingMoney(mContext, serviceSubcategoryProject, maintenance, mCount);
            }

            @Override
            public void onDelSuccess(int mCount) {
                Log.e("减去数量 = " + mCount);
                //计算总价
                setTvChoosingMoney(mContext, serviceSubcategoryProject, maintenance, mCount);
            }
        });

        laySelectAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                int mCount = animChoosingShopBtn.getMCount();
                if (baseCallback != null) {
                    baseCallback.sure(maintenance, mCount);
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
        ShopCarData shopCarData = ShopCarUtil.getShopCarData(serviceSubcategoryProject);
        maintenance = new Maintenance();
        //有选择维保
        if (shopCarData != null) {
            if (shopCarData.getMaintenanceId() == ConstantUtil.DEF_MAINTENANCE){
                //选择了 商品 没有选择维保
                maintenance.setId(-1);
                maintenance.setNotChoosingMaintenance(mContext.getString(R.string.not_choosing_maintenance));
                tvChoosingMaintenanceTime.setText(mContext.getString(R.string.not_choosing_maintenance_));
            }else{
                maintenance.setId(shopCarData.getMaintenanceId());
                maintenance.setAmount(DensityUtils.stringTypeDouble(shopCarData.getMaintenanceMoney()));
                maintenance.setNumber(DensityUtils.stringTypeInteger(shopCarData.getMaintenanceTime()));
                tvChoosingMaintenanceTime.setText(mContext.getString(R.string.maintenance_date, shopCarData.getMaintenanceTime()));
            }
            tvChoosingMoney.setText(mContext.getString(R.string.content_money, String.valueOf(ShopCarUtil.getTotalMoney())));
            animChoosingShopBtn.setIsStatus(DensityUtils.stringTypeInteger(shopCarData.getNumber()));
            setTvChoosingMoney(mContext, serviceSubcategoryProject, maintenance, DensityUtils.stringTypeInteger(shopCarData.getNumber()));
        } else {
            //没有选择维保
            maintenance.setId(-1);
            maintenance.setNotChoosingMaintenance(mContext.getString(R.string.not_choosing_maintenance));
            tvChoosingMaintenanceTime.setText(mContext.getString(R.string.not_choosing_maintenance_));
            tvChoosingMoney.setText(mContext.getString(R.string.content_money, String.valueOf(0)));
        }
        alertDialog.setView(view);
        return this;
    }

    private void setTvChoosingMoney(Context mContext, ServiceSubcategoryProject serviceSubcategoryProject,
                                    Maintenance maintenance, int count) {
        Double price = serviceSubcategoryProject.getPrice();
        Double price2 = serviceSubcategoryProject.getPrice2();
        Double price3 = serviceSubcategoryProject.getPrice3();

        Double amount = 0.0;
        if (maintenance != null) {
            Double amount1 = maintenance.getAmount();
            if (amount1 != null) {
                amount = maintenance.getAmount();
            }
        }
        Double totalMoneys = ShopCarUtil.getTotalMoneys(count, price, price2, price3, amount);
        tvChoosingMoney.setText(mContext.getString(R.string.content_money, String.valueOf(totalMoneys)));
    }

    public ChoosingGoodsDialog setTvChoosingGoodsName(String choosingGoodsName) {
        tvChoosingGoodsName.setText(choosingGoodsName);
        return this;
    }

    public ChoosingGoodsDialog setRvChoosingGoods(final Context mContext, final ServiceSubcategoryProject serviceSubcategoryProject,
                                                  final List<Maintenance> maintenanceList) {
        Maintenance defMaintenance = new Maintenance();
        defMaintenance.setId(ConstantUtil.DEF_MAINTENANCE);
        maintenanceList.add(0, defMaintenance);
        final DialogRVChoosingAdapter dialogRVChoosingAdapter = new DialogRVChoosingAdapter();
        dialogRVChoosingAdapter.setNewData(maintenanceList);
        dialogRVChoosingAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                maintenance = maintenanceList.get(position);
                if (maintenance.getId() == -1) {
                    tvChoosingMaintenanceTime.setText(mContext.getString(R.string.not_choosing_maintenance_));
                } else {
                    tvChoosingMaintenanceTime.setText(mContext.getString(R.string.maintenance_date, String.valueOf(maintenance.getNumber())));
                }
                dialogRVChoosingAdapter.checkItem(maintenance.getId());
                setTvChoosingMoney(mContext, serviceSubcategoryProject, maintenance, animChoosingShopBtn.getMCount());
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
         * @param number 商品数量
         */
        void sure(Maintenance maintenance, int number);
    }

    public ChoosingGoodsDialog setICallBase(BaseCallback baseCallback) {
        this.baseCallback = baseCallback;
        return this;
    }

}
