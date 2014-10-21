package app.ros.com.rosapp.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.lidroid.xutils.ViewUtils;

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

    protected void onInitialize(){
        /** Fill this block with initialize settings. */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(getBroadcastReceiver(), getIntentFilter());
        if (null != getActionBar())
            getActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        onInitialize();
    }

    protected Activity getActivity(){
        return this;
    }

    protected Context getContext(){
        return getApplicationContext();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ViewUtils.inject(getActivity());
    }

    protected void exit(){
        LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent(ACTION_EXIT_APPLICATION));
    }
}
