package com.xiaomai.zhuangba.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
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

import com.example.toollib.util.SPUtils;
import com.xiaomai.zhuangba.R;
import com.xiaomai.zhuangba.data.db.DBHelper;
import com.xiaomai.zhuangba.spf.SpfConst;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class Util {

    public static void logout() {
        DBHelper.getInstance().getUserInfoDao().deleteAll();
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
}
