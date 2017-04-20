package fr.amu.univ.smartcalendar.model.entity;

import java.util.Date;

/**
 * Created by elbaz on 18/04/2017.
 */

public class Evenement {
        private String titre;
        private String description;
        private long dateDebut;
        private long dateFin;


    public Evenement() {

    }

    public Evenement(String titre, String description){
        this.titre = titre;
        this.description = description;
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

    public long getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut.getTime();
    }

    public long getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin.getTime();
    }
}
