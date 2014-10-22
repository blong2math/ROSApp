package app.ros.com.rosapp.view.fragment;

/**
 * Created by Huxley on 2014/10/18.
 */

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnFocusChange;

import app.ros.com.rosapp.R;
import app.ros.com.rosapp.base.BaseFragment;
import app.ros.com.rosapp.helper.SwitchHelper;
import app.ros.com.rosapp.view.activity.MainActivity;
import app.ros.com.rosapp.view.activity.SearchableActivity;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends BaseFragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    @ViewInject(R.id.searchable_text)
    private EditText searchText;

    @ViewInject(R.id.main_webView)
    private WebView webView;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MainFragment newInstance(int sectionNumber) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {
    }

    @Override
    protected void onInitialize(Bundle savedInstanceState) {
        super.onInitialize(savedInstanceState);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @OnFocusChange(R.id.searchable_text)
    public void onSearchableTextClick(View view, boolean b){
        SwitchHelper.startActivity(getActivity(), SearchableActivity.class, SwitchHelper.ANIM_FADE);
    }
}
