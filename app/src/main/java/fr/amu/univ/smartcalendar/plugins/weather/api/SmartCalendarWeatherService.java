package fr.amu.univ.smartcalendar.plugins.weather.api;

import fr.amu.univ.smartcalendar.plugins.weather.constants.SmartCalendarWeatherUrl;
import fr.amu.univ.smartcalendar.plugins.weather.models.SmartCalendarWeatherModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 *
 * Created by j.Katende on 30/04/2017.
 */

public interface SmartCalendarWeatherService {
    @GET(SmartCalendarWeatherUrl.WEATHER_API_REQUESTED_SERVICE)
    Call<SmartCalendarWeatherModel> getWeather(
            @Query("lat")double latitude, @Query("lon")double longitude,
            @Query("units")String units, @Query("appid")String appKey
    );
}
