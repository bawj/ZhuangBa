package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.util.ToastUtil;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.MultiGraphSelectionAdapter;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.LuBanUtil;
import com.xiaomai.zhuangba.util.MapUtils;
import com.xiaomai.zhuangba.util.QiNiuUtil;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;
import com.xiaomai.zhuangba.weight.PhotoTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.xiaomai.zhuangba.weight.PhotoTool.GET_IMAGE_BY_CAMERA;

/**
 * @author Bawj
 * CreateDate:     2020/3/3 0003 09:53
 * 设备重定位
 */
public class EquipmentRelocationFragment extends BaseFragment implements MultiGraphSelectionAdapter.OnMultiGraphClickListener {

    @BindView(R.id.recyclerLocationUrl)
    RecyclerView recyclerLocationUrl;
    @BindView(R.id.tvLocationAddress)
    TextView tvLocationAddress;

    private Uri imageUriFromCamera;
    private AMapLocation mapLocation;
    public Uri resultUri = null;
    public List<String> mediaSelectorFiles = new ArrayList<>();
    private MultiGraphSelectionAdapter multiGraphSelectionAdapter;

    public static final String EQUIPMENT_ID = "equipment_id";

    public static EquipmentRelocationFragment newInstance(int equipmentId) {
        Bundle args = new Bundle();
        args.putInt(EQUIPMENT_ID, equipmentId);
        EquipmentRelocationFragment fragment = new EquipmentRelocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {
        mediaSelectorFiles.add(null);
        recyclerLocationUrl.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        multiGraphSelectionAdapter = new MultiGraphSelectionAdapter(getActivity(), mediaSelectorFiles);
        recyclerLocationUrl.setAdapter(multiGraphSelectionAdapter);
        multiGraphSelectionAdapter.setOnMultiGraphClickListener(this);
        recyclerLocationUrl.addItemDecoration(new GridSpacingItemDecoration(4, 32, false));

        //定位
        MapUtils.location(getActivity(), new BaseCallback<AMapLocation>() {
            @Override
            public void onSuccess(AMapLocation aMapLocation) {
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
                tvLocationAddress.setText(stringBuilder.append(provider).append(city).append(district).append(street).append(streetNum));
                mapLocation = aMapLocation;
            }
        });
    }

    @Override
    public void addImg() {
        //权限
        RxPermissionsUtils.applyPermission(getActivity(), new BaseCallback<String>() {
            @Override
            public void onSuccess(String obj) {
                //拍照
                imageUriFromCamera = PhotoTool.createImagePathUri(getActivity());
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
                startActivityForResult(intent, GET_IMAGE_BY_CAMERA);
            }

            @Override
            public void onFail(Object obj) {
                showToast(getString(R.string.please_open_permissions));
            }
        }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_IMAGE_BY_CAMERA:
                //拍照之后的处理
                if (resultCode == RESULT_OK && getActivity() != null) {
                    resultUri = Uri.parse("file:///" + PhotoTool.getImageAbsolutePath(getActivity(), imageUriFromCamera));
                    LuBanUtil.getInstance().compress(getActivity(), resultUri, new BaseCallback<Uri>() {
                        @Override
                        public void onSuccess(Uri obj) {
                            mediaSelectorFiles.add(0, obj.toString());
                            multiGraphSelectionAdapter.notifyDataSetChanged();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void rightTitleClick(View v) {
        final List<String> uriList = new ArrayList<>(mediaSelectorFiles);
        uriList.remove(uriList.size() - 1);
        if (uriList.isEmpty()) {
            ToastUtil.showShort(getString(R.string.please_upload_photos_of_point_devices));
        }if (mapLocation == null){
            ToastUtil.showShort(getString(R.string.location_error));
        }else {
            //保存
            RxUtils.getObservable(QiNiuUtil.newInstance().getObservable(uriList))
                    .compose(this.<List<String>>bindToLifecycle())
                    .doOnNext(new Consumer<List<String>>() {
                        @Override
                        public void accept(List<String> strings) throws Exception {
                        }
                    }).concatMap(new Function<List<String>, ObservableSource<HttpResult<Object>>>() {
                @Override
                public ObservableSource<HttpResult<Object>> apply(List<String> imgUrlList) throws Exception {
                    return RxUtils.getObservable(ServiceUrl.getUserApi().postRelocation(getEquipmentId()
                            ,tvLocationAddress.getText().toString(),getPicturesUrl(imgUrlList),mapLocation.getLongitude(),mapLocation.getLatitude()));
                }
            }).subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                @Override
                protected void onSuccess(Object response) {
                    ToastUtil.showShort(getString(R.string.commit_success));
                }
            });
        }
    }

    private String getPicturesUrl(List<String> stringList) {
        if (stringList != null){
            StringBuilder stringBuilder = new StringBuilder();
            for (String s : stringList) {
                stringBuilder.append(s).append(",");
            }
            return stringBuilder.toString();
        }
        return null;
    }
    private int getEquipmentId() {
        if (getArguments() != null) {
            return getArguments().getInt(EQUIPMENT_ID);
        }
        return -1;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_equipment_relocation;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.nearby_pictures);
    }

    @Override
    public String getRightTitle() {
        return getString(R.string.save);
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

}
