package com.xiaomai.zhuangba.fragment.authentication.master;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.Log;
import com.example.toollib.util.ToastUtil;
import com.google.gson.Gson;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.MasterAuthenticationInfo;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.weight.PhotoTool;
import com.xiaomai.zhuangba.weight.dialog.NavigationDialog;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.xiaomai.zhuangba.weight.PhotoTool.GET_IMAGE_BY_CAMERA;
import static com.xiaomai.zhuangba.weight.PhotoTool.GET_IMAGE_FROM_PHONE;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class BareheadedFragment extends BaseFragment {

    @BindView(R.id.ivBareheaded)
    ImageView ivBareheaded;
    @BindView(R.id.btnBareheaded)
    Button btnBareheaded;
    @BindView(R.id.btnReUpload)
    QMUIButton btnReUpload;
    @BindView(R.id.tvBareheadedTip)
    TextView tvBareheadedTip;

    /**
     * 是否上传了头像
     */
    public boolean isUpload;
    public Uri resultUri = null;
    private Uri imageUriFromCamera;
    public static final String BAREHEADED = "bareheaded";

    public static BareheadedFragment newInstance(String masterAuthenticationInfo) {
        Bundle args = new Bundle();
        args.putString(BAREHEADED, masterAuthenticationInfo);
        BareheadedFragment fragment = new BareheadedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.btnBareheaded, R.id.btnReUpload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBareheaded:
                //上传照片 或者 下一步
                uploadOrNext();
                break;
            case R.id.btnReUpload:
                //重新上传
                showUploadDialog();
                break;
            default:
        }
    }

    private void uploadOrNext() {
        if (!isUpload) {
            //上传
            showUploadDialog();
        } else {
            //下一步
            next();
        }
    }

    private void next() {
        if (resultUri == null) {
            ToastUtil.showShort(getString(R.string.please_upload_photo));
        } else {
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
                                MasterAuthenticationInfo masterAuthenticationInfo = getMasterAuthenticationInfo();
                                if (masterAuthenticationInfo != null) {
                                    masterAuthenticationInfo.setPhotoPath(response.toString());
                                }
                                startFragment(ScopeOfServiceFragment.newInstance(new Gson().toJson(masterAuthenticationInfo)));
                            }
                        });

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
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
                    resultUri = Uri.parse("file:///" + imageAbsolutePath);
                    btnBareheaded.setText(getString(R.string.next));
                    btnReUpload.setVisibility(View.VISIBLE);
                    isUpload = true;
                    GlideManager.loadUriImage(getActivity(), resultUri, ivBareheaded);
                }
                break;
            case GET_IMAGE_BY_CAMERA:
                //拍照之后的处理
                if (resultCode == RESULT_OK && getActivity() != null) {
                    resultUri = Uri.parse("file:///" + PhotoTool.getImageAbsolutePath(getActivity(), imageUriFromCamera));
                    btnBareheaded.setText(getString(R.string.next));
                    btnReUpload.setVisibility(View.VISIBLE);
                    isUpload = true;
                    GlideManager.loadUriImage(getActivity(), resultUri, ivBareheaded);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_bareheaded;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.upload_photo);
    }

    private MasterAuthenticationInfo getMasterAuthenticationInfo() {
        if (getArguments() != null) {
            String masterAuthenticationInfo = getArguments().getString(BAREHEADED);
            return new Gson().fromJson(masterAuthenticationInfo, MasterAuthenticationInfo.class);
        }
        return null;
    }
}
