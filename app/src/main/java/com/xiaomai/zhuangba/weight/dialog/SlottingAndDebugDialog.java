package com.xiaomai.zhuangba.weight.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.util.DensityUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.shop.AuxiliaryMaterialsAdapter;
import com.xiaomai.zhuangba.adapter.shop.SelectDebuggingAdapter;
import com.xiaomai.zhuangba.adapter.shop.SelectionSlotLengthAdapter;
import com.xiaomai.zhuangba.data.bean.Debugging;
import com.xiaomai.zhuangba.data.bean.Slotting;
import com.xiaomai.zhuangba.data.bean.db.MaterialsListDB;
import com.xiaomai.zhuangba.data.bean.db.ShopAuxiliaryMaterialsDB;
import com.xiaomai.zhuangba.data.bean.db.SlottingListDB;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.util.ShopCarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/20 0020
 * <p>
 * 必选项
 */
public class SlottingAndDebugDialog implements BaseQuickAdapter.OnItemClickListener {

    private QMUIDialog qmuiDialog;
    private BaseCallback baseCallback;
    private SelectionSlotLengthAdapter selectionSlotLengthAdapter;
    private SelectDebuggingAdapter selectDebuggingAdapter;
    private AuxiliaryMaterialsAdapter auxiliaryMaterialsAdapter;
    private TextView tvSlottingMoney , tvSelectDebugging;
    private RecyclerView rvSelectDebugging;
    private Context mContext;

    private double slottingSlottingPrice = 0d,
            debuggingPrice = 0d, materialsSlottingPrice = 0d;

    private SlottingListDB item;
    private Debugging debugging;
    private MaterialsListDB materialsListDB;

    public static SlottingAndDebugDialog getInstance() {
        return new SlottingAndDebugDialog();
    }

    public SlottingAndDebugDialog initView(final Context mContext, final Slotting slotting) {
        this.mContext = mContext;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_slotting_and_debug);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_slotting_and_debug, null));

        RecyclerView rvSelectionOfSlotLength = qmuiDialog.findViewById(R.id.rvSelectionOfSlotLength);
        rvSelectDebugging = qmuiDialog.findViewById(R.id.rvSelectDebugging);
        tvSelectDebugging = qmuiDialog.findViewById(R.id.tvSelectDebugging);
        RecyclerView rvAuxiliaryMaterials = qmuiDialog.findViewById(R.id.rvAuxiliaryMaterials);
        //开槽长度
        rvSelectionOfSlotLength.setLayoutManager(new GridLayoutManager(mContext, 3));
        selectionSlotLengthAdapter = new SelectionSlotLengthAdapter();
        selectionSlotLengthAdapter.setNewData(slotting.getSlottingList());
        rvSelectionOfSlotLength.setAdapter(selectionSlotLengthAdapter);
        selectionSlotLengthAdapter.setOnItemClickListener(this);
        //选择调试
        rvSelectDebugging.setLayoutManager(new GridLayoutManager(mContext, 2));
        List<Debugging> debuggingList = getDebuggingList(mContext, slotting.getDebugPrice());
        selectDebuggingAdapter = new SelectDebuggingAdapter();
        selectDebuggingAdapter.setNewData(debuggingList);
        rvSelectDebugging.setAdapter(selectDebuggingAdapter);
        selectDebuggingAdapter.setOnItemClickListener(this);
        //选择辅材
        rvAuxiliaryMaterials.setLayoutManager(new GridLayoutManager(mContext, 3));
        auxiliaryMaterialsAdapter = new AuxiliaryMaterialsAdapter();
        auxiliaryMaterialsAdapter.setNewData(slotting.getMaterialsList());
        rvAuxiliaryMaterials.setAdapter(auxiliaryMaterialsAdapter);
        auxiliaryMaterialsAdapter.setOnItemClickListener(this);

        ShopAuxiliaryMaterialsDB shopAuxiliaryMaterialsDB = DBHelper.getInstance()
                .getShopAuxiliaryMaterialsDBDao().queryBuilder().unique();
        if (shopAuxiliaryMaterialsDB != null) {
            selectionSlotLengthAdapter.checkItem(shopAuxiliaryMaterialsDB.getSlottingSlottingId());
            selectDebuggingAdapter.checkItem(shopAuxiliaryMaterialsDB.getDebuggingId());
            auxiliaryMaterialsAdapter.checkItem(shopAuxiliaryMaterialsDB.getMaterialsSlottingId());

            slottingSlottingPrice = shopAuxiliaryMaterialsDB.getSlottingSlottingPrice();
            debuggingPrice = shopAuxiliaryMaterialsDB.getDebuggingPrice();
            materialsSlottingPrice = shopAuxiliaryMaterialsDB.getMaterialsSlottingPrice();

            item = ShopCarUtil.getSelectionSlotLength();
            debugging = ShopCarUtil.getSelectDebugging();
            materialsListDB = ShopCarUtil.getAuxiliaryMaterials();
        }

        tvSlottingMoney = qmuiDialog.findViewById(R.id.tvSlottingMoney);
        qmuiDialog.findViewById(R.id.ivChoosingGoodsClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qmuiDialog.dismiss();
            }
        });


        qmuiDialog.findViewById(R.id.btnSlotting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item != null && /*debugging != null &&*/ materialsListDB != null) {
                    ShopCarUtil.updateSelectionSlotLength(item);
                    if (debugging != null){
                        ShopCarUtil.updateSelectDebugging(debugging.getId(), debugging.getPrice());
                    }
                    ShopCarUtil.updateAuxiliaryMaterials(materialsListDB);
                    qmuiDialog.dismiss();
                    baseCallback.ok();
                }
            }
        });
        calculationPrice();
        return this;
    }

    public void showDialog() {
        if (qmuiDialog != null) {
            qmuiDialog.show();
            Window window = qmuiDialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.getDecorView().setPadding(DensityUtil.dp2px(32), 0, DensityUtil.dp2px(32), 0);
            }
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (selectionSlotLengthAdapter != null && adapter == selectionSlotLengthAdapter) {
            //开槽长度
            item = (SlottingListDB) view.findViewById(R.id.tvItemMessage).getTag();
            selectionSlotLengthAdapter.checkItem(item.getSlottingId());
            baseCallback.calculationPrice();
        } else if (selectDebuggingAdapter != null && adapter == selectDebuggingAdapter) {
            //选择调试
            debugging = (Debugging) view.findViewById(R.id.tvItemMessage).getTag();
            selectDebuggingAdapter.checkItem(debugging.getId());
            baseCallback.calculationPrice();
        } else if (auxiliaryMaterialsAdapter != null && adapter == auxiliaryMaterialsAdapter) {
            //选择辅材
            materialsListDB = (MaterialsListDB) view.findViewById(R.id.tvItemMessage).getTag();
            auxiliaryMaterialsAdapter.checkItem(materialsListDB.getSlottingId());
            baseCallback.calculationPrice();
        }
        calculationPrice();
    }

    private void calculationPrice() {
        //计算价格
        if (item != null) {
            slottingSlottingPrice = item.getSlottingPrice();
        }
        if (debugging != null) {
            debuggingPrice = debugging.getPrice();
        }
        if (materialsListDB != null) {
            materialsSlottingPrice = materialsListDB.getSlottingPrice();
        }
        tvSlottingMoney.setText(mContext.getString(R.string.content_money, String.valueOf(slottingSlottingPrice + materialsSlottingPrice + debuggingPrice)));
    }

    public static List<Debugging> getDebuggingList(Context mContext, double debugPrice) {
        List<Debugging> debuggingList = new ArrayList<>();
        Debugging debugging = new Debugging();
        debugging.setId(1);
        debugging.setMessage(mContext.getString(R.string.no_debugging_required));
        debugging.setPrice(0d);
        debuggingList.add(debugging);
        Debugging debugging1 = new Debugging();
        debugging1.setId(2);
        debugging1.setMessage(mContext.getString(R.string.need_debugging));
        debugging1.setPrice(debugPrice);
        debuggingList.add(debugging1);
        return debuggingList;
    }

    public SlottingAndDebugDialog isSelectDebugging(int visibility){
        tvSelectDebugging.setVisibility(visibility);
        rvSelectDebugging.setVisibility(visibility);
        return this;
    }

    public interface BaseCallback {
        /**
         * 确定
         */
        void ok();

        /**
         * 计算总价
         */
        void calculationPrice();
    }

    public SlottingAndDebugDialog setICallBase(BaseCallback baseCallback) {
        this.baseCallback = baseCallback;
        return this;
    }

}
