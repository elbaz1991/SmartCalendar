package fr.amu.univ.smartcalendar.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * Created by j.Katende on 18/04/2017.
 */

public class SmartCalendarDataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "smart_calendar.db";

    public SmartCalendarDataBaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        final String SQL_CREATE_ACTIVITY_TABLE = "CREATE TABLE (" + "); ";
        final String SQL_CREATE_ADDRESS_TABLE = "CREATE TABLE (" + "); ";
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){

    }
}
