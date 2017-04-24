package fr.amu.univ.smartcalendar.utils.direction.config;

import okhttp3.OkHttpClient;

/**
 *
 * Created by j.Katende on 23/04/2017.
 */

public class SmartCalendarGoogleDirectionConfiguration {
    private static SmartCalendarGoogleDirectionConfiguration instance;

    private boolean isLogEnabled = false;

    private OkHttpClient customClient;

    public static synchronized SmartCalendarGoogleDirectionConfiguration getInstance(){
        if(instance == null){
            instance = new SmartCalendarGoogleDirectionConfiguration();
        }
        return instance;
    }

    public boolean isLogEnabled(){
        return isLogEnabled;
    }

    public void setLogEnabled(boolean logEnabled){ this.isLogEnabled = logEnabled; }

    public OkHttpClient getCustomClient(){ return customClient; }

    public void setCustomClient(OkHttpClient client){ this.customClient = client; }
}
