package com.xiaomai.zhuangba.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.LocationSearch;

import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public class LocationSearchAdapter extends ArrayAdapter {

    private Activity mContext;
    private int mResourceId;
    private List<LocationSearch> locationSearches;

    public LocationSearchAdapter(Activity context, int resId, List<LocationSearch> locationSearches){
        super(context, resId, locationSearches);
        mContext = context;
        mResourceId = resId;
        this.locationSearches = locationSearches;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mContext.getLayoutInflater();
        View itemView = inflater.inflate(mResourceId, null);

        TextView tvItemInputAddress = itemView.findViewById(R.id.tvItemInputAddress);
        TextView tvItemInputsDistance = itemView.findViewById(R.id.tvItemInputsDistance);
        TextView tvItemInputsName = itemView.findViewById(R.id.tvItemInputsName);

        final LocationSearch locationSearch = locationSearches.get(position);
        tvItemInputAddress.setText(locationSearch.getAddress());
        tvItemInputsDistance.setText(locationSearch.getDistance());
        tvItemInputsName.setText(locationSearch.getName());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null){
                    itemClick.onItemClick(locationSearch.getAddress() , locationSearch.getLongitude() , locationSearch.getLatitude());
                }
            }
        });
        return itemView;
    }

    private OnItemClick itemClick;
    public void setOnItemClick(OnItemClick itemClick){
        this.itemClick = itemClick;
    }

    public interface OnItemClick{
        /**
         * item click
         * @param address 地址
         * @param longitude 经度
         * @param latitude 纬度
         */
        void onItemClick(String address , Double longitude , Double latitude);
    }
}
