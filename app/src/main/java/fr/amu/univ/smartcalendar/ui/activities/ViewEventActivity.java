package fr.amu.univ.smartcalendar.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.adapters.SmartCalendarNavigationAdapter;
import fr.amu.univ.smartcalendar.model.entity.SmartCalendarActivityModel;
import fr.amu.univ.smartcalendar.model.entity.SmartCalendarAddressModel;
import fr.amu.univ.smartcalendar.model.entity.SmartCalendarEventModel;
import fr.amu.univ.smartcalendar.plugins.direction.SmartCalendarDirection;
import fr.amu.univ.smartcalendar.plugins.direction.api.SmartCalendarGoogleDirectionCallback;
import fr.amu.univ.smartcalendar.plugins.direction.constant.SmartCalendarTransportMode;
import fr.amu.univ.smartcalendar.plugins.direction.models.SmartCalendarDirectionModel;
import fr.amu.univ.smartcalendar.plugins.weather.SmartCalendarWeather;
import fr.amu.univ.smartcalendar.plugins.weather.api.SmartCalendarWeatherCallBack;
import fr.amu.univ.smartcalendar.plugins.weather.constants.SmartCalendarWeatherUrl;
import fr.amu.univ.smartcalendar.plugins.weather.models.SmartCalendarWeatherModel;
import fr.amu.univ.smartcalendar.ui.constants.SmartCalendarFieldsLabel;
import fr.amu.univ.smartcalendar.utils.SmartCalendarImageLoader;
import fr.amu.univ.smartcalendar.utils.SmartCalendarUtils;


/**
 *
 * Created by j.Katende on 18/04/2017.
 */

public class ViewEventActivity extends Activity implements SmartCalendarGoogleDirectionCallback, SmartCalendarWeatherCallBack {
    private Object lock = new Object();

   // private static final String REQUEST_BASE_URI = "https://maps.googleapis.com/maps/";

    //private static final HttpTransport SMART_CALENDAR_HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

    private SmartCalendarAddressModel destinationAddress, originAddress;

    private SmartCalendarEventModel smartCalendarEvent;

    private Intent paramsReader = null;

    private TextView activity_title;

    private TextView activityDayOfWeek;
    private TextView activityTemperature;
    private TextView activityDayMaxTemp;
    private TextView activityDayMinTemp;

    private LinearLayout smartCalendarDirectionItems;

    private String transportMode;



    LatLng source , destination; /*= new LatLng(43.299847, 5.385218);
    LatLng destination = new LatLng(43.231412, 5.437300) ; */


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(!SmartCalendarUtils.isConnected(ViewEventActivity.this)){
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ViewEventActivity.this);
            dialogBuilder.setMessage(getResources().getString(R.string.smart_calendar_unable_to_use_internet_message))
                    .setPositiveButton(getResources().getString(R.string.smart_calendar_fire_label), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent networkSettings = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivityForResult(networkSettings, 0);
                            /*int ind = 0;
                            while(true){
                                if(SmartCalendarUtils.isConnected(ViewEventActivity.this)){
                                    Log.d("DEBUG", "je suis connecter ");
                                    break;
                                }
                                synchronized (this) {
                                    try {
                                        getApplicationContext().wait(1000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    this.notify();
                                }

ind++; Log.d("DEBUG", "context. coction" + ind);
                            }
                            Log.d("DEBUG", "context. coction" + ind);
                            stopService(networkSettings);
                            //context.startActivity(((Activity) context).getIntent());
                        //}
                        //context.*/
                        }
                    }).setNegativeButton(getResources().getString(R.string.smart_calendar_cancel_label), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            dialogBuilder.create().show();
            //SmartCalendarUtils.showConnexionAlert(ViewEventActivity.this);
        }else {
            renderActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if(requestCode == 0){
            WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if(!wifiManager.isWifiEnabled()){
                renderActivity();
            }
        }
    }

    public void renderActivity(){
        setContentView(R.layout.activity_view_event);
        activity_title = (TextView) findViewById(R.id.smart_calendar_view_activity_title);

        smartCalendarDirectionItems = (LinearLayout) findViewById(R.id.smart_calendar_direction_items);

        paramsReader = getIntent();
        int eventId = Integer.parseInt(paramsReader.getStringExtra(SmartCalendarFieldsLabel.SMART_CALENDAR_EVENT_ID));
        //smartCalendarEvent = (SmartCalendarActivityModel) paramsReader.getParcelableExtra("calendar_activity");
        if (eventId > 0) {Log.d("DEBUG", "event id " + eventId);
            smartCalendarEvent = new SmartCalendarEventModel(this, eventId);
            originAddress = new SmartCalendarAddressModel(this, smartCalendarEvent.getOriginAddressId());
            destinationAddress = new SmartCalendarAddressModel(this, smartCalendarEvent.getDestinationAddressId());

            renderWeather();
            renderDirectionProposal();
        } else {
            //We can't display unavailable activities their for redirect user to the activity creation page.
        }

    }

    private void renderWeather(){
        destination = new LatLng(destinationAddress.getLatitude(), destinationAddress.getLongitude());
        SmartCalendarWeather.createWithServerKey(SmartCalendarFieldsLabel.WEATHER_API_SERVER_KEY).setDestination(destination).execute(this);
    }

    @Override
    public void onWeatherSuccess(final SmartCalendarWeatherModel weather, String data){
        if(weather.getCode() == 200) {
            //activity_title.setText("transmission reussie " + weather.getCityName());
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
            activity_day_temp.setText(String.valueOf(weather.getData().getTemperature()));

            SmartCalendarImageLoader imageLoader = new SmartCalendarImageLoader(ViewEventActivity.this);
            String weatherIconPath = SmartCalendarWeatherUrl.WEATHER_API_ICON_ROOT_URL + weather.getWeathers().get(0).getIcon() + ".png";
            imageLoader.displayImage(weatherIconPath, (ImageView)findViewById(R.id.smart_calendar_weather_app_photo));
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
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setMessage(getResources().getString(R.string.smart_calendar_weather_retrieve_error_message))
                .setPositiveButton(getResources().getString(R.string.smart_calendar_fire_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //PROCCESS
                    }
                }).setNegativeButton(getResources().getString(R.string.smart_calendar_cancel_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }


    private void renderDirectionProposal(){
        destination = new LatLng(destinationAddress.getLatitude(), destinationAddress.getLongitude());
        source = new LatLng(originAddress.getLatitude(), originAddress.getLongitude());
        SmartCalendarDirection.createWithServerKey(SmartCalendarFieldsLabel.DIRECTION_API_SERVER_KEY).setOrigin(source)
                .setDestination(destination).setTransportMode(transportMode)
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
                map.put(SmartCalendarFieldsLabel.TRAFFIC_KEY_ROUTE, " " + (index + 1));
                map.put(SmartCalendarFieldsLabel.TRAFFIC_KEY_ROUTE_POINTS, direction.getRouteList().get(index).getOverviewPolyline().getRawPointList());
                //map.put(TRAFFIC_KEY_DISTANCE, direction.getRouteList().get(index).getOverviewPolyline());
                map.put(SmartCalendarFieldsLabel.TRAFFIC_KEY_DURATION, direction.getRouteList().get(index).getLegList().get(0).getDuration().getText());
                map.put(SmartCalendarFieldsLabel.TRAFFIC_KEY_DISTANCE, direction.getRouteList().get(index).getLegList().get(0).getDistance().getText());
                map.put(SmartCalendarFieldsLabel.TRAFFIC_KEY_TRANSPORT_MODE, "walking");
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
