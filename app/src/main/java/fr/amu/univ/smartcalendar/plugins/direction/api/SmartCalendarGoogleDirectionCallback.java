package fr.amu.univ.smartcalendar.plugins.direction.api;

import fr.amu.univ.smartcalendar.plugins.direction.models.SmartCalendarDirectionModel;

/**
 *
 * Created by j.Katende on 30/04/2017.
 */

public interface SmartCalendarGoogleDirectionCallback {
    void onDirectionSuccess(SmartCalendarDirectionModel direction, String raw);
    void onDirectionFailure(Throwable ignored);
}
