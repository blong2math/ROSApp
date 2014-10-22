package app.ros.com.rosapp.control.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

import app.ros.com.rosapp.R;
import app.ros.com.rosapp.control.bean.TravelInfoBean;

/**
 * Created by Huxley on 2014/10/22.
 */
public class TravelInfoAdapter extends BaseAdapter {
    private Context ctx;
    private List<TravelInfoBean> list;

    public TravelInfoAdapter(Context ctx, List<TravelInfoBean> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public TravelInfoBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TravelInfoBean tib = list.get(i);
        if (null == view){
            view = LayoutInflater.from(ctx).inflate(R.layout.item_travelinfo, viewGroup, false);
            view.setTag(new ViewHolder(view));
        }
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        viewHolder.setTravelStartTimeText(tib.getStartTime());
        viewHolder.setTravelArrivalTimeText(tib.getArrivalTime());
        viewHolder.setTravelNarrowStateText(tib.getNarrowState());

        return view;
    }

    public static class ViewHolder{
        @ViewInject(R.id.travel_start_time)
        private TextView travelStartTimeText;

        @ViewInject(R.id.travel_arrival_time)
        private TextView travelArrivalTimeText;

        @ViewInject(R.id.travel_narrow_state)
        private TextView travelNarrowStateText;

        public ViewHolder(View paramView){
            ViewUtils.inject(this, paramView);
        }

        public void setTravelStartTimeText(String startTime){
            travelStartTimeText.setText(travelStartTimeText.getText().toString() + startTime);
        }

        public void setTravelArrivalTimeText(String arrivalTime){
            travelArrivalTimeText.setText(travelArrivalTimeText.getText().toString() + arrivalTime);
        }

        public void setTravelNarrowStateText(String narrowStateText){
            travelNarrowStateText.setText(travelNarrowStateText.getText().toString() + narrowStateText);
        }
    }
}
