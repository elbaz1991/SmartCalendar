package fr.amu.univ.smartcalendar.models;

/**
 *
 * Created by j.Katende on 18/04/2017.
 */

public class SmartCalendarAddressModel extends SmartCalendarModel {
    private int address_id;

    private String address_label;

    private float longitude;

    private float latitude;

    public SmartCalendarAddressModel(){ this(0); }

    public SmartCalendarAddressModel(int addressId){
        // TODO: 18/04/2017 build constructor
    }

    public int getAddressId(){ return this.address_id; }

    public String getAddressLabel(){
        return this.address_label;
    }

    public float getLongitude(){
        return this.longitude;
    }

    public float getLatitude(){
        return this.latitude;
    }

    public void setAddressId(int addressId){ this.address_id = addressId; }

    public void setAddressLabel(String addressLabel){ this.address_label = addressLabel; }

    public void setLongitude(float addressLongitude){ this.longitude = addressLongitude; }

    public void setLatitude(float addressLatitude){ this.latitude = addressLatitude; }

    public boolean add(){ return true; }

    public boolean update(){ return true; }

    public boolean delete(){ return true; }
}
