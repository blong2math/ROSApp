package app.ros.com.rosapp.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import app.ros.com.rosapp.R;
import app.ros.com.rosapp.base.BaseActivity;
import app.ros.com.rosapp.base.BaseCallback;
import app.ros.com.rosapp.control.adapter.TravelInfoAdapter;
import app.ros.com.rosapp.control.api.ApiHelper;
import app.ros.com.rosapp.control.bean.TravelInfoBean;
import app.ros.com.rosapp.helper.PathFinder;
import app.ros.com.rosapp.view.customized.ROSView;

public class SearchableActivity extends BaseActivity {
    private static final int ANIMATION_DURATION = 400;
    private static final int SAVED_HISTORY_LENGTH = 10;

    private int[] tempStationIds = {102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113,
                                    114, 115, 116, 117, 119, 120, 121, 122, 123, 201, 202, 203,
                                    204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215,
                                    216, 217, 218, 301, 302, 303, 304, 305, 306, 307, 308, 309,
                                    310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321,
                                    322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333,
                                    334, 335, 336, 337, 338, 339, 609, 610, 611, 612, 613, 614,
                                    615, 616, 617, 618, 620, 621, 623, 625, 626, 628, 632, 633};

    private String[] tempStationNames = {"小什字", "较场口", "七星岗", "两路口", "鹅岭", "大坪",
                                        "石油路", "歇台子", "石桥铺", "高庙村", "马家岩", "小龙坎",
                                        "沙坪坝", "杨公桥", "烈士墓", "磁器口", "双碑", "赖家桥",
                                        "微电园", "陈家桥", "大学城", "较场口", "临江门", "黄花园",
                                        "大溪沟", "曾家岩", "牛角沱", "李子坝", "佛图关", "大坪",
                                        "袁家岗", "谢家湾", "杨家坪", "动物园", "大堰村", "马王场",
                                        "平安", "大渡口", "新山村", "鱼洞", "金竹", "鱼胡路",
                                        "学堂湾", "大山村", "花溪", "岔路口", "九公里", "麒龙",
                                        "八公里", "二塘", "六公里", "五公里", "四公里", "南坪",
                                        "工贸", "铜元局", "两路口", "牛角沱", "华新街", "观音桥",
                                        "红旗河沟", "嘉州路", "郑家院子", "唐家院子", "狮子坪", "重庆北站",
                                        "龙头寺", "童家院子", "双龙", "金渝", "金童路", "鸳鸯",
                                        "园博园", "翠云", "长福路", "回兴", "碧津", "江北机场",
                                        "五里店", "红土地", "黄泥塝", "红旗河沟", "花卉园", "大龙山",
                                        "冉家坝", "光电园", "大竹林", "康庄", "礼嘉", "金山寺",
                                        "蔡家", "龙凤溪", "状元碑", "北碚", "国博中心", "悦来"};

    private ArrayList<String> stationNames = new ArrayList<String>();
    private ArrayList<Integer> stationIds = new ArrayList<Integer>();

    @ViewInject(R.id.map_view)
    private ROSView mMapView;

    @ViewInject(R.id.search_icon)
    private ImageView mSearchIcon;

    @ViewInject(R.id.searchable)
    private LinearLayout mSearchable;

    @ViewInject(R.id.search_detail)
    private LinearLayout mSearchDetail;

    @ViewInject(R.id.origin_text)
    private AutoCompleteTextView mOriginText;

    @ViewInject(R.id.terminal_text)
    private AutoCompleteTextView mTerminalText;

    @ViewInject(R.id.travelling_textView)
    private TextView mTravellingTextView;

    @ViewInject(R.id.travelling_listView)
    private ListView mTravellingListView;

    @Override
    protected void onInitialize() {
        super.onInitialize();
        mSearchDetail.setVisibility(View.GONE);
        // set auto completion
        initSearchDetialHistory("search_history", mOriginText);
        initSearchDetialHistory("search_history", mTerminalText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        for(int i = 0; i < tempStationIds.length; i++){
            stationIds.add(tempStationIds[i]);
            stationNames.add(tempStationNames[i]);
        }
        mMapView.getBackground().setAlpha(255);
        mTerminalText.setOnKeyListener(new ShowRouteListener());
        mOriginText.setOnKeyListener(new ShowRouteListener());
    }



    class ShowRouteListener implements View.OnKeyListener{

        public int terminalId = 0;
        public int originId = 0;
        private ArrayList<Integer> path;

        @Override
        public boolean onKey(View view, int index, KeyEvent keyEvent) {

            if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                String terminal = mTerminalText.getText().toString();
                String origin = mOriginText.getText().toString();
                if(!(terminal.equals("")||origin.equals(""))){
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                    int terminalIndex = stationNames.indexOf(terminal);
                    int originIndex = stationNames.indexOf(origin);
                    terminalId = stationIds.get(terminalIndex);
                    originId = stationIds.get(originIndex);

                    new PathSearchTask().execute();
                    
                    mMapView.getBackground().setAlpha(130);
                }
            }

            return false;
        }

        class PathSearchTask extends AsyncTask<String, String, String>{

            @Override
            protected String doInBackground(String... strings) {
                PathFinder pf = new PathFinder();
                path = pf.dijkstra(originId, terminalId);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                mMapView.setHighlight(R.drawable.highlight);
                mMapView.highlightStation(path);
                super.onPostExecute(s);
            }
        }
    }



    @OnClick(R.id.search_icon)
    private void OnSearchIconClicked(View view){
        if (View.VISIBLE == mSearchDetail.getVisibility()){
            Animation alphaDecrAnimation = new AlphaAnimation(1, 0);
            alphaDecrAnimation.setDuration(ANIMATION_DURATION);
            mSearchDetail.setVisibility(View.GONE);
            mSearchDetail.setAnimation(alphaDecrAnimation);
        } else if (View.GONE == mSearchDetail.getVisibility()){
            Animation alphaIncrAnimation = new AlphaAnimation(0, 1);
            alphaIncrAnimation.setDuration(ANIMATION_DURATION);
            mSearchDetail.setVisibility(View.VISIBLE);
            mSearchDetail.setAnimation(alphaIncrAnimation);
        }
    }



    private void initSearchDetialHistory(final String field, final AutoCompleteTextView autoCompleteTextView){
        SharedPreferences sp = getSharedPreferences("histories", 0);
        String rawHistory = sp.getString(field, "");
        String[] histories = {};
        if(rawHistory.length() != 0){
            histories = rawHistory.split(",");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdown_item_line, histories);
        if (histories.length > SAVED_HISTORY_LENGTH){ // only save `SAVED_HISTORY_LENGTH` records
            String[] newHistories = new String[SAVED_HISTORY_LENGTH];
            System.arraycopy(histories, 0, newHistories, 0, SAVED_HISTORY_LENGTH);
            adapter = new ArrayAdapter<String>(this, R.layout.dropdown_item_line, newHistories);
        }
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                System.out.println("FocusChange");
                AutoCompleteTextView v = (AutoCompleteTextView)view;
                if (0 != v.getAdapter().getCount() && hasFocus)
                    v.showDropDown();
                if (!hasFocus){ // save search item
                    System.out.println("hasFocus");
                    String item = v.getText().toString().trim();
                    if ("".equals(item)) return;
                    SharedPreferences sp = getSharedPreferences("histories", 0);
                    String rawHistory = sp.getString(field, "");
                    if (rawHistory.contains(item)) return;
                    rawHistory += 0 == rawHistory.length() ? v.getText() : "," + v.getText();
                    sp.edit().putString(field, rawHistory).apply();

                    if (!"".equals(mOriginText.getText().toString()) && !"".equals(mTerminalText.getText().toString())){
                        ApiHelper apiHelper = ApiHelper.getSingleton(getContext());
                        apiHelper.postTravelInfo(mOriginText.getText().toString(),
                                mTerminalText.getText().toString(), new TravelInfoCallback());
                    }
                }
            }

        });
    }

    @OnItemClick(R.id.travelling_listView)
    public void OnTravellingListViewClick(final AdapterView<?> adapterView, View view, int i, long l){
        new AlertDialog.Builder(getActivity()).setTitle(R.string.book_confirm).
                setNegativeButton(R.string.cancel, null).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String subwayId = ((TravelInfoBean)(adapterView.getAdapter().getItem(i))).getId();

                ApiHelper apiHelper = ApiHelper.getSingleton(getContext());
                apiHelper.book(subwayId, new BookCallback());
            }
        }).show();
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

    class TravelInfoCallback extends BaseCallback{
        @Override
        public void onSuccess(String json) {
            List<TravelInfoBean> list = JSON.parseArray(json, TravelInfoBean.class);
            mTravellingListView.setAdapter(new TravelInfoAdapter(getContext(), list));

            mSearchable.setVisibility(View.GONE);
            mTravellingTextView.setVisibility(View.VISIBLE);
            mTravellingListView.setVisibility(View.VISIBLE);
        }
    }

    class BookCallback extends BaseCallback{
        @Override
        public void onSuccess(String json) {
            Toast.makeText(getContext(), getString(R.string.book_succeed), Toast.LENGTH_SHORT).show();
        }
    }
}
