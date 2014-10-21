package app.ros.com.rosapp.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import com.lidroid.xutils.ViewUtils;

/**
 * Created by Huxley on 2014/10/20.
 */
public class BaseFragment extends Fragment {

    protected void exit(){
        if ((getActivity() == null) || !(getActivity() instanceof BaseActivity))
            return;
        ((BaseActivity) getActivity()).exit();
    }

    protected Context getContext(){
        return getActivity().getApplicationContext();
    }

    protected Fragment getFragment(){
        return this;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onInitialize(savedInstanceState);
        onPostCreated(savedInstanceState);
    }

    protected void onInitialize(Bundle savedInstanceState) {
        ViewUtils.inject(getFragment(), getView());
    }

    protected void onPostCreated(Bundle savedInstanceState) {

    }
}
