package com.xiaomai.zhuangba.fragment.orderdetail.master;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.weight.description.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * 电子签名
 */
public class DescriptionContentFragment extends BaseFragment {

    @BindView(R.id.signature_pad)
    SignaturePad mSignaturePad;

    public static final String PHOTO_PATH = "photo_path";

    public static DescriptionContentFragment newInstance() {
        Bundle args = new Bundle();
        DescriptionContentFragment fragment = new DescriptionContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        getBaseFragmentActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @OnClick(R.id.btnDescriptionContent)
    public void onViewClicked() {
        Bitmap signatureBitmap = mSignaturePad.getSignatureBitmap();
        String photoPath = addJpgSignatureToGallery(signatureBitmap);
        Intent bundle = new Intent();
        bundle.putExtra(PHOTO_PATH , photoPath);
        setFragmentResult(ForResultCode.RESULT_OK.getCode(), bundle);
        popBackStack();
    }

    public String addJpgSignatureToGallery(Bitmap signature) {
        File photo = new File(getAlbumStorageDir("SignaturePad"), String.format("Signature_%d.jpg", System.currentTimeMillis()));
        String photoPath = photo.getPath();
        try {
            saveBitmapToJPG(signature, photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photoPath;
    }

    public File getAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("SignaturePad", "Directory not created");
        }
        return file;
    }

    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        stream.close();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getBaseFragmentActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_description_content;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.electronic_signature);
    }

    @Override
    public String getRightTitle() {
        return getString(R.string.clear);
    }

    @Override
    public void rightTitleClick(View v) {
        mSignaturePad.clear();
    }
}
