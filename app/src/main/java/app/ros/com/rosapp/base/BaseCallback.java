package app.ros.com.rosapp.base;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;


/**
 * Created by Huxley on 2014/10/20.
 */
public abstract class BaseCallback extends RequestCallBack<String> {
    public void onSuccess(String json){ /* null */ }

    @Override
    public void onSuccess(ResponseInfo<String> stringResponseInfo) {
        onSuccess(stringResponseInfo.result);
    }

    @Override
    public void onFailure(HttpException e, String s) {
        Log.d("debug", "HttpFailure: " + e.getMessage());
    }
}
