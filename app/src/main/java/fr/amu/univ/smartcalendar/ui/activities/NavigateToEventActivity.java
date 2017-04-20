package fr.amu.univ.smartcalendar.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.utils.PermissionUtils;


/**
 *
 * Created by j.Katende on 20/04/2017.
 */

public class NavigateToEventActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    protected GoogleMap navigationMap;

    private Spinner navigationMapSpinner;

    private CheckBox navigationMapTrafficCheckBox;
    private CheckBox navigationMapLocationCheckBox;
    private CheckBox navigationMapBuildingCheckBox;
    private CheckBox navigationMapIndoorCheckBox;

    private boolean showPermissionDeniedDialog = false;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.navigate_to_event);

        navigationMapSpinner = (Spinner) findViewById(R.id.smart_calendar_navigation_map_spinner);
        ArrayAdapter<CharSequence> adapterSequence = ArrayAdapter.createFromResource(this, R.array.layers_array, android.R.layout.simple_spinner_item);
        adapterSequence.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        navigationMapSpinner.setAdapter(adapterSequence);
        navigationMapSpinner.setOnItemSelectedListener(this);

        navigationMapTrafficCheckBox = (CheckBox) findViewById(R.id.smart_calendar_navigation_map_traffic);
        navigationMapLocationCheckBox = (CheckBox) findViewById(R.id.smart_calendar_navigation_map_location);
        navigationMapBuildingCheckBox = (CheckBox) findViewById(R.id.smart_calendar_navigation_map_buildings);
        navigationMapIndoorCheckBox = (CheckBox) findViewById(R.id.smart_calendar_navigation_map_indoor);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.smart_calendar_navigation_map);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        navigationMap = map;
        updateMapType();
        updateTraffic();
        updateLocation();
        updateIndoor();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] results) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (PermissionUtils.isPermissionGranted(permissions, results, Manifest.permission.ACCESS_FINE_LOCATION)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                navigationMap.setMyLocationEnabled(true);
                navigationMapLocationCheckBox.setChecked(true);
            }else {
                showPermissionDeniedDialog = true;
            }
        }
    }

    @Override
    public void onResumeFragments(){
        if(showPermissionDeniedDialog){
            PermissionUtils.PermissionDeniedDialog.newInstance(false).show(getSupportFragmentManager(), "dialog");
            showPermissionDeniedDialog = false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        updateMapType();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent){}


    public void onTrafficToggled(View view){ updateTraffic(); }

    public void onLocationToggled(View view){updateLocation(); }

    public void onBuildingsToggled(View view){ updateBuildings(); }

    public void onIndoorToggled(View view){ updateIndoor(); }

    private void updateMapType(){
        if(isNavigationMapReady()){
            String layerName = ((String)navigationMapSpinner.getSelectedItem());
            if(layerName.equals(R.string.smart_calendar_normal_map)){
                navigationMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }else if(layerName.equals(R.string.smart_calendar_hybrid_map)){
                navigationMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }else if(layerName.equals(R.string.smart_calendar_satellite_map)){
                navigationMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }else if(layerName.equals(R.string.smart_calendar_terrain_map)){
                navigationMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            }else if(layerName.equals(R.string.smart_calendar_none_map)){
                navigationMap.setMapType(GoogleMap.MAP_TYPE_NONE);
            }else{
                Log.i("LDA", "Error setting layer with name " + layerName);
            }
        }
    }

    private void updateTraffic(){
        if(isNavigationMapReady()){
            navigationMap.setTrafficEnabled(navigationMapTrafficCheckBox.isChecked());
        }
    }

    private void updateLocation(){
        if(isNavigationMapReady()){
            if(!navigationMapLocationCheckBox.isChecked()){
                navigationMap.setMyLocationEnabled(false);
            }else{
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    navigationMap.setMyLocationEnabled(true);
                }else{
                    navigationMapLocationCheckBox.setChecked(true);
                    PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, false);
                }
            }
        }
    }

    private void updateBuildings(){
        if(isNavigationMapReady()){
            navigationMap.setBuildingsEnabled(navigationMapBuildingCheckBox.isChecked());
        }
    }

    private void updateIndoor(){
        if(isNavigationMapReady()){
            navigationMap.setIndoorEnabled(navigationMapIndoorCheckBox.isChecked());
        }
    }

    private boolean isNavigationMapReady(){
        if(navigationMap == null){
            Toast.makeText(this, R.string.smart_calendar_map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
