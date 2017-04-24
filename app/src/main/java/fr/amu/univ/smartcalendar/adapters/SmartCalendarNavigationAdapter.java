package fr.amu.univ.smartcalendar.adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.ui.activities.ViewEventActivity;
import fr.amu.univ.smartcalendar.utils.SmartCalendarImageLoader;

/**
 *
 * Created by j.Katende on 19/04/2017.
 */

public class SmartCalendarNavigationAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> renderData;
    private static LayoutInflater inflater = null;
    public SmartCalendarImageLoader imageLoader;

    public SmartCalendarNavigationAdapter(Context base, ArrayList<HashMap<String, String>> data){
        context = base;
        renderData = data;
        inflater = (LayoutInflater)base.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new SmartCalendarImageLoader(base.getApplicationContext());
    }

    @Override
    public int getCount() {
        return renderData.size();
    }

    @Override
    public Object getItem(int i) {
        return renderData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.navigation_layout, viewGroup, false);
        if(view == null){
            view = inflater.inflate(R.layout.navigation_layout, null);
        }
        TextView trafficJamTitle = (TextView)view.findViewById(R.id.smart_calendar_traffic_jam_title);
        TextView trafficJamDuration = (TextView)view.findViewById(R.id.smart_calendar_traffic_ride_duration);
        TextView trafficDistance = (TextView)view.findViewById(R.id.smart_calendar_traffic_distance);

        HashMap<String, String> trafficItem = new HashMap<>();
        trafficItem = renderData.get(i);

        trafficJamTitle.setText(R.string.smart_calendar_itinerary_label + " " + (i + 1));
        trafficJamDuration.setText(R.string.smart_calendar_duration_label + " : " + trafficItem.get(ViewEventActivity.TRAFFIC_KEY_DURATION));
        trafficDistance.setText(trafficItem.get(ViewEventActivity.TRAFFIC_KEY_DISTANCE));
        return view;
    }
}
