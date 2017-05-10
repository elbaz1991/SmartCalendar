package fr.amu.univ.smartcalendar.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import fr.amu.univ.smartcalendar.model.entity.Adresse;
import fr.amu.univ.smartcalendar.model.entity.Evenement;

/**
 * Created by elbaz on 26/04/2017.
 */

public class AdresseDAO extends DatabaseDAO{
    public static final String TABLE_NAME = "Adresse";
    public static final String COL_ID= "Id";
    public static final String COL_ID_EVENT= "IdEvent";
    public static final String COL_ID_ADRESSE = "IdAdresse";
    public static final String COL_ADRESSE = "Adresse";
    public static final String COL_NOM = "Nom";
    public static final String COL_LATITUDE = "Latitude";
    public static final String COL_LONGITUDE = "Longitude";


    public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "+
            COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COL_ID_EVENT +" INTEGER,"+
            COL_ID_ADRESSE +" TEXT,"+
            COL_ADRESSE +" TEXT,"+
            COL_NOM +" TEXT,"+
            COL_LATITUDE +" REAL,"+
            COL_LONGITUDE + " REAL"+
            ");";

    public static final String TABLE_DROP = "DROP TABLE IF EXIST "+TABLE_NAME;


    public AdresseDAO(Context pContext) {
        super(pContext);
    }

    /**
     * @param adresse l'adresse à ajouter
     */
    public boolean insert(Adresse adresse,int EventId) {
        open();// ouverture de la connexion
        ContentValues values = new ContentValues();
        values.put(AdresseDAO.COL_ID_ADRESSE,adresse.getIdAdresse());
        values.put(AdresseDAO.COL_ID_EVENT,EventId);
        values.put(AdresseDAO.COL_ADRESSE,adresse.getAdresse());
        values.put(AdresseDAO.COL_NOM,adresse.getNom());
        values.put(AdresseDAO.COL_LATITUDE,adresse.getLatitude());
        values.put(AdresseDAO.COL_LONGITUDE,adresse.getLongitude());

        long result = db.insert(AdresseDAO.TABLE_NAME,null,values); // la méthode insert retourne -1 si l'insertion a été échoué
        close();// fermeture de la connexion

        if(result == -1)
            return false;

        return true;
    }

    /**
     * @param adresseId l'identifiant de l'adresser
     */
    public Adresse findByAdresseId(String adresseId){

        if(adresseId != null) {
            Adresse adresse = new Adresse();
            String query = "SELECT * FROM Adresse WHERE " + AdresseDAO.COL_ID_ADRESSE + "='" + adresseId + "'";
            open();
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()){
                adresse.setIdAdresse(cursor.getString(cursor.getColumnIndex(AdresseDAO.COL_ID_ADRESSE)));
                adresse.setAdresse(cursor.getString(cursor.getColumnIndex(AdresseDAO.COL_ADRESSE)));
                adresse.setIdEvent(cursor.getInt(cursor.getColumnIndex(AdresseDAO.COL_ID_EVENT)));
                adresse.setNom(cursor.getString(cursor.getColumnIndex(AdresseDAO.COL_NOM)));
                adresse.setLatitude(cursor.getDouble(cursor.getColumnIndex(AdresseDAO.COL_LATITUDE)));
                adresse.setLongitude(cursor.getDouble(cursor.getColumnIndex(AdresseDAO.COL_LONGITUDE)));
                return adresse;
            }
        }
        return null;
    }


    /**
     * @author elbaz
     * @param idEvent l'evenement à supprimer de la base
     */
    public void delete(int idEvent) {
        String query = "DELETE FROM "+TABLE_NAME + " WHERE "+COL_ID_EVENT +" ="+idEvent;
        open();
        db.execSQL(query);
        close();

    }
}
