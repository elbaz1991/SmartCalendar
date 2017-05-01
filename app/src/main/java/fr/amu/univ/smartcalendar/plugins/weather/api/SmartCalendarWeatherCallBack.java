package fr.amu.univ.smartcalendar.plugins.weather.api;

import fr.amu.univ.smartcalendar.plugins.weather.models.SmartCalendarWeatherModel;

/**
 *
 * Created by j.Katende on 30/04/2017.
 */

public interface SmartCalendarWeatherCallBack {
    void onWeatherSuccess(final SmartCalendarWeatherModel weather, String data);
    void onWeatherFailure(Throwable ignored);
}
