package fr.amu.univ.smartcalendar.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import fr.amu.univ.smartcalendar.model.entity.SmartCalendarAddressModel;

/**
 *
 * Created by j.Katende on 04/05/2017.
 */

public class AddressDAO extends DatabaseDAO{
    public static final String TABLE_NAME = "Address";
    public static final String COL_ID = "Id";
    public static final String COL_ALIAS = "Alias";
    public static final String COL_EVENT_ID = "Event_ID";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_ORIGIN = "origin";
    public static final String COL_DESTINATION = "destination";
    //public static final String COL_

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_ALIAS + " VARCHAR(255), " +
            COL_EVENT_ID + " INTEGER, " +
            COL_LATITUDE + " DOUBLE, " +
            COL_LONGITUDE + " DOUBLE, " +
            COL_ORIGIN + " TINYINT(1), " +
            COL_DESTINATION + " TINYINT(1) );";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public AddressDAO(Context pContext) {
        super(pContext);
    }

    /**
     * function to insert address into the database
     * @param address
     */
    public long insert(SmartCalendarAddressModel address){
        open();
        ContentValues values = new ContentValues();
        values.put(COL_EVENT_ID, address.getEventId());
        values.put(COL_ALIAS, address.getAddressLabel());
        values.put(COL_LATITUDE, address.getLatitude());
        values.put(COL_LONGITUDE, address.getLongitude());
        values.put(COL_ORIGIN, (address.isOrigin() ? 1 : 0));
        values.put(COL_DESTINATION, (address.isDestination() ? 1 :0));
        long result = db.insert(TABLE_NAME, null, values);
        close();

        return result;
    }

    public boolean update(SmartCalendarAddressModel address){
        ContentValues values = new ContentValues();
        values.put(COL_EVENT_ID, address.getEventId());
        values.put(COL_ALIAS, address.getAddressLabel());
        values.put(COL_LATITUDE, address.getLatitude());
        values.put(COL_LONGITUDE, address.getLongitude());
        values.put(COL_ORIGIN, (address.isOrigin() ? 1 : 0));
        values.put(COL_DESTINATION, (address.isDestination() ? 1 : 0));

        String selection = COL_ID +  " = ?";

        open();
        int result = db.update(TABLE_NAME, values, selection, new String[]{String.valueOf(address.getAddressId())});
        close();
        return result > 0;
    }

    public SmartCalendarAddressModel getAddressById(int addressId){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + addressId;

        open();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor != null) {
            cursor.moveToFirst();
            SmartCalendarAddressModel addressModel = new SmartCalendarAddressModel();
            addressModel.setAddressId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            addressModel.setAddressLabel(cursor.getString(cursor.getColumnIndex(COL_ALIAS)));
            addressModel.setLongitude(cursor.getDouble(cursor.getColumnIndex(COL_LONGITUDE)));
            addressModel.setLatitude(cursor.getDouble(cursor.getColumnIndex(COL_LATITUDE)));
            addressModel.setEventId(cursor.getInt(cursor.getColumnIndex(COL_EVENT_ID)));
            addressModel.setOrigin(cursor.getInt(cursor.getColumnIndex(COL_ORIGIN) > 0 ? 1 : 0));
            addressModel.setDestination(cursor.getInt(cursor.getColumnIndex(COL_DESTINATION) > 1 ? 1 : 0));
            return addressModel;
        }
        close();
        return null;
    }

    public boolean delete(int addressId){
        String selection = COL_ID + " = ?";
        open();
        int result = db.delete(TABLE_NAME, selection, new String[]{String.valueOf(addressId)});
        close();
        return result > 0;
    }
}
