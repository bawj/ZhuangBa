package com.xiaomai.zhuangba.fragment.personal.master.patrol;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.weight.camera.global.Constant;
import com.xiaomai.zhuangba.weight.camera.utils.FileUtils;
import com.xiaomai.zhuangba.weight.camera.utils.ImageUtils;
import com.xiaomai.zhuangba.weight.camera.view.CameraPreview;
import com.xiaomai.zhuangba.weight.camera.view.CameraUtils;
import com.xiaomai.zhuangba.weight.camera.view.ViewFinderView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/10/12 0012
 */
public class PhotoFragment extends BaseFragment {

    @BindView(R.id.photoCameraPreview)
    CameraPreview mCameraPreview;
    @BindView(R.id.viewFinderView)
    ViewFinderView viewFinderView;

    private Bitmap mCropBitmap;

    public static PhotoFragment newInstance() {
        Bundle args = new Bundle();
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        statusBarWhite();
    }

    @OnClick({R.id.btnPhotoShot, R.id.photoCameraPreview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnPhotoShot:
                //拍照
                takePhoto();
                break;
            case R.id.photoCameraPreview:
                mCameraPreview.focus();
                break;
            default:
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        mCameraPreview.setEnabled(false);
        CameraUtils.getCamera().setOneShotPreviewCallback(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(final byte[] bytes, Camera camera) {
                //获取预览大小
                final Camera.Size size = camera.getParameters().getPreviewSize();
                camera.stopPreview();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final int w = size.width;
                        final int h = size.height;
                        Bitmap bitmap = ImageUtils.getBitmapFromByte(bytes, w, h);
                        cropImage(bitmap);
                    }
                }).start();
            }
        });
    }

    /**
     * 裁剪图片
     */
    private void cropImage(Bitmap bitmap) {
        /*计算扫描框的坐标点*/
//        float left = viewFinderView.getWidth();
//        float top = viewFinderView.getTop();
//        float right = viewFinderView.getRight() + left;
//        float bottom = viewFinderView.getBottom();

        float left = viewFinderView.getFrameRect().left;
        float top = viewFinderView.getFrameRect().top;
        float right = viewFinderView.getFrameRect().right;
        float bottom = viewFinderView.getFrameRect().bottom;


        /*计算扫描框坐标点占原图坐标点的比例*/
        float leftProportion = left / mCameraPreview.getWidth();
        float topProportion = top / mCameraPreview.getHeight();
        float rightProportion = right / mCameraPreview.getWidth();
        float bottomProportion = bottom / mCameraPreview.getBottom();

        /*自动裁剪*/
        mCropBitmap = Bitmap.createBitmap(bitmap,
                (int) (leftProportion * (float) bitmap.getWidth()),
                (int) (topProportion * (float)bitmap.getHeight()),
                (int) ((rightProportion - leftProportion) * (float) bitmap.getWidth()),
                (int) ((bottomProportion - topProportion) * (float) bitmap.getHeight()));
        /*设置成手动裁剪模式*/
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    confirm();
                }
            });
        }
    }

    private void confirm() {
        /*保存图片到sdcard并返回图片路径*/
        if (FileUtils.createOrExistsDir(Constant.DIR_ROOT)) {
            String imagePath = Constant.DIR_ROOT +
                    Constant.APP_NAME + "." +
                    System.currentTimeMillis() +
                    ".jpg";
            if (ImageUtils.save(mCropBitmap, imagePath, Bitmap.CompressFormat.JPEG)) {
                Intent intent = new Intent();
                intent.putExtra(ForResultCode.RESULT_KEY.getExplain(), imagePath);
                setFragmentResult(ForResultCode.RESULT_OK.getCode(), intent);
                popBackStack();
            }
        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_photo;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    public boolean isBackArrow() {
        return true;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mCameraPreview != null) {
            mCameraPreview.onStart();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mCameraPreview != null) {
            mCameraPreview.onStop();
        }
    }

    @Override
    public void onDestroyView() {
        statusBarBlack();
        super.onDestroyView();
    }
}
