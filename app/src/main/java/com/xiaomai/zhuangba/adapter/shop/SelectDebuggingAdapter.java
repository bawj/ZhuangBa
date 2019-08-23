package com.xiaomai.zhuangba.adapter.shop;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.Debugging;
import com.xiaomai.zhuangba.data.bean.db.ShopAuxiliaryMaterialsDB;
import com.xiaomai.zhuangba.data.db.DBHelper;

/**
 * @author Administrator
 * @date 2019/8/20 0020
 * <p>
 * 是否需要调试
 */
public class SelectDebuggingAdapter extends BaseQuickAdapter<Debugging, BaseViewHolder> {

    private Integer id = -1;
    ///private ShopAuxiliaryMaterialsDB shopAuxiliaryMaterialsDB;

    public SelectDebuggingAdapter() {
        super(R.layout.item_selection_slotlength, null);
        //shopAuxiliaryMaterialsDB = DBHelper.getInstance().getShopAuxiliaryMaterialsDBDao().queryBuilder().unique();
    }

    @Override
    protected void convert(BaseViewHolder helper, Debugging item) {
        TextView tvItemMessage = helper.getView(R.id.tvItemMessage);
        tvItemMessage.setText(item.getMessage());

        ///boolean isCheck = shopAuxiliaryMaterialsDB != null && shopAuxiliaryMaterialsDB.getDebuggingPrice() == item.getPrice();
        //|| isCheck

        if (String.valueOf(item.getId()).equals(String.valueOf(id)) ) {
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
