package com.xiaomai.zhuangba.util;

import com.example.toollib.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author Administrator
 * @date 2019/11/15 0015
 */
public class GogleMapUtil {

    /**
     * @param addr 查询的地址
     * @return
     * @throws IOException
     */
    public static Object[] getCoordinate(String addr) throws IOException {
        //经度
        String lng = null;
        //纬度
        String lat = null;
        String address = "";
        try {
            address = java.net.URLEncoder.encode(addr, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String key = "iQWBRMOXBiUa1WWmGONiGaOZ";
        String url = String.format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s", address, key);
        URL myURL = null;
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStreamReader insr = null;
        BufferedReader br = null;
        try {
            // 不使用代理
            if (myURL != null) {
                httpsConn = (URLConnection) myURL.openConnection();
                if (httpsConn != null) {
                    insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
                    br = new BufferedReader(insr);
                    String data = null;
                    int count = 1;
                    while ((data = br.readLine()) != null) {
                        if (count == 5) {
                            //经度
                            lng = (String) data.subSequence(data.indexOf(":") + 1, data.indexOf(","));
                            count++;
                        } else if (count == 6) {
                            //纬度
                            lat = data.substring(data.indexOf(":") + 1);
                            count++;
                        } else {
                            count++;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (insr != null) {
                insr.close();
            }
            if (br != null) {
                br.close();
            }
        }
        return new Object[]{lng, lat};
    }
}
