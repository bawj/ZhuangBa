package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.Log;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.enums.StaticExplain;
import com.xiaomai.zhuangba.util.ConstantUtil;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;

import butterknife.BindView;
import butterknife.OnClick;

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

    private static final String NOODLES = "noodles";

    public static PatrolInspectionRecordsPhotoDetailFragment newInstance(String noodles) {
        Bundle args = new Bundle();
        args.putString(NOODLES, noodles);
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
                break;
            case R.id.btnPhotoSave:
                //保存
                break;
            case R.id.btnPhotoProblemFeedback:
                //问题反馈
                break;
            default:
        }
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()) {
                //地址选择成功返回
                String imgUrl = data.getStringExtra(ForResultCode.RESULT_KEY.getExplain());
                Log.e("imgUrl = " + imgUrl);
                GlideManager.loadUriImage(getActivity(), imgUrl, ivPhotoImg);
                isVisibility();
            }
        }
    }

    private void isVisibility(){
        btnPhotoShot.setVisibility(View.GONE);
        btnPhotoRemake.setVisibility(View.VISIBLE);
        btnPhotoSave.setVisibility(View.VISIBLE);
        btnPhotoProblemFeedback.setVisibility(View.VISIBLE);
    }


    public String getNoodles() {
        if (getArguments() != null) {
            return getArguments().getString(NOODLES);
        }
        return "";
    }
}
