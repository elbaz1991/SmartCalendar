package fr.amu.univ.smartcalendar.utils;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * Created by elbaz on 25/04/2017.
 */

public class SmartCalendarDateFormat {
    private static SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy");
    //private static SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy");
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMMM - yyyy");
    private static SimpleDateFormat dateFormatYearMonthDay = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormatEventNumDay = new SimpleDateFormat("dd");
    private static SimpleDateFormat dateFormatEventNameDay = new SimpleDateFormat("EEE");
    private static SimpleDateFormat dateFormatDayMonth = new SimpleDateFormat("dd MMMM");

    private static SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
    private static Calendar calendar = Calendar.getInstance();


    /**
     * @param date à transformer
     * @return date au format MMMM - yyyy
     */
    public static String dateFormatMonth(Date date){
        String result =  dateFormatMonth.format(date);
        return result.substring(0,1).toUpperCase() + result.substring(1);
    }


    /**
     * @param date à transformer
     * @return date au format dd - MMMM - yyyy
     */
    public static String dateFormat(Date date){
        return dateFormat.format(date);
    }



    /**
     * @param date à transformer
     * @return date au format yy-MM-dd  example -> 2017-12-24
     */
    public static String getDateFormatYearMonthDay(Date date){
        return dateFormatYearMonthDay.format(date);
    }

    /**
     * @param date à transformer
     * @return le mois à partir d'une date
     */
    public static int getMonthFromTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(date.getTime());
        return calendar.get(Calendar.MONTH) + 1;
    }


    /**
     * @param date à transformer
     * @return le jour correspondant à la date ex 05-12-2017 -> la fonction renvoi 05
     */
    public static String getEventNumDay(Date date){
        return dateFormatEventNumDay.format(date);
    }

    /**
     * @param date à transformer
     * @return le nom du jour correspondant à la date ex 05-12-2017 -> la fonction renvoi lun.
     */
    public static String getEventNameDay(Date date){
        String result =  dateFormatEventNameDay.format(date);
        return result.substring(0,1).toUpperCase() + result.substring(1);
    }


    /**
     * @param date à formater
     * @return extraire l'heure d'une date
     */
    public static String getHourFormat(Date date){
        return hourFormat.format(date);
    }

    public static String dateFormatDayAndMonth(Date date){
        return dateFormatDayMonth.format(date);
    }
}
