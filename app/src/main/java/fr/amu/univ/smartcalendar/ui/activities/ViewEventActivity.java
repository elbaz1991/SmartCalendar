package fr.amu.univ.smartcalendar.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.adapters.SmartCalendarNavigationAdapter;
import fr.amu.univ.smartcalendar.models.SmartCalendarActivityModel;
import fr.amu.univ.smartcalendar.models.SmartCalendarAddressModel;
import fr.amu.univ.smartcalendar.plugins.direction.SmartCalendarDirection;
import fr.amu.univ.smartcalendar.plugins.direction.api.SmartCalendarGoogleDirectionCallback;
import fr.amu.univ.smartcalendar.plugins.direction.constant.SmartCalendarTransportMode;
import fr.amu.univ.smartcalendar.plugins.direction.models.SmartCalendarDirectionModel;
import fr.amu.univ.smartcalendar.plugins.weather.SmartCalendarWeather;
import fr.amu.univ.smartcalendar.plugins.weather.api.SmartCalendarWeatherCallBack;
import fr.amu.univ.smartcalendar.plugins.weather.models.SmartCalendarWeatherModel;
import fr.amu.univ.smartcalendar.ui.extension.SmartCalendarListView;


/**
 *
 * Created by j.Katende on 18/04/2017.
 */

public class ViewEventActivity extends Activity implements SmartCalendarGoogleDirectionCallback, SmartCalendarWeatherCallBack {
    private static final String DIRECTION_API_SERVER_KEY = "AIzaSyBoxT_Im1KWhogP6ERKp5Skw2tTUIP_xRk";

    private static final String WEATHER_API_SERVER_KEY = "c631d13091388de7ae8c89007238da75";

   // private static final String REQUEST_BASE_URI = "https://maps.googleapis.com/maps/";

    //private static final HttpTransport SMART_CALENDAR_HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

    private SmartCalendarAddressModel destinationAddress;

    private SmartCalendarActivityModel smartCalendarActivity;

    private Intent paramsReader = null;

    private TextView activity_title;

    private TextView activityDayOfWeek;
    private TextView activityTemperature;
    private TextView activityDayMaxTemp;
    private TextView activityDayMinTemp;

    private LinearLayout smartCalendarDirectionItems;

    public static final String TRAFFIC_KEY_DURATION = "duration";
    public static final String TRAFFIC_KEY_DISTANCE = "distance";
    public static final String TRAFFIC_KEY_ROUTE = "route";
    public static final String TRAFFIC_KEY_ = "";
    public static final String TRAFFIC_KEY_TRANSPORT_MODE = "transport_mode";



    LatLng source = new LatLng(43.299847, 5.385218);
    LatLng destination = new LatLng(43.231412, 5.437300);


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        activity_title = (TextView)findViewById(R.id.smart_calendar_view_activity_title);

        smartCalendarDirectionItems = (LinearLayout)findViewById(R.id.smart_calendar_direction_items);

        paramsReader = getIntent();
        smartCalendarActivity = (SmartCalendarActivityModel)paramsReader.getParcelableExtra("calendar_activity");
        if(smartCalendarActivity != null){
            destinationAddress = new SmartCalendarAddressModel(this, smartCalendarActivity.getAddressId());
            //renderWeather();
            //renderDirectionProposal();
        }else {
            //We can't display unavailable activities their for redirect user to the activity creation page.
        }
        renderWeather();
        renderDirectionProposal();
    }

    private void renderWeather(){
        SmartCalendarWeather.createWithServerKey(WEATHER_API_SERVER_KEY).setDestination(destination).execute(this);
    }

    @Override
    public void onWeatherSuccess(final SmartCalendarWeatherModel weather, String data){
        if(weather.getCode() == 200) {
            activity_title.setText("transmission reussie " + weather.getCityName());
            /*activityTemperature = (TextView)findViewById(R.id.smart_calendar_weather_activity_day_of_week);
            activityDayOfWeek.setText(String.valueOf(weather.getData().getTemperature()));
            activityDayOfWeek.setText(String.valueOf(weather.getData().getMaxTemperature()));
            activityDayOfWeek.setText(String.valueOf(weather.getData().getMinTemperature()));*/
            TextView activity_weather_city_name = (TextView)findViewById(R.id.smart_calendar_weather_city_name);
            activity_weather_city_name.setText(weather.getCityName());

            TextView activity_max_temp = (TextView)findViewById(R.id.smart_calendar_weather_activity_day_max_temperature);
            activity_max_temp.setText(String.valueOf(weather.getData().getMaxTemperature()));

            TextView activity_min_temp = (TextView)findViewById(R.id.smart_calendar_weather_activity_day_min_temperature);
            activity_min_temp.setText(String.valueOf(weather.getData().getMinTemperature()));

            TextView activity_day_temp = (TextView)findViewById(R.id.smart_calendar_weather_temperature);
            activity_day_temp.setText(String.valueOf(weather.getData().getMinTemperature()));
        }
        /*Thread th = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activity_title.setText(String.valueOf(weather.getCode()));
                            }
                        });
                    }
                }catch (InterruptedException ignored){

                }
            }
        };
        th.start();*/
    }

    @Override
    public void onWeatherFailure(Throwable ignored){
        Log.d("DEBUG", ignored.getMessage());
        activity_title.setText("error appened");
    }


    private void renderDirectionProposal(){
        SmartCalendarDirection.createWithServerKey(DIRECTION_API_SERVER_KEY).setOrigin(source)
                .setDestination(destination).setTransportMode(SmartCalendarTransportMode.WALKING)
                .setAlternativeRoute(true).execute(this);
    }

    @Override
    public void onDirectionSuccess(SmartCalendarDirectionModel direction, String raw){
        if(direction.isOk()){
            SmartCalendarNavigationAdapter adapter;
            ArrayList<HashMap<String,String>> navigationItems = new ArrayList<>();
            //smartCalendarDirectionItems.setAdapter(null);
            HashMap<String, String> map;
            for(int index = 0; index < direction.getRouteList().size(); index++){
                map = new HashMap<>();
                map.put(TRAFFIC_KEY_ROUTE, " " + (index + 1));
                //map.put(TRAFFIC_KEY_DISTANCE, direction.getRouteList().get(index).getOverviewPolyline());
                map.put(TRAFFIC_KEY_DURATION, direction.getRouteList().get(index).getLegList().get(0).getDuration().getText());
                map.put(TRAFFIC_KEY_DISTANCE, direction.getRouteList().get(index).getLegList().get(0).getDistance().getText());
                map.put(TRAFFIC_KEY_TRANSPORT_MODE, "walking");
                //activityDayOfWeek.setText(activityDayOfWeek.getText() + direction.getRouteList().get(index).getOverviewPolyline().getRawPointList() + "\n");
                adapter = new SmartCalendarNavigationAdapter(ViewEventActivity.this, map);
                smartCalendarDirectionItems.addView(adapter.getView(index));
            }
            //SmartCalendarNavigationAdapter navigationAdapter = new SmartCalendarNavigationAdapter(this, navigationItems);

            /*smartCalendarDirectionItems.setAdapter(navigationAdapter);
            smartCalendarDirectionItems.setExpanded(true);*/

        //}else{
            //activityDayOfWeek.setText("nombre de chemins 0");
        }
    }

    @Override
    public void onDirectionFailure(Throwable ignored) {
        activityDayOfWeek.setText("nombre de chemins null");
    }
}
