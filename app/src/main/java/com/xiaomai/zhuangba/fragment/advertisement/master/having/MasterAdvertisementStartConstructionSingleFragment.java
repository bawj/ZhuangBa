package com.xiaomai.zhuangba.fragment.advertisement.master.having;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.Log;
import com.example.toollib.util.ToastUtil;
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.fragment.masterworker.MasterWorkerFragment;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.LuBanUtil;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.weight.PhotoTool;

import java.io.File;
import java.net.URI;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.xiaomai.zhuangba.weight.PhotoTool.GET_IMAGE_BY_CAMERA;

/**
 * @author Administrator
 * @date 2019/10/19 0019
 * <p>
 * 开始施工 单图上传
 */
public class MasterAdvertisementStartConstructionSingleFragment extends BaseFragment {

    @BindView(R.id.ivPhotoImg)
    ImageView ivPhotoImg;
    @BindView(R.id.btnPhotoShot)
    QMUIButton btnPhotoShot;
    @BindView(R.id.btnPhotoRemake)
    QMUIButton btnPhotoRemake;
    @BindView(R.id.btnPhotoSave)
    QMUIButton btnPhotoSave;

    /**
     * 提交到服务器后返回的图片地址
     */
    public String requestImgUrl = "";
    private Uri imageUriFromCamera;
    /**
     * 提交图片前的本地地址
     */
    public Uri resultUri = null;

    public static MasterAdvertisementStartConstructionSingleFragment newInstance(String orderCode, String orderType) {
        Bundle args = new Bundle();
        args.putString(ConstantUtil.ORDER_CODE, orderCode);
        args.putString(ConstantUtil.ORDER_TYPE, orderType);
        MasterAdvertisementStartConstructionSingleFragment fragment = new MasterAdvertisementStartConstructionSingleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.btnPhotoShot, R.id.btnPhotoRemake, R.id.btnPhotoSave})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPhotoShot:
                //拍照
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
                break;
            case R.id.btnPhotoRemake:
                //重拍
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
                break;
            case R.id.btnPhotoSave:
                //保存  上传图片
                CommonlyDialog.getInstance().initView(getActivity())
                        .isTvDialogBondTipsVisibility(View.GONE)
                        .setTvDialogCommonlyOk(getString(R.string.confirm_preservation))
                        .setTvDialogCommonlyOkColor(getResources().getColor(R.color.tool_lib_gray_222222))
                        .setTvDialogCommonlyClose(getString(R.string.close))
                        .setTvDialogCommonlyContent(getString(R.string.save_the_current_photo))
                        .setICallBase(new CommonlyDialog.BaseCallback() {
                            @Override
                            public void sure() {
                                uploadImg();
                            }
                        }).showDialog();
                break;
            default:
        }
    }

    private void uploadImg() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        try {
            File file = new File(new URI(resultUri.toString()));
            builder.addFormDataPart("file", file.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
            Observable<HttpResult<Object>> responseBodyObservable = ServiceUrl.getUserApi().uploadFile(builder.build());
            RxUtils.getObservable(responseBodyObservable)
                    .compose(this.<HttpResult<Object>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                        @Override
                        protected void onSuccess(Object response) {
                            requestImgUrl = response.toString();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rightTitleClick(View v) {
        //完成 提交
        if (!TextUtils.isEmpty(requestImgUrl)) {
            RxUtils.getObservable(ServiceUrl.getUserApi().startTaskAdvertisingOrder(getOrderCode(), requestImgUrl))
                    .compose(this.<HttpResult<Object>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                        @Override
                        protected void onSuccess(Object response) {
                            startFragment(MasterWorkerFragment.newInstance());
                        }
                    });
        } else if (resultUri == null) {
            ToastUtil.showShort(getString(R.string.please_shot_img));
        } else {
            ToastUtil.showShort(getString(R.string.please_save_shot_img));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_IMAGE_BY_CAMERA:
                //拍照之后的处理
                if (resultCode == RESULT_OK && getActivity() != null) {
                    String imageAbsolutePath = PhotoTool.getImageAbsolutePath(getActivity(), imageUriFromCamera);
                    LuBanUtil.getInstance().compress(getActivity(), imageAbsolutePath, new BaseCallback<String>() {
                        @Override
                        public void onSuccess(String obj) {
                            //压缩成功后调用，返回压缩后的图片文件
                            Log.e("压缩成功 时间 = " + System.currentTimeMillis());
                            Log.e("压缩图片地址 = " + obj);
                            resultUri = Uri.parse("file:///" + obj);
                            GlideManager.loadUriImage(getActivity(), resultUri, ivPhotoImg);
                            isVisibility();
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private void isVisibility() {
        btnPhotoShot.setVisibility(View.GONE);
        btnPhotoRemake.setVisibility(View.VISIBLE);
        btnPhotoSave.setVisibility(View.VISIBLE);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_master_advertisement_start_construction_single;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.pre_construction_photos);
    }

    @Override
    public String getRightTitle() {
        return getString(R.string.complete);
    }

    private String getOrderCode() {
        if (getArguments() != null) {
            return getArguments().getString(ConstantUtil.ORDER_CODE);
        }
        return "";
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

}
