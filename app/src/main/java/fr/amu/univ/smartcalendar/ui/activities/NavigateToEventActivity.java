package fr.amu.univ.smartcalendar.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.amu.univ.smartcalendar.R;

/**
 *
 * Created by j.Katende on 20/04/2017.
 */

public class NavigateToEventActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap navigationMap;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigate_to_event);
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.smart_calendar_navigation_map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        navigationMap = googleMap;
        LatLng sidney = new LatLng(-35, 151);
        navigationMap.addMarker(new MarkerOptions().position(sidney).title("Sydney"));
        navigationMap.moveCamera(CameraUpdateFactory.newLatLng(sidney));
    }
}