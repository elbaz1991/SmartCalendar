package fr.amu.univ.smartcalendar.utils;

import android.util.Log;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by j.Katende on 09/05/2017.
 */

public class SmartCalendarDateUtils {
    public int day;

    public int month;

    public int year;

    public int hour;

    public int minute;

    public int second;

    public SmartCalendarDateUtils(long timeStamp){
        Date date = new Date(timeStamp);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
    }
}
