package app.ros.com.rosapp.helper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import app.ros.com.rosapp.R;

/**
 * Created by Huxley on 2014/10/17.
 */
public class SwitchHelper {
    public static void startActivity(Activity paramActivity, Class<? extends Activity> paramClass){
        paramActivity.startActivity(new Intent(paramActivity, paramClass));
        paramActivity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
    }

    public static void startActivity(Activity paramActivity, Class<? extends Activity> paramClass, Bundle paramBundle){
        paramActivity.startActivity(new Intent(paramActivity, paramClass).putExtras(paramBundle));
        paramActivity.overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_from_left);
    }
}
