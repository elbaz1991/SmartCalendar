package fr.amu.univ.smartcalendar.model.entity;

import android.content.Context;

import fr.amu.univ.smartcalendar.model.SmartCalendarModel;
import fr.amu.univ.smartcalendar.model.dao.AddressDAO;

/**
 *
 * Created by j.Katende on 18/04/2017.
 */

public class SmartCalendarAddressModel{
    private int address_id;

    private String address_label;

    private double longitude;

    private double latitude;

    private int event_id;

    private int origin;

    private int destination;

    private Context context = null;

    public SmartCalendarAddressModel(){

    }

    public SmartCalendarAddressModel(Context base){ this(base, 0); }

    public SmartCalendarAddressModel(Context context, int addressId){
        this.context = context;

        if(addressId > 0){
            AddressDAO addressDAO = new AddressDAO(context);
            SmartCalendarAddressModel address = addressDAO.getAddressById(addressId);
            if(address != null) {
                this.address_id = address.getEventId();
                this.address_label = address.getAddressLabel();
                this.latitude = address.getLatitude();
                this.longitude = address.getLongitude();
                this.event_id = address.getEventId();
                this.origin = address.isOrigin() ? 1 : 0;
                this.destination = address.isDestination() ? 1 : 0;
            }
        }
    }

    public int getAddressId(){ return this.address_id; }

    public String getAddressLabel(){
        return this.address_label;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public double getLatitude(){
        return this.latitude;
    }

    public void setAddressId(int addressId){ this.address_id = addressId; }

    public void setAddressLabel(String addressLabel){ this.address_label = addressLabel; }

    public void setLongitude(double addressLongitude){ this.longitude = addressLongitude; }

    public void setLatitude(double addressLatitude){ this.latitude = addressLatitude; }

    public int getEventId(){ return this.event_id; }

    public void setEventId(int eventId){
        this.event_id = eventId;
    }

    public boolean isOrigin(){ return (origin == 1); }

    public void setOrigin(int o){ this.origin = 1; }

    public void setDestination(int d){ this.destination = d; }

    public boolean isDestination(){ return  (destination == 1); }
}
