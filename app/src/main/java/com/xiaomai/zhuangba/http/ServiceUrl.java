package com.xiaomai.zhuangba.http;


import com.example.toollib.http.RetrofitUtils;

/**
 * @author Administrator
 * @date 2019/5/7 0007
 */
public class ServiceUrl {

    private static IApi apiUtils;

    public static IApi getUserApi() {
        if (apiUtils == null) {
            apiUtils = RetrofitUtils.getInstance().retrofit().create(IApi.class);
        }
        return apiUtils;
    }

}
