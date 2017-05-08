package fr.amu.univ.smartcalendar.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * Created by elbaz on 19/04/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EvenementDAO.TABLE_CREATE);
        sqLiteDatabase.execSQL(AddressDAO.CREATE_TABLE);
        sqLiteDatabase.execSQL(ParticipantDAO.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(EvenementDAO.TABLE_DROP);
        sqLiteDatabase.execSQL(AddressDAO.DROP_TABLE);
        sqLiteDatabase.execSQL(ParticipantDAO.DROP_TABLE);
        onCreate(sqLiteDatabase);
    }



    /*private static final String DATABASE_NAME = "SmartCalendar.db";
    SmartCalendarEventModel evenement = new SmartCalendarEventModel(); // entité Eveènement
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("base de donnee","Goooooooooooooooooooooooo !");
        db.execSQL(evenement.getCreateTableReq());
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(evenement.getDropTableReq());
        onCreate(db);
    }
    */
}