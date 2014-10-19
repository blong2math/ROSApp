package app.ros.com.rosapp.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import app.ros.com.rosapp.R;
import app.ros.com.rosapp.base.BaseActivity;
import app.ros.com.rosapp.helper.SwitchHelper;
import app.ros.com.rosnotify.NotificationActivity;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        switchNext();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchNext(){
        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                SwitchHelper.startActivity(getActivity(), NotificationActivity.class);
            }
        }, 2000L);
    }
}
