package com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.Log;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.BaseAdvertisementPhotoTabAdapter;
import com.xiaomai.zhuangba.data.bean.BaseAdvertisementPhotoTabEntity;
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;
import com.xiaomai.zhuangba.data.bean.StayUrl;
import com.xiaomai.zhuangba.util.LuBanUtil;
import com.xiaomai.zhuangba.util.MapUtils;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.weight.PhotoTool;
import com.xiaomai.zhuangba.weight.dialog.NavigationDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo.BaseAdvertisementPhotoFragment.DEVICE_SURFACE_INFORMATION_LIST_STRING;
import static com.xiaomai.zhuangba.weight.PhotoTool.GET_IMAGE_BY_CAMERA;
import static com.xiaomai.zhuangba.weight.PhotoTool.GET_IMAGE_FROM_PHONE;

public class BaseAdvertisementPhotoTabFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    public static final String DEVICE_SURFACE_INFORMATION = "device_Surface_Information";

    @BindView(R.id.relPhoto)
    RecyclerView relPhoto;
    @BindView(R.id.ivAdvertisementPhoto)
    ImageView ivAdvertisementPhoto;
    @BindView(R.id.tvAdvertisementAddress)
    TextView tvAdvertisementAddress;
    @BindView(R.id.editAdvertisingRemark)
    EditText editAdvertisingRemark;

    private Integer position;
    private AMapLocation mapLocation;
    public Uri resultUri = null;
    private Uri imageUriFromCamera;
    /**
     * 待上传到服务器的图片
     */
    private StayUrl stayUrl = new StayUrl();
    private BaseAdvertisementPhotoTabAdapter baseAdvertisementPhotoTabAdapter;

    public static BaseAdvertisementPhotoTabFragment newInstance(String deviceSurfaceInformationListString, String deviceSurfaceInformationString) {
        Bundle args = new Bundle();
        args.putString(DEVICE_SURFACE_INFORMATION_LIST_STRING, deviceSurfaceInformationListString);
        args.putString(DEVICE_SURFACE_INFORMATION, deviceSurfaceInformationString);
        BaseAdvertisementPhotoTabFragment fragment = new BaseAdvertisementPhotoTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        baseAdvertisementPhotoTabAdapter = new BaseAdvertisementPhotoTabAdapter();
        relPhoto.setLayoutManager(new LinearLayoutManager(getActivity()));
        relPhoto.setAdapter(baseAdvertisementPhotoTabAdapter);
        baseAdvertisementPhotoTabAdapter.setNewData(getList());
        baseAdvertisementPhotoTabAdapter.setOnItemClickListener(this);

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
                tvAdvertisementAddress.setText(stringBuilder.append(provider).append(city).append(district).append(street).append(streetNum));
                mapLocation = aMapLocation;
            }
        });
    }

    @OnClick({R.id.btnCompleteSubmission, R.id.ivAdvertisementShot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivAdvertisementShot:
                //拍摄
                showUploadDialog();
                break;
            case R.id.btnCompleteSubmission:
                //完成提交
                String address = tvAdvertisementAddress.getText().toString();
                String remark = editAdvertisingRemark.getText().toString();
                DeviceSurfaceInformation deviceSurfaceInformation = getDeviceSurfaceInformation();
                String panorama = stayUrl.getPanorama();
                String headerCloseRange = stayUrl.getHeaderCloseRange();
                String headerProspect = stayUrl.getHeaderProspect();
                String other = stayUrl.getOther();
                if (TextUtils.isEmpty(panorama)) {
                    ToastUtil.showShort(getString(R.string.please_take_a_panorama));
                } else if (TextUtils.isEmpty(headerCloseRange)) {
                    ToastUtil.showShort(getString(R.string.please_take_close_up_with_newspaper));
                } else if (TextUtils.isEmpty(headerProspect)) {
                    ToastUtil.showShort(getString(R.string.please_take_foreground_of_header));
                } else if (TextUtils.isEmpty(other)) {
                    ToastUtil.showShort(getString(R.string.please_take_other_pictures));
                } else {
                    completeSubmission(deviceSurfaceInformation, mapLocation, address, remark, stayUrl);
                }
                break;
            default:
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        this.position = position;
        List<BaseAdvertisementPhotoTabEntity> data = baseAdvertisementPhotoTabAdapter.getData();
        baseAdvertisementPhotoTabAdapter.setCheckPosition(position);
        BaseAdvertisementPhotoTabEntity baseAdvertisementPhotoTabEntity = data.get(position);
        String url = baseAdvertisementPhotoTabEntity.getUrl();
        GlideManager.loadImage(getActivity(), url, ivAdvertisementPhoto, R.drawable.ic_notice_img_add);
        ivAdvertisementPhoto.setBackground(getResources().getDrawable(R.drawable.ic_green_frame));
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_advertisement_photo_tab;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    public DeviceSurfaceInformation getDeviceSurfaceInformation() {
        if (getArguments() != null) {
            String string = getArguments().getString(DEVICE_SURFACE_INFORMATION);
            return new Gson().fromJson(string, DeviceSurfaceInformation.class);
        }
        return new DeviceSurfaceInformation();
    }

    public List<DeviceSurfaceInformation> getDeviceSurfaceInformationList() {
        if (getArguments() != null) {
            return new Gson().fromJson(getArguments().getString(DEVICE_SURFACE_INFORMATION_LIST_STRING)
                    , new TypeToken<List<DeviceSurfaceInformation>>() {
                    }.getType());
        }
        return new ArrayList<>();
    }

    public List<BaseAdvertisementPhotoTabEntity> getList() {
        List<BaseAdvertisementPhotoTabEntity> urlList = new ArrayList<>();
        urlList.add(new BaseAdvertisementPhotoTabEntity(null, getString(R.string.panorama)));
        urlList.add(new BaseAdvertisementPhotoTabEntity(null, getString(R.string.with_head_close_up)));
        urlList.add(new BaseAdvertisementPhotoTabEntity(null, getString(R.string.with_head_vision)));
        urlList.add(new BaseAdvertisementPhotoTabEntity(null, getString(R.string.other)));
        return urlList;
    }

    private void showUploadDialog() {
        NavigationDialog.getInstance().setContext(getActivity())
                .setDialogCallBack(new NavigationDialog.IHeadPortraitPopupCallBack() {
                    @Override
                    public void onBaiDuMap() {
                        RxPermissionsUtils.applyPermission(getActivity(), new BaseCallback<String>() {
                            @Override
                            public void onSuccess(String obj) {
                                //相机
                                if (getActivity() != null) {
                                    imageUriFromCamera = PhotoTool.createImagePathUri(getActivity());
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUriFromCamera);
                                    startActivityForResult(intent, GET_IMAGE_BY_CAMERA);
                                }
                            }

                            @Override
                            public void onFail(Object obj) {
                                showToast(getString(R.string.please_open_permissions));
                            }
                        }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }

                    @Override
                    public void onGoldenMap() {
                        //选择图片
                        RxPermissionsUtils.applyPermission(getActivity(), new BaseCallback<String>() {
                            @Override
                            public void onSuccess(String obj) {
                                if (getActivity() != null) {
                                    Intent intent = new Intent();
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(intent, GET_IMAGE_FROM_PHONE);
                                }
                            }

                            @Override
                            public void onFail(Object obj) {
                                showToast(getString(R.string.please_open_permissions));
                            }
                        }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    }
                }).initView()
                .setTvBaiDuMap(getString(R.string.photo))
                .setTvGoldenMap(getString(R.string.check_camera)).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("requestCode = " + requestCode);
        switch (requestCode) {
            case GET_IMAGE_FROM_PHONE:
                //选择相册之后的处理
                if (resultCode == RESULT_OK && getActivity() != null) {
                    String imageAbsolutePath = PhotoTool.getImageAbsolutePath(getActivity(), data.getData());
                    initImage(imageAbsolutePath);
                }
                break;
            case GET_IMAGE_BY_CAMERA:
                //拍照之后的处理
                if (resultCode == RESULT_OK && getActivity() != null) {
                    String imageAbsolutePath = PhotoTool.getImageAbsolutePath(getActivity(), imageUriFromCamera);
                    initImage(imageAbsolutePath);
                }
                break;
            default:
                break;
        }
    }

    private void initImage(String imageAbsolutePath) {
        resultUri = Uri.parse("file:///" + imageAbsolutePath);
        LuBanUtil.getInstance().compress(getActivity(), imageAbsolutePath, new BaseCallback<String>() {
            @Override
            public void onSuccess(String obj) {
                List<BaseAdvertisementPhotoTabEntity> data = baseAdvertisementPhotoTabAdapter.getData();
                BaseAdvertisementPhotoTabEntity baseAdvertisementPhotoTabEntity = data.get(position);
                baseAdvertisementPhotoTabEntity.setUrl(obj);
                saveStayUrl(position, obj);
                GlideManager.loadImage(getActivity(), obj, ivAdvertisementPhoto);
                baseAdvertisementPhotoTabAdapter.notifyDataSetChanged();
            }
        });
    }

    private void saveStayUrl(Integer position, String url) {
        if (position == 0) {
            //全景
            stayUrl.setPanorama(url);
        } else if (position == 1) {
            //带报头 近景
            stayUrl.setHeaderCloseRange(url);
        } else if (position == 2) {
            //带报头 远景
            stayUrl.setHeaderProspect(url);
        } else if (position == 3) {
            //其它
            stayUrl.setOther(url);
        }
    }

    public void completeSubmission(DeviceSurfaceInformation deviceSurfaceInformation, AMapLocation mapLocation, String address, String remark, StayUrl stayUrl) {
        //完成提交
    }
}
