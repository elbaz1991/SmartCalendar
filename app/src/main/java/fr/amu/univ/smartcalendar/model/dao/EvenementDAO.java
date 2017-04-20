package fr.amu.univ.smartcalendar.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import fr.amu.univ.smartcalendar.model.entity.Evenement;

/**
 * Created by elbaz on 19/04/2017.
 */

public class EvenementDAO extends DatabaseDAO{

    public static final String TABLE_NAME = "Evenement";
    public static final String COL_ID= "Id";
    public static final String COL_TITRE = "Titre";
    public static final String COL_DESC = "Description";
    public static final String COL_DATE_DEBUT = "DateDebut";
    public static final String COL_DATE_FIN = "DateFin";


    public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( "+
                                                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                                COL_TITRE +" TEXT,"+
                                                COL_DESC +" TEXT,"+
                                                COL_DATE_DEBUT +" INTEGER,"+
                                                COL_DATE_FIN + " INTEGER"+
                                                ");";

    public static final String TABLE_DROP = "DROP TABLE IF EXIST "+TABLE_NAME;

    public EvenementDAO(Context context) {
        super(context);
    }


    /**
     * @param e l'evenement à ajouter à la base
     */
    public boolean insert(Evenement e) {
        open();// ouverture de la connexion
        ContentValues values = new ContentValues();
        values.put(EvenementDAO.COL_TITRE,e.getTitre());
        values.put(EvenementDAO.COL_DESC,e.getDescription());
        values.put(EvenementDAO.COL_DATE_DEBUT,e.getDateDebut());
        values.put(EvenementDAO.COL_DATE_FIN,e.getDateFin());
        long result = db.insert(EvenementDAO.TABLE_NAME,null,values); // la méthode insert retourne -1 si l'insertion a été échoué
        close();// fermeture de la connexion

        if(result == -1)
            return false;

        return true;
    }


    /**
     * @param e l'evenement à supprimer de la base
     */
    public void delete(Evenement e) {

    }


    /**
     * @param e l'evenement à modifer
     */
    public void update(Evenement e) {

    }




}
