package app.ros.com.rosapp.view.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Random;

import app.ros.com.rosapp.R;
import app.ros.com.rosapp.base.BaseCallback;
import app.ros.com.rosapp.control.api.ApiHelper;
import app.ros.com.rosapp.model.sp.OwnerHelper;
import app.ros.com.rosapp.view.fragment.NavigationDrawerFragment;

/**
 * Created by Huxley on 2014/10/20.
 */
public class LoginDialog extends DialogFragment {
    private static final String ACTION_LOGIN_SUCCEED = "ACTION_LOGIN_SUCCEED";

    @ViewInject(R.id.dialog_login_username)
    private EditText username;

    @ViewInject(R.id.dialog_login_password)
    private EditText password;

    private String tag = String.valueOf(new Random().nextLong());

    public AlertDialog getLoginDialog(){
        return (AlertDialog)getDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_login,
                (ViewGroup)getActivity().findViewById(R.id.dialog_login_root));
        ViewUtils.inject(this, view);
        builder.setView(view);
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /** null */
            }
        });

        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ApiHelper apiHelper = ApiHelper.getSingleton(getActivity().getApplicationContext());
                apiHelper.postUser(username.getText().toString(), password.getText().toString(), new UserCallBack());
            }
        });

        return builder.create();
    }

    public void show(FragmentManager manager) {
        super.show(manager, tag);
    }

    class UserCallBack extends BaseCallback{
        @Override
        public void onSuccess(String json) {
            JSONObject jobj = JSON.parseObject(json);
            OwnerHelper ownerHelper = OwnerHelper.getSingleton(getActivity());
            ownerHelper.setOwnerId(jobj.getInteger("id"));
            ownerHelper.setOwnerToken(jobj.getString("token"));
            ownerHelper.setOwnerAvater(jobj.getString("avater"));
            LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(new Intent(ACTION_LOGIN_SUCCEED));
        }
    }
}
