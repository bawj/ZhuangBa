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
import com.xiaomai.zhuangba.data.bean.DeviceSurfaceInformation;
import com.xiaomai.zhuangba.data.bean.ServiceSampleEntity;
import com.xiaomai.zhuangba.util.LuBanUtil;
import com.xiaomai.zhuangba.util.MapUtils;
import com.xiaomai.zhuangba.util.QiNiuUtil;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.weight.PhotoTool;
import com.xiaomai.zhuangba.weight.dialog.NavigationDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xiaomai.zhuangba.application.PretendApplication.IMG_URL;
import static com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.BaseAdvertisingBillDetailFragment.ORDER_CODES;
import static com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo.BaseAdvertisementPhotoFragment.DEVICE_SURFACE_INFORMATION_LIST_STRING;
import static com.xiaomai.zhuangba.fragment.orderdetail.master.advertising.photo.BaseAdvertisementPhotoFragment.SERVICE_SAMPLE;
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

    private Integer position = 0;
    private AMapLocation mapLocation;
    public Uri resultUri = null;
    private Uri imageUriFromCamera;
    private List<ServiceSampleEntity> serviceSample;
    /**
     * 待上传到服务器的图片
     */
    private List<ServiceSampleEntity> waitForServiceSampleEntities = new ArrayList<>();
    private BaseAdvertisementPhotoTabAdapter baseAdvertisementPhotoTabAdapter;

    /**
     * @param serviceSample                      默认样图
     * @param deviceSurfaceInformationListString 集合 所有面的数据
     * @param deviceSurfaceInformationString     单个 当前面的数据
     */
    public static BaseAdvertisementPhotoTabFragment newInstance(String orderCodes, String serviceSample, String deviceSurfaceInformationListString, String deviceSurfaceInformationString) {
        Bundle args = new Bundle();
        args.putString(DEVICE_SURFACE_INFORMATION_LIST_STRING, deviceSurfaceInformationListString);
        args.putString(DEVICE_SURFACE_INFORMATION, deviceSurfaceInformationString);
        args.putString(SERVICE_SAMPLE, serviceSample);
        args.putString(ORDER_CODES, orderCodes);
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
        //样式图  或者 默认图
        serviceSample = getDataInit();
        if (serviceSample != null && !serviceSample.isEmpty()) {
            GlideManager.loadImage(getActivity(), serviceSample.get(1).getPicUrl()
                    , ivAdvertisementPhoto, R.drawable.ic_notice_img_add);
        }
        baseAdvertisementPhotoTabAdapter.setNewData(serviceSample);
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

    @OnClick({R.id.btnPhotoSave, R.id.ivAdvertisementShot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivAdvertisementShot:
                //拍摄
                if (serviceSample != null && serviceSample.size() > 0){
                    showUploadDialog();
                }else {
                    showToast(getString(R.string.tip));
                }
                break;
            case R.id.btnPhotoSave:
                //完成提交
                String address = tvAdvertisementAddress.getText().toString();
                String remark = editAdvertisingRemark.getText().toString();
                //判断是否所有的照片都拍完了
                //模板照片
                for (ServiceSampleEntity waitForServiceSampleEntity : waitForServiceSampleEntities) {
                    String picUrl = waitForServiceSampleEntity.getPicUrl();
                    //判断照片是否存在
                    if (TextUtils.isEmpty(picUrl)) {
                        ToastUtil.showShort(getString(R.string.please_take_tip, waitForServiceSampleEntity.getAdverName()));
                        return;
                    }
                }
                DeviceSurfaceInformation deviceSurfaceInformation = getDeviceSurfaceInformation();
                completeSubmission(deviceSurfaceInformation, waitForServiceSampleEntities, mapLocation, address, remark);
                break;
            default:
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        this.position = position;
        List<ServiceSampleEntity> data = baseAdvertisementPhotoTabAdapter.getData();
        baseAdvertisementPhotoTabAdapter.setCheckPosition(position);
        ServiceSampleEntity serviceSampleEntity = data.get(position);
        String url = serviceSampleEntity.getPicUrl();
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

    public String getOrderCodes() {
        if (getArguments() != null) {
            return getArguments().getString(ORDER_CODES);
        }
        return "";
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
            try {
                return new Gson().fromJson(getArguments().getString(DEVICE_SURFACE_INFORMATION_LIST_STRING)
                        , new TypeToken<List<DeviceSurfaceInformation>>() {
                        }.getType());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
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
                List<ServiceSampleEntity> data = baseAdvertisementPhotoTabAdapter.getData();
                ServiceSampleEntity serviceSampleEntity = data.get(position);
                serviceSampleEntity.setPicUrl(obj);
                //保存到 待上传
                if (waitForServiceSampleEntities.size() >= position) {
                    waitForServiceSampleEntities.get(position).setPicUrl(obj);
                }
                GlideManager.loadImage(getActivity(), obj, ivAdvertisementPhoto);
                baseAdvertisementPhotoTabAdapter.notifyDataSetChanged();
            }
        });
    }

    public List<ServiceSampleEntity> getServiceSample() {
        try {
            if (getArguments() != null) {
                return new Gson().fromJson(getArguments().getString(SERVICE_SAMPLE), new TypeToken<List<ServiceSampleEntity>>() {
                }.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<ServiceSampleEntity> getDataInit() {
        //样式图
        List<ServiceSampleEntity> serviceSample = getServiceSample();
        //拍过的图片
        List<ServiceSampleEntity> serviceSampleEntities = getServiceSampleEntities();
        if (!serviceSampleEntities.isEmpty()) {
            waitForServiceSampleEntities.addAll(serviceSampleEntities);
            return serviceSampleEntities;
        }
        if (serviceSample != null) {
            for (ServiceSampleEntity serviceSampleEntity : serviceSample) {
                waitForServiceSampleEntities.add(new ServiceSampleEntity("", serviceSampleEntity.getAdverName()));
            }
        }
        return serviceSample;
    }

    /**
     * 回显的图片
     *
     * @return list
     */
    public List<ServiceSampleEntity> getServiceSampleEntities() {
        return new ArrayList<>();
    }

    /**
     * @param deviceSurfaceInformation 当前面的数据
     * @param serviceSampleEntities    待提交的数据
     * @param mapLocation              经纬度
     * @param address                  地址
     * @param remark                   描述
     */
    private void completeSubmission(DeviceSurfaceInformation deviceSurfaceInformation, List<ServiceSampleEntity> serviceSampleEntities,
                                    AMapLocation mapLocation, String address, String remark) {
        //所有面的数据
        List<DeviceSurfaceInformation> deviceSurfaceInformationList = getDeviceSurfaceInformationList();
        HashMap<String, Object> hashMap = new HashMap<>();
        //当前面的订单编号
        String orderCode = deviceSurfaceInformation.getOrderCode();
        hashMap.put("orderCode", deviceSurfaceInformation.getOrderCode());
        //除拍照外的订单编号集合，以逗号分隔
        StringBuilder otherOrderCodes = new StringBuilder();
        for (DeviceSurfaceInformation surfaceInformation : deviceSurfaceInformationList) {
            String otherOrderCode = surfaceInformation.getOrderCode();
            if (!otherOrderCode.equals(orderCode)) {
                otherOrderCodes.append(",").append(otherOrderCode);
            }
        }
        hashMap.put("orderCodes", otherOrderCodes.toString());
        //经纬度
        hashMap.put("lon", mapLocation.getLongitude());
        hashMap.put("lat", mapLocation.getLatitude());
        //定位
        hashMap.put("address", address);
        //备注
        hashMap.put("remark", remark);
        //这里有两个集合 已经提交  域名+图片名称  未提交只有本地图片的路径
        List<ServiceSampleEntity> submitted = new ArrayList<>();
        //这个是需要提交的
        List<ServiceSampleEntity> notSubmitted = new ArrayList<>();

        //判断 是否是重复的图片 已经提交过的 不提交到七牛云
        for (ServiceSampleEntity serviceSampleEntity : serviceSampleEntities) {
            String picUrl = serviceSampleEntity.getPicUrl();
            //正服 和 测服 图片地址混乱 不知道什么原因(多个端上传) http://pic.hangzhouzhuangba.com/  http://testpic.hangzhouzhuangba.com/
            if (!TextUtils.isEmpty(picUrl) && !picUrl.contains(IMG_URL) && !picUrl.contains("http://pic.hangzhouzhuangba.com/")) {
                //已经提交的
                String imgName = QiNiuUtil.newInstance().getImgName();
                submitted.add(new ServiceSampleEntity(IMG_URL + imgName, serviceSampleEntity.getAdverName()));
                //需要提交的
                notSubmitted.add(new ServiceSampleEntity(serviceSampleEntity.getPicUrl(), serviceSampleEntity.getAdverName(), imgName));
            } else {
                submitted.add(serviceSampleEntity);
            }
        }
        submitAdvertisementPhoto(submitted, notSubmitted, hashMap);
    }

    /**
     * @param submitted    已经提交
     * @param notSubmitted 未提交
     * @param hashMap      待提交的参数
     */
    public void submitAdvertisementPhoto(List<ServiceSampleEntity> submitted, List<ServiceSampleEntity> notSubmitted, HashMap<String, Object> hashMap) {

    }
}
