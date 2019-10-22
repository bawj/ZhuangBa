package com.xiaomai.zhuangba.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.Projection;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Tip;
import com.example.toollib.util.AmountUtil;
import com.example.toollib.util.ToastUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.LocationSearch;
import com.xiaomai.zhuangba.enums.StringTypeExplain;
import com.zaaach.citypicker.db.DBManager;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public class MapUtils {

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
        //连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        return myLocationStyle;
    }

    public static void showError(Context context, int rCode) {
        try {
            switch (rCode) {
                //服务错误码
                case 1001:
                    throw new AMapException(AMapException.AMAP_SIGNATURE_ERROR);
                case 1002:
                    throw new AMapException(AMapException.AMAP_INVALID_USER_KEY);
                case 1003:
                    throw new AMapException(AMapException.AMAP_SERVICE_NOT_AVAILBALE);
                case 1004:
                    throw new AMapException(AMapException.AMAP_DAILY_QUERY_OVER_LIMIT);
                case 1005:
                    throw new AMapException(AMapException.AMAP_ACCESS_TOO_FREQUENT);
                case 1006:
                    throw new AMapException(AMapException.AMAP_INVALID_USER_IP);
                case 1007:
                    throw new AMapException(AMapException.AMAP_INVALID_USER_DOMAIN);
                case 1008:
                    throw new AMapException(AMapException.AMAP_INVALID_USER_SCODE);
                case 1009:
                    throw new AMapException(AMapException.AMAP_USERKEY_PLAT_NOMATCH);
                case 1010:
                    throw new AMapException(AMapException.AMAP_IP_QUERY_OVER_LIMIT);
                case 1011:
                    throw new AMapException(AMapException.AMAP_NOT_SUPPORT_HTTPS);
                case 1012:
                    throw new AMapException(AMapException.AMAP_INSUFFICIENT_PRIVILEGES);
                case 1013:
                    throw new AMapException(AMapException.AMAP_USER_KEY_RECYCLED);
                case 1100:
                    throw new AMapException(AMapException.AMAP_ENGINE_RESPONSE_ERROR);
                case 1101:
                    throw new AMapException(AMapException.AMAP_ENGINE_RESPONSE_DATA_ERROR);
                case 1102:
                    throw new AMapException(AMapException.AMAP_ENGINE_CONNECT_TIMEOUT);
                case 1103:
                    throw new AMapException(AMapException.AMAP_ENGINE_RETURN_TIMEOUT);
                case 1200:
                    throw new AMapException(AMapException.AMAP_SERVICE_INVALID_PARAMS);
                case 1201:
                    throw new AMapException(AMapException.AMAP_SERVICE_MISSING_REQUIRED_PARAMS);
                case 1202:
                    throw new AMapException(AMapException.AMAP_SERVICE_ILLEGAL_REQUEST);
                case 1203:
                    throw new AMapException(AMapException.AMAP_SERVICE_UNKNOWN_ERROR);
                    //sdk返回错误
                case 1800:
                    throw new AMapException(AMapException.AMAP_CLIENT_ERRORCODE_MISSSING);
                case 1801:
                    throw new AMapException(AMapException.AMAP_CLIENT_ERROR_PROTOCOL);
                case 1802:
                    throw new AMapException(AMapException.AMAP_CLIENT_SOCKET_TIMEOUT_EXCEPTION);
                case 1803:
                    throw new AMapException(AMapException.AMAP_CLIENT_URL_EXCEPTION);
                case 1804:
                    throw new AMapException(AMapException.AMAP_CLIENT_UNKNOWHOST_EXCEPTION);
                case 1806:
                    throw new AMapException(AMapException.AMAP_CLIENT_NETWORK_EXCEPTION);
                case 1900:
                    throw new AMapException(AMapException.AMAP_CLIENT_UNKNOWN_ERROR);
                case 1901:
                    throw new AMapException(AMapException.AMAP_CLIENT_INVALID_PARAMETER);
                case 1902:
                    throw new AMapException(AMapException.AMAP_CLIENT_IO_EXCEPTION);
                case 1903:
                    throw new AMapException(AMapException.AMAP_CLIENT_NULLPOINT_EXCEPTION);
                    //云图和附近错误码
                case 2000:
                    throw new AMapException(AMapException.AMAP_SERVICE_TABLEID_NOT_EXIST);
                case 2001:
                    throw new AMapException(AMapException.AMAP_ID_NOT_EXIST);
                case 2002:
                    throw new AMapException(AMapException.AMAP_SERVICE_MAINTENANCE);
                case 2003:
                    throw new AMapException(AMapException.AMAP_ENGINE_TABLEID_NOT_EXIST);
                case 2100:
                    throw new AMapException(AMapException.AMAP_NEARBY_INVALID_USERID);
                case 2101:
                    throw new AMapException(AMapException.AMAP_NEARBY_KEY_NOT_BIND);
                case 2200:
                    throw new AMapException(AMapException.AMAP_CLIENT_UPLOADAUTO_STARTED_ERROR);
                case 2201:
                    throw new AMapException(AMapException.AMAP_CLIENT_USERID_ILLEGAL);
                case 2202:
                    throw new AMapException(AMapException.AMAP_CLIENT_NEARBY_NULL_RESULT);
                case 2203:
                    throw new AMapException(AMapException.AMAP_CLIENT_UPLOAD_TOO_FREQUENT);
                case 2204:
                    throw new AMapException(AMapException.AMAP_CLIENT_UPLOAD_LOCATION_ERROR);
                    //路径规划
                case 3000:
                    throw new AMapException(AMapException.AMAP_ROUTE_OUT_OF_SERVICE);
                case 3001:
                    throw new AMapException(AMapException.AMAP_ROUTE_NO_ROADS_NEARBY);
                case 3002:
                    throw new AMapException(AMapException.AMAP_ROUTE_FAIL);
                case 3003:
                    throw new AMapException(AMapException.AMAP_OVER_DIRECTION_RANGE);
                    //短传分享
                case 4000:
                    throw new AMapException(AMapException.AMAP_SHARE_LICENSE_IS_EXPIRED);
                case 4001:
                    throw new AMapException(AMapException.AMAP_SHARE_FAILURE);
                default:
                    ToastUtil.showShort("查询失败：" + rCode);
                    break;
            }
        } catch (Exception e) {
            ToastUtil.showShort(e.getMessage());
        }
    }

    /**
     * 计算距离
     *
     * @param mStartPoint 起点
     * @param point       终点
     * @return 距离
     */
    private static String calculatedDistance(LatLonPoint mStartPoint, LatLonPoint point) {
        float v = AMapUtils.calculateLineDistance(new LatLng(mStartPoint.getLatitude(), mStartPoint.getLongitude()),
                new LatLng(point.getLatitude(), point.getLongitude()));
        double div = AmountUtil.div(v, 1000, 0);
        return (int) div + "KM";
    }

    public static List<LocationSearch> getListLocationSearch(AMap aMap, List<Tip> tipList) {
        List<LocationSearch> locationSearches = new ArrayList<>();
        for (int i = 0; i < tipList.size(); i++) {
            Tip tip = tipList.get(i);
            if (!TextUtils.isEmpty(tip.getAdcode())) {
                LocationSearch locationSearch = new LocationSearch();
                locationSearch.setAddress(tip.getAddress());
                locationSearch.setName(tip.getName());
                LatLonPoint point = tip.getPoint();
                Location myLocation = aMap.getMyLocation();
                LatLonPoint mStartPoint = new LatLonPoint(myLocation.getLatitude(), myLocation.getLongitude());
                ///计算距离
                String distance = MapUtils.calculatedDistance(mStartPoint, point);
                locationSearch.setDistance(distance);

                locationSearch.setLatitude(tip.getPoint().getLatitude());
                locationSearch.setLongitude(tip.getPoint().getLongitude());
                locationSearches.add(locationSearch);
            }
        }
        return locationSearches;
    }

    private static String city;
    private static AMapLocationClient mLocationClient;

    public static void location(final AMap aMap, final Context mContext, final TextView tvLocation) {
        mLocationClient = new AMapLocationClient(mContext);
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
                    if (TextUtils.isEmpty(city)) {
                        city = aMapLocation.getCity();
                    }
                    tvLocation.setText(aMapLocation.getCity() == null ? "" : aMapLocation.getCity());
                    mLocationClient.stopLocation();
                }
            }
        });
        mLocationClient.startLocation();
    }


    public static void location(final Context mContext, final TextView tvLocation) {
        mLocationClient = new AMapLocationClient(mContext);
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
                //省
                String provider = aMapLocation.getProvince();
                //市
                String city = aMapLocation.getCity();
                //区
                String district = aMapLocation.getDistrict();
                //街道
                String street = aMapLocation.getStreet();
                //门牌号
                String streetNum = aMapLocation.getStreetNum();
                StringBuilder stringBuilder = new StringBuilder();
                tvLocation.setText(stringBuilder.append(provider).append(city).append(district).append(street).append(streetNum));
            }
        });
        mLocationClient.startLocation();
    }

    public static void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    public static void setMarker(Context mContext, AMap aMap) {
        Marker marker = aMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(
                        mContext.getApplicationContext().getResources(), R.drawable.ic_map_location))));
        LatLng latLng = aMap.getCameraPosition().target;
        Projection projection = aMap.getProjection();
        Point screenPosition = projection.toScreenLocation(latLng);
        marker.setPositionByPixels(screenPosition.x, screenPosition.y);
        marker.showInfoWindow();
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


    public static List<HotCity> getHotCity(DBManager dbManager) {
        List<HotCity> hotCities = new ArrayList<>();
        City beijingCity = dbManager.queryCityName("北京市");
        if (beijingCity != null) {
            hotCities.add(new HotCity(beijingCity.getName(), beijingCity.getProvince(), beijingCity.getCode()));
        }
        City shanghaiCity = dbManager.queryCityName("上海市");
        if (shanghaiCity != null) {
            hotCities.add(new HotCity(shanghaiCity.getName(), shanghaiCity.getProvince(), shanghaiCity.getCode()));
        }
        City guangzhouity = dbManager.queryCityName("广州市");
        if (guangzhouity != null) {
            hotCities.add(new HotCity(guangzhouity.getName(), guangzhouity.getProvince(), guangzhouity.getCode()));
        }

        City shenzhenCity = dbManager.queryCityName("深圳市");
        if (shenzhenCity != null) {
            hotCities.add(new HotCity(shenzhenCity.getName(), shenzhenCity.getProvince(), shenzhenCity.getCode()));
        }

        City chengduCity = dbManager.queryCityName("成都市");
        if (chengduCity != null) {
            hotCities.add(new HotCity(chengduCity.getName(), chengduCity.getProvince(), chengduCity.getCode()));
        }
        City hangzhouCity = dbManager.queryCityName("杭州市");
        if (hangzhouCity != null) {
            hotCities.add(new HotCity(hangzhouCity.getName(), hangzhouCity.getProvince(), hangzhouCity.getCode()));
        }

        City nanjingCity = dbManager.queryCityName("南京市");
        if (nanjingCity != null) {
            hotCities.add(new HotCity(nanjingCity.getName(), nanjingCity.getProvince(), nanjingCity.getCode()));
        }

        City suzhouCity = dbManager.queryCityName("苏州市");
        if (suzhouCity != null) {
            hotCities.add(new HotCity(suzhouCity.getName(), suzhouCity.getProvince(), suzhouCity.getCode()));
        }

        City chongqingCity = dbManager.queryCityName("重庆市");
        if (chongqingCity != null) {
            hotCities.add(new HotCity(chongqingCity.getName(), chongqingCity.getProvince(), chongqingCity.getCode()));
        }

        City tianjinCity = dbManager.queryCityName("天津市");
        if (tianjinCity != null) {
            hotCities.add(new HotCity(tianjinCity.getName(), tianjinCity.getProvince(), tianjinCity.getCode()));
        }

        City wuhanCity = dbManager.queryCityName("武汉市");
        if (wuhanCity != null) {
            hotCities.add(new HotCity(wuhanCity.getName(), wuhanCity.getProvince(), wuhanCity.getCode()));
        }

        City xianCity = dbManager.queryCityName("西安市");
        if (xianCity != null) {
            hotCities.add(new HotCity(xianCity.getName(), xianCity.getProvince(), xianCity.getCode()));
        }
        return hotCities;
    }

    /**
     * 地图导航
     */
    public static void mapNavigation(final Context mContext, final float latitude, final float longitude) {
        new QMUIBottomSheet.BottomListSheetBuilder(mContext)
                .addItem(mContext.getString(R.string.using_bai_du_map_navigation))
                .addItem(mContext.getString(R.string.navigation_using_golden_map))
                .addItem(mContext.getString(R.string.close))
                .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                    @Override
                    public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                        switch (position) {
                            case 0:
                                LatLng latLng = MapUtils.GCJ2BD(new LatLng(latitude, longitude));
                                startBaiDuMap(mContext, latLng.latitude, latLng.longitude);
                                break;
                            case 1:
                                startGolden(mContext, latitude, longitude);
                                break;
                            case 2:
                                dialog.dismiss();
                                break;
                            default:
                        }
                        dialog.dismiss();
                    }
                }).build().show();
    }


    /**
     * 高德地图导航
     *
     * @param latitude  纬度
     * @param longitude 经度
     */
    public static void startGolden(Context mContext, final double latitude, final double longitude) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //将功能Scheme以URI的方式传入data
        Uri uri = Uri.parse("androidamap://navi?sourceApplication=" + mContext.getString(R.string.app_name) + "&" +
                "lat=" + latitude + "&lon=" + longitude + "&dev=0&style=2");
        intent.setData(uri);
        if (Util.isAppInstalled(mContext, StringTypeExplain.A_MAP_PACKAGE_NAME.getCode())) {
            mContext.startActivity(intent);
        } else {
            ToastUtil.showShort(mContext.getString(R.string.not_install_golden_map));
        }
    }

    public static void startBaiDuMap(Context mContext, final double latitude, final double longitude) {
        if (Util.isAppInstalled(mContext, StringTypeExplain.A_MAP_BAI_DU_PACKAGE_NAME.getCode())) {
            Intent intent = new Intent();
            intent.setData(Uri.parse("baidumap://map/navi?location=" + longitude + "," + latitude + "&src= " + mContext.getString(R.string.app_name)));
            mContext.startActivity(intent);
        } else {
            ToastUtil.showShort(mContext.getString(R.string.not_install_bai_du_map));
        }
    }


    /**
     * BD-09 坐标转换成 GCJ-02 坐标
     */
    public static LatLng BD2GCJ(LatLng bd) {
        double x = bd.longitude - 0.0065, y = bd.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * Math.PI);

        double lng = z * Math.cos(theta);
        double lat = z * Math.sin(theta);
        return new LatLng(lat, lng);
    }

    /**
     * 高德地图和腾讯地图需要传入GCJ02坐标系坐标
     * 百度地图用的 BD-09 坐标系坐标
     * GCJ-02 坐标转换成 BD-09 坐标
     */
    public static LatLng GCJ2BD(LatLng bd) {
        double x = bd.longitude, y = bd.latitude;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * Math.PI);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * Math.PI);
        double tempLon = z * Math.cos(theta) + 0.0065;
        double tempLat = z * Math.sin(theta) + 0.006;
        return new LatLng(tempLat, tempLon);
    }
}
