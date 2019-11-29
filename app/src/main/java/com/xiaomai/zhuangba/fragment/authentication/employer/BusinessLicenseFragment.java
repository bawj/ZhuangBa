package com.xiaomai.zhuangba.fragment.authentication.employer;

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
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.weight.PhotoTool;
import com.xiaomai.zhuangba.weight.camera.global.Constant;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.xiaomai.zhuangba.weight.PhotoTool.GET_IMAGE_BY_CAMERA;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 * 上传营业执照
 */
public class BusinessLicenseFragment extends BaseFragment {

    @BindView(R.id.ivBusinessLicense)
    ImageView ivBusinessLicense;
    public Uri resultUri = null;
    private Uri imageUriFromCamera;
    public static BusinessLicenseFragment newInstance() {
        Bundle args = new Bundle();
        BusinessLicenseFragment fragment = new BusinessLicenseFragment();
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

    @OnClick({R.id.btnBusinessLicense, R.id.ivBusinessLicense})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBusinessLicense:
                RxPermissionsUtils.applyPermission(getActivity(), new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        //相机
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
            case R.id.btnBusinessLicense:
                next();
                break;
            default:
        }
    }

    private void next() {
        if (resultUri == null) {
            ToastUtil.showShort(getString(R.string.img_not_null));
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
                                startFragment(EmployerInformationFragment.newInstance(response.toString()));
                            }
                        });
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("requestCode = " + requestCode);
        switch (requestCode) {
            case GET_IMAGE_BY_CAMERA:
                //拍照之后的处理
                if (resultCode == RESULT_OK && getActivity() != null) {
                    String imageAbsolutePath = PhotoTool.getImageAbsolutePath(getActivity(), imageUriFromCamera);
                    //压缩图片
                    Luban.with(getActivity())
                            .load(imageAbsolutePath)
                            .ignoreBy(100)
                            .setTargetDir(Constant.DIR_ROOT)
                            .filter(new CompressionPredicate() {
                                @Override
                                public boolean apply(String path) {
                                    return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                                }
                            })
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                    //压缩开始前调用，可以在方法内启动 loading UI
                                    Log.e("开始压缩 时间 = " + System.currentTimeMillis());
                                }

                                @Override
                                public void onSuccess(File file) {
                                    //压缩成功后调用，返回压缩后的图片文件
                                    Log.e("压缩成功 时间 = " + System.currentTimeMillis());
                                    Log.e("压缩图片地址 = " + file.getPath());
                                    resultUri = Uri.parse("file:///" + file.getPath());
                                    GlideManager.loadUriImage(getActivity(), resultUri, ivBusinessLicense);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    //当压缩过程出现问题时调用
                                    Log.e("压缩失败 error " + e);
                                    resultUri = Uri.parse("file:///" + PhotoTool.getImageAbsolutePath(getActivity(), imageUriFromCamera));
                                    Log.e("requestCode resultUri = " + resultUri.toString());
                                    GlideManager.loadUriImage(getActivity(), resultUri, ivBusinessLicense);
                                }
                            }).launch();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_business_license;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.business_license);
    }
}
