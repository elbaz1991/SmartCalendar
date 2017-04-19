package fr.amu.univ.smartcalendar.data.entity;

/**
 * Created by elbaz on 18/04/2017.
 */

public class Evenement {
    private static final String TABLE_NAME = "Evenement.db";
    private static final String COL_TITRE = "Titre";
    private static final String COL_DESC = "Description";
    private static final String COL_DATE_DEBUT = "DateDebut";
    private static final String COL_DATE_FIN = "DateFin";

    private String titre;
    private String description;

    public Evenement(){

    }

    public Evenement(String titre, String description) {
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
}
