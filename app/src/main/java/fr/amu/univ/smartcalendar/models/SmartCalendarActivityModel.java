package fr.amu.univ.smartcalendar.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by j.Katende on 18/04/2017.
 */

public class SmartCalendarActivityModel extends SmartCalendarModel{
    private int activity_id;

    private String title;

    private String description;

    private int address_id;

    private boolean remind_guests;

    private List<Integer> guests;

    private Date date_add;

    private Date date_upd;

    private Date start_date;

    private Date end_date;

    public SmartCalendarActivityModel(){ this(0); }

    public SmartCalendarActivityModel(int activityId){
        // TODO: 18/04/2017  implements constructor
    }

    public int getActivityId(){
        return activity_id;
    }

    public String getTitle(){ return title; }

    public String getDescription(){ return description; }

    public int getAddressId(){ return address_id; }

    public boolean shouldRemindGuests(){ return remind_guests; }

    public List<Integer> getGuests(){
        // TODO: 18/04/2017 implements guests retrieval
        return new ArrayList<>();
    }

    public void setActivityId(int activityId){ this.activity_id = activityId; }

    public void setTitle(String title){ this.title = title; }

    public void setDescription(String description){ this.description = description; }

    public void setAddressId(int addressId){ this.address_id = addressId; }

    public void setRemindGuests(boolean remind){ this.remind_guests = remind; }

    public void setGuests(List<Integer> guestList){
        // TODO: 18/04/2017 update database
        this.guests = guestList;
    }

    public boolean addGuest(int guestId){
        // TODO: 18/04/2017 manage guest completion both data base and list
        return true;
    }

    public boolean removeGuest(int guestId){
        // TODO: 18/04/2017 implements guest removal
        return true;
    }

    public boolean add(){ return true; }

    public boolean update(){ return true; }

    public boolean delete(){ return true; }
}
