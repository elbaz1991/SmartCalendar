package fr.amu.univ.smartcalendar.model.entity;

import com.google.android.gms.location.places.Place;

/**
 * Created by elbaz on 26/04/2017.
 */

public class Adresse {
    private String idAdresse;
    private String adresse;
    private String nom;
    private double latitude;
    private double longitude;

    public Adresse(){

    }

    public Adresse(Place place){
        if(place != null) {
            this.idAdresse = place.getId();
            this.adresse = place.getAddress().toString();
            this.nom = place.getName().toString();
            this.latitude = place.getLatLng().latitude;
            this.longitude = place.getLatLng().longitude;
        }
    }

    public String getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(String idAdresse) {
        this.idAdresse = idAdresse;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
