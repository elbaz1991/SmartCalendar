package fr.amu.univ.smartcalendar.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;

import fr.amu.univ.smartcalendar.R;

/**
 *
 * Created by j.Katende on 24/04/2017.
 */

public class SmartCalendarUtils {
    private static final int BUFFER_SIZE = 1024;


    public static void copyStream(InputStream inputStream, OutputStream out){
        try{
            byte[] bytes = new byte[BUFFER_SIZE];
            int count = inputStream.read(bytes, 0, BUFFER_SIZE);
            while(count != -1){
                out.write(bytes, 0, count);
                count = inputStream.read(bytes, 0, BUFFER_SIZE);
            }
        }catch(Exception ignored){}
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    public static void showConnexionAlert(final Context context){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage(context.getResources().getString(R.string.smart_calendar_unable_to_use_internet_message))
                .setPositiveButton(context.getResources().getString(R.string.smart_calendar_fire_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent networkSettings = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        ((Activity) context).startActivityForResult(networkSettings, 0);
                        /*while(!isConnected(context)){

                        }
                        Log.d("DEBUG", context.);
                        //if(context.stopService(networkSettings)) {
                            context.startActivity(((Activity) context).getIntent());
                        //}
                        context.*/
                    }
                }).setNegativeButton(context.getResources().getString(R.string.smart_calendar_cancel_label), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        dialogBuilder.create().show();
    }

    public static String getAddressFromCoordinates(double latitute, double longitude){
        return "";
    }
}