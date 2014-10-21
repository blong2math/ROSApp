package app.ros.com.rosapp.view.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import app.ros.com.rosapp.R;
import app.ros.com.rosapp.base.BaseActivity;
import app.ros.com.rosapp.view.customized.ROSView;

public class SearchableActivity extends BaseActivity {

    ROSView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        mapView = (ROSView) findViewById(R.id.subway_map);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.searchable, menu);
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
}
