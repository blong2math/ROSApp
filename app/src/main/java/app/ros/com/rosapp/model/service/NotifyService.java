package app.ros.com.rosapp.model.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.ros.com.rosapp.R;
import app.ros.com.rosapp.view.activity.DataActivity;
import app.ros.com.rosapp.view.activity.MainActivity;

public class NotifyService extends Service {
    public NotifyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public class AskForStrategy extends AsyncTask<String, String, String> {

        private NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        /*
         * Need to be modified for your need
         */
        private String stationId = "109";
        private static final String URL_STRATEGY = "www.baidu.com";
        private String compressedText = "Blue Shit";
        private int compressedIconId = R.drawable.access_icon;
        private String text = "Wa Jue Ji";
        private String errorMsg = "Red shit";

        @Override
        protected String doInBackground(String... strings) {
            InputStream is = null;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Station", stationId));
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(URL_STRATEGY);
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(params, "GBK"));
                HttpResponse response = httpClient.execute(httpPost);
                Log.d("Response:", response.getStatusLine().toString());
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            } catch(Exception e){
                Log.d("Login Error:", e.getMessage().toString());
            }
            if(is != null){
                try{
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "GBK"));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while((line = br.readLine()) != null){
                        sb.append(line + "\n");
                    }
                    is.close();
                    String result = sb.toString();
                    JSONTokener jsonParser = new JSONTokener(result);
                    JSONObject temp = (JSONObject) jsonParser.nextValue();
                    //status = temp.getString("state");  /* GET STRATEGY HERE!!! */
                    //uid = temp.getString("userid");
                }catch(JSONException e){
                    Log.d("JSON error",e.getMessage().toString());
                }catch(Exception e){
                    Log.d("Login Error:", e.getMessage().toString());
                }
            }else{
                return "error";
            }
            return "success";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equals("success")){
                Notification notif = new Notification(compressedIconId,
                        compressedText,
                        System.currentTimeMillis());
                // Jump to what Activity

                Intent intent = new Intent(getApplicationContext(), DataActivity.class);
                PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                notif.defaults = Notification.DEFAULT_SOUND;
                notif.flags = Notification.FLAG_AUTO_CANCEL;
                notif.setLatestEventInfo(getApplicationContext(),
                        "ROS",
                        text,
                        pi);
                nm.notify(0, notif);
            }else{
                Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
