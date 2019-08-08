package com.xiaomai.zhuangba.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.toollib.util.DensityUtils;
import com.example.toollib.util.Log;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.ShopCarData;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.data.module.IOnAddDelListeners;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.ShopCarUtil;
import com.xiaomai.zhuangba.weight.AnimShopsButton;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/8/7 0007
 */
public class ShopCarAdapter extends BaseAdapter {

    private Context mContext;
    private List<ShopCarData> shopCarDataList;

    public ShopCarAdapter(Context mContext, List<ShopCarData> shopCarDataList) {
        this.mContext = mContext;
        this.shopCarDataList = shopCarDataList;
    }

    @Override
    public int getCount() {
        return shopCarDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return shopCarDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop_car, null);
            viewHolder = new ViewHolder();
            viewHolder.tvShopCarName = convertView.findViewById(R.id.tvShopCarName);
            viewHolder.tvShopMoney = convertView.findViewById(R.id.tvShopMoney);
            viewHolder.tvShopCarTallMoney = convertView.findViewById(R.id.tvShopCarTallMoney);
            viewHolder.animShopCar = convertView.findViewById(R.id.animShopCar);
            viewHolder.tvShopCarMaintenance = convertView.findViewById(R.id.tvShopCarMaintenance);
            viewHolder.ivShopCarRight = convertView.findViewById(R.id.ivShopCarRight);
            viewHolder.layShopCarMaintenance = convertView.findViewById(R.id.layShopCarMaintenance);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ShopCarData shopCarData = shopCarDataList.get(position);
        //服务项目 名称
        viewHolder.tvShopCarName.setText(shopCarData.getText());
        //服务项目 单价
        viewHolder.tvShopMoney.setText(mContext.getString(R.string.shop_car_money, shopCarData.getMoney()));
        //服务项目 总价
        Double totalMoneys = ShopCarUtil.getTotalMoneys(DensityUtils.stringTypeInteger(shopCarData.getNumber()),
                DensityUtils.stringTypeDouble(shopCarData.getMoney()), DensityUtils.stringTypeDouble(shopCarData.getMoney2()),
                DensityUtils.stringTypeDouble(shopCarData.getMoney3()), DensityUtils.stringTypeDouble(shopCarData.getMaintenanceMoney()));
        viewHolder.tvShopCarTallMoney.setText(mContext.getString(R.string.content_money, String.valueOf(totalMoneys)));
        //服务项目 数量
        viewHolder.animShopCar.setIsStatus(DensityUtils.stringTypeInteger(shopCarData.getNumber()));

        Log.e("是否 有维保 " + shopCarData.getMaintenanceId());
        //维保服务 价格 和 数量  维保服务 箭头
        if (shopCarData.getMaintenanceId() == ConstantUtil.DEF_MAINTENANCE) {
            // 没有选择维保
            viewHolder.tvShopCarMaintenance.setText(mContext.getString(R.string.no_maintenance));
            viewHolder.tvShopCarMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_222222));
            viewHolder.ivShopCarRight.setBackgroundResource(R.drawable.ic_right_back);
        } else {
            //有选择维保
            viewHolder.tvShopCarMaintenance.setText(mContext.getString(R.string.shop_car_maintenance
                    , shopCarData.getMaintenanceTime(), shopCarData.getMaintenanceMoney()));
            viewHolder.tvShopCarMaintenance.setTextColor(mContext.getResources().getColor(R.color.tool_lib_gray_B1B1B1));
            viewHolder.ivShopCarRight.setBackgroundResource(R.drawable.ic_right_gray);
        }
        viewHolder.animShopCar.setOnAddDelListener(new IOnAddDelListeners() {
            @Override
            public void onAddSuccess(int count) {
                if (iShopCarAdapterCallBack != null) {
                    iShopCarAdapterCallBack.updateShopCarFragment(shopCarData, count, position);
                }
            }

            @Override
            public void onDelSuccess(int count) {
                if (iShopCarAdapterCallBack != null) {
                    iShopCarAdapterCallBack.updateShopCarFragment(shopCarData, count, position);
                }
            }
        });
        viewHolder.layShopCarMaintenance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iShopCarAdapterCallBack != null) {
                    iShopCarAdapterCallBack.showMaintenanceDialog(shopCarData);
                }
            }
        });
        return convertView;
    }

    public void setNewData() {
        this.shopCarDataList = DBHelper.getInstance().getShopCarDataDao().queryBuilder().list();
        notifyDataSetChanged();
    }

    class ViewHolder {
        public TextView tvShopCarName;
        public TextView tvShopMoney;
        public TextView tvShopCarTallMoney;
        public AnimShopsButton animShopCar;
        public TextView tvShopCarMaintenance;
        public ImageView ivShopCarRight;
        public LinearLayout layShopCarMaintenance;
    }


    private IShopCarAdapterCallBack iShopCarAdapterCallBack;


    public interface IShopCarAdapterCallBack {
        /**
         * 项目数量 增加或者 减少
         *
         * @param shopCarData 商品
         * @param count       数量
         * @param position    下标
         */
        void updateShopCarFragment(ShopCarData shopCarData, int count , int position);

        /**
         * 弹出 选择维保dialog
         *
         * @param shopCarData 商品
         */
        void showMaintenanceDialog(ShopCarData shopCarData);
    }

    public void setIShopCarAdapterCallBack(IShopCarAdapterCallBack iShopCarAdapterCallBack) {
        this.iShopCarAdapterCallBack = iShopCarAdapterCallBack;
    }
}
