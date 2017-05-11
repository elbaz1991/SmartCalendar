package fr.amu.univ.smartcalendar.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.plugins.direction.models.SmartCalendarDirectionRouteModel;
import fr.amu.univ.smartcalendar.ui.constants.SmartCalendarFieldsLabel;

/**
 *
 * Created by j.Katende on 20/04/2017.
 */

public class NavigateToEventActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap navigationMap;
    private List<LatLng> navigationRoute;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigate_to_event);
        Intent data = getIntent();
        String polyline = data.getStringExtra(SmartCalendarFieldsLabel.TRAFFIC_KEY_ROUTE_POINTS);

        navigationRoute = SmartCalendarDirectionRouteModel.decodePolyLine(polyline);
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.smart_calendar_navigation_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        navigationMap = googleMap;
        navigationMap.addMarker(new MarkerOptions().position(navigationRoute.get(0)).title(""));
        navigationMap.addMarker(new MarkerOptions().position(navigationRoute.get(navigationRoute.size() - 1)).title(""));
        navigationMap.moveCamera(CameraUpdateFactory.newLatLngZoom(navigationRoute.get(0), 12.0f));
        navigationMap.addPolyline(new PolylineOptions().addAll(navigationRoute).width(4).color(Color.RED));
    }
}