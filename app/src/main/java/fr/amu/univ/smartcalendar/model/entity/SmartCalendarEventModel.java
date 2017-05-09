package fr.amu.univ.smartcalendar.model.entity;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;

/**
 *
 * Created by elbaz on 18/04/2017.
 */

public class SmartCalendarEventModel {
    private int event_id;
    private String titre;
    private String description;
    private long dateDebut;
    private long dateFin;

    private long currentDate;
    private int color;

    private boolean remind_guests;

    private List<Integer> participants;

    private int origin_address_id;

    private int destination_address_id;

    private Context context = null;

    public SmartCalendarEventModel(){}

    public SmartCalendarEventModel(Context base){
        this(base, 0);
    }


    public SmartCalendarEventModel(Context base, int eventId) {
        context = base;

        if(eventId > 0){
            EvenementDAO evenementDAO = new EvenementDAO(context);
            SmartCalendarEventModel eventModel = evenementDAO.getEventById(eventId);
            if(eventModel != null) {
                this.event_id = eventModel.getEventId();
                this.titre = eventModel.getTitre();
                this.description = eventModel.getDescription();
                this.dateDebut = eventModel.getDateDebut();
                this.dateFin = eventModel.getDateFin();
                this.destination_address_id = eventModel.getDestinationAddressId();
                this.origin_address_id = eventModel.getOriginAddressId();
            }
        }
    }

    public SmartCalendarEventModel(String titre, String description){
        this.titre = titre;
        this.description = description;
    }

    public int getEventId(){ return this.event_id; }

    public void setEventId(int eventId){ this.event_id = eventId; }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDateDebut() {
        return dateDebut * 1000;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut.getTime();
    }

    public long getDateDebutSeconds() {
        return dateDebut / 1000;
    }

    public void setDateDebut(Calendar dateDebut) {
        this.dateDebut = dateDebut.getTimeInMillis();
    }

    public long getDateFin() {
        return dateFin * 1000;
    }

    public void setDateFin(Calendar dateFin) {
        this.dateFin = dateFin.getTimeInMillis();
    }

    public long getDateFinSeconds() {
        return dateFin / 1000;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setDateDebut(long dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(long dateFin) {
        this.dateFin = dateFin;
    }

    public int getOriginAddressId(){
        return this.origin_address_id;
    }

    public void setOriginAddressId(int addressId){ this.origin_address_id = addressId; }

    public int getDestinationAddressId(){ return this.destination_address_id; }

    public void setDestinationAddressId(int addressId){
        this.destination_address_id = addressId;
    }

    public long getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(long currentDate) {
        this.currentDate = currentDate;
    }

    public static List<SmartCalendarEventModel> getSmartCalendarEvents(Context context){
        //Database
        Cursor cursor = EvenementDAO.getEventList(context);
        ArrayList<SmartCalendarEventModel> events = new ArrayList<>();

        if(cursor != null){
            try {
                cursor.moveToFirst();
                do {
                    Log.d("DEBUG", EvenementDAO.COL_TITRE +  cursor.getString(0));
                } while (cursor.moveToNext());
            }catch (Exception ignored){
                Log.d("ERROR", "une erreur");
            }
        }

        return events;
    }
}