package com.xiaomai.zhuangba.fragment.orderdetail.master.base;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.manager.GlideManager;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.adapter.MultiGraphSelectionAdapter;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.fragment.orderdetail.master.DescriptionContentFragment;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.weight.GridSpacingItemDecoration;
import com.xiaomai.zhuangba.weight.PhotoTool;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.xiaomai.zhuangba.fragment.orderdetail.master.DescriptionContentFragment.PHOTO_PATH;
import static com.xiaomai.zhuangba.weight.PhotoTool.GET_IMAGE_BY_CAMERA;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 拍照 和签名
 */
public class BaseAutographFragment extends BaseFragment implements MultiGraphSelectionAdapter.OnMultiGraphClickListener {
    @BindView(R.id.tvBaseAutographImgTip)
    TextView tvBaseAutographImgTip;
    @BindView(R.id.recyclerBeforeInstallation)
    RecyclerView recyclerBeforeInstallation;
    @BindView(R.id.tvBaseAutographServiceTip)
    TextView tvBaseAutographServiceTip;
    @BindView(R.id.tvSubmitAcceptanceDescriptionContent)
    TextView tvSubmitAcceptanceDescriptionContent;
    /**
     * 电子签名 照片
     */
    @BindView(R.id.ivSubmitDescription)
    ImageView ivSubmitDescription;

    /**
     * 拍照图片保存
     */
    public List<String> mediaSelectorFiles = new ArrayList<>();
    private MultiGraphSelectionAdapter multiGraphSelectionAdapter;
    private Uri imageUriFromCamera;
    public Uri resultUri = null;

    public String descriptionPhotoPath;

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        mediaSelectorFiles.add(null);
        tvBaseAutographImgTip.setText(getAutographImgTip());
        tvBaseAutographServiceTip.setText(getAutographServiceTip());
        recyclerBeforeInstallation.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        multiGraphSelectionAdapter = new MultiGraphSelectionAdapter(getActivity(), mediaSelectorFiles);
        recyclerBeforeInstallation.setAdapter(multiGraphSelectionAdapter);
        multiGraphSelectionAdapter.setOnMultiGraphClickListener(this);
        recyclerBeforeInstallation.addItemDecoration(new GridSpacingItemDecoration(4, 32, false));
    }

    @OnClick({R.id.relAcceptanceDescription, R.id.btnBeforeInstallation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relAcceptanceDescription:
                //点击 签名
                startFragmentForResult(DescriptionContentFragment.newInstance(), ForResultCode.START_FOR_RESULT_CODE.getCode());
                break;
            case R.id.btnBeforeInstallation:
                //确认提交
                beforeInstallation();
                break;
            default:
        }
    }


    @Override
    public void addImg() {
        //权限
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GET_IMAGE_BY_CAMERA:
                //拍照之后的处理
                if (resultCode == RESULT_OK && getActivity() != null) {
                    resultUri = Uri.parse("file:///" + PhotoTool.getImageAbsolutePath(getActivity(), imageUriFromCamera));

//                    Uri imagePathUri = PhotoTool.createImagePathUri(getActivity());
//                    Luban.with(getActivity())
//                            .load(resultUri)
//                            .ignoreBy(100)
//                            .setTargetDir(imagePathUri.getPath())
//                            .filter(new CompressionPredicate() {
//                                @Override
//                                public boolean apply(String path) {
//                                    return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
//                                }
//                            })
//                            .setCompressListener(new OnCompressListener() {
//                                @Override
//                                public void onStart() {
//                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
//                                }
//
//                                @Override
//                                public void onSuccess(File file) {
//                                    // TODO 压缩成功后调用，返回压缩后的图片文件
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//                                    // TODO 当压缩过程出现问题时调用
//                                }
//                            }).launch();


                    mediaSelectorFiles.add(0, resultUri.toString());
                    multiGraphSelectionAdapter.notifyDataSetChanged();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (resultCode == ForResultCode.RESULT_OK.getCode()) {
            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode()) {
                String photoPath = data.getStringExtra(PHOTO_PATH);
                if (!TextUtils.isEmpty(photoPath)) {
                    descriptionPhotoPath = photoPath;
                    tvSubmitAcceptanceDescriptionContent.setVisibility(View.GONE);
                    GlideManager.loadImage(getActivity(), photoPath, ivSubmitDescription);
                }
            }
        }
    }


    /**
     * 确认提交
     */
    public void beforeInstallation() {

    }

    @Override
    public int getContentView() {
        return R.layout.fragment_base_autograph;
    }

    public String getAutographServiceTip() {
        return "";
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    public String getAutographImgTip() {
        return "";
    }
}
