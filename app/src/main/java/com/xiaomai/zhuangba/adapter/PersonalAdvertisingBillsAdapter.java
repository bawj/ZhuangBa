package com.xiaomai.zhuangba.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.AdvertisingBillsBean;

/**
 * @author Administrator
 * @date 2019/10/17 0017
 */
public class PersonalAdvertisingBillsAdapter extends BaseQuickAdapter<AdvertisingBillsBean, BaseViewHolder> {

    public PersonalAdvertisingBillsAdapter() {
        super(R.layout.item_personal_advertising_bills, null);
    }

    @Override
    protected void convert(BaseViewHolder helper, final AdvertisingBillsBean item) {
        //小区或街道名称
        TextView tvItemOrdersTitle = helper.getView(R.id.tvItemOrdersTitle);
        String villageName = item.getVillageName();
        if (!TextUtils.isEmpty(villageName)) {
            tvItemOrdersTitle.setText(villageName);
        } else {
            String street = item.getStreet();
            tvItemOrdersTitle.setText(street);
        }
        //任务数量
        TextView tvAdvertisingNumber = helper.getView(R.id.tvAdvertisingNumber);
        int num = item.getNum();
        tvAdvertisingNumber.setText(mContext.getString(R.string.task_number_, String.valueOf(num)));
        //详细地址
        TextView tvItemAdvertisingAddress = helper.getView(R.id.tvItemAdvertisingAddress);
        //省
        String province = item.getProvince();
        //市
        String city = item.getCity();
        //区
        String district = item.getDistrict();
        //街道
        String street = item.getStreet();
        StringBuffer stringBuffer = new StringBuffer(province);
        if (!province.equals(city)) {
            stringBuffer.append(city);
        }
        stringBuffer.append(district).append(street).append(villageName);
        tvItemAdvertisingAddress.setText(mContext.getString(R.string.advertising_address , stringBuffer));

        tvItemOrdersTitle.setTag(item);


        Button btnDelete = helper.getView(R.id.btnDelete);
        final int adapterPosition = helper.getAdapterPosition();
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    mOnSwipeListener.onDel(item , adapterPosition);
                }
            }
        });
        helper.getView(R.id.relPersonalAdvertisingBills).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnSwipeListener) {
                    mOnSwipeListener.onItemClick(item);
                }
            }
        });
    }

    private IOnSwipeListener mOnSwipeListener;

    public IOnSwipeListener getOnDelListener() {
        return mOnSwipeListener;
    }

    public void setOnDelListener(IOnSwipeListener mOnDelListener) {
        this.mOnSwipeListener = mOnDelListener;
    }

    public interface IOnSwipeListener {
        /**
         * 删除
         *
         * @param advertisingBillsBean 广告单
         * @param pos   pos
         */
        void onDel(AdvertisingBillsBean advertisingBillsBean, int pos);


        void onItemClick(AdvertisingBillsBean advertisingBillsBean);
    }
}
