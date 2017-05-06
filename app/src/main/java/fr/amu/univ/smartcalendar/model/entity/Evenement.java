package fr.amu.univ.smartcalendar.model.entity;



import java.util.Calendar;


/**
 * Created by elbaz on 18/04/2017.
 */

public class Evenement {
        protected int id;
        protected String titre;
        protected String description;
        protected long dateDebut;
        protected long dateFin;
        protected Adresse adresseDepart;
        protected Adresse adresseRdv;
        protected long currentDate;
        protected int color;


    public Evenement() {

    }

    public Evenement(String titre, String description){
        this.titre = titre;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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


    /**
     *  /1000  conversion vers millisecondes
     *  * 1000 conversion vers secondes
     */

    public long getDateDebut() {
        return dateDebut * 1000;
    }

    public long getDateDebutSecondes() {
        return dateDebut / 1000;
    }

    public void setDateDebut(Calendar dateDebut) {
        this.dateDebut = dateDebut.getTimeInMillis();
    }

    public long getDateFin() {
        return dateFin * 1000;
    }

    public long getDateFinSecondes() {
        return dateFin / 1000;
    }

    public void setDateFin(Calendar dateFin) {
        this.dateFin = dateFin.getTimeInMillis();
    }

    public void setDateDebut(long dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(long dateFin) {
        this.dateFin = dateFin;
    }

    public Adresse getAdresseDepart() {
        return adresseDepart;
    }

    public void setAdresseDepart(Adresse adresseDepart) {
        this.adresseDepart = adresseDepart;
    }

    public Adresse getAdresseRdv() {
        return adresseRdv;
    }

    public void setAdresseRdv(Adresse adresseRdv) {
        this.adresseRdv = adresseRdv;
    }

    public long getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(long currentDate) {
        this.currentDate = currentDate;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
