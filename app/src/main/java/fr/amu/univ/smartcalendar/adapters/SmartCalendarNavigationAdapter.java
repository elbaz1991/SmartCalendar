package fr.amu.univ.smartcalendar.adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.plugins.direction.constant.SmartCalendarTransportMode;
import fr.amu.univ.smartcalendar.ui.activities.NavigateToEventActivity;
import fr.amu.univ.smartcalendar.ui.activities.ViewEventActivity;
import fr.amu.univ.smartcalendar.utils.SmartCalendarImageLoader;

/**
 *
 * Created by j.Katende on 19/04/2017.
 */

public class SmartCalendarNavigationAdapter {
    private Context context;
    private HashMap<String, String> renderData;
    private static LayoutInflater inflater = null;
    public SmartCalendarImageLoader imageLoader;

    public SmartCalendarNavigationAdapter(Context base, HashMap<String, String> data){
        context = base;
        renderData = data;
        inflater = (LayoutInflater)base.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new SmartCalendarImageLoader(base.getApplicationContext());
    }

    public View getView(int i){ //int i, View view, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.navigation_layout, null, false);
        /*if(view == null){
            view = inflater.inflate(R.layout.navigation_layout, null);
        }*/
        TextView trafficJamTitle = (TextView)view.findViewById(R.id.smart_calendar_traffic_jam_title);
        TextView trafficJamDuration = (TextView)view.findViewById(R.id.smart_calendar_traffic_ride_duration);
        TextView trafficDistance = (TextView)view.findViewById(R.id.smart_calendar_traffic_distance);

        trafficJamTitle.setText(context.getResources().getString(R.string.smart_calendar_itinerary_label) + " " + (i + 1));
        trafficJamDuration.setText(context.getResources().getString(R.string.smart_calendar_duration_label) + " : " + renderData.get(ViewEventActivity.TRAFFIC_KEY_DURATION));
        trafficDistance.setText(context.getResources().getString(R.string.smart_calendar_distance_label) + " : " + renderData.get(ViewEventActivity.TRAFFIC_KEY_DISTANCE));
        ImageView transportMode = (ImageView)view.findViewById(R.id.smart_calendar_traffic_jam_icon);
        SmartCalendarImageLoader imageLoader = new SmartCalendarImageLoader(context);
        //context.getResources().getDrawable(R.drawable.bicycle).get
        //String fileDirectory = "android.resource://fr.amu.univ.smartcalendar/drawable/";
        //String path;
        switch (renderData.get(ViewEventActivity.TRAFFIC_KEY_TRANSPORT_MODE)){
            case SmartCalendarTransportMode.BICYCLING:
                if(Build.VERSION.SDK_INT >= 21) {
                    transportMode.setImageDrawable(context.getDrawable(R.drawable.bicycle));
                }else{
                    transportMode.setImageDrawable(context.getResources().getDrawable(R.drawable.bicycle));
                }
                break;
            case SmartCalendarTransportMode.DRIVING:
                if(Build.VERSION.SDK_INT >= 21) {
                    transportMode.setImageDrawable(context.getDrawable(R.drawable.car));
                }else{
                    transportMode.setImageDrawable(context.getResources().getDrawable(R.drawable.car));
                }
                break;
            case SmartCalendarTransportMode.TRANSIT:
                if(Build.VERSION.SDK_INT >= 21) {
                    transportMode.setImageDrawable(context.getDrawable(R.drawable.walking));
                }else{
                    transportMode.setImageDrawable(context.getResources().getDrawable(R.drawable.walking));
                }
                break;
            case SmartCalendarTransportMode.WALKING:
            default:
                if(Build.VERSION.SDK_INT >= 21) {
                    transportMode.setImageDrawable(context.getDrawable(R.drawable.walking));
                }else{
                    transportMode.setImageDrawable(context.getResources().getDrawable(R.drawable.walking));
                }
                break;
        }

        transportMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navigationData = new Intent(context, NavigateToEventActivity.class);
                navigationData.setAction(Intent.ACTION_SEND);
                navigationData.putExtra(ViewEventActivity.TRAFFIC_KEY_TRANSPORT_MODE, renderData.get(ViewEventActivity.TRAFFIC_KEY_TRANSPORT_MODE));
                navigationData.putExtra(ViewEventActivity.TRAFFIC_KEY_ROUTE_POINTS, renderData.get(ViewEventActivity.TRAFFIC_KEY_ROUTE_POINTS));
                context.startActivity(navigationData);
            }
        });
        return view;
    }
}
