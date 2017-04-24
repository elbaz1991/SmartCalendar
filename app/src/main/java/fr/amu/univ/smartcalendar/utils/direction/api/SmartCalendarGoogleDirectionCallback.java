package fr.amu.univ.smartcalendar.utils.direction.api;

import fr.amu.univ.smartcalendar.utils.direction.models.SmartCalendarDirectionModel;

/**
 *
 * Created by j.Katende on 22/04/2017.
 */

public interface SmartCalendarGoogleDirectionCallback {
    void onDirectionSuccess(SmartCalendarDirectionModel direction, String raw);
    void onDirectionFailure(Throwable ignored);
}
