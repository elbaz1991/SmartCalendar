package fr.amu.univ.smartcalendar.plugins.direction.api;

import fr.amu.univ.smartcalendar.plugins.direction.constant.SmartCalendarDirectionUrl;
import fr.amu.univ.smartcalendar.plugins.direction.models.SmartCalendarDirectionModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
/**
 *
 * Created by j.Katende on 22/04/2017.
 */

public interface SmartCalendarDirectionService {
    @GET(SmartCalendarDirectionUrl.DIRECTION_API_URL)
    Call<SmartCalendarDirectionModel> getDirection(@Query("origin")String origin,
                                                   @Query("destination")String destination,
                                                   @Query("way_points") String wayPoints,
                                                   @Query("mode") String mode,
                                                   @Query("departure_time") String departureTime,
                                                   @Query("language") String language,
                                                   @Query("units") String units,
                                                   @Query("avoid") String avoid,
                                                   @Query("transit_mode") String transitMode,
                                                   @Query("alternatives") boolean alternatives,
                                                   @Query("key") String apiKey);
}
