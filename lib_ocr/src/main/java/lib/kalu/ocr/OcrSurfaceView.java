package lib.kalu.ocr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.toollib.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import exocr.exocrengine.EXOCREngine;
import exocr.exocrengine.EXOCRModel;

/**
 * description: 后置摄像头, ocr
 * create by kalu on 2018/12/7 21:45
 */
public class OcrSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private Context mContext;
    private Camera mCamera;

    /**********************************************************************************************/

    public OcrSurfaceView(Context context) {
        this(context, null, 0);
        this.mContext = context;
    }

    public OcrSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.mContext = context;
    }

    public OcrSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        boolean b = EXOCREngine.InitDict(getContext().getApplicationContext());
        if (!b) {
            Toast.makeText(getContext().getApplicationContext(), "初始化失败", Toast.LENGTH_SHORT).show();
            return;
        }

        getHolder().addCallback(this);
        getHolder().setKeepScreenOn(true);
        getHolder().setFormat(PixelFormat.TRANSPARENT);
        getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    //    int width = 640;
//    int height = 480;
//    int mWidth = 1920;
//    int mHeight = 1080;
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
////        int imageHeight = getScreenHeight(mContext);
////        int imageWidth = getScreenWidth(mContext);
////        Log.i("onMeasure " + imageHeight + "x" + imageWidth);
//        setMeasuredDimension(4160, 3120);
//    }

    /**
     * 获取屏幕宽度（px）
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    /**
     * 获取屏幕高度（px）
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取最佳预览大小
     *
     * @param sizes 所有支持的预览大小
     * @param w     SurfaceView宽
     * @param h     SurfaceView高
     * @return
     */
    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) w / h;
        if (sizes == null){
            return null;
        }

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = h;

        // Try to find an size match aspect ratio and size
        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE){
                continue;
            }
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        // Cannot find the one match the aspect ratio, ignore the requirement
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    /**********************************************************************************************/

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        final PackageManager pm = getContext().getPackageManager();
        final boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
        if (!hasACamera) {
            Log.e("没有发现相机");
            return;
        }

        try {
            mCamera = Camera.open();

            //得到摄像头的参数
            Camera.Parameters parameters = mCamera.getParameters();
            //设置照片的质量
            parameters.setJpegQuality(100);



//            List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();
//            bestVideoSize(supportedVideoSizes, previewSizes.get(0).width);
//            Camera.Size size = supportedVideoSizes.get(0);

            List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();//获取所有支持的预览大小
            Camera.Size bestSize = getOptimalPreviewSize(sizeList, getScreenWidth(mContext), getScreenHeight(mContext));

            //设置预览尺寸
            parameters.setPreviewSize(bestSize.width, bestSize.height);
            //设置照片尺寸
            parameters.setPictureSize(bestSize.width, bestSize.height);
            Log.i("设置的预览尺寸" + bestSize.width + "x" + bestSize.height);


            List<Camera.Size> supportedVideoSizes = parameters.getSupportedPictureSizes();
            StringBuilder s = new StringBuilder();
            for (Camera.Size sizes : supportedVideoSizes) {
                if (s.length() != 0) {
                    s.append(",\n");
                }
                s.append(sizes.width).append('x').append(sizes.height);
            }
            Log.i("摄像头分辨率全部: " + "\n" + s);

            // 连续对焦模式
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            mCamera.cancelAutoFocus();
            //Camera.Parameters.FOCUS_MODE_AUTO; //自动聚焦模式
            //Camera.Parameters.FOCUS_MODE_INFINITY;//无穷远
            //Camera.Parameters.FOCUS_MODE_MACRO;//微距
            //Camera.Parameters.FOCUS_MODE_FIXED;//固定焦距
            mCamera.setParameters(parameters);

            Camera.CameraInfo info = new Camera.CameraInfo();
            //获取摄像头信息
            Camera.getCameraInfo(0, info);
            WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            int rotation = manager.getDefaultDisplay().getRotation();
            //获取摄像头当前的角度
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
                default:
            }
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                // 前置摄像头
                int result = (360 - ((info.orientation + degrees) % 360)) % 360; // compensate the mirror
                mCamera.setDisplayOrientation(result);
            } else {
                // 后置摄像头
                int result = (info.orientation - degrees + 360) % 360;
                mCamera.setDisplayOrientation(result);
            }
            //通过SurfaceView显示取景画面
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();//开始预览

            mCamera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    //Log.e("kalu", "onPreviewFrame");

                    boolean alive = mThread.isAlive();
                    if (!alive) {
                        return;
                    }
                    //Log.e("kalu11", "setPreviewCallback ==> ");

                    final Message obtain = Message.obtain();
                    obtain.obj = data;
                    //obtain.arg1 = 640;
                    //obtain.arg2 = 480;,
                    obtain.arg1 = bestSize.width;
                    obtain.arg2 = bestSize.height;
                    mHandler.removeCallbacksAndMessages(null);
                    mHandler.sendMessage(obtain);
                }
            });

        } catch (Exception e) {
            if (mCamera != null) {
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

        EXOCREngine.clearDict();
    }

    /**********************************************************************************************/

    @SuppressLint("HandlerLeak")
    private final Handler mMain = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {

            if (null == listener || null == msg.obj) {
                return;
            }

            final EXOCRModel model = (EXOCRModel) msg.obj;
            listener.onSucc(model);
        }
    };

    private final HandlerThread mThread = new HandlerThread("OcrThread");

    {
        mThread.start();
    }

    private final Handler mHandler = new Handler(mThread.getLooper()) {
        @Override
        public void handleMessage(Message msg) {

            final byte[] data = (byte[]) msg.obj;
            final int width = msg.arg1;
            final int height = msg.arg2;

            final EXOCRModel decode = EXOCREngine.decodeByte(data, width, height);
            if (null == decode) {
                return;
            }

            final Message obtain = Message.obtain();
            obtain.obj = decode;
            obtain.arg1 = width;
            obtain.arg2 = height;
            mMain.removeCallbacksAndMessages(null);
            mMain.sendMessage(obtain);

            mHandler.removeCallbacksAndMessages(null);
            mThread.quit();
        }
    };

    /**********************************************************************************/

    private OnOcrChangeListener listener;

    public interface OnOcrChangeListener {
        void onSucc(final EXOCRModel exocrModel);
    }

    public void setOnOcrChangeListener(OnOcrChangeListener listener) {
        this.listener = listener;
    }


}
