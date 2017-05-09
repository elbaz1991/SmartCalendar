package fr.amu.univ.smartcalendar.model.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by elbaz on 19/04/2017.
 */

public abstract class DatabaseDAO {
    protected Context context;

    // Version de la base de donnée
    protected final static int VERSION = 1;
    // Le nom du fichier qui représente la base
    protected final static String NOM = "SmartCalendar.db";

    protected SQLiteDatabase db = null;
    protected DatabaseHelper handler = null;

    public DatabaseDAO(Context pContext) {
        this.handler = new DatabaseHelper(pContext, NOM, null, VERSION);
        this.context = pContext;
    }

    public SQLiteDatabase open() {
        // Pas besoin de fermer la dernière base puisque getWritableDatabase s'en charge
        db = handler.getWritableDatabase();
        return db;
    }

    public SQLiteDatabase getReadableDatabase(Context c){
        return new DatabaseHelper(c, NOM, null, VERSION).getReadableDatabase();
    }

    public void close() {
        db.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    protected static Cursor getList(Context context, String tableName, String[] columns){
        return getList(context, tableName, columns, null, null, null, null);
    }

    protected static Cursor getList(Context context, String tableName, String[] columns, String selection){
        return getList(context, tableName, columns, selection, null, null);
    }

    protected static Cursor getList(Context context, String tableName, String[] columns, String selection, String[] selectionArgs){
        return getList(context, tableName, columns, selection, selectionArgs, null, null);
    }

    protected static Cursor getList(Context context, String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy){
        return getList(context, tableName, columns, selection, selectionArgs, groupBy, null, null);
    }

    protected static Cursor getList(Context context, String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having){
        return getList(context, tableName, columns, selection, selectionArgs, groupBy, having, null);
    }

    protected static Cursor getList(Context context, String tableName, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy){
        SQLiteDatabase dbObject = new DatabaseHelper(context, NOM, null, VERSION).getReadableDatabase();
        Cursor cursor = dbObject.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy);

        dbObject.close();
        return cursor;
    }
}