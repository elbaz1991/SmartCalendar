package fr.amu.univ.smartcalendar.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import fr.amu.univ.smartcalendar.model.entity.Evenement;

/**
 * Created by elbaz on 19/04/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EvenementDAO.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(EvenementDAO.TABLE_DROP);
        onCreate(sqLiteDatabase);
    }



    /*private static final String DATABASE_NAME = "SmartCalendar.db";
    Evenement evenement = new Evenement(); // entité Eveènement


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
