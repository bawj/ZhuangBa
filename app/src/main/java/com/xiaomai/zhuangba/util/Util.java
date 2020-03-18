package com.xiaomai.zhuangba.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.toollib.util.AmountUtil;
import com.example.toollib.util.spf.SPUtils;
import com.example.toollib.util.spf.SpfConst;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.bean.District;
import com.xiaomai.zhuangba.data.bean.ProvinceBean;
import com.xiaomai.zhuangba.data.bean.ProvincialData;
import com.xiaomai.zhuangba.data.bean.UserInfo;
import com.xiaomai.zhuangba.data.db.DBHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class Util {

    public static void logout() {
        UserInfo unique = DBHelper.getInstance().getUserInfoDao().queryBuilder().unique();
        if (unique != null){
            UMengUtil.deleteAlias(unique.getPhoneNumber());
        }
        DBHelper.getInstance().getUserInfoDao().deleteAll();
        DBHelper.getInstance().getPushNotificationDBDao().deleteAll();
        SPUtils.getInstance().put(SpfConst.TOKEN , "");
    }


    public static String getZero(double d) {
        if (d == 0) {
            return "0.00";
        }
        return String.valueOf(d);
    }

    public static int isAppAlive(Context context, String packageName) {
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> listInfos = activityManager
                .getRunningTasks(20);
        // 判断程序是否在栈顶
        if (listInfos.get(0).topActivity.getPackageName().equals(packageName)) {
            return 1;
        } else {
            // 判断程序是否在栈里
            for (ActivityManager.RunningTaskInfo info : listInfos) {
                if (info.topActivity.getPackageName().equals(packageName)) {
                    return 2;
                }
            }
            return 0;
        }
    }

    /**
     * 设置TextView段落间距
     *
     * @param context          上下文
     * @param tv               给谁设置段距，就传谁
     * @param content          文字内容
     * @param paragraphSpacing 请输入段落间距（单位dp）
     * @param lineSpacingExtra xml中设置的的行距（单位dp）
     */
    public static void setParagraphSpacing(Context context, TextView tv, String content, int paragraphSpacing, int lineSpacingExtra) {
        if (TextUtils.isEmpty(content)){
            return;
        }
        if (!content.contains("\n")) {
            tv.setText(content);
            return;
        }
        content = content.replace("\n", "\n\r");

        int previousIndex = content.indexOf("\n\r");
        //记录每个段落开始的index，第一段没有，从第二段开始
        List<Integer> nextParagraphBeginIndexes = new ArrayList<>();
        nextParagraphBeginIndexes.add(previousIndex);
        while (previousIndex != -1) {
            int nextIndex = content.indexOf("\n\r", previousIndex + 2);
            previousIndex = nextIndex;
            if (previousIndex != -1) {
                nextParagraphBeginIndexes.add(previousIndex);
            }
        }
        //获取行高（包含文字高度和行距）
        float lineHeight = tv.getLineHeight();

        //把\r替换成透明长方形（宽:1px，高：字高+段距）
        SpannableString spanString = new SpannableString(content);
        Drawable d = ContextCompat.getDrawable(context, R.drawable.transparent);
        float density = context.getResources().getDisplayMetrics().density;
        //int强转部分为：行高 - 行距 + 段距
        d.setBounds(0, 0, 1, (int) ((lineHeight - lineSpacingExtra * density) / 1.2 + (paragraphSpacing - lineSpacingExtra) * density));
        for (int index : nextParagraphBeginIndexes) {
            // \r在String中占一个index
            spanString.setSpan(new ImageSpan(d), index + 1, index + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spanString);
    }

    /**
     * 禁止EditText输入特殊符号 表情
     * @param editText 输入
     * @param length   最多输入
     */
    public static void setEditTextInhibitInputSpaChat(EditText editText , int length) {
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher=  Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]").matcher(charSequence);
                if(!matcher.find()){
                    return null;
                }else{
                    return "";
                }
            }
        };
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length),inputFilter});
    }

    public static int getWidthPixels(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        // 屏幕宽度（像素）
        return dm.widthPixels;
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
        } catch (Exception e) {
            packageInfo = null;
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;
        }
    }

    public static String[] getValDate(String valDate) {
        if (!TextUtils.isEmpty(valDate) && valDate.contains("-")) {
            return valDate.split("-");
        }
        return null;
    }

    public static String getDate(String date) {
        if (date.equals("长期")){
            return "9999.99.99";
        }
        if (!TextUtils.isEmpty(date) && date.length() >= 8) {
            String substring = date.substring(0, 4);
            String substring1 = date.substring(4, 6);
            String substring2 = date.substring(6, 8);
            return substring + "." + substring1 + "." + substring2;
        }
        return "";
    }

    public static void getProvincialAssemble(Context mContext, ArrayList<ProvinceBean> options1Items
            , ArrayList<ArrayList<String>> options2Items) {
        List<ProvincialData> provincialData = Util.getProvincialData(mContext);
        for (int i = 0; i < provincialData.size(); i++) {
            ProvincialData provincialData1 = provincialData.get(i);
            options1Items.add(new ProvinceBean(i, provincialData1.getName(), "描述部分", "其他数据"));
            List<ProvincialData.ChildBean> child = provincialData1.getChild();
            ArrayList<String> optionsItem = new ArrayList<>();
            for (ProvincialData.ChildBean childBean : child) {
                optionsItem.add(childBean.getName());
            }
            options2Items.add(optionsItem);
        }

    }

    private static List<ProvincialData> getProvincialData(Context mContext) {
        StringBuilder newstringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = mContext.getResources().getAssets().open("ProvincialData.json");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                newstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = newstringBuilder.toString();
        return new Gson().fromJson(result, new TypeToken<List<ProvincialData>>() {
        }.getType());
    }


    public static void parseData(Context mContext, List<District> options1Items
            , ArrayList<ArrayList<String>> options2Items , ArrayList<ArrayList<ArrayList<String>>> options3Items) {
        ArrayList<District> district = getDistrict(mContext);
        options1Items.addAll(district);
        //遍历省份
        for (int i = 0; i < district.size(); i++) {
            //该省的城市列表（第二级）
            ArrayList<String> cityList = new ArrayList<>();
            //该省的所有地区列表（第三极）
            ArrayList<ArrayList<String>> provinceAreaList = new ArrayList<>();
            //遍历该省份的所有城市
            for (int c = 0; c < district.get(i).getCity().size(); c++) {
                String cityName = district.get(i).getCity().get(c).getName();
                //添加城市
                cityList.add(cityName);
                //该城市的所有地区列表
                ArrayList<String> cityAreaList = new ArrayList<>();
                List<District.CityBean.AreaBean> area = district.get(i).getCity().get(c).getArea();
                if (area != null){
                    for (int i1 = 0; i1 < area.size(); i1++) {
                        cityAreaList.add(area.get(i1).getName());
                    }
                }
                //添加该省所有地区数据
                provinceAreaList.add(cityAreaList);
            }
            //添加城市数据
            options2Items.add(cityList);

            //添加地区数据
            options3Items.add(provinceAreaList);
        }

    }

    private static ArrayList<District> getDistrict(Context mContext) {
        StringBuilder newstringBuilder = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = mContext.getResources().getAssets().open("district.json");
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String jsonLine;
            while ((jsonLine = reader.readLine()) != null) {
                newstringBuilder.append(jsonLine);
            }
            reader.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = newstringBuilder.toString();
        return new Gson().fromJson(result, new TypeToken<List<District>>() {
        }.getType());
    }


    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *k
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context mContext ,String phoneNum) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            mContext.startActivity(intent);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static List<String> getList(String s) {
        List<String> urlList;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser parser = new JsonParser();
            JsonElement je = parser.parse(s);
            urlList = gson.fromJson(je, new TypeToken<List<String>>() {
            }.getType());
        } catch (Exception e) {
            //兼容 string 的图
            urlList = new ArrayList<>();
            urlList.add(s);
        }
        return urlList;
    }


    /**
     * 大于一万 单位显示W
     * @param title   标题
     * @param content 显示数量
     * @param string  标题改变
     * @param string1 标题
     * @param number 数量
     */
    public static void setTenThousand(TextView title , TextView content ,String string,String string1, long number){
        if (number > 9999) {
            double div = AmountUtil.div(number, 10000, 2);
            content.setText(String.valueOf(div));
            title.setText(string);
        } else {
            title.setText(string1);
            content.setText(String.valueOf(number));
        }
    }

    public static String getNoodles(String noodles){
        StringBuilder stringBuilder = null;
        if (noodles.contains(",")){
            stringBuilder = new StringBuilder();
            noodles = noodles.replace("," , "");
            stringBuilder.append(noodles);
        }
        return stringBuilder == null ? noodles : stringBuilder.toString();
    }

    public static String[] getNoodle(String noodles){
        String[] title = null;
        if (noodles.contains(",")){
            title = noodles.split(",");
        }else if (!TextUtils.isEmpty(noodles)){
            title = new String[]{noodles};
        }
        return title;
    }

    public static List<String> getUrl(String noodles){
        if (TextUtils.isEmpty(noodles)){
            return new ArrayList<>();
        }if (noodles.contains("[")){
            List<String> stringList = new Gson().fromJson(noodles, new TypeToken<List<String>>() {
            }.getType());
            return stringList;
        }else {
            String[] title = null;
            if (noodles.contains(",")){
                title = noodles.split(",");
            }else if (!TextUtils.isEmpty(noodles)){
                title = new String[]{noodles};
            }
            return Arrays.asList(title);
        }
    }

    public static String getAddress(String address) {
        if (!TextUtils.isEmpty(address) && address.contains("/")){
            address = address.replaceAll("/","");
            if (address.contains("&")){
                address = address.replaceAll("&" , "");
            }
        }
        return address;
    }

    /**
     * @param address 省市数组
     * @return string[]
     */
    public static String[] getProvinceCity(String address) {
        if (!TextUtils.isEmpty(address) && address.contains("/")){
            return address.split("/");
        }
        return new String[]{address};
    }

    public static String getProvinceCityArea(String province , String city , String area , String addressDeatil){
        return province + "/" + city + "/" + area + "&" + addressDeatil;
    }
}
