package fr.amu.univ.smartcalendar.data;

/**
 * Created by elbaz on 18/04/2017.
 */

public class EventEntity {
    private String titre;
    private String description;

    public EventEntity(){

    }

    public EventEntity(String titre, String description) {
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
