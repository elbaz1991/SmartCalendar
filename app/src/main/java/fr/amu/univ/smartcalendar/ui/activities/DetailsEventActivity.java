package fr.amu.univ.smartcalendar.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Date;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;
import fr.amu.univ.smartcalendar.model.entity.Evenement;
import fr.amu.univ.smartcalendar.outils.DateFormater;

import static fr.amu.univ.smartcalendar.R.id.spinner;

public class DetailsEventActivity extends AppCompatActivity implements OnMapReadyCallback {

    private EvenementDAO evenementDAO = new EvenementDAO(this);
    private DateFormater dateFormater = new DateFormater();
    private TextView ui_titreDeLevenement;
    private TextView ui_descriptionDeLevenement;
    private TextView ui_timeDeLevenement;
    private TextView ui_villeDepart;
    private TextView ui_villeArrive;
    private TextView ui_moyenDetransport;
    private TextView ui_rappel1;
    private TextView ui_rappel2;
    private LinearLayout ui_couleurDetails;
    private LinearLayout ui_layoutMap;
    private TextView ui_invite1;
    private TextView ui_invite2;
    private GoogleMap mMap;

    private static  int idEvenement;
    private static  Evenement evenement;
    //private static Activity detailsActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_event);
        //detailsActivity.setContentView(R.layout.activity_details_event);
         idEvenement = getIntent().getExtras().getInt("idEvenement");
         evenement = evenementDAO.findById(idEvenement);

        // intialisation
            ui_titreDeLevenement = (TextView) findViewById(R.id.detailsTitre);
            ui_descriptionDeLevenement = (TextView) findViewById(R.id.DetailsDescription);
            ui_timeDeLevenement = (TextView) findViewById(R.id.DetailsTime);
            ui_villeDepart = (TextView) findViewById(R.id.DetailsLieuDepart);
            ui_villeArrive = (TextView) findViewById(R.id.DetailsLieuArrive);
            ui_moyenDetransport = (TextView) findViewById(R.id.DetailsMoyenDeTransport);
            ui_rappel1 = (TextView) findViewById(R.id.DetailsRappel1);
            ui_rappel2 = (TextView) findViewById(R.id.DetailsRappel2);
            ui_couleurDetails = (LinearLayout) findViewById(R.id.couleurDetails);
            ui_layoutMap = (LinearLayout) findViewById(R.id.layoutMap);
            ui_invite1 = (TextView) findViewById(R.id.DetailsEmails1);
            ui_invite2 = (TextView) findViewById(R.id.DetailsEmails2);

        // modification
        //Toast.makeText(this,"rappel :  "+evenement.getRappel1(),Toast.LENGTH_SHORT).show();


        ui_couleurDetails.setBackgroundColor(evenement.getColor());

        if(evenement.getTitre().equals("")){
            ui_titreDeLevenement.setText("(Pas de titre) ");
        }else {
            ui_titreDeLevenement.setText(evenement.getTitre());
        }

        if(evenement.getDescription().equals("")){
            ui_descriptionDeLevenement.setText("(Pas de description ... )");
        }else {
            ui_descriptionDeLevenement.setText(evenement.getDescription());
        }

        ui_timeDeLevenement.setText(dateFormater.dateFormatddMM(new Date(evenement.getDateDebut()))+" à "+dateFormater.heureFormat(new Date(evenement.getDateDebut()))
                                    +" - "+ dateFormater.dateFormatddMM(new Date(evenement.getDateFin()))+" à "+dateFormater.heureFormat(new Date(evenement.getDateFin())));

      if(evenement.getAdresseDepart() != null){
          ui_villeDepart.setText("- "+evenement.getAdresseDepart().getAdresse());
        }else {
          ui_villeDepart.setText("(Adresse de départ non spécifie ... )");
      }

        if(evenement.getAdresseRdv() != null){
            ui_villeArrive.setText("- "+evenement.getAdresseRdv().getAdresse());
        }else {
            ui_villeArrive.setText("(Adresse d'arrivé non spécifie ... )");
        }

        if(evenement.getMoyenneDeTransport() == null){
            ui_moyenDetransport.setText("(Moyen de transport non spécifi ..)");
        }else {
            ui_moyenDetransport.setText(evenement.getMoyenneDeTransport());

            switch (evenement.getMoyenneDeTransport()){
                case "Voiture":
                    ui_moyenDetransport.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_voiture,0,0,0); break;
                case "Vélo":
                    ui_moyenDetransport.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_velo,0,0,0);break;
                case "Transport commun":
                    ui_moyenDetransport.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_transportcommun,0,0,0);break;
                case "à pieds":
                    ui_moyenDetransport.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_apieds,0,0,0);break;

            }

        }

     if(evenement.getRappel1() == 0){
            ui_rappel1.setText("Au moment du rendez-vous");
        }else if(evenement.getRappel1() < -1) {
            ui_rappel1.setText("Auccun");
        }else {
         int rappel1 = evenement.getRappel1()/60;

                afficherRappels(rappel1,ui_rappel1);

            //ui_rappel1.setText("Avant "+evenement.getRappel1()+" ");
        }

        if(evenement.getRappel2() == 0){
            ui_rappel2.setText("Au moment du rendez-vous");
        }else if(evenement.getRappel2() < 0) {
            ui_rappel2.setText("Auccun");
        }else {
            int rappel2 = evenement.getRappel2()/60;

            afficherRappels(rappel2,ui_rappel2);
            //ui_rappel2.setText("Avant "+evenement.getRappel2()+" ");
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(DetailsEventActivity.this);






//**********************************************





    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


       mMap = googleMap;
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setAllGesturesEnabled(false);
       final float zoomLevel = (float) 8.0;

        if(evenement.getAdresseRdv() != null) {
            LatLng position = new LatLng(evenement.getAdresseRdv().getLatitude(), evenement.getAdresseRdv().getLongitude());

            mMap.addMarker(new MarkerOptions().position(position).title("Lieu de RDV"));

            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, zoomLevel));
        }else {


            mMap.getProjection();

        }


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(evenement.getAdresseDepart() != null && evenement.getAdresseRdv() != null && evenement.getMoyenneDeTransport() != null ){
                    Intent intent = new Intent(DetailsEventActivity.this, MapsActivity.class);
                    intent.putExtra("Depart",evenement.getAdresseDepart().getIdAdresse());
                    intent.putExtra("arrive",evenement.getAdresseRdv().getIdAdresse());
                    intent.putExtra("moyenDeTrans",evenement.getMoyenneDeTransport());
                    startActivity(intent);
                }else {
                    Toast.makeText(DetailsEventActivity.this," Entrez votre ( Lieu de depart , Lieu De RDV , Moyen de transport) pour avoir plus de details sur votre trajet ..  ",Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    private void afficherRappels (int rappel, TextView textView){

        if(rappel<60){
            textView.setText("Avant "+rappel+" minutes");
        }else {
            int heures = rappel / 60;
            int minutes = rappel % 60;

            if(heures < 24){
                textView.setText("Avant "+heures+" heures et "+minutes+" minutes");
            }else{
                int nbrJours = heures/24;
                int nbrHeures = heures % 24;

                if(nbrJours < 7){
                    textView.setText("Avant "+nbrJours+" jours et "+nbrHeures+" heures");

                }else{
                    int nbrSemaine = nbrJours / 7;
                    int nbrResteJour = nbrJours % 7;

                    if(nbrSemaine<4){
                        textView.setText("Avant "+nbrSemaine+" semaines et "+nbrResteJour+" jours");

                    }else {
                        int nbrMois = nbrSemaine/4;
                        int nbrSemaineRest = nbrSemaine % 4;

                        if(nbrMois < 12){
                            textView.setText("Avant "+nbrMois+" mois et "+nbrSemaineRest+" semaines");

                        }else {
                            int nbrAnnees = nbrMois / 12;
                            textView.setText("Avant "+nbrAnnees+" années");

                        }
                    }
                }
            }
        }
    }

}
