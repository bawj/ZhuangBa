package com.xiaomai.zhuangba.util.download;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.example.toollib.BuildConfig;
import com.example.toollib.R;
import com.example.toollib.util.ToastUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.xiaomai.zhuangba.application.PretendApplication;

import java.io.File;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class DownLoadUtil {

    private static final String TAG = "DownLoadUtil";

    private static String mSaveFolder = FileDownloadUtils.getDefaultSaveRootPath() + File.separator ;

    private static String mSinglePath = mSaveFolder + "zhuangba";

    public static void downLoad(final Context mContext, String updateUrl, final OnDownloadListener onDownloadListener) {
        deleteFiles(new File(mSaveFolder));
        BaseDownloadTask singleTask = FileDownloader.getImpl().create(updateUrl)
                .setPath(mSinglePath, true)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                //.setTag()
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG, "pending: pending taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        //百分比
                        int progress = (int) ((soFarBytes * 1.0 / totalBytes) * 100);
                        onDownloadListener.onDownloading(progress);
                        Log.e(TAG, "pending: progress taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + progress + ",speed:" + task.getSpeed());
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.e(TAG, "pending: blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() + ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
                        String apkUrl = task.getPath() + "/" + "zhuangba.apk";
                        renameFile(task.getPath() + "/" + task.getFilename() , apkUrl);
                        Log.e(TAG , "apkurl = " + apkUrl);
                        installApk(apkUrl, mContext);
                        onDownloadListener.onDownloading(100);
                        onDownloadListener.onDownloadSuccess(new File(task.getPath()));
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.e(TAG, "pending: completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG, "pending: paused taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes + ",percent:" + soFarBytes * 1.0 / totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        ToastUtil.showShort(mContext.getString(R.string.tool_lib_down_load_error));
                        onDownloadListener.onDownloadFailed();
                        Log.e(TAG, "pending: error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.e(TAG, "pending: warn taskId:" + task.getId());
                    }
                });
        singleTask.start();
    }

    /**
     * 删除文件
     *
     * @param file 删除的文件夹的所在位置
     */
    private static void deleteFiles(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFiles(f);
            }
            ////如要保留文件夹，只删除文件，注释这行
            file.delete();
        } else if (file.exists()) {
            file.delete();
        }
    }

    private static void renameFile(String oldPath, String newPath) {
        File oleFile = new File(oldPath);
        File newFile = new File(newPath);
        //执行重命名
        oleFile.renameTo(newFile);
    }

    public static void installApk(String file, Context context) {
        File apkFile = new File(file);
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context.getApplicationContext(), (context.getPackageName() + ".fileprovider"), apkFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    public interface OnDownloadListener {
        /**
         * 下载成功
         *
         * @param file 下载成功后的文件
         */
        void onDownloadSuccess(File file);

        /**
         * 下载进度
         *
         * @param progress int
         */
        void onDownloading(int progress);

        /**
         * 下载异常信息
         */
        void onDownloadFailed();
    }

}
