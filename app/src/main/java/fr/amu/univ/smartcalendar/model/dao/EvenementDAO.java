package fr.amu.univ.smartcalendar.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.amu.univ.smartcalendar.model.entity.SmartCalendarEventModel;
import fr.amu.univ.smartcalendar.utils.SmartCalendarDateFormat;

/**
 *
 * Created by elbaz on 19/04/2017.
 */

public class EvenementDAO extends DatabaseDAO{
    public static final String TABLE_NAME = "Evenement";
    public static final String COL_ID= "Id";
    public static final String COL_TITRE = "Titre";
    public static final String COL_DESC = "Description";
    public static final String COL_DATE_DEBUT = "DateDebut";
    public static final String COL_DATE_FIN = "DateFin";
    public static final String COL_ADDRESS_DEPART = "origin_id";
    public static final String COL_ADDRESS_DESTINATION = "destination_id";
    public static final String COL_COLOR = "Color";


    public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "+
            COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COL_TITRE +" TEXT, "+
            COL_DESC +" TEXT, "+
            COL_DATE_DEBUT +" INTEGER, "+
            COL_DATE_FIN + " INTEGER, "+
            COL_ADDRESS_DEPART + " INTEGER, " + COL_ADDRESS_DESTINATION + " INTEGER " +
            ");";

    public static final String TABLE_DROP = "DROP TABLE IF EXIST "+TABLE_NAME;

    private static final String queryFindAll = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COL_DATE_DEBUT;

    public EvenementDAO(Context context) {
        super(context);
    }


    /**
     * @param e l'evenement à ajouter à la base
     */
    public long insert(SmartCalendarEventModel e) {
        open();// ouverture de la connexion
        ContentValues values = new ContentValues();
        values.put(EvenementDAO.COL_TITRE,e.getTitre());
        values.put(EvenementDAO.COL_DESC,e.getDescription());
        values.put(EvenementDAO.COL_DATE_DEBUT,e.getDateDebut());
        values.put(EvenementDAO.COL_DATE_FIN,e.getDateFin());

        long result = db.insert(EvenementDAO.TABLE_NAME,null,values); // la méthode insert retourne -1 si l'insertion a été échoué
        close();// fermeture de la connexion

        return result;
    }


    /**
     * @param e l'evenement à supprimer de la base
     */
    public void delete(SmartCalendarEventModel e) {

    }


    /**
     * @param e l'evenement à modifer
     */
    public boolean update(SmartCalendarEventModel e) {
        return true;
    }

    public void updateIntField(int eventId, String field, int value){
        ContentValues values = new ContentValues();
        values.put(field, value);
        String selection = field + " = ?";
        open();
        int result = db.update(TABLE_NAME, values, selection, new String[]{String.valueOf(eventId)} );
        close();
    }

    /**
     *@return renvoi tout les évènements
     */
    public List<SmartCalendarEventModel> findAll(Context context) {
        List<SmartCalendarEventModel> li = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery(this.queryFindAll,null);
        // si la table est vide
        if(cursor.getCount() == 0)
            return null;
        //Log.e("DATA",String.valueOf(cursor.getCount()));

        SmartCalendarEventModel event;
        while (cursor.moveToNext()){
            event = new SmartCalendarEventModel(context);
            event.setTitre(cursor.getString(cursor.getColumnIndex(COL_TITRE)));
            event.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESC)));
            Calendar d = Calendar.getInstance();
            d.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_DATE_DEBUT)));
            event.setDateDebut(d);
            d.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_DATE_FIN)));
            event.setDateFin(d);
            event.setColor(cursor.getInt(cursor.getColumnIndex(COL_COLOR)));
            li.add(event);
        }
        return li;
    }

    /***
     *
     */
    public List<SmartCalendarEventModel> findEventsByDate(Context context, Date date){
        String dateString = SmartCalendarDateFormat.getDateFormatYearMonthDay(date);
        String queryFindByMonth = "SELECT * FROM " + EvenementDAO.TABLE_NAME +
                " WHERE '"+dateString+"'"+
                " BETWEEN date(datetime(dateDebut, 'unixepoch', 'localtime')) "+
                " AND date(datetime(dateFin, 'unixepoch', 'localtime'))";

        List<SmartCalendarEventModel> li = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery(queryFindByMonth,null);
        // si la table est vide
        if(cursor.getCount() == 0)
            return null;

        Calendar d = Calendar.getInstance();
        SmartCalendarEventModel event;
        while (cursor.moveToNext()){
            event = new SmartCalendarEventModel(context);
            event.setEventId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            event.setTitre(cursor.getString(cursor.getColumnIndex(COL_TITRE)));
            event.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESC)));
            d.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_DATE_DEBUT)));
            event.setDateDebut(d);

            d.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_DATE_FIN)));
            event.setDateFin(d);
            //event.setAdresseDepart(adresseDAO.findByAdresseId(cursor.getString(cursor.getColumnIndex(COL_ADRESSE_DEPART))));
            //event.setAdresseRdv(adresseDAO.findByAdresseId(cursor.getString(cursor.getColumnIndex(COL_ADRESSE_RDV))));
            event.setCurrentDate(date.getTime());
            event.setDateFin(d);
            event.setColor(cursor.getInt(cursor.getColumnIndex(COL_COLOR)));
            li.add(event);
        }
        return li;
    }

    /**
     * @author elbaz
     * @return cette methode retourne la liste des dates(long) des évènements sans doublant
     * pour construire la cellule global qui va contenir les évènements de chaque jour
     */
    public List<Long> findDistinctEventsDates(){
        /* Requete pour recuperer les dates des evenemement sans doublant* /
        String query = "SELECT strftime('%s',distinctDate) FROM " +
                "(SELECT DISTINCT date(datetime(dateDebut, 'unixepoch', 'localtime')) AS distinctDate " +
                "FROM Evenement " +
                "ORDER BY dateDebut ASC)";*/
        String query = "SELECT * FROM " + TABLE_NAME;

        List<Long> li = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery(query,null);
        // si la table est vide
        if(cursor.getCount() == 0)
            return null;

        while (cursor.moveToNext()){
            li.add((cursor.getLong(0) * 1000));  // 0 -> column 0 //  *1000 pour convertir le resultat en millisecondes
        }
        return li;
    }

    /**
     * @author elbaz
     * @return cette methode retourne la liste des dates(long) des évènements avec doublant
     * pour afficher les évènements sur le calendrier (plusieurs évèenemnt par jour)
     */
    public List<Long> findAllEventsDates(){
        /* Requete pour recuperer les dates de tout les evenemements*/
        String query = "SELECT strftime('%s',distinctDate) FROM " +
                "(SELECT date(datetime(dateDebut, 'unixepoch', 'localtime')) AS distinctDate " +
                "FROM Evenement " +
                "ORDER BY dateDebut ASC)";

        List<Long> li = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery(query,null);
        // si la table est vide
        if(cursor.getCount() == 0)
            return null;

        while (cursor.moveToNext()){
            li.add((cursor.getLong(0) * 1000));  // 0 -> column 0 //  *1000 pour convertir le resultat en millisecondes
        }
        return li;
    }

    /**
     * @author elbaz
     * @param date pour retourner les évènements du mois passé en paramètre
     * @return cette methode retourne la liste des dates(long) des évènements avec doublant
     * pour afficher les évènements sur le calendrier (plusieurs évèenemnt par jour)
     */
    public List<SmartCalendarEventModel> findAllEventsByMonth(Date date){
        String month = SmartCalendarDateFormat.getDateFormatYearMonthDay(date);

        /*String query = "SELECT strftime('%s',distinctDate) FROM " +
                "(SELECT date(datetime(dateDebut, 'unixepoch', 'localtime')) AS distinctDate " +
                "FROM Evenement " +
                /*"WHERE " +
                "(SELECT strftime('%m',(SELECT datetime(dateDebut, 'unixepoch', 'localtime'))))" +
                " = (SELECT strftime('%m','"+month+"'))" + * /
                "ORDER BY dateDebut ASC)"; */
        String query = "SELECT * FROM " + TABLE_NAME ;



        List<SmartCalendarEventModel> eventItems = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery(query,null);
        // si la table est vide
        if(cursor.getCount() == 0)
            return null;

        boolean dejaTraite = false;
        long dernierDateTraiter = -1;
        if(cursor != null) {
            SmartCalendarEventModel event;
            cursor.moveToFirst();
            do{
                event = new SmartCalendarEventModel();
                event.setEventId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
                event.setTitre(cursor.getString(cursor.getColumnIndex(COL_TITRE)) + event.getEventId());
                event.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESC)));
                event.setOriginAddressId(cursor.getInt(cursor.getColumnIndex(COL_ADDRESS_DEPART)));
                event.setDestinationAddressId(cursor.getInt(cursor.getColumnIndex(COL_ADDRESS_DESTINATION)));
                event.setDateDebut(cursor.getLong(cursor.getColumnIndex(COL_DATE_DEBUT)));
                event.setDateFin(cursor.getLong(cursor.getColumnIndex(COL_DATE_FIN)));
            /*long dateDebutSeconds = cursor.getLong(0);
            long dateDebutMilliSeconds = dateDebutSeconds * 1000; // *1000 pour convertir le resultat en millisecondes

            li.add((dateDebutMilliSeconds));

            if(dernierDateTraiter != dateDebutMilliSeconds) {
                dejaTraite = false;
            }

            if(!dejaTraite) {
                List<Integer> list = getAllEventDuree(dateDebutSeconds);
                if(list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        //Log.e("Duuuree", String.valueOf(list.get(i)));
                        for (int j = 1; j <= list.get(i); j++) {
                            Calendar c = Calendar.getInstance();
                            c.setTimeInMillis(dateDebutMilliSeconds);
                            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + j);
                            li.add(c.getTimeInMillis());
                        }
                    }
                    dejaTraite = true;
                }
            }
                dernierDateTraiter = dateDebutMilliSeconds;*/
                eventItems.add(event);
            }while(cursor.moveToNext());
        }
        return eventItems;
    }

    public List<Integer> getAllEventDuree(long dateDebut){
        List<Integer> li = new ArrayList<>();
        String query = "SELECT CAST (julianday(datetime(dateFin, 'unixepoch', 'localtime')) - " +
                "julianday(datetime(dateDebut, 'unixepoch', 'localtime')) AS INTEGER) FROM " +
                TABLE_NAME + " WHERE strftime('%s',date(datetime(dateDebut, 'unixepoch', 'localtime'))) = '" +
                dateDebut + "' AND strftime('%s',date(datetime(dateDebut, 'unixepoch', 'localtime'))) " +
                " != strftime('%s',date(datetime(dateFin, 'unixepoch', 'localtime')))";


        open();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.getCount() == 0)
            return null;

        while (cursor.moveToNext()){
            li.add(cursor.getInt(0));
        }
        return li;
    }

    public SmartCalendarEventModel getEventById(int id){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + id;

        open();
        Cursor cursor = db.rawQuery(query, null);
        close();
        if(cursor != null){
            cursor.moveToFirst();
            SmartCalendarEventModel event = new SmartCalendarEventModel();
            event.setEventId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            event.setTitre(cursor.getString(cursor.getColumnIndex(COL_TITRE)) + event.getEventId());
            event.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESC)));
            event.setOriginAddressId(cursor.getInt(cursor.getColumnIndex(COL_ADDRESS_DEPART)));
            event.setDestinationAddressId(cursor.getInt(cursor.getColumnIndex(COL_ADDRESS_DESTINATION)));
            event.setDateDebut(cursor.getLong(cursor.getColumnIndex(COL_DATE_DEBUT)));
            event.setDateFin(cursor.getLong(cursor.getColumnIndex(COL_DATE_FIN)));
            return event;
        }
        return null;
    }

    public static Cursor getEventList(Context context){
        String[] columns = {EvenementDAO.COL_ID, EvenementDAO.COL_TITRE, EvenementDAO.COL_DESC};
        return getList(context, EvenementDAO.TABLE_NAME, columns);
    }

}