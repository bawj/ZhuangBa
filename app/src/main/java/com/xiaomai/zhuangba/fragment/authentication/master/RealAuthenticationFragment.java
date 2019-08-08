package com.xiaomai.zhuangba.fragment.authentication.master;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.base.BaseCallback;
import com.google.gson.Gson;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.appilcation.PretendApplication;
import com.xiaomai.zhuangba.data.bean.ImgUrl;
import com.xiaomai.zhuangba.data.bean.MasterAuthenticationInfo;
import com.xiaomai.zhuangba.data.module.authentication.IMasterAuthenticationModule;
import com.xiaomai.zhuangba.data.module.authentication.IMasterAuthenticationView;
import com.xiaomai.zhuangba.data.module.authentication.MasterAuthenticationModule;
import com.xiaomai.zhuangba.enums.ForResultCode;
import com.xiaomai.zhuangba.util.FileUtil;
import com.xiaomai.zhuangba.util.RxPermissionsUtils;
import com.xiaomai.zhuangba.util.Util;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Administrator
 * @date 2019/8/1 0001
 * <p>
 * 师傅端实名认证
 */
public class RealAuthenticationFragment extends BaseFragment<IMasterAuthenticationModule> implements IMasterAuthenticationView {

    @BindView(R.id.layClickIdCard)
    FrameLayout layClickIdCard;
    @BindView(R.id.layClickIdCardBack)
    FrameLayout layClickIdCardBack;
    @BindView(R.id.editAuthenticationName)
    EditText editAuthenticationName;
    @BindView(R.id.editEmergencyContact)
    EditText editEmergencyContact;
    @BindView(R.id.editAddress)
    EditText editAddress;
    @BindView(R.id.editAuthenticationIdCard)
    EditText editAuthenticationIdCard;
    @BindView(R.id.tvAuthenticationTermOfValidity)
    TextView tvAuthenticationTermOfValidity;
    @BindView(R.id.ivAuthenticationFont)
    ImageView ivAuthenticationFont;
    @BindView(R.id.ivAuthenticationBack)
    ImageView ivAuthenticationBack;
    @BindView(R.id.btnAuthenticationNext)
    Button btnAuthenticationNext;

    /**
     * 身份证正面
     */
    private String absolutePath;
    /**
     * 身份证反面
     */
    private String absoluteBlackPath;

    public static RealAuthenticationFragment newInstance() {
        Bundle args = new Bundle();
        RealAuthenticationFragment fragment = new RealAuthenticationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IMasterAuthenticationModule initModule() {
        return new MasterAuthenticationModule();
    }

    @Override
    public void initView() {

    }


    @OnClick({R.id.layClickIdCard, R.id.layClickIdCardBack, R.id.btnAuthenticationNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layClickIdCard:
                RxPermissionsUtils.applyPermission(getActivity(), new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        //lib_ocr // TODO: 2019/8/7 0007 放开注释
//                        Intent scanIntent = new Intent(getActivity(), IDCardRecognitionActivity.class);
//                        scanIntent.putExtra(IDCardRecognitionActivity.FRONT, true);
//                        startActivityForResult(scanIntent, ForResultCode.START_FOR_RESULT_CODE.getCode());
                    }

                    @Override
                    public void onFail(Object obj) {
                        showToast(getString(R.string.please_open_permissions));
                    }
                }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.layClickIdCardBack:
                RxPermissionsUtils.applyPermission(getActivity(), new BaseCallback<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        //lib_ocr// TODO: 2019/8/7 0007 放开注释
//                        Intent scanIntent = new Intent(getActivity(), IDCardRecognitionActivity.class);
//                        scanIntent.putExtra(IDCardRecognitionActivity.FRONT, false);
//                        startActivityForResult(scanIntent, ForResultCode.START_FOR_RESULT_CODE_.getCode());
                    }

                    @Override
                    public void onFail(Object obj) {
                        showToast(getString(R.string.please_open_permissions));
                    }
                }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                break;
            case R.id.btnAuthenticationNext:
                iModule.requestIdCardImg();
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 2019/8/7 0007 放开注释
//        if (resultCode == RESULT_CODE) {
//            if (requestCode == ForResultCode.START_FOR_RESULT_CODE.getCode() && null != data) {
//                final EXOCRModel result = (EXOCRModel) data.getSerializableExtra(RESULT);
//                editAuthenticationName.setText(result.name);
//                editAuthenticationIdCard.setText(result.cardnum);
//
//                String absolutePath = FileUtil.getSaveFile(PretendApplication.getInstance()).getAbsolutePath();
//
//                byte[] decode = Base64.decode(result.base64bitmap, Base64.DEFAULT);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
//                FileUtil.saveBitmap(absolutePath, bitmap);
//                this.absolutePath = absolutePath;
//                ivAuthenticationFont.setImageBitmap(bitmap);
//
//            } else if (requestCode == ForResultCode.START_FOR_RESULT_CODE_.getCode() && null != data) {
//                final EXOCRModel result = (EXOCRModel) data.getSerializableExtra(RESULT);
//                byte[] decode = Base64.decode(result.base64bitmap, Base64.DEFAULT);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(decode, 0, decode.length);
//                String absolutePath = FileUtil.getSaveFile(PretendApplication.getInstance()).getAbsolutePath();
//                FileUtil.saveBitmap(absolutePath, bitmap);
//                absoluteBlackPath = absolutePath;
//                ivAuthenticationBack.setImageBitmap(bitmap);
//                String[] valDate = Util.getValDate(result.validdate);
//                if (valDate != null && valDate.length > 1) {
//                    String string = getString(R.string.id_card_date, Util.getDate(valDate[0]), Util.getDate(valDate[1]));
//                    tvAuthenticationTermOfValidity.setText(string);
//                }
//            }
//        }
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_real_authentication;
    }

    @Override
    protected String getActivityTitle() {
        return getString(R.string.real_authentication);
    }

    @Override
    public String getUserText() {
        return editAuthenticationName.getText().toString();
    }

    @Override
    public String getIdentityCard() {
        return editAuthenticationIdCard.getText().toString();
    }

    @Override
    public String getIdCardFrontPhoto() {
        return absolutePath;
    }

    @Override
    public String getIdCardBackPhoto() {
        return absoluteBlackPath;
    }

    @Override
    public String getValidityData() {
        return tvAuthenticationTermOfValidity.getText().toString();
    }

    @Override
    public String getEmergencyContact() {
        return editEmergencyContact.getText().toString();
    }

    @Override
    public String getAddress() {
        return editAddress.getText().toString();
    }

    @Override
    public void uploadSuccess(ImgUrl imgUrl) {
        MasterAuthenticationInfo masterAuthenticationInfo = new MasterAuthenticationInfo();
        masterAuthenticationInfo.setUserText(getUserText());
        masterAuthenticationInfo.setValidityData(getValidityData());
        masterAuthenticationInfo.setIdentityCard(getIdentityCard());
        masterAuthenticationInfo.setIdCardFrontPhoto(imgUrl.getFrontPhoto());
        masterAuthenticationInfo.setIdCardBackPhoto(imgUrl.getIdCardBackPhoto());
        String emergencyContact = editEmergencyContact.getText().toString();
        String address = editAddress.getText().toString();
        masterAuthenticationInfo.setEmergencyContact(emergencyContact);
        masterAuthenticationInfo.setContactAddress(address);
        startFragment(BareheadedFragment.newInstance(new Gson().toJson(masterAuthenticationInfo)));
    }

}
