package com.xiaomai.zhuangba.data.module.authentication;

import com.example.toollib.data.base.IBaseView;
import com.xiaomai.zhuangba.data.bean.ImgUrl;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public interface IMasterAuthenticationView extends IBaseView {

    /**
     * 姓名
     * @return string
     */
    String getUserText();

    /**
     * 身份证号码
     * @return string
     */
    String getIdentityCard();

    /**
     * 身份证正面 图片地址
     * @return string
     */
    String getIdCardFrontPhoto();

    /**
     * 身份证反面 图片地址
     * @return string
     */
    String getIdCardBackPhoto();

    /**
     * 身份证有效期
     * @return string
     */
    String getValidityData();

    /**
     * 身份证上传成功
     * @param imgUrl img
     */
    void uploadSuccess(ImgUrl imgUrl);

    /**
     * 紧急联系地址
     * @return string
     */
    String getEmergencyContact();

    /**
     * 联系地址
     * @return string
     */
    String getAddress();
}
