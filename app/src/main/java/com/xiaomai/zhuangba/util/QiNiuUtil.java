package com.xiaomai.zhuangba.util;

import android.net.Uri;

import com.example.toollib.http.exception.ApiException;
import com.example.toollib.util.Log;
import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;

import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class QiNiuUtil {

    private static QiNiuUtil qiNiuUtil;
    private static final String accessKey = "0jyopzon2rvnQJsBsEDYspeeLo9grHoUmL_fFbuR";
    private static final String secretKey = "maSmlklPLx66YRShRzQ6dyPgll1MPTJ_xA0sE5vE";
    // TODO: 2019/11/27 0027 正式发布需要修改
//    private static final String IMG_URL = "http://q1mh3knsr.bkt.clouddn.com/";
//    private static final String bucket = "zhengshi-zhuangba";

    //测试服 地址
    private static final String IMG_URL = "http://q1kc8zk9i.bkt.clouddn.com/";
    private static final String bucket = "zhuangba-upload-image";



    public static QiNiuUtil newInstance() {
        if (qiNiuUtil == null) {
            qiNiuUtil = new QiNiuUtil();
        }
        return qiNiuUtil;
    }

    /**
     * @param dirPath 图片地址 路径
     * @return UploadManager
     */
    private UploadManager getUploadManager(String dirPath) {
        Recorder recorder = null;
        try {
            recorder = new FileRecorder(dirPath);
        } catch (Exception e) {
        }
        //默认使用key的url_safe_base64编码字符串作为断点记录文件的文件名
        //避免记录文件冲突（特别是key指定为null时），也可自定义文件名(下方为默认实现)：
        KeyGenerator keyGen = new KeyGenerator() {
            public String gen(String key, File file) {
                // 不必使用url_safe_base64转换，uploadManager内部会处理
                // 该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名
                return key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
            }
        };

        Configuration.Builder config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(30)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https 默认是false
                .responseTimeout(120)          // 服务器响应超时。默认60秒
                .recorder(recorder)           // recorder分片上传时，已上传片记录器。默认null
                .recorder(recorder, keyGen)   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(FixedZone.zone2);// 设置区域，指定不同区域的上传域名、备用域名、备用IP。

        //UploadManager对象只需要创建一次重复使用
        return new UploadManager(config.build());
    }

    public Observable<List<String>> getObservable(final List<String> uriList) {
        //上传到七牛云的图片路径
        final List<String> resultImagePath = new ArrayList<>();
        final String token = Auth.create(QiNiuUtil.accessKey, QiNiuUtil.secretKey).uploadToken(QiNiuUtil.bucket);
        return Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<String>> emitter) throws URISyntaxException {
                for (String uri : uriList) {
                    Uri compressPath = Uri.parse(uri);
                    File file = new File(new URI(compressPath.toString()));
                    String path = file.getPath();
                    String absolutePath = file.getAbsolutePath();
                    Log.e("path = " + path);
                    Log.e("absolutePath = " + path);
                    getUploadManager(path)
                            .put(path, getImgName(), token, new UpCompletionHandler() {
                                @Override
                                public void complete(String key, ResponseInfo info, JSONObject response) {
                                    Log.e("key：" + key + "\ninfo：" + info + "\nres：" + response);
                                    if (info.isOK()) {
                                        Log.e("上传结果：Upload Success");
                                        // 七牛返回的文件名
                                        resultImagePath.add(IMG_URL + key);
                                        //将七牛返回图片的文件名添加到list集合中
                                        //list集合中图片上传完成后,发送消息回主线程进行其他操作
                                        if (uriList.size() == resultImagePath.size()) {
                                            emitter.onNext(resultImagePath);
                                        }
                                    } else {
                                        Log.e("上传结果：Upload Fail");
                                        throw new ApiException(Integer.parseInt(String.valueOf(info.statusCode)), info.error, response.toString());
                                    }
                                }
                            }, null);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * @return 上传到服务器的文件名
     */
    public String getImgName() {
        return UUIDUtil.getUuidUitl().generateOrderlyUUID() + ".jpg";
    }

}
