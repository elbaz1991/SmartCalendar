package fr.amu.univ.smartcalendar.plugins.direction;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.List;

import fr.amu.univ.smartcalendar.plugins.direction.api.SmartCalendarDirectionService;
import fr.amu.univ.smartcalendar.plugins.direction.api.SmartCalendarGoogleDirectionCallback;
import fr.amu.univ.smartcalendar.plugins.direction.constant.SmartCalendarDirectionAvoidType;
import fr.amu.univ.smartcalendar.plugins.direction.constant.SmartCalendarDirectionUrl;
import fr.amu.univ.smartcalendar.plugins.direction.models.SmartCalendarDirectionModel;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by j.Katende on 30/04/2017.
 */

public class SmartCalendarDirection {
    private static SmartCalendarDirection instance;

    private SmartCalendarDirectionService service;

    private String server_key;

    private LatLng origin;

    private LatLng destination;

    private String transport_mode = "";

    private boolean alternative_route = false;

    private String units = "metric";

    private String avoid = SmartCalendarDirectionAvoidType.ALLOW_EVERY_WAYS;

    private String language ="fr";

    private String departure_time = "";

    private String transit_mode = "";

    private List<LatLng> way_points;

    private boolean optimized_way_points;

    private boolean log_enabled = true;

    private SmartCalendarDirection(String apiKey) {
        this.server_key = apiKey;
    }

    public static SmartCalendarDirection createWithServerKey(String apiKey){
        if(instance == null) {
            instance = new SmartCalendarDirection(apiKey);
        }else if(!instance.getServerKey().equals(apiKey)){
            instance = new SmartCalendarDirection(apiKey);
        }
        return instance;
    }

    private String getServerKey(){ return this.server_key; }

    public LatLng getOrigin(){ return origin; }

    public SmartCalendarDirection setOrigin(LatLng originPosition){
        this.origin = originPosition;
        return this;
    }

    public LatLng getDestination(){ return destination; }

    public SmartCalendarDirection setDestination(LatLng destinationPosition){
        this.destination = destinationPosition;
        return this;
    }

    public SmartCalendarDirection setTransportMode(String transportMode){
        this.transport_mode = transportMode;
        return this;
    }

    public String getTransportMode(){ return this.transport_mode; }

    public String getLanguage(){ return language; }

    public void setLanguage(String lang){
        this.language = lang;
    }

    public String getUnits(){ return units; }

    public void setUnits(String unit){
        this.units = unit;
    }

    public String getAvoid(){ return this.avoid; }

    public void setAvoid(String avoid){ this.avoid = avoid; }

    public SmartCalendarDirection setAlternativeRoute(boolean alternativeRoute){
        this.alternative_route = alternativeRoute;
        return this;
    }

    public boolean getAlternativeRoute(){ return this.alternative_route; }

    /*public boolean isAlternatives(){
        return alternatives;
    }

    public void setAlternatives(boolean alternative){
        this.alternatives = alternative;
    }*/

    public String getDepartureTime(){ return this.departure_time; }

    public void setDepartureTime(String departureTime){
        this.departure_time = departureTime;
    }

    public List<LatLng> getWayPoints(){ return this.way_points; }

    public void setWayPoints(List<LatLng> wayPoints){ this.way_points = wayPoints; }

    public String getTransitMode(){ return this.transit_mode; }

    public void setTransitMode(String transitMode){ this.transit_mode = transitMode; }

    public boolean isOptimizedWayPoints(){ return optimized_way_points; }

    private String wayPointsToString(List<LatLng> wayPoints){
        String query = "";
        if(wayPoints != null && !wayPoints.isEmpty()){
            query = isOptimizedWayPoints() ? "optimize:true|" : "";
            query += wayPoints.get(0).latitude + "," + wayPoints.get(0).longitude;
            for(int k = 1; k < wayPoints.size(); k++){
                query += "|" + wayPoints.get(k).latitude + "," + wayPoints.get(k).longitude;
            }
        }
        return query;
    }

    public void execute(final SmartCalendarGoogleDirectionCallback callback){
        createService();
        Call<SmartCalendarDirectionModel> direction = service.getDirection(getOrigin().latitude + "," + getOrigin().longitude,
                getDestination().latitude + "," + getDestination().longitude, wayPointsToString(getWayPoints()), getTransportMode(),
                getDepartureTime(), getLanguage(), getUnits(), getAvoid(), getTransitMode(), getAlternativeRoute(), getServerKey());

        direction.enqueue(new Callback<SmartCalendarDirectionModel>(){
            @Override
            public void onResponse(Call<SmartCalendarDirectionModel> call, Response<SmartCalendarDirectionModel> response){
                if(callback != null){
                    callback.onDirectionSuccess(response.body(), new Gson().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<SmartCalendarDirectionModel> call, Throwable ignored){
                callback.onDirectionFailure(ignored);
            }
        });
    }

    private void createService(){
        if(service == null){
            Retrofit retrofit = new Retrofit.Builder().client(getClient())
                    .baseUrl(SmartCalendarDirectionUrl.MAPS_API_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(SmartCalendarDirectionService.class);
        }
    }

    private OkHttpClient getClient(){
        OkHttpClient client = getCustomClient();
        if(client != null){ return client; }
        return createDefaultClient();
    }

    // TODO: 01/05/2017  build customized client 
    private OkHttpClient getCustomClient(){
        return null;
    }

    private OkHttpClient createDefaultClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(isLogEnabled()){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    public boolean isLogEnabled(){
        return log_enabled;
    }
}
