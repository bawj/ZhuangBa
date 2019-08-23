package com.xiaomai.zhuangba.adapter.shop;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.db.MaterialsListDB;
import com.xiaomai.zhuangba.data.bean.db.ShopAuxiliaryMaterialsDB;
import com.xiaomai.zhuangba.data.db.DBHelper;

/**
 * @author Administrator
 * @date 2019/8/20 0020
 * 选择辅材
 */
public class AuxiliaryMaterialsAdapter extends BaseQuickAdapter<MaterialsListDB, BaseViewHolder> {

    private Integer id = -1;

    ///private ShopAuxiliaryMaterialsDB shopAuxiliaryMaterialsDB;

    public AuxiliaryMaterialsAdapter() {
        super(R.layout.item_selection_slotlength, null);
        //shopAuxiliaryMaterialsDB = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao().queryBuilder().unique();
    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialsListDB item) {
        TextView tvItemMessage = helper.getView(R.id.tvItemMessage);
        tvItemMessage.setText(mContext.getString(R.string.slotting_start_end_length, item.getStartLength(), item.getEndLength()));

        ///boolean isCheck = shopAuxiliaryMaterialsDB != null && shopAuxiliaryMaterialsDB.getMaterialsSlottingId() == item.getSlottingId();
        // || isCheck

        if (String.valueOf(item.getSlottingId()).equals(String.valueOf(id))) {
            //选中
            tvItemMessage.setBackgroundResource(R.drawable.red_scope_of_ervice_bg);
            tvItemMessage.setTextColor(mContext.getResources().getColor(R.color.tool_lib_red_EF2B2B));
        } else {
            //未选中
            tvItemMessage.setBackgroundResource(R.drawable.gray_scope_of_ervice_bg);
            tvItemMessage.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_777777));
        }
        tvItemMessage.setTag(item);
    }

    public void checkItem(Integer id) {
        this.id = id;
        notifyDataSetChanged();
    }
}
