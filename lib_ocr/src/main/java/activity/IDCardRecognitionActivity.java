package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.toollib.base.BaseActivity;
import com.example.toollib.util.Log;

import fragment.IdCardRecognitionFragment;
import lib.kalu.ocr.R;

/**
 * @author Administrator
 * @date 2019/8/2 0002
 */
public class IDCardRecognitionActivity extends BaseActivity {

    public static final String FRONT = "front";

    @Override
    protected int getContextViewId() {
        return R.layout.activity_id_card_recognition;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isFront = getIntent().getBooleanExtra(FRONT, true);
        IdCardRecognitionFragment idCardRecognitionFragment = IdCardRecognitionFragment.newInstance(isFront);
        getSupportFragmentManager()
                .beginTransaction()
                .add(getContextViewId(), idCardRecognitionFragment, idCardRecognitionFragment.getClass().getSimpleName())
                .addToBackStack(idCardRecognitionFragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("resultCode = " + resultCode + "--data" + data);
    }
}
