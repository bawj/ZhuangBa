package fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;

import exocr.exocrengine.EXOCRModel;
import lib.kalu.ocr.CaptureView;
import lib.kalu.ocr.OcrSurfaceView;
import lib.kalu.ocr.R;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class IdCardRecognitionFragment extends BaseFragment {

    public static final String FRONT = "front";
    public static final String RESULT = "result";
    public static final int RESULT_CODE = 1234;

    private TranslateAnimation animation;

    private ImageView captureScanLine;
    private CaptureView capture;
    private OcrSurfaceView surface;
    private TextView text;
    private ImageView ivBack;

    public static IdCardRecognitionFragment newInstance(boolean front) {
        Bundle args = new Bundle();
        args.putBoolean(FRONT , front);
        IdCardRecognitionFragment fragment = new IdCardRecognitionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        if (view != null) {
            captureScanLine = view.findViewById(R.id.capture_scan_line);
            capture = view.findViewById(R.id.captuer_scan);
            surface = view.findViewById(R.id.surface);
            text = view.findViewById(R.id.text);
            ivBack = view.findViewById(R.id.ivBack);

            final boolean isFront = isFront();
            capture.setFront(isFront);

            surface.setOnOcrChangeListener(new OcrSurfaceView.OnOcrChangeListener() {
                @Override
                public void onSucc(EXOCRModel exocrModel) {
                    if (exocrModel.isOk(isFront) && getActivity() != null) {
                        Intent intent = new Intent();
                        intent.putExtra(RESULT, exocrModel);
                        getActivity().setResult(RESULT_CODE, intent);
                        getBaseFragmentActivity().popBackStack();
                    } else {
                        text.setText(getString(R.string.tip));
                    }
                }
            });

            animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.f, Animation.RELATIVE_TO_PARENT,
                    0.f, Animation.RELATIVE_TO_PARENT, -0.3f, Animation.RELATIVE_TO_PARENT, 0.8f);
            animation.setDuration(2500);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.RESTART);
            captureScanLine.setAnimation(animation);

            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getBaseFragmentActivity().finish();
                }
            });
        }

        //getBaseFragmentActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    private boolean isFront(){
        if (getArguments() != null){
            return getArguments().getBoolean(FRONT);
        }
        return false;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_id_card_recognition;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ///getBaseFragmentActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (animation != null){
            animation.cancel();
        }
    }
}
