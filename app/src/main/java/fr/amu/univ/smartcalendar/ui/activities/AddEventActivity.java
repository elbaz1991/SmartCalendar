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
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import fr.amu.univ.smartcalendar.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import fr.amu.univ.smartcalendar.model.dao.AddressDAO;
import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;
import fr.amu.univ.smartcalendar.model.dao.ParticipantDAO;
import fr.amu.univ.smartcalendar.model.entity.SmartCalendarAddressModel;
import fr.amu.univ.smartcalendar.model.entity.SmartCalendarEventModel;
import fr.amu.univ.smartcalendar.model.entity.SmartCalendarParticipantModel;
import fr.amu.univ.smartcalendar.ui.constants.SmartCalendarFieldsLabel;
import fr.amu.univ.smartcalendar.utils.SmartCalendarUtils;


public class AddEventActivity extends AppCompatActivity {

    private EvenementDAO evenementDAO = new EvenementDAO(this);
    private AddressDAO addressDAO = new AddressDAO(this);
    private ParticipantDAO participantDAO = new ParticipantDAO(this);

    private EditText ui_titre;
    private EditText ui_description;
    private TextView ui_dateDebut;
    private TextView ui_dateFin;
    private TextView ui_heureDebut;
    private TextView ui_heureFin;
    private EditText smartCalendarDepartureLocation;
    private EditText smartCalendarDestinationLocation;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
    SimpleDateFormat simpleHeureFormat = new SimpleDateFormat("HH:mm");

    private Calendar dateDebut;
    private Calendar dateFin;
    private final Calendar calendar = Calendar.getInstance();
    private final PlaceAutocomplete.IntentBuilder intentBuilder = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY);


    SmartCalendarAddressModel departureAddress;
    SmartCalendarAddressModel destinationAddress;
    SmartCalendarEventModel event;
    SmartCalendarParticipantModel participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final Intent eventData = getIntent();
        int eventId = Integer.parseInt(eventData.getStringExtra(SmartCalendarFieldsLabel.SMART_CALENDAR_EVENT_ID));


        final TextView btnSave = (TextView) findViewById(R.id.toolbar_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bundleIntent = new Intent(AddEventActivity.this, MainActivity.class);
                if(saveData()){
                    bundleIntent.putExtra(SmartCalendarFieldsLabel.SMART_CALENDAR_EVENT_OBJECT, event);
                    setResult(Activity.RESULT_OK , bundleIntent);
                    finishActivity(SmartCalendarFieldsLabel.REQUEST_EVENT_EDITION_CODE);
                }
            }
        });

        // ========= initialisation
        ui_titre = (EditText) findViewById(R.id.titre);
        ui_description = (EditText) findViewById(R.id.description);
        ui_dateDebut = (TextView) findViewById(R.id.dateDebut);
        ui_dateFin = (TextView) findViewById(R.id.dateFin);
        ui_heureDebut = (TextView) findViewById(R.id.heureDebut);
        ui_heureFin = (TextView) findViewById(R.id.heureFin);
        smartCalendarDepartureLocation = (EditText)findViewById(R.id.smart_calendar_departure_location);
        //ui_lieuDepart.setFocusable(false);
        smartCalendarDestinationLocation = (EditText)findViewById(R.id.smart_calendar_destination_location);

        dateDebut = Calendar.getInstance();
        dateFin = Calendar.getInstance();

        //========== */

        if(eventId > 0){
            event = new SmartCalendarEventModel(this, eventId);
            departureAddress = new SmartCalendarAddressModel(this, event.getOriginAddressId());
            destinationAddress = new SmartCalendarAddressModel(this, event.getDestinationAddressId());
            participants = new SmartCalendarParticipantModel(this, eventId);

            /* filling form based on the data retrieved from the database **/
            ui_titre.setText(event.getTitre());
            ui_description.setText(event.getDescription());
            ui_dateDebut.setText(simpleDateFormat.format(event.getDateDebut()));
            ui_dateFin.setText(simpleDateFormat.format(event.getDateFin()));
            //ui_heureDebut.setText(simpleHeureFormat.format());
            //ui_heureFin.setText(simpleHeureFormat.format());

            /*if(!departureAddress.getAddressLabel().equals("")){
                ui_lieuDepart.setText(departureAddress.getAddressLabel());
            }else{
                ui_lieuDepart.setText(SmartCalendarUtils.getAddressFromCoordinates(
                        departureAddress.getLatitude(), departureAddress.getLongitude()
                ));
            }

            if(!destinationAddress.getAddressLabel().equals("")){
                smartCalendarDestinationLocation.setText(destinationAddress.getAddressLabel());
            }else{
                smartCalendarDestinationLocation.setText(
                        SmartCalendarUtils.getAddressFromCoordinates(
                                destinationAddress.getLatitude(), destinationAddress.getLongitude()
                        )
                );
            } */
        }else {
            event = new SmartCalendarEventModel(this);
            departureAddress = new SmartCalendarAddressModel(this);
            destinationAddress = new SmartCalendarAddressModel(this);

            Log.e("DateD",dateDebut.getTime().toString());

            ui_dateDebut.setText(simpleDateFormat.format(dateDebut.getTime()));


            ui_dateFin.setText(simpleDateFormat.format(dateFin.getTime()));


            ui_heureDebut.setText(simpleHeureFormat.format(dateDebut.getTime()));


            ui_heureFin.setText(simpleHeureFormat.format(dateFin.getTime()));
        }
        addFormListeners();
    }

    private void addFormListeners(){
        ui_dateDebut.setOnClickListener(onClickListenerDatePicker());
        ui_dateFin.setOnClickListener(onClickListenerDatePicker());
        ui_heureFin.setOnClickListener(onClickListenerDatePicker());
        ui_heureDebut.setOnClickListener(onClickListenerDatePicker());

        smartCalendarDepartureLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    handlePositionUpdate(SmartCalendarFieldsLabel.REQUEST_DEPARTURE_PLACE_CODE);
                }
            }
        });

        smartCalendarDestinationLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus){
                if(hasFocus){
                    handlePositionUpdate(SmartCalendarFieldsLabel.REQUEST_DESTINATION_PLACE_CODE);
                }
            }
        });
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
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                if(view == ui_dateDebut){
                    DatePickerDialog datePickerDialog = new DatePickerDialog(AddEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int annee, int mois, int jours) {
                            dateDebut.set(annee,mois,jours);
                            ui_dateDebut.setText(simpleDateFormat.format(dateDebut.getTime()));
                        }
                    }
                            ,year,month,day);
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
                            ,year,month,day);
                    datePickerDialog.show();
                }
                if(view == ui_heureDebut){
                    int heure = dateDebut.get(Calendar.HOUR_OF_DAY);
                    int minutes = dateDebut.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int heure, int minute) {
                            timePicker.setIs24HourView(true);
                            //Log.e("Heure : ",String.valueOf(heure));
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

        event.setTitre(titre);
        event.setDescription(desc);
        event.setDateDebut(dateDebut.getTime());
        event.setDateFin(dateFin.getTime());
        /**
         * setting departure address information
         */
        boolean result = true;
        if(smartCalendarDepartureLocation.getText().toString().equals("")){
            smartCalendarDepartureLocation.requestFocus();
        }else if(smartCalendarDestinationLocation.getText().toString().equals("")){
            smartCalendarDestinationLocation.requestFocus();
        }else {
            departureAddress.setOrigin(1);
            departureAddress.setDestination(0);

            destinationAddress.setOrigin(0);
            destinationAddress.setDestination(1);


            if (event.getEventId() > 0) {
                result &= evenementDAO.update(event);
                result &= addressDAO.update(departureAddress);
                result &= addressDAO.update(destinationAddress);
                result &= participantDAO.update(participants);

            } else {
                /* create new data **/
                event.setEventId((int) evenementDAO.insert(event));
                departureAddress.setEventId(event.getEventId());
                departureAddress.setAddressId((int) addressDAO.insert(departureAddress));
                evenementDAO.updateIntField(event.getEventId(), EvenementDAO.COL_ADDRESS_DEPART, departureAddress.getAddressId());

                destinationAddress.setEventId(event.getEventId());
                destinationAddress.setAddressId((int) addressDAO.insert(destinationAddress));
                evenementDAO.updateIntField(event.getEventId(), EvenementDAO.COL_ADDRESS_DESTINATION, destinationAddress.getAddressId());
                /*participants.setEventId(event.getEventId());
                result &= participantDAO.insert(participants);*/
            }
        }

        return result;
    }


    /**
     * Request code passed to the PlacePicker intent to identify its result when it returns.
     */

    public void onClickUpdateDeparturePosition(View view) {
        handlePositionUpdate(SmartCalendarFieldsLabel.REQUEST_DEPARTURE_PLACE_CODE);
    }

    public void handlePositionUpdate(int code) {
        try {
            Intent intent = intentBuilder.build(this);
            // Start the Intent by requesting a result, identified by a request code.
            startActivityForResult(intent, code);
        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), this, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void onClickUpdateDestinationPosition(View view){
        handlePositionUpdate(SmartCalendarFieldsLabel.REQUEST_DESTINATION_PLACE_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // BEGIN_INCLUDE(activity_result)
        if (requestCode == SmartCalendarFieldsLabel.REQUEST_DEPARTURE_PLACE_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                departureAddress.setAddressLabel(place.getAddress().toString());
                departureAddress.setLatitude(place.getLatLng().latitude);
                departureAddress.setLongitude(place.getLatLng().longitude);
                departureAddress.setOrigin(1);
                departureAddress.setDestination(0);
                smartCalendarDepartureLocation.setText(place.getAddress());
            } else {
                // User has not selected a place, hide the card.
                //getCardStream().hideCard(CARD_DETAIL);
            }
        }else if(requestCode == SmartCalendarFieldsLabel.REQUEST_DESTINATION_PLACE_CODE){
            if(resultCode == Activity.RESULT_OK){
                Place place = PlaceAutocomplete.getPlace(this, data);
                destinationAddress.setLatitude(place.getLatLng().latitude);
                destinationAddress.setLongitude(place.getLatLng().longitude);
                destinationAddress.setAddressLabel(place.getAddress().toString());
                destinationAddress.setOrigin(0);
                destinationAddress.setDestination(1);
                smartCalendarDestinationLocation.setText(place.getAddress().toString());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
        // END_INCLUDE(activity_result)
    }
}