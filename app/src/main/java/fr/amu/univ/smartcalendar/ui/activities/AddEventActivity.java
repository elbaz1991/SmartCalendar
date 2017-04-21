package fr.amu.univ.smartcalendar.ui.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;
import fr.amu.univ.smartcalendar.model.entity.Evenement;


public class AddEventActivity extends AppCompatActivity {

    private EvenementDAO evenementDAO = new EvenementDAO(this);
    private EditText ui_titre;
    private EditText ui_description;
    private TextView ui_dateDebut;
    private TextView ui_dateFin;
    private TextView ui_heureDebut;
    private TextView ui_heureFin;
    private EditText ui_lieuDepart;



    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
    SimpleDateFormat simpleHeureFormat = new SimpleDateFormat("HH:mm");

    private Calendar dateDebut;
    private Calendar dateFin;
    private final Calendar calendar = Calendar.getInstance();

    //
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayHomeAsUpEnabled(true);



       final TextView btnSave = (TextView) findViewById(R.id.toolbar_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Date Debut : ", (dateDebut.getTime().toString()));
                Log.e("Date Debut long : ", (String.valueOf(dateDebut.getTime().getTime())));

                Log.e("Date Fin : ", (dateFin.getTime().toString()));
                Log.e("Date Fin long : ", (String.valueOf(dateFin.getTime().getTime())));


                Toast.makeText(getApplicationContext(),saveData().toString(),Toast.LENGTH_SHORT).show();
            }
        });

        // ========= initialisation
        ui_titre = (EditText) findViewById(R.id.titre);
        ui_description = (EditText) findViewById(R.id.description);
        ui_dateDebut = (TextView) findViewById(R.id.dateDebut);
        ui_dateFin = (TextView) findViewById(R.id.dateFin);
        ui_heureDebut = (TextView) findViewById(R.id.heureDebut);
        ui_heureFin = (TextView) findViewById(R.id.heureFin);
        ui_lieuDepart = (EditText) findViewById(R.id.lieuDepart);
        ui_lieuDepart.setFocusable(false);


        dateDebut = Calendar.getInstance();
        dateFin = Calendar.getInstance();

        Log.e("DateD",dateDebut.getTime().toString());

        ui_dateDebut.setText(simpleDateFormat.format(dateDebut.getTime()).toString());
        ui_dateDebut.setOnClickListener(onClickListenerDatePicker());

        ui_dateFin.setText(simpleDateFormat.format(dateFin.getTime()).toString());
        ui_dateFin.setOnClickListener(onClickListenerDatePicker());

        ui_heureDebut.setText(simpleHeureFormat.format(dateDebut.getTime()).toString());
        ui_heureFin.setOnClickListener(onClickListenerDatePicker());

        ui_heureFin.setText(simpleHeureFormat.format(dateFin.getTime()).toString());
        ui_heureDebut.setOnClickListener(onClickListenerDatePicker());

        //==========

    }


    @Override
    public boolean onSupportNavigateUp(){
        //code pour fermer l'activity lorsqu'on appuie sur le bouton de retour
        finish();
        return true;
    }

    /**
     * @author elbaz
     * cette methode retourne onClickListener qui sera assosier aux views responsables du choix de la date
     */
    private View.OnClickListener onClickListenerDatePicker(){
        return new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                int jour = calendar.get(Calendar.DAY_OF_MONTH);
                int mois = calendar.get(Calendar.MONTH);
                int annee = calendar.get(Calendar.YEAR);

                if(view == ui_dateDebut){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int annee, int mois, int jours) {
                            dateDebut.set(annee,mois,jours);
                            ui_dateDebut.setText(simpleDateFormat.format(dateDebut.getTime()));
                        }
                    }
                    ,annee,mois,jour);
                    datePickerDialog.show();
                }
                if(view == ui_dateFin){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int annee, int mois, int jours) {
                            dateFin.set(annee,mois,jours);
                            ui_dateFin.setText(simpleDateFormat.format(dateFin.getTime()));
                        }
                    }
                    ,annee,mois,jour);
                    datePickerDialog.show();
                }
                if(view == ui_heureDebut){
                    int heure = dateDebut.get(Calendar.HOUR_OF_DAY);
                    int minutes = dateDebut.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int heure, int minute) {
                            timePicker.setIs24HourView(true);
                            Log.e("Heure : ",String.valueOf(heure));
                            dateDebut.set(Calendar.HOUR_OF_DAY,heure);
                            dateDebut.set(Calendar.MINUTE,minute);
                            ui_heureDebut.setText(simpleHeureFormat.format(dateDebut.getTime()));
                        }
                    },heure,minutes,true);

                    timePickerDialog.show();
                }
                if(view == ui_heureFin ){
                    int heure = dateFin.get(Calendar.HOUR_OF_DAY);
                    int minutes = dateFin.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int heure, int minute) {
                            timePicker.setIs24HourView(true);
                            dateFin.set(Calendar.HOUR_OF_DAY,heure);
                            dateFin.set(Calendar.MINUTE,minute);
                            ui_heureFin.setText(simpleHeureFormat.format(dateFin.getTime()));
                        }
                    },heure,minutes,true);
                    timePickerDialog.show();
                }

            }
        };
    }


    private Boolean saveData(){
        String titre = ui_titre.getText().toString();
        String desc = ui_description.getText().toString();

        Evenement event = new Evenement();
        event.setTitre(titre);
        event.setDescription(desc);
        event.setDateDebut(dateDebut.getTime());
        event.setDateFin(dateFin.getTime());

        return evenementDAO.insert(event);
    }






    /**
     * Request code passed to the PlacePicker intent to identify its result when it returns.
     */
    private static final int REQUEST_PLACE_PICKER = 1;
    public void onClickLieuDepart(View view) {
        try {
            Log.e("Yeeeeeeeeeees !! ","Yeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeees");
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);
            // Start the Intent by requesting a result, identified by a request code.
            startActivityForResult(intent, REQUEST_PLACE_PICKER);
        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), this, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // BEGIN_INCLUDE(activity_result)
        if (requestCode == REQUEST_PLACE_PICKER) {
            // This result is from the PlacePicker dialog.

            // Enable the picker option
            //showPickAction(true);

            if (resultCode == Activity.RESULT_OK) {
                /* User has picked a place, extract data.
                   Data is extracted from the returned intent by retrieving a Place object from
                   the PlacePicker.
                 */
                final Place place = PlacePicker.getPlace(this, data);

                /* A Place object contains details about that place, such as its name, address
                and phone number. Extract the name, address, phone number, place ID and place types.
                 */
                final CharSequence name = place.getName();
                final CharSequence address = place.getAddress();
                final CharSequence phone = place.getPhoneNumber();
                final String placeId = place.getId();
                String attribution = PlacePicker.getAttributions(data);
                if(attribution == null){
                    attribution = "";
                }

                ui_lieuDepart.setText(place.getAddress());



            } else {
                // User has not selected a place, hide the card.
                //getCardStream().hideCard(CARD_DETAIL);
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        // END_INCLUDE(activity_result)
    }
}
