package fr.amu.univ.smartcalendar.ui.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import fr.amu.univ.smartcalendar.MainActivity;
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
     * @author elbaz
     * Activity qui permet le choix d'une position sur la map
     */
    private void openAutocompleteActivity() {
        try {
            // The autocomplete activity requires Google Play Services to be available. The intent
            // builder checks this and throws an exception if it is not the case.
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                    .build(this);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            // Indicates that Google Play Services is either not installed or not up to date. Prompt
            // the user to correct the issue.
            GoogleApiAvailability.getInstance().getErrorDialog(this, e.getConnectionStatusCode(),
                    0 /* requestCode */).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            // Indicates that Google Play Services is not available and the problem is not easily
            // resolvable.
            String message = "Google Play Services is not available: " +
                    GoogleApiAvailability.getInstance().getErrorString(e.errorCode);

            Log.e("Maps", message);
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Called after the autocomplete activity has finished to return its result.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check that the result was from the autocomplete widget.
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == RESULT_OK) {
                // Get the user's selected place from the Intent.
                Place place = PlaceAutocomplete.getPlace(this, data);
                //Log.i(TAG, "Place Selected: " + place.getName());


                // Format the place's details and display them in the TextView.
                /*ui_lieuDepart.setText(formatPlaceDetails(getResources(), place.getName(),
                        place.getId(), place.getAddress(), place.getPhoneNumber(),
                        place.getWebsiteUri()));*/
                ui_lieuDepart.setText(place.getAddress());
                // Display attributions if required.
                CharSequence attributions = place.getAttributions();
                if (!TextUtils.isEmpty(attributions)) {
                    //ui_lieuDepart.setText(Html.fromHtml(attributions.toString()));
                } else {
                    //ui_lieuDepart.setText("");
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                //Log.e(TAG, "Error: Status = " + status.toString());
            } else if (resultCode == RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed the back button.
            }
        }
    }



    public void onClickLieuDepart(View view) {
        openAutocompleteActivity();
    }

    /*
    * Fin choix place
    */


}
