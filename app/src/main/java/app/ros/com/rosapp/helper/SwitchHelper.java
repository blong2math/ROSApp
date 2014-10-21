package app.ros.com.rosapp.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import app.ros.com.rosapp.R;

/**
 * Created by Huxley on 2014/10/17.
 */
public class SwitchHelper {
    public static final int ANIM_FADE = 0;
    public static final int ANIM_SLIDE = 1;

    public static void startActivity(Activity paramActivity, Class<? extends Activity> paramClass, int type){
        paramActivity.startActivity(new Intent(paramActivity, paramClass));
        setAnimType(paramActivity, type);
    }

    public static void startActivity(Activity paramActivity, Class<? extends Activity> paramClass, int type, Bundle paramBundle){
        paramActivity.startActivity(new Intent(paramActivity, paramClass).putExtras(paramBundle));
        setAnimType(paramActivity, type);
    }

    private static void setAnimType(Activity paramActivity, int type){
        switch (type){
            case ANIM_FADE:
                paramActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;
            case ANIM_SLIDE: default:
                paramActivity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
        }
    }
}
