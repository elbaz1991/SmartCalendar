package fr.amu.univ.smartcalendar.ui.activities.adapters;

import android.content.Context;
import android.util.Log;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import fr.amu.univ.smartcalendar.outils.DateFormater;
import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.model.dao.AdresseDAO;
import fr.amu.univ.smartcalendar.model.entity.Adresse;

/**
 * Created by elbaz on 01/05/2017.
 */

public class EventViewAdapter{
    private Context context;
    private AdresseDAO adresseDAO;
    public EventViewAdapter(Context context){
        this.context = context;
        adresseDAO = new AdresseDAO(context);
    }


    public String formatTitre(String titre,long dateDebut,long dateFin,long currentDate){
        String titreResult = "";
        if(titre.equals(""))
            titreResult =  context.getString(R.string.sans_titre);
        else
            titreResult = titre;

        String dateDebutStr = DateFormater.dateFormatYyMmDd(new Date(dateDebut));
        String dateFinStr = DateFormater.dateFormatYyMmDd(new Date(dateFin));
        String currentDateStr = DateFormater.dateFormatYyMmDd(new Date(currentDate));

        if(getDifferenceDays(dateDebut,dateFin) > 0){
            long durreRdv = getDifferenceDays(dateDebut,dateFin) +1;
            Log.e("Date",": "+currentDate);
            long difference = getDifferenceDays(dateDebut,currentDate) +1 ;
            titreResult = titreResult + " (jour "+difference +"/"+ durreRdv +")";
        }

        return titreResult;
    }


    public String getDuree(long dateDebut,long dateFin,long currentDate){

        String dateDebutStr = DateFormater.dateFormatYyMmDd(new Date(dateDebut));
        String dateFinStr = DateFormater.dateFormatYyMmDd(new Date(dateFin));
        String currentDateStr = DateFormater.dateFormatYyMmDd(new Date(currentDate));



        String duree = "";

        if(getDifferenceDays(dateDebut,dateFin) == 0){
            duree = DateFormater.heureFormat(new Date(dateDebut)) +
                    " - " + DateFormater.heureFormat(new Date(dateFin));
        }

        if((dateDebutStr.equals(currentDateStr)) && !dateFinStr.equals(currentDateStr)){
            duree = DateFormater.heureFormat(new Date(dateDebut)) + " - " +
                    DateFormater.dateFormatddMM(new Date(dateFin)) /*+" à "+ DateFormater.heureFormat(new Date(dateFin))*/;
        }

        if((dateFinStr.equals(currentDateStr)) && !dateDebutStr.equals(currentDateStr)){
            duree = DateFormater.dateFormatddMM(new Date(dateDebut)) + " - " +
                    DateFormater.heureFormat(new Date(dateFin)) /*+" à "+ DateFormater.heureFormat(new Date(dateFin))*/;
        }

        if(!dateFinStr.equals(currentDateStr) && !dateDebutStr.equals(currentDateStr)){
            duree = DateFormater.dateFormatddMM(new Date(dateDebut)) + " - " +
                    DateFormater.dateFormatddMM(new Date(dateFin)) /*+" à "+ DateFormater.heureFormat(new Date(dateFin))*/;
        }


        return duree;
    }



    public String getPositionRdv(String lieu){
        if(lieu.equals(""))
            return null;
        Adresse adresse = adresseDAO.findByAdresseId(lieu);
        return adresse.getNom();
    }


    private long getDifferenceDays(long d1, long d2) {
        long diff = d2 - d1;
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }





}
