package com.xiaomai.zhuangba.data.module.authentication;

import com.example.toollib.data.IBaseModule;

/**
 * @author Administrator
 */
public interface IMasterAuthenticationModule extends IBaseModule<IMasterAuthenticationView> {

    /**
     * 上传身份证 照片
     */
    void requestIdCardImg();

}
