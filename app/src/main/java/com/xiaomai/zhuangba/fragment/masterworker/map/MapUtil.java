package com.xiaomai.zhuangba.fragment.masterworker.map;

import android.content.Context;
import android.graphics.Color;
import android.location.LocationManager;
import android.text.TextUtils;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;

public class MapUtil {

    public static void setUiSetting(AMap aMap) {
        UiSettings mUiSettings = aMap.getUiSettings();
        //去掉缩放按钮
        mUiSettings.setZoomControlsEnabled(false);
        //不显示默认的定位按钮
        mUiSettings.setMyLocationButtonEnabled(false);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        MyLocationStyle myLocationStyle = getMyLocationStyle();
        aMap.setMyLocationStyle(myLocationStyle);
        //设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
        aMap.setMyLocationEnabled(true);
    }

    private static MyLocationStyle getMyLocationStyle() {
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.ic_gps_point));
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(0);
        //设置圆形区域（以定位位置为圆心，定位半径的圆形区域）的边框颜色
        myLocationStyle.strokeColor(Color.TRANSPARENT);
        myLocationStyle.radiusFillColor(Color.TRANSPARENT);
        //定位一次、蓝点会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        return myLocationStyle;
    }

    public static void location(final AMap aMap, final Context mContext) {
        final AMapLocationClient mLocationClient = new AMapLocationClient(mContext);
        AMapLocationClientOption mAMapLocationClientOption = new AMapLocationClientOption();
        // 设置定位模式为高精度模式
        mAMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置发送定位请求的时间间隔,最小值为1000，如果小于1000，按照1000算
        mAMapLocationClientOption.setInterval(2000);
        // 设置定位参数
        mLocationClient.setLocationOption(mAMapLocationClientOption);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (null == aMapLocation) {
                    ToastUtil.showShort(mContext.getString(R.string.location_error));
                } else if (aMapLocation.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_CONNECTION
                        || aMapLocation.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_PARSER) {
                    ToastUtil.showShort(mContext.getString(R.string.location_error_code_failure_connection));
                } else if (aMapLocation.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_LOCATION_PERMISSION
                        || aMapLocation.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_NOWIFIANDAP) {
                    ToastUtil.showShort(mContext.getString(R.string.location_error_code_failure_location_permission));
                } else if (aMapLocation.getErrorCode() == AMapLocation.ERROR_CODE_FAILURE_NOENOUGHSATELLITES) {
                    ToastUtil.showShort(mContext.getString(R.string.location_error_code_failure_no_enough_satellites));
                } else if (aMapLocation.getErrorCode() == AMapLocation.LOCATION_SUCCESS) {
                    double longitude = aMapLocation.getLongitude();
                    double latitude = aMapLocation.getLatitude();
                    aMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                            new LatLng(latitude, longitude),
                            14,
                            0,
                            0
                    )));
                    mLocationClient.stopLocation();
                }
            }
        });
        mLocationClient.startLocation();
    }


    public static MarkerOptions getMarkerOption(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude, longitude);
        return new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_location))
                .position(latLng).draggable(false);
    }

    /**
     * 检测定位服务是否开启
     */
    public static boolean isLocServiceEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (gps || network) {
            return true;
        }
        return false;
    }

}
