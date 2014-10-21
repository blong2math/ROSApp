package app.ros.com.rosapp.control.api;

import android.content.Context;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Huxley on 2014/10/20.
 */
public class ApiHelper {
    private static final String URL = "http://rsbase.sinaapp.com/ros";

    private static ApiHelper singleton;
    private HttpUtils client;
    private Context ctx;

    private ApiHelper(Context ctx){
        this.ctx = ctx;
        this.client = new HttpUtils();
        this.client.configDefaultHttpCacheExpiry(10000L);
    }

    public static ApiHelper getSingleton(Context ctx){
        if (null == singleton) singleton = new ApiHelper(ctx);
        return singleton;
    }

    private void post(String url, Map<String, String> postData, RequestCallBack callBack){
        RequestParams params = new RequestParams();
        Set<String> keys = postData.keySet();
        for (String key: keys)
            params.addBodyParameter(key, postData.get(key));

        client.send(HttpRequest.HttpMethod.POST, url, params, callBack);
    }

    public void postUser(String username, String password, RequestCallBack callBack){
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("username", username);
        postData.put("password", password);
        post(URL + "/login", postData, callBack);
    }

}
