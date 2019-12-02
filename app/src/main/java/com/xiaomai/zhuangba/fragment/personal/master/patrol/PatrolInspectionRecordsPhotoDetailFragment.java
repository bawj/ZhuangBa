package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.toollib.weight.dialog.CommonlyDialog;
import com.google.gson.Gson;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.PatrolInspectionRecordsDetailImgBean;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.http.ServiceUrl;
import com.xiaomai.zhuangba.util.LuBanUtil;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.weight.dialog.EditTextDialogBuilder;

import java.io.File;
import java.net.URI;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @date 2019/10/11 0011
 * 巡查任务 图片上传
 */
public class PatrolInspectionRecordsPhotoDetailFragment extends BaseFragment {

    @BindView(R.id.btnPhotoShot)
    QMUIButton btnPhotoShot;
    @BindView(R.id.btnPhotoRemake)
    QMUIButton btnPhotoRemake;
    @BindView(R.id.btnPhotoSave)
    QMUIButton btnPhotoSave;
    @BindView(R.id.btnPhotoProblemFeedback)
    QMUIButton btnPhotoProblemFeedback;
    @BindView(R.id.ivPhotoImg)
    ImageView ivPhotoImg;

    private String imgUrl = "";
    private String remarks = "";
    private static final String NOODLES = "noodles";
    public static final String TASK_PICTURE_LIST_BEAN = "task_picture_list_bean";
    private PatrolInspectionRecordsDetailImgBean.TaskPictureListBean taskPictureListBean;

    public static PatrolInspectionRecordsPhotoDetailFragment newInstance(String noodles, String taskPictureListBean) {
        Bundle args = new Bundle();
        args.putString(NOODLES, noodles);
        args.putString(TASK_PICTURE_LIST_BEAN, taskPictureListBean);
        PatrolInspectionRecordsPhotoDetailFragment fragment = new PatrolInspectionRecordsPhotoDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        if (getArguments() != null) {
            taskPictureListBean = new Gson().fromJson(getArguments().getString(TASK_PICTURE_LIST_BEAN)
                    , PatrolInspectionRecordsDetailImgBean.TaskPictureListBean.class);
        } else {
            taskPictureListBean = new PatrolInspectionRecordsDetailImgBean.TaskPictureListBean();
        }
        String pic = taskPictureListBean.getPic();
        if (!TextUtils.isEmpty(pic)){
            GlideManager.loadUriImage(getActivity(), pic, ivPhotoImg);
        }
        remarks = taskPictureListBean.getRemarks();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_patrol_inspection_records_photo_detail;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }


    @OnClick({R.id.btnPhotoShot, R.id.btnPhotoRemake, R.id.btnPhotoSave, R.id.btnPhotoProblemFeedback})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPhotoShot:
                //拍照
                RxPermissionsUtils.applyPermission(getActivity(), new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        //相机
                        startFragmentForResult(PhotoFragment.newInstance(), ForResultCode.START_FOR_RESULT_CODE.getCode());
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
                        //相机
                        startFragmentForResult(PhotoFragment.newInstance(), ForResultCode.START_FOR_RESULT_CODE.getCode());
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
            case R.id.btnPhotoProblemFeedback:
                //问题反馈
                EditTextDialogBuilder.getInstance()
                        .initView(getActivity())
                        .setContent(remarks)
                        .setTitle(getString(R.string.problem_feedback))
                        .setDialogOk(getString(R.string.feedback_and_save))
                        .setDialogOkColor(getResources().getColor(R.color.tool_lib_gray_222222))
                        .setICallBase(new EditTextDialogBuilder.BaseCallback() {
                            @Override
                            public void ok(String content) {
                                taskPictureListBean.setRemarks(content);
                            }
                        }).showDialog();
                break;
            default:
        }
    }

    private void uploadImg() {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        try {
            File file = new File(new URI("file:///" + imgUrl));
            builder.addFormDataPart("file", file.getName(),
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
            // TODO: 2019/11/25 0025 换成没有水印的图片上传接口
//            Observable<HttpResult<Object>> responseBodyObservable = ServiceUrl.getUserApi().uploadImg(builder.build());
            Observable<HttpResult<Object>> responseBodyObservable = ServiceUrl.getUserApi().uploadFile(builder.build());
            RxUtils.getObservable(responseBodyObservable)
                    .compose(this.<HttpResult<Object>>bindToLifecycle())
                    .subscribe(new BaseHttpRxObserver<Object>(getActivity()) {
                        @Override
                        protected void onSuccess(Object response) {
                            taskPictureListBean.setPic(response.toString());
                            isUpload = true;
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 是否拍摄了图片 */
    private boolean isShot = false;
    /** 拍摄后是否上传了图片 */
    private boolean isUpload = false;

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()) {
                //地址选择成功返回
                imgUrl = data.getStringExtra(ForResultCode.RESULT_KEY.getExplain());
                LuBanUtil.getInstance().compress(getActivity(), imgUrl, new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        //压缩成功后调用，返回压缩后的图片文件
                        Log.e("压缩成功 时间 = " + System.currentTimeMillis());
                        Log.e("压缩图片地址 = " + obj);
                        imgUrl =  obj;
                        GlideManager.loadUriImage(getActivity(), imgUrl, ivPhotoImg);
                    }
                });
                isVisibility();
                isShot = true;
            }
        }
    }

    private void isVisibility() {
        btnPhotoShot.setVisibility(View.GONE);
        btnPhotoRemake.setVisibility(View.VISIBLE);
        btnPhotoSave.setVisibility(View.VISIBLE);
        btnPhotoProblemFeedback.setVisibility(View.VISIBLE);
    }

    public PatrolInspectionRecordsDetailImgBean.TaskPictureListBean getTaskPictureListBean() {
        return taskPictureListBean == null ? new PatrolInspectionRecordsDetailImgBean.TaskPictureListBean() : taskPictureListBean;
    }

    public String getNoodles() {
        if (getArguments() != null) {
            return getArguments().getString(NOODLES);
        }
        return "";
    }

    public boolean isShot() {
        return isShot;
    }

    public boolean isUpload() {
        return isUpload;
    }
}
