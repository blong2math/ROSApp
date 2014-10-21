package app.ros.com.rosapp.model.sp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Huxley on 2014/10/20.
 */
public class OwnerHelper {
    private static final String OWNER_AVATER = "owneravater";
    private static final String OWNER_ID = "ownerid";
    private static final String OWNER_ROLE = "ownerrole";
    private static final String OWNER_TOKEN = "ownertoken";

    private static OwnerHelper singleton;
    private SharedPreferences sp;

    private OwnerHelper(Context ctx){
        sp = ctx.getSharedPreferences("ownersp", 0);
    }

    public static OwnerHelper getSingleton(Context ctx){
        if (null == singleton) singleton = new OwnerHelper(ctx);
        return singleton;
    }

    public void clear(){
        sp.edit().clear().apply();
    }

    public String getOwnerAvater(){
        return sp.getString(OWNER_AVATER, "");
    }

    public void setOwnerAvater(String avater){
        sp.edit().putString(OWNER_AVATER, avater).apply();
    }

    public int getOwnerId(){
        return sp.getInt(OWNER_ID, -1);
    }

    public void setOwnerId(int id){
        sp.edit().putInt(OWNER_ID, id).apply();
    }

    public String getOwnerToken(){
        return sp.getString(OWNER_TOKEN, "");
    }

    public void setOwnerToken(String token){
        sp.edit().putString(OWNER_TOKEN, token).apply();
    }

    public String getOwnerRole(){ return sp.getString(OWNER_ROLE, "user"); }

    public void setOwnerRole(String role){
        sp.edit().putString(OWNER_ROLE, role).apply();
    }
}
