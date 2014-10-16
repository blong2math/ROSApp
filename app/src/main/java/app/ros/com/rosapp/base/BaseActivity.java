package app.ros.com.rosapp.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

/**
 * Created by Huxley on 2014/10/17.
 */
public class BaseActivity extends Activity {
    private static final String ACTION_EXIT_APPLICATION = "ACTION_EXIT_APPLICATION";

    private BroadcastReceiver getBroadcastReceiver(){
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                BaseActivity.this.finish();
            }
        };
    }

    private IntentFilter getIntentFilter(){
        return  new IntentFilter(ACTION_EXIT_APPLICATION);
    }

    protected Activity getActivity(){
        return this;
    }

    protected Context getContext(){
        return getApplicationContext();
    }

    protected void exit(){
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(ACTION_EXIT_APPLICATION));
    }
}
