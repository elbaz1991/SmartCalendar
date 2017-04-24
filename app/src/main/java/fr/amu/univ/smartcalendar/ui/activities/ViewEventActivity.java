package fr.amu.univ.smartcalendar.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.adapters.SmartCalendarNavigationAdapter;
import fr.amu.univ.smartcalendar.models.SmartCalendarActivityModel;
import fr.amu.univ.smartcalendar.models.SmartCalendarAddressModel;
import fr.amu.univ.smartcalendar.utils.direction.SmartCalendarGoogleDirection;
import fr.amu.univ.smartcalendar.utils.direction.api.SmartCalendarGoogleDirectionCallback;
import fr.amu.univ.smartcalendar.utils.direction.constants.SmartCalendarTransportMode;
import fr.amu.univ.smartcalendar.utils.direction.models.SmartCalendarDirectionModel;

/**
 *
 * Created by j.Katende on 18/04/2017.
 */

public class ViewEventActivity extends Activity implements SmartCalendarGoogleDirectionCallback{
    private static final String DIRECTION_API_SERVER_KEY = "AIzaSyBoxT_Im1KWhogP6ERKp5Skw2tTUIP_xRk";

    private static final String REQUEST_BASE_URI = "https://maps.googleapis.com/maps/";

    //private static final HttpTransport SMART_CALENDAR_HTTP_TRANSPORT = AndroidHttp.newCompatibleTransport();

    private SmartCalendarAddressModel destinationAddress;

    private SmartCalendarActivityModel smartCalendarActivity;

    private Intent paramsReader = null;

    private TextView activityDayOfWeek;

    private ListView smartCalendarDirectionItems;

    public static final String TRAFFIC_KEY_DURATION = "duration";
    public static final String TRAFFIC_KEY_DISTANCE = "distance";
    public static final String TRAFFIC_KEY_ = "";
    public static final String TRAFFIC_KEY_TRANSPORT_MODE = "transport_mode";


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        activityDayOfWeek = (TextView)findViewById(R.id.activity_day_of_week);
        smartCalendarDirectionItems = (ListView)findViewById(R.id.smart_calendar_direction_items);

        paramsReader = getIntent();
        smartCalendarActivity = (SmartCalendarActivityModel)paramsReader.getParcelableExtra("calendar_activity");
        if(smartCalendarActivity != null){
            destinationAddress = new SmartCalendarAddressModel(this, smartCalendarActivity.getAddressId());
            renderWeather();
            renderDirectionProposal();
        }else {
            //We can't display unavailable activities their for redirect user to the activity creation page.
        }
        renderDirectionProposal();
    }

    private void renderWeather(){}

    private void renderDirectionProposal(){
        LatLng source = new LatLng(43.299847, 5.385218);
        LatLng destination = new LatLng(43.231412, 5.437300);
        SmartCalendarGoogleDirection.createWithServerKey(DIRECTION_API_SERVER_KEY).setOrigin(source)
                .setDestination(destination).setTransportMode(SmartCalendarTransportMode.WALKING)
                .setAlternativeRoute(true).execute(this);
        activityDayOfWeek.setText("nombre de chemins null ,;,; :vgfgfhg");
    }

    @Override
    public void onDirectionSuccess(SmartCalendarDirectionModel direction, String raw) {
        if(direction.isOk()){
            List<> navigationItems = new ArrayList<>();
            smartCalendarDirectionItems.setAdapter(null);
            for(int index = 0; index < direction.getRouteList().size(); index++){
                activityDayOfWeek.setText(activityDayOfWeek.getText() + direction.getRouteList().get(index).getSummary());
            }
            SmartCalendarNavigationAdapter navigationAdapter = new SmartCalendarNavigationAdapter(this, navigationItems);
            smartCalendarDirectionItems.setAdapter(navigationAdapter);
        //}else{
            //activityDayOfWeek.setText("nombre de chemins 0");
        }
    }

    @Override
    public void onDirectionFailure(Throwable ignored) {
        activityDayOfWeek.setText("nombre de chemins null");
    }
}
