package com.example.toollib.http.interceptor;


import android.support.annotation.NonNull;

import com.example.toollib.util.SPUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Administrator
 * @date 2019/5/7 0007
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        String url = request.url().uri().toString();
        if ((url.indexOf("userLogin/login")) != -1){
            String cookie = response.headers().get("Set-Cookie");
            if (cookie != null){
                SPUtils.getInstance().put("cookie" , cookie);
            }
        }
        return response;
    }
}
