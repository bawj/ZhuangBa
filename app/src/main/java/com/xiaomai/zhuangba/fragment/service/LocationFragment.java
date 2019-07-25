package com.xiaomai.zhuangba.fragment.service;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.TextureMapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.LocationAdapter;
import com.xiaomai.zhuangba.adapter.LocationSearchAdapter;
import com.xiaomai.zhuangba.data.LocationSearch;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.MapUtils;
import com.xiaomai.zhuangba.util.Util;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.db.DBManager;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/7/11 0011
 */
public class LocationFragment extends BaseFragment implements TextWatcher, AMap.OnCameraChangeListener {

    @BindView(R.id.mapView)
    TextureMapView mapView;
    @BindView(R.id.tvLocation)
    TextView tvLocation;
    @BindView(R.id.tvLocationTip)
    TextView tvLocationTip;
    @BindView(R.id.ivLocationBottomArrow)
    ImageView ivLocationBottomArrow;
    @BindView(R.id.recyclerLocation)
    RecyclerView recyclerLocation;
    @BindView(R.id.autoCompleteSearch)
    AutoCompleteTextView autoCompleteSearch;

    private int page = 1;
    private AMap aMap;
    private CameraPosition cameraPosition;
    private LocationAdapter locationAdapter;
    private Bundle savedInstanceState;

    public static LocationFragment newInstance() {
        Bundle args = new Bundle();
        LocationFragment fragment = new LocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
    }

    @Override
    protected void onEnterAnimationEnd(@Nullable Animation animation) {
        super.onEnterAnimationEnd(animation);
        MapUtils.location(aMap, getActivity(),tvLocation);
        aMap.setOnCameraChangeListener(this);
        if (getActivity() != null) {
            MapUtils.setMarker(getActivity(), aMap);
        }
        autoCompleteSearch.addTextChangedListener(this);
        recyclerLocation.setLayoutManager(new LinearLayoutManager(getActivity()));
        locationAdapter = new LocationAdapter();
        locationAdapter.openLoadAnimation();
        locationAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        locationAdapter.isFirstOnly(true);
        locationAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LocationSearch locationSearch = (LocationSearch) view.findViewById(R.id.tvItemInputAddress).getTag();
                fragmentResultPop(locationSearch.getAddress() , locationSearch.getLongitude() , locationSearch.getLatitude());
            }
        });
        //上拉加载
        locationAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                onCameraChangeFinish(cameraPosition);
            }
        }, recyclerLocation);
        recyclerLocation.setAdapter(locationAdapter);
    }

    @Override
    public void initView() {
        mapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        MapUtils.setUiSetting(aMap);
        //定位服务是否开启
        if (getActivity() != null){
            boolean locServiceEnable = MapUtils.isLocServiceEnable(getActivity());
            if (!locServiceEnable) {
                showLocServiceDialog();
            }
        }
    }

    @OnClick({R.id.tvLocation, R.id.ivLocationBottomArrow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvLocation:
                //城市选择
                cityCheck();
                break;
            case R.id.ivLocationBottomArrow:
                //城市选择
                cityCheck();
                break;
            default:
        }
    }

    private void cityCheck() {
        if (getActivity() != null) {
            DBManager dbManager = new DBManager(getActivity());
            List<HotCity> hotCities = MapUtils.getHotCity(dbManager);
            City cityLocation = dbManager.queryCityName(getCity());
            CityPicker from = CityPicker.from(getActivity());
            from.enableAnimation(true);
            from.setAnimationStyle(R.style.DefaultCityPickerAnimation);
            if (cityLocation != null) {
                from.setLocatedCity(new LocatedCity(cityLocation.getName(), cityLocation.getProvince(), cityLocation.getCode()));
            }
            from.setHotCities(hotCities)
                    .setOnPickListener(new OnPickListener() {
                        @Override
                        public void onPick(int position, City data) {
                            tvLocation.setText(data.getName());
                        }

                        @Override
                        public void onCancel() {
                        }

                        @Override
                        public void onLocate() {
                        }
                    }).show();
        }
    }

    public String getCity() {
        return tvLocation.getText().toString();
    }

    public String getSearchText() {
        return autoCompleteSearch.getText().toString();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        InputtipsQuery inputQuery = new InputtipsQuery(getSearchText(), getCity());
        Inputtips inputTips = new Inputtips(getActivity(), inputQuery);
        inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
            @Override
            public void onGetInputtips(List<Tip> list, int rCode) {
                // 正确返回
                if (rCode == AMapException.CODE_AMAP_SUCCESS) {
                    searchSuccess(list);
                }
            }
        });
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        page = 1;
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        this.cameraPosition = cameraPosition;
        PoiSearch.Query query = new PoiSearch.Query("", "");
        query.setPageNum(page);
        query.setPageSize(StaticExplain.PAGE_NUM.getCode());
        PoiSearch search = new PoiSearch(getActivity(), query);
        search.setBound(new PoiSearch.SearchBound(
                new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude), 1000));
        search.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                List<LocationSearch> searchList = new ArrayList<>();
                ArrayList<PoiItem> poiItems = poiResult.getPois();
                for (PoiItem poi : poiItems) {
                    String name = poi.toString();
                    String address = poi.getProvinceName() + poi.getCityName() + poi.getAdName() + poi.getSnippet();
                    LocationSearch locationSearch = new LocationSearch(name, address, "", poi.getLatLonPoint().getLongitude(), poi.getLatLonPoint().getLatitude());
                    searchList.add(locationSearch);
                }
                searchPoiSuccess(searchList);
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {
            }
        });
        search.searchPOIAsyn();
    }

    public void searchSuccess(List<Tip> tipList) {
        List<LocationSearch> locationSearches = MapUtils.getListLocationSearch(aMap, tipList);
        if (getActivity() != null) {
            autoCompleteSearch.setDropDownWidth(Util.getWidthPixels(getActivity()));
            LocationSearchAdapter locationSearchAdapter = new LocationSearchAdapter(getActivity(), R.layout.item_inputs, locationSearches);
            autoCompleteSearch.setAdapter(locationSearchAdapter);
            locationSearchAdapter.notifyDataSetChanged();
            locationSearchAdapter.setOnItemClick(new LocationSearchAdapter.OnItemClick() {
                @Override
                public void onItemClick(String address, Double longitude, Double latitude) {
                    fragmentResultPop(address, longitude, latitude);
                }
            });
        }
    }

    public void searchPoiSuccess(List<LocationSearch> locationSearches) {
        if (locationSearches.isEmpty()) {
            recyclerLocation.setVisibility(View.GONE);
            tvLocationTip.setVisibility(View.VISIBLE);
        } else {
            recyclerLocation.setVisibility(View.VISIBLE);
            tvLocationTip.setVisibility(View.GONE);
        }
        if (page == 1) {
            locationAdapter.setNewData(locationSearches);
        } else {
            locationAdapter.addData(locationSearches);
        }
        if (locationSearches.size() < StaticExplain.PAGE_NUM.getCode()) {
            //第一页如果不够一页就不显示没有更多数据布局
            locationAdapter.loadMoreEnd(false);
        } else {
            locationAdapter.loadMoreComplete();
        }
    }

    private void fragmentResultPop(String address, Double longitude, Double latitude) {
        Intent intent = new Intent();
        intent.putExtra(ForResultCode.RESULT_KEY.getExplain(), address);
        intent.putExtra(ForResultCode.LONGITUDE.getExplain(), longitude);
        intent.putExtra(ForResultCode.LATITUDE.getExplain(), latitude);
        setFragmentResult(ForResultCode.RESULT_OK.getCode(), intent);
        popBackStack();
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

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_location;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.location_title);
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        savedInstanceState = null;
        MapUtils.stopLocation();
        super.onDestroy();
    }
}
