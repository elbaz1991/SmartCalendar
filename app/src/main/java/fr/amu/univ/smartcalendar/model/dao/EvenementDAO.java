package fr.amu.univ.smartcalendar.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.outils.DateFormater;
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
    public static final String COL_ADRESSE_DEPART = "AdresseDepart";
    public static final String COL_ADRESSE_RDV = "AdresseRdv";
    public static final String COL_COLEUR = "Coleur";
    public static final String COL_RAPPEL_1 = "rappel1";
    public static final String COL_RAPPEL_2 = "rappel2";
    public static final String COL_MOYENNE_TRANSPORT = "moyenneTransport";



    public static final String TABLE_CREATE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ( "+
                                                COL_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                                                COL_TITRE +" TEXT,"+
                                                COL_DESC +" TEXT,"+
                                                COL_DATE_DEBUT +" INTEGER,"+
                                                COL_DATE_FIN + " INTEGER,"+
                                                COL_ADRESSE_DEPART + " TEXT,"+
                                                COL_ADRESSE_RDV + " TEXT,"+
                                                COL_COLEUR + " INTEGER,"+
                                                COL_RAPPEL_1 + " INTEGER,"+
                                                COL_RAPPEL_2 + " INTEGER,"+
                                                COL_MOYENNE_TRANSPORT + " TEXT"+
                                                ");";

    public static final String TABLE_DROP = "DROP TABLE IF EXIST "+TABLE_NAME;

    private String queryFindAll = "SELECT * FROM Evenement ORDER BY dateDebut";



    private AdresseDAO adresseDAO;

    private Context context;
    public EvenementDAO(Context context) {
        super(context);
        this.context = context;
        this.adresseDAO = new AdresseDAO(context);
    }


    /**
     * @param e l'evenement à ajouter à la base
     */
    public boolean insert(Evenement e) {
        open();// ouverture de la connexion
        ContentValues values = new ContentValues();
        values.put(EvenementDAO.COL_TITRE,e.getTitre());
        values.put(EvenementDAO.COL_DESC,e.getDescription());
        values.put(EvenementDAO.COL_DATE_DEBUT,e.getDateDebutSecondes());
        values.put(EvenementDAO.COL_DATE_FIN,e.getDateFinSecondes());
        values.put(EvenementDAO.COL_ADRESSE_DEPART,e.getAdresseDepart().getIdAdresse());
        values.put(EvenementDAO.COL_ADRESSE_RDV,e.getAdresseRdv().getIdAdresse());
        values.put(EvenementDAO.COL_COLEUR,e.getColor());
        values.put(EvenementDAO.COL_RAPPEL_1,e.getRappel1());
        values.put(EvenementDAO.COL_RAPPEL_2,e.getRappel2());
        values.put(EvenementDAO.COL_RAPPEL_2,e.getRappel2());
        values.put(EvenementDAO.COL_MOYENNE_TRANSPORT,e.getMoyenneDeTransport());



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
        open();

        close();

    }

    /**
     * @author elbaz
     * @param idEvent l'evenement à supprimer de la base
     */
    public void delete(int idEvent) {
        String query = "DELETE FROM "+TABLE_NAME + " WHERE "+COL_ID +" ="+idEvent;
        open();
        db.execSQL(query);
        adresseDAO.delete(idEvent);
        close();

    }


    /**
     * @param e l'evenement à modifer
     */
    public void update(Evenement e) {

    }


    /**
     *@return renvoi tout les évènements
     */
    public List<Evenement> findAll() {
        List<Evenement> li = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery(this.queryFindAll,null);
        // si la table est vide
        if(cursor.getCount() == 0)
            return null;
        //Log.e("DATA",String.valueOf(cursor.getCount()));

        Evenement event;
        while (cursor.moveToNext()){
            event = new Evenement();
            event.setTitre(cursor.getString(cursor.getColumnIndex(COL_TITRE)));
            Calendar d = Calendar.getInstance();
            d.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_DATE_DEBUT)));
            event.setDateDebut(d);
            d.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_DATE_FIN)));
            event.setDateFin(d);
            event.setColor(cursor.getInt(cursor.getColumnIndex(COL_COLEUR)));
            li.add(event);
        }
        close();
        return li;
    }


    /**
     * @author elbaz
     *@return renvoi tout les évènements par mois
     */
    public List<Evenement> findByMonthAndYear(int month) {
        List<Evenement> li = new ArrayList<>();
        String query = "SELECT * FROM Evenement \n" +
                "WHERE \n" +
                "CAST ( (SELECT strftime('%m',(SELECT datetime(dateDebut, 'unixepoch', 'localtime')))) AS INTEGER)  = "+month+"\n" +
                "ORDER BY dateDebut ASC";
        open();
        Cursor cursor = db.rawQuery(query,null);
        // si la table est vide
        if(cursor.getCount() == 0)
            return null;
        //Log.e("DATA",String.valueOf(cursor.getCount()));

        Evenement event;
        while (cursor.moveToNext()){
            event = new Evenement();
            event.setTitre(cursor.getString(cursor.getColumnIndex(COL_TITRE)));
            Calendar d = Calendar.getInstance();
            d.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_DATE_DEBUT)));
            event.setDateDebut(d);
            d.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_DATE_FIN)));
            event.setDateFin(d);
            event.setColor(cursor.getInt(cursor.getColumnIndex(COL_COLEUR)));
            li.add(event);
        }
        close();
        return li;
    }


    /**
     * @author elbaz
     * @param idEvent
     *@return renvoi l'évènement par id
     */
    public Evenement findById(int idEvent) {

        Evenement event;
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE id="+idEvent;
        Calendar d = Calendar.getInstance();
        open();
        Cursor cursor = db.rawQuery(query,null);
        // si la table est vide
        if(cursor.getCount() == 0)
            return null;
        else {
            cursor.moveToFirst();
            event = new Evenement();
            event.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            event.setTitre(cursor.getString(cursor.getColumnIndex(COL_TITRE)));
            event.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESC)));
            d.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_DATE_DEBUT)));
            event.setDateDebut(d);
            d.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_DATE_FIN)));
            event.setDateFin(d);
            event.setAdresseDepart(adresseDAO.findByAdresseId(cursor.getString(cursor.getColumnIndex(COL_ADRESSE_DEPART))));
            event.setAdresseRdv(adresseDAO.findByAdresseId(cursor.getString(cursor.getColumnIndex(COL_ADRESSE_RDV))));
            event.setDateFin(d);
            event.setColor(cursor.getInt(cursor.getColumnIndex(COL_COLEUR)));
            event.setMoyenneDeTransport(cursor.getString(cursor.getColumnIndex(COL_MOYENNE_TRANSPORT)));
            event.setRappel1(cursor.getInt(cursor.getColumnIndex(COL_RAPPEL_1)));
            event.setRappel2(cursor.getInt(cursor.getColumnIndex(COL_RAPPEL_2)));

        }

        close();
        return event;
    }





    /***
     *
     */
    public List<Evenement> findByDateEvent(Date date){
        String dateString = DateFormater.dateFormatYyMmDd(date);
        /*String queryFindByMonth = "SELECT * FROM Evenement " +
                "WHERE " +
                "(SELECT strftime('%d',(SELECT datetime(dateDebut, 'unixepoch', 'localtime')))) = (SELECT strftime('%d','"+dateString+"')) " +
                "AND " +
                "(SELECT strftime('%m',(SELECT datetime(dateDebut, 'unixepoch', 'localtime')))) = (SELECT strftime('%m','"+dateString+"')) " +
                "AND " +
                "(SELECT strftime('%Y',(SELECT datetime(dateDebut, 'unixepoch', 'localtime')))) = (SELECT strftime('%Y','"+dateString+"')) ";
        */

        String queryFindByMonth = "SELECT * FROM Evenement" +
                " WHERE '"+dateString+"'"+
                " BETWEEN date(datetime(dateDebut, 'unixepoch', 'localtime')) "+
                " AND date(datetime(dateFin, 'unixepoch', 'localtime'))";

        List<Evenement> li = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery(queryFindByMonth,null);
        // si la table est vide
        if(cursor.getCount() == 0)
            return null;

        Calendar d = Calendar.getInstance();
        Evenement event;
        while (cursor.moveToNext()){
            event = new Evenement();
            event.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            event.setTitre(cursor.getString(cursor.getColumnIndex(COL_TITRE)));
            event.setDescription(cursor.getString(cursor.getColumnIndex(COL_DESC)));
            d.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_DATE_DEBUT)));
            event.setDateDebut(d);

            d.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(COL_DATE_FIN)));
            event.setDateFin(d);
            event.setAdresseDepart(adresseDAO.findByAdresseId(cursor.getString(cursor.getColumnIndex(COL_ADRESSE_DEPART))));
            event.setAdresseRdv(adresseDAO.findByAdresseId(cursor.getString(cursor.getColumnIndex(COL_ADRESSE_RDV))));
            event.setCurrentDate(date.getTime());
            event.setDateFin(d);
            event.setColor(cursor.getInt(cursor.getColumnIndex(COL_COLEUR)));
            li.add(event);
        }
        close();
        return li;
    }

    /**
     * @author elbaz
     * @return cette methode retourne la liste des dates(long) des évènements sans doublant
     * pour construire la cellule global qui va contenir les évènements de chaque jour
     */
    public List<Long> findDistinctEventsDates(){
        /* Requete pour recuperer les dates des evenemement sans doublant*/
        String query = "SELECT strftime('%s',distinctDate) FROM " +
                       "(SELECT DISTINCT date(datetime(dateDebut, 'unixepoch', 'localtime')) AS distinctDate " +
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
        close();
        return li;
    }


    /**
     * @author elbaz
     * @return cette methode retourne la liste des dates(long) des évènements avec doublant
     * pour afficher les évènements sur le calendrier (plusieurs évèenemnt par jour)
     */
    public List<Long> findAllEventsDates() {
        String query = "SELECT strftime('%s',distinctDate) FROM " +
                "(SELECT date(datetime(dateDebut, 'unixepoch', 'localtime')) AS distinctDate " +
                "FROM Evenement " +
                "ORDER BY dateDebut ASC)";


        List<Long> li = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery(query, null);
        // si la table est vide
        if (cursor.getCount() == 0)
            return null;

        boolean dejaTraite = false;
        long dernierDateTraiter = -1;
        while (cursor.moveToNext()) {
            long dateDebutSecondes = cursor.getLong(0);
            long dateDebutMilliSecondes = dateDebutSecondes * 1000; // *1000 pour convertir le resultat en millisecondes

            li.add((dateDebutMilliSecondes));

            if (dernierDateTraiter != dateDebutMilliSecondes) {
                dejaTraite = false;
            }

            if (!dejaTraite) {
                List<Integer> list = getAllEventDuree(dateDebutSecondes);
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        //Log.e("Duuuree", String.valueOf(list.get(i)));
                        for (int j = 1; j <= list.get(i); j++) {
                            Calendar c = Calendar.getInstance();
                            c.setTimeInMillis(dateDebutMilliSecondes);
                            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + j);
                            li.add(c.getTimeInMillis());
                        }
                    }
                    dejaTraite = true;
                }
            }
            dernierDateTraiter = dateDebutMilliSecondes;
        }
        close();
        return li;
    }

    /**
     * @author elbaz
     * @param date pour retourner les évènements du mois passé en paramètre
     * @return cette methode retourne la liste des dates(long) des évènements avec doublant
     * pour afficher les évènements sur le calendrier (plusieurs évèenemnt par jour)
     */
    public List<Long> findAllEventsByMonth(Date date){
        String month = DateFormater.dateFormatYyMmDd(date);

        String query = "SELECT strftime('%s',distinctDate) FROM " +
                "(SELECT date(datetime(dateDebut, 'unixepoch', 'localtime')) AS distinctDate " +
                "FROM Evenement " +
                "WHERE " +
                "(SELECT strftime('%m',(SELECT datetime(dateDebut, 'unixepoch', 'localtime'))))" +
                " = (SELECT strftime('%m','"+month+"'))" +
                "ORDER BY dateDebut ASC)";



        List<Long> li = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery(query,null);
        // si la table est vide
        if(cursor.getCount() == 0)
            return null;

        boolean dejaTraite = false;
        long dernierDateTraiter = -1;
        while (cursor.moveToNext()){
            long dateDebutSecondes = cursor.getLong(0);
            long dateDebutMilliSecondes = dateDebutSecondes * 1000; // *1000 pour convertir le resultat en millisecondes

            li.add((dateDebutMilliSecondes));

            if(dernierDateTraiter != dateDebutMilliSecondes) {
                dejaTraite = false;
            }

            if(!dejaTraite) {
                List<Integer> list = getAllEventDuree(dateDebutSecondes);
                if(list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        //Log.e("Duuuree", String.valueOf(list.get(i)));
                        for (int j = 1; j <= list.get(i); j++) {
                            Calendar c = Calendar.getInstance();
                            c.setTimeInMillis(dateDebutMilliSecondes);
                            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + j);
                            li.add(c.getTimeInMillis());
                        }
                    }
                    dejaTraite = true;
                }
            }
            dernierDateTraiter = dateDebutMilliSecondes;
        }
        close();
        return li;
    }


    /**
     * @author elbaz
     * @return cette methode retourne la liste des dates(long) des évènements sans doublant
     * pour construire la cellule global qui va contenir les évènements de chaque jour
     */
    public List<Long> findDistinctAllEvents(){
        /* Requete pour recuperer les dates des evenemement sans doublant*/
        String query = "SELECT strftime('%s',distinctDate) FROM " +
                "(SELECT DISTINCT date(datetime(dateDebut, 'unixepoch', 'localtime')) AS distinctDate " +
                "FROM Evenement " +
                "ORDER BY dateDebut ASC)";

        List<Long> li = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery(query,null);
        // si la table est vide
        if(cursor.getCount() == 0)
            return null;

        while (cursor.moveToNext()){
            long dateDebutSecondes = cursor.getLong(0);
            long dateDebutMilliSecondes = dateDebutSecondes * 1000; // *1000 pour convertir le resultat en millisecondes

            // pour verifier si la date est déjà existante sur la liste
            // (il est déjà ajouté suite à un évènement sur plusieurs jours ...
            if(!existDeja(li,dateDebutMilliSecondes))
                li.add((dateDebutMilliSecondes));
            //if(dateDebutMilliSecondes > getLastList(li))

            int maxDuree = getMaxDuree(dateDebutSecondes);
            if(maxDuree > 0){
                for(int i=1;i<=maxDuree;i++){
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(dateDebutMilliSecondes);
                    c.set(Calendar.DAY_OF_YEAR,c.get(Calendar.DAY_OF_YEAR) + i);
                    // pour verifier si la date est déjà existante sur la liste
                    // (il est déjà ajouté suite à un évènement sur plusieurs jours ...
                    if(!existDeja(li,c.getTimeInMillis()))
                        li.add(c.getTimeInMillis());
                }
            }

        }
        close();
        return li;
    }



    /**
     * @author elbaz
     * @param month pour retourner les évènements du mois passé en paramètre
     * @return cette methode retourne la liste des dates(long) des évènements sans doublant
     * pour construire la cellule global qui va contenir les évènements de chaque jour
     */
    public List<Long> findDistinctMonthEvents(int month){
        //String dateMonth = DateFormater.dateFormatYyMmDd(date);
        /* Requete pour recuperer les dates des evenemement sans doublant*/
        String query = "SELECT strftime('%s',distinctDate) FROM " +
                "(SELECT DISTINCT date(datetime(dateDebut, 'unixepoch', 'localtime')) AS distinctDate " +
                "FROM Evenement " +
                "WHERE " +
                "CAST ( (SELECT strftime('%m',(SELECT datetime(dateDebut, 'unixepoch', 'localtime')))) AS INTEGER)" +
                "  = "+month+
                " ORDER BY dateDebut ASC)";

        List<Long> li = new ArrayList<>();
        open();
        Cursor cursor = db.rawQuery(query,null);
        // si la table est vide
        if(cursor.getCount() == 0)
            return null;

        while (cursor.moveToNext()){
            long dateDebutSecondes = cursor.getLong(0);
            long dateDebutMilliSecondes = dateDebutSecondes * 1000; // *1000 pour convertir le resultat en millisecondes

            // pour verifier si la date est déjà existante sur la liste
            // (il est déjà ajouté suite à un évènement sur plusieurs jours ...
            if(!existDeja(li,dateDebutMilliSecondes))
                li.add((dateDebutMilliSecondes));
            //if(dateDebutMilliSecondes > getLastList(li))

            int maxDuree = getMaxDuree(dateDebutSecondes);
            if(maxDuree > 0){
                for(int i=1;i<=maxDuree;i++){
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(dateDebutMilliSecondes);
                    c.set(Calendar.DAY_OF_YEAR,c.get(Calendar.DAY_OF_YEAR) + i);
                    // pour verifier si la date est déjà existante sur la liste
                    // (il est déjà ajouté suite à un évènement sur plusieurs jours ...
                    if(!existDeja(li,c.getTimeInMillis()))
                        li.add(c.getTimeInMillis());
                }
            }

        }
        close();
        return li;
    }



    /**
     * @author elbaz
     * @param list
     * @param value
     * @return
     */
    public boolean existDeja(List<Long> list,long value){
        for(int i=0;i<list.size();i++){
            if(list.get(i) == value)
                return true;
        }
        return false;
    }

    /**
     * @author elbaz
     * @param dateDebut date de debut de l'évènement
     * @return la durée maximal (en jours) d'un évènement afin de l'ajouter sur toutes les dates dont il fait parti ...
     */
    public int getMaxDuree(long dateDebut){
        String query = "SELECT " +
                       "CAST (MAX (julianday(datetime(dateFin, 'unixepoch', 'localtime')) - julianday(datetime(dateDebut, 'unixepoch', 'localtime'))) AS INTEGER) " +
                       "FROM Evenement " +
                       "WHERE strftime('%s',date(datetime(dateDebut, 'unixepoch', 'localtime'))) = '"+dateDebut+"' " +
                       "AND " +
                       "strftime('%s',date(datetime(dateDebut, 'unixepoch', 'localtime'))) != strftime('%s',date(datetime(dateFin, 'unixepoch', 'localtime')))";


        open();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor != null){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return -1;
    }

    /**
     *
     * @param dateDebut date de debut d'un évenènement afin de recuperer la duree de tous les évènement qui debute à ce jour
     * @return la duree de tous les évènements
     */
    public List<Integer> getAllEventDuree(long dateDebut){
        List<Integer> li = new ArrayList<>();
        String query = "SELECT " +
                "CAST (julianday(datetime(dateFin, 'unixepoch', 'localtime')) - julianday(datetime(dateDebut, 'unixepoch', 'localtime')) AS INTEGER) " +
                "FROM Evenement " +
                "WHERE strftime('%s',date(datetime(dateDebut, 'unixepoch', 'localtime'))) = '"+dateDebut+"' " +
                "AND " +
                "strftime('%s',date(datetime(dateDebut, 'unixepoch', 'localtime'))) != strftime('%s',date(datetime(dateFin, 'unixepoch', 'localtime')))";


        open();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.getCount() == 0)
            return null;

        while (cursor.moveToNext()){
            li.add(cursor.getInt(0));
        }
        close();
        return li;
    }



    public int getEventColor(long dateDebut){
        long dateInSeconde = dateDebut / 1000;
        List<Integer> li = new ArrayList<>();
        String query = "SELECT "+COL_COLEUR+ " FROM "+TABLE_NAME+
                        " WHERE strftime('%s',date(datetime(dateDebut, 'unixepoch', 'localtime'))) = '"+dateInSeconde+"'";

        open();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.getCount() >0) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
       return -1;
    }

    public int getLastInsertedId(){
        String query = "SELECT seq from SQLITE_SEQUENCE WHERE name = '"+TABLE_NAME+"'";
        open();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() >0) {
            cursor.moveToFirst();
            Log.e("Tets",cursor.getInt(0)+" ");
            return cursor.getInt(0);
        }
        return 0;
    }

}
