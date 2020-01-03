package com.xiaomai.zhuangba.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;

/**
 * @author Bawj
 * CreateDate:     2020/1/3 0003 14:25
 */
public class CompensateDialogAdapter extends BaseQuickAdapter<DeviceSurfaceInformation, BaseViewHolder> {

    private int position;

    public CompensateDialogAdapter() {
        super(R.layout.item_compensate_dialog, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceSurfaceInformation item) {
        TextView tvEmployerReal = helper.getView(R.id.tvEmployerReal);
        tvEmployerReal.setText(item.getDeviceSurface());

        int adapterPosition = helper.getAdapterPosition();
        if (position == adapterPosition){
            tvEmployerReal.setBackground(mContext.getResources().getDrawable(R.drawable.bg_red));
            tvEmployerReal.setTextColor(mContext.getResources().getColor(R.color.tool_lib_color_E74C3C));
        }else {
            tvEmployerReal.setBackground(mContext.getResources().getDrawable(R.drawable.bg_gray));
            tvEmployerReal.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_777777));
        }
        tvEmployerReal.setTag(item);
    }

    public void isCheck(int position){
        this.position = position;
    }

    public int getPosition(){
        return position;
    }
}
