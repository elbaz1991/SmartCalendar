package fr.amu.univ.smartcalendar.plugins.weather;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import fr.amu.univ.smartcalendar.plugins.weather.api.SmartCalendarWeatherCallBack;
import fr.amu.univ.smartcalendar.plugins.weather.api.SmartCalendarWeatherService;
import fr.amu.univ.smartcalendar.plugins.weather.constants.SmartCalendarWeatherUrl;
import fr.amu.univ.smartcalendar.plugins.weather.models.SmartCalendarWeatherModel;
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

public class SmartCalendarWeather {
    private static SmartCalendarWeather instance;

    private final String server_key;

    private LatLng destination;

    private SmartCalendarWeatherService service;

    private boolean log_enabled = true;

    private String units = "metric";

    private SmartCalendarWeather(String serverKey) {
        this.server_key = serverKey;
    }

    public static SmartCalendarWeather createWithServerKey(String serverKey){
        if(instance == null) {
            instance = new SmartCalendarWeather(serverKey);
        }else if(!instance.getServerKey().equals(serverKey)){
            instance = new SmartCalendarWeather(serverKey);
        }
        return instance;
    }

    private String getServerKey(){ return this.server_key; }

    public SmartCalendarWeather setDestination(LatLng dest){
        this.destination = dest;
        return this;
    }

    public void execute(final SmartCalendarWeatherCallBack callBack){
        createService();
        Call<SmartCalendarWeatherModel> weather = service.getWeather(destination.latitude,
                destination.longitude, this.units, server_key);

        weather.enqueue(new Callback<SmartCalendarWeatherModel>() {
            @Override
            public void onResponse(Call<SmartCalendarWeatherModel> call, Response<SmartCalendarWeatherModel> response) {
                if(callBack != null)
                    callBack.onWeatherSuccess(response.body(), new Gson().toJson(response.body()));

            }

            @Override
            public void onFailure(Call<SmartCalendarWeatherModel> call, Throwable t) {
                callBack.onWeatherFailure(t);
            }
        });
    }

    private void createService(){
        if(service == null){
            Retrofit retrofit = new Retrofit.Builder().client(getClient())
                    .baseUrl(SmartCalendarWeatherUrl.WEATHER_API_SERVER_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
            service = retrofit.create(SmartCalendarWeatherService.class);
        }
    }

    private OkHttpClient getClient(){
        OkHttpClient client = getCustomClient();
        if(client != null){ return client; }
        return createDefaultClient();
    }

    private OkHttpClient createDefaultClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(log_enabled){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }
        return builder.build();
    }

    private OkHttpClient getCustomClient(){
        return null;
    }
}
