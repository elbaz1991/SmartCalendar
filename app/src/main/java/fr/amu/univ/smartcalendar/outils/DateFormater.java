package fr.amu.univ.smartcalendar.outils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by elbaz on 25/04/2017.
 */

public class DateFormater {
    private static SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy");
    private static SimpleDateFormat dateFormatMonthYear = new SimpleDateFormat("MMMM yyyy");

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMMM - yyyy");
    private static SimpleDateFormat dateFormatYyMmDd = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat dateFormatEventNumDay = new SimpleDateFormat("dd");
    private static SimpleDateFormat dateFormatEventNameDay = new SimpleDateFormat("EEE");
    private static SimpleDateFormat dateFormatddMM = new SimpleDateFormat("dd MMMM");

    private static SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm");
    private static Calendar calendar = Calendar.getInstance();


    /**
     * @author elbaz
     * @param date à transformer
     * @return date au format MMMM - yyyy
     */
    public static String dateFormatMonth(Date date){
        String result =  dateFormatMonth.format(date);
        return result.substring(0,1).toUpperCase() + result.substring(1);
    }


    /**
     * @author elbaz
     * @param date à transformer
     * @return date au format dd - MMMM - yyyy
     */
    public static String dateFormat(Date date){
        return dateFormat.format(date);
    }



    /**
     * @author elbaz
     * @param date à transformer
     * @return date au format yy-MM-dd  exemple -> 2017-12-24
     */
    public static String dateFormatYyMmDd(Date date){
        return dateFormatYyMmDd.format(date);
    }

    /**
     * @author elbaz
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
     * @author elbaz
     * @param date à transformer
     * @return le jour correspondant à la date ex 05-12-2017 -> la fonction renvoi 05
     */
    public static String getEventNumDay(Date date){
        return dateFormatEventNumDay.format(date);
    }

    /**
     * @author elbaz
     * @param date à transformer
     * @return le nom du jour correspondant à la date ex 05-12-2017 -> la fonction renvoi lun.
     */
    public static String getEventNameDay(Date date){
        String result =  dateFormatEventNameDay.format(date);
        return result.substring(0,1).toUpperCase() + result.substring(1);
    }


    /**@author elbaz
     * @param date à formater
     * @return extraire l'heure d'une date
     */
    public static String heureFormat(Date date){
        return heureFormat.format(date);
    }

    public static String dateFormatddMM(Date date){
        return dateFormatddMM.format(date);
    }


    /**@author elbaz
     * @param date à transformer
     * @return date au format MMMM yyyy
     */
    public static String dateFormatMonthYear(Date date){
        String result =  dateFormatMonthYear.format(date);
        return result.substring(0,1).toUpperCase() + result.substring(1);
    }


    /**@author elbaz
     * @param d1 la première date
     * @param d2 la deuxième date
     * @return la différence en jours entre les deux dates
     */
    public static long getDifferenceDays(long d1, long d2) {
        long diff = d2 - d1;
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }
}


