package fr.amu.univ.smartcalendar.utils.direction;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.List;

import fr.amu.univ.smartcalendar.utils.direction.models.SmartCalendarDirectionModel;
import fr.amu.univ.smartcalendar.utils.direction.api.SmartCalendarGoogleDirectionCallback;

import fr.amu.univ.smartcalendar.utils.direction.network.SmartCalendarDirectionConnexion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 * Created by j.Katende on 22/04/2017.
 */

public class SmartCalendarGoogleDirection {
    protected SmartCalendarGoogleDirectionParams params;

    private String server_key;

    private LatLng origin;

    private LatLng destination;

    private String transport_mode;

    private boolean alternative_route = false;

    private SmartCalendarGoogleDirection(String apiKey){
        this.server_key = apiKey;
        params = new SmartCalendarGoogleDirectionParams();
        params.setApiKey(apiKey);
    }

    public static SmartCalendarGoogleDirection createWithServerKey(String apiKey){
        return new SmartCalendarGoogleDirection(apiKey);
    }

    public SmartCalendarGoogleDirection setOrigin(LatLng originPosition){
        this.origin = originPosition;
        params.setOrigin(originPosition);
        return this;
    }

    public SmartCalendarGoogleDirection setDestination(LatLng destinationPosition){
        this.destination = destinationPosition;
        params.setDestination(destinationPosition);
        return this;
    }

    public SmartCalendarGoogleDirection setTransportMode(String transportMode){
        this.transport_mode = transportMode;
        params.setTransportMode(transportMode);
        return this;
    }

    public SmartCalendarGoogleDirection setAlternativeRoute(boolean alternativeRoute){
        this.alternative_route = alternativeRoute;
        params.setAlternatives(alternativeRoute);
        return this;
    }

    private String wayPointsToString(List<LatLng> wayPoints){
        String query = "";
        if(wayPoints != null && !wayPoints.isEmpty()){
            query = params.isOptimizeWayPoints() ? "optimize:true|" : "";
            query += wayPoints.get(0).latitude + "," + wayPoints.get(0).longitude;
            for(int k = 1; k < wayPoints.size(); k++){
                query += "|" + wayPoints.get(k).latitude + "," + wayPoints.get(k).longitude;
            }
        }
        return query;
    }

    public void execute(final SmartCalendarGoogleDirectionCallback callback){
        Call<SmartCalendarDirectionModel> direction = SmartCalendarDirectionConnexion.getInstance().createService()
                .getDirection(params.getOrigin().latitude + "," + params.getOrigin().longitude,
                        params.getDestination().latitude + "," + params.getDestination().longitude,
                        wayPointsToString(params.getWayPoints()), params.getTransportMode(),
                        params.getDepartureTime(), params.getLanguage(), params.getUnit(), params.getAvoid(),
                        params.getTransitMode(), params.isAlternatives(), params.getApiKey());

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
}
