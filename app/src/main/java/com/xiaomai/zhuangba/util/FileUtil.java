package com.xiaomai.zhuangba.util;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class FileUtil {

    public static File getSaveFile(Context context) {
        return new File(context.getFilesDir(), System.currentTimeMillis() + "pic.jpg");
    }

    public static void saveBitmap(String absolutePath, Bitmap mBitmap) {
        try {
            FileOutputStream fOut = new FileOutputStream(new File(absolutePath));
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
