package com.xiaomai.zhuangba.fragment.masterworker.map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.view.animation.Animation;

import com.amap.api.maps.AMap;
import com.amap.api.maps.TextureMapView;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.LatAndLon;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 小区分布
 * 显示点位
 */
public class PlotDistributionFragment extends BaseFragment {

    private static final String TITLE = "title";
    private static final String LAT_AND_LON = "lat_and_lon";

    @BindView(R.id.mapView)
    TextureMapView mapView;

    private AMap aMap;
    private Bundle savedInstanceState;

    public static PlotDistributionFragment newInstance(String title,List<LatAndLon> latAndLonList) {
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putParcelableArrayList(LAT_AND_LON, (ArrayList<? extends Parcelable>) latAndLonList);
        PlotDistributionFragment fragment = new PlotDistributionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    @Override
    public void initView() {
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        MapUtil.setUiSetting(aMap);
        //定位服务是否开启
        if (getActivity() != null){
            boolean locServiceEnable = MapUtil.isLocServiceEnable(getActivity());
            if (!locServiceEnable) {
                showLocServiceDialog();
            }
        }
    }

    @Override
    protected void onEnterAnimationEnd(@Nullable Animation animation) {
        super.onEnterAnimationEnd(animation);
        MapUtil.location(aMap, getActivity());
        List<LatAndLon> latAndLon = getLatAndLon();
        for (LatAndLon andLon : latAndLon) {
            if (andLon != null){
                aMap.addMarker(MapUtil.getMarkerOption(andLon.getLat(), andLon.getLon()));
            }
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_plot_distribution;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    protected String getActivityTitle() {
        return getTitle();
    }

    private String getTitle() {
        if (getArguments() != null) {
            return getArguments().getString(TITLE);
        }
        return "";
    }

    /**
     * 显示定位服务未开启确认对话框
     */
    private void showLocServiceDialog() {
        CommonlyDialog.getInstance()
                .initView(getActivity())
                .setTvDialogCommonlyContent(getString(R.string.mobile_location_service_not_opened))
                .setTvDialogCommonlyOk(getString(R.string.go_set_transaction))
                .setTvDialogCommonlyClose(getString(R.string.close))
                .setICallBase(new CommonlyDialog.BaseCallback() {
                    @Override
                    public void sure() {
                        try {
                            final Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).showDialog();
    }


    private List<LatAndLon> getLatAndLon(){
        if (getArguments() != null){
            return getArguments().getParcelableArrayList(LAT_AND_LON);
        }
        return new ArrayList<>();
    }

}
