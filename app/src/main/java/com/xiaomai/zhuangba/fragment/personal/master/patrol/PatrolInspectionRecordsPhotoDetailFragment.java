package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.os.Bundle;
import android.view.View;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.qmuiteam.qmui.layout.QMUIButton;
import com.xiaomai.zhuangba.R;

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

    public static PatrolInspectionRecordsPhotoDetailFragment newInstance() {
        Bundle args = new Bundle();
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
    public void rightTitleClick(View v) {
        //完成
    }

    @Override
    public String getRightTitle() {
        return getString(R.string.complete);
    }
}
