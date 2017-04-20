package fr.amu.univ.smartcalendar.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by elbaz on 19/04/2017.
 */

public abstract class DatabaseDAO {

    // Version de la base de donnée
    protected final static int VERSION = 1;
    // Le nom du fichier qui représente la base
    protected final static String NOM = "SmartCalendar.db";

    protected SQLiteDatabase db = null;
    protected DatabaseHelper handler = null;

    public DatabaseDAO(Context pContext) {
        this.handler = new DatabaseHelper(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        db = handler.getWritableDatabase();
        return db;
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
