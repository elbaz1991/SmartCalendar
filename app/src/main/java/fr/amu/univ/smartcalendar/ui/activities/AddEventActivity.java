package fr.amu.univ.smartcalendar.ui.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.model.bean.ColorEvent;
import fr.amu.univ.smartcalendar.model.dao.AdresseDAO;
import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;
import fr.amu.univ.smartcalendar.model.entity.Adresse;
import fr.amu.univ.smartcalendar.model.entity.Evenement;
import fr.amu.univ.smartcalendar.outils.CommaTokenizer;
import fr.amu.univ.smartcalendar.ui.activities.adapters.AdapterMoyenTrans;
import static fr.amu.univ.smartcalendar.R.id.spinner;


public class AddEventActivity extends AppCompatActivity {

    private EvenementDAO evenementDAO = new EvenementDAO(this);
    private AdresseDAO adresseDAO = new AdresseDAO(this);
    private Toolbar toolbar;
    private EditText ui_titre;
    private EditText ui_description;
    private TextView ui_dateDebut;
    private TextView ui_dateFin;
    private TextView ui_heureDebut;
    private TextView ui_heureFin;
    private EditText ui_lieuDepart;
    private EditText ui_lieuRdv;
    // Toubi
    private TextView ui_ajouterRappel;
    private TextView textRappel1, textRappel2;
    private TextView ui_modifierCouleur;
    private LinearLayout ui_layoutRappel;
    private Spinner ui_spinnerMoyenTrans;
    private Switch ui_touteLaJournee;

    private String moyenDeTransport;
    private int couleurEvenement;
    private int nombreDeNotification = 0;
    private int rappelEnMinute1 , rappelEnMinute2;
    // Fin Toubi



    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
    SimpleDateFormat simpleHeureFormat = new SimpleDateFormat("HH:mm");

    private Calendar dateDebut;
    private Calendar dateFin;
    private final Calendar calendar = Calendar.getInstance();
    private Place placeDepart;
    private Place placeRdv;

    private static final int REQUEST_PLACE_PICKER_DEPART = 1; // PlacePicker
    private static final int REQUEST_PLACE_PICKER_RDV = 2; // PlacePicker



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayHomeAsUpEnabled(true);



       final TextView btnSave = (TextView) findViewById(R.id.toolbar_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saveData()){
                    Toast.makeText(getApplicationContext(),"Événement ajouté",Toast.LENGTH_SHORT).show();
                    finish();
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
        ui_lieuDepart = (EditText) findViewById(R.id.lieuDepart);
        ui_lieuRdv= (EditText) findViewById(R.id.lieuRdv);

        //
        ui_spinnerMoyenTrans = (Spinner) findViewById(R.id.spinnerMoyenTrans);
        ui_ajouterRappel = (TextView) findViewById(R.id.ajouterUnnRappel);
        ui_layoutRappel = (LinearLayout) findViewById(R.id.layoutRappel);
        ui_modifierCouleur = (TextView) findViewById(R.id.modifierLaCouleur);
        ui_touteLaJournee = (Switch) findViewById(R.id.touteLaJournee);


        // Récupération de la date sélecionnée
        long DateDepart = getIntent().getLongExtra("selectedDateDepart",calendar.getTimeInMillis());
        dateDebut = Calendar.getInstance();
        dateDebut.setTimeInMillis(DateDepart);


        // Si l'utilisateut à choisi une date on mis à jour l'heures 00:00
        if(DateDepart != calendar.getTimeInMillis()) {
            dateDebut.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
            dateDebut.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        }

        dateFin = Calendar.getInstance();
        dateFin.setTime(dateDebut.getTime());
        dateFin.set(Calendar.HOUR_OF_DAY, (calendar.get(Calendar.HOUR_OF_DAY))+1);




        ui_dateDebut.setText(simpleDateFormat.format(dateDebut.getTime()).toString());
        ui_dateDebut.setOnClickListener(onClickListenerDatePicker());

        ui_dateFin.setText(simpleDateFormat.format(dateFin.getTime()).toString());
        ui_dateFin.setOnClickListener(onClickListenerDatePicker());

        ui_heureDebut.setText(simpleHeureFormat.format(dateDebut.getTime()).toString());
        ui_heureFin.setOnClickListener(onClickListenerDatePicker());

        ui_heureFin.setText(simpleHeureFormat.format(dateFin.getTime()).toString());
        ui_heureDebut.setOnClickListener(onClickListenerDatePicker());



        ///
        // test toute la journée
        final Time debutDeJour = Time.valueOf("00:00:00");
        final Time finDeJour = Time.valueOf("23:59:00");
        ui_touteLaJournee.setOnCheckedChangeListener(touteLaJourneeListener());
        couleurEvenement = R.color.colorParDefaut;
        // fin de test



        // test ajouter un rappel
        ui_ajouterRappel.setOnClickListener(ajouterRappelListener());
        // fin test rappel

        // debut moyen de transport
        AdapterMoyenTrans adapter = new AdapterMoyenTrans(AddEventActivity.this);
        ui_spinnerMoyenTrans.setAdapter(adapter);
        ui_spinnerMoyenTrans.setOnItemSelectedListener(onSelectMoyenTrans());
        // fin test Moyen de transport

        // debut test invité par email
    try {
        MultiAutoCompleteTextView autoCompleteTextAdress = (MultiAutoCompleteTextView) findViewById(R.id.autoCompleteTextContacts);
        autoCompleteTextAdress.setAdapter(getEmailAddress(AddEventActivity.this));

        autoCompleteTextAdress.setThreshold(1);
        autoCompleteTextAdress.setTokenizer(new CommaTokenizer());
    }catch (Exception ex){

    }
        //fin test invité par email

        // changer couleur
        ui_modifierCouleur.setOnClickListener(changerCouleurListener());
        //changer couleur
    }



    @Override
    public boolean onSupportNavigateUp(){
        //code pour fermer l'activity lorsqu'on appuie sur le bouton de retour
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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


                if(view == ui_dateDebut){
                    int jour = dateDebut.get(Calendar.DAY_OF_MONTH);
                    int mois = dateDebut.get(Calendar.MONTH);
                    int annee = dateDebut.get(Calendar.YEAR);
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
                    int jour = dateFin.get(Calendar.DAY_OF_MONTH);
                    int mois = dateFin.get(Calendar.MONTH);
                    int annee = dateFin.get(Calendar.YEAR);
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


    /**
     * @author elbaz
     * enregistrement des données sur la base de donnée
     * @return true si l'enregistrement a reussi
     */
    private Boolean saveData(){
        String titre = ui_titre.getText().toString();
        String desc = ui_description.getText().toString();

        Evenement event = new Evenement();
        event.setTitre(titre);
        event.setDescription(desc);
        event.setDateDebut(dateDebut);
        event.setDateFin(dateFin);
        event.setAdresseDepart(new Adresse(placeDepart));
        event.setAdresseRdv(new Adresse(placeRdv));
        event.setColor(couleurEvenement);
        event.setRappel1(rappelEnMinute1 * 60);
        event.setRappel2(rappelEnMinute2 * 60);
        event.setMoyenneDeTransport(moyenDeTransport);


        boolean etatEnregistrement = evenementDAO.insert(event);
        if(etatEnregistrement) {
            int lastInsertedEventId = evenementDAO.getLastInsertedId();
            Log.e("Id"," : "+lastInsertedEventId);
            if (placeDepart != null)
                etatEnregistrement &= adresseDAO.insert(new Adresse(placeDepart),lastInsertedEventId);

            if (placeRdv != null)
                etatEnregistrement &= adresseDAO.insert(new Adresse(placeRdv),lastInsertedEventId);
        }
        return etatEnregistrement;
    }



    /**
     * @author elbaz
     * Request code passed to the PlacePicker intent to identify its result when it returns.
     */
    public void onClickLieuDepart(View view) {
        showPlacePicker(REQUEST_PLACE_PICKER_DEPART);
    }

    public void onClickLieuRdv(View view) {
        showPlacePicker(REQUEST_PLACE_PICKER_RDV);
    }


    public void showPlacePicker(int ID_REQUEST){
        try {
            PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
            Intent intent = intentBuilder.build(this);

            // Start the Intent by requesting a result, identified by a request code.
            startActivityForResult(intent, ID_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            GooglePlayServicesUtil
                    .getErrorDialog(e.getConnectionStatusCode(), this, 0);
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(this, "Google Play Services is not available.",
                    Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * @author elbaz
     * Resultat retourné aprés la selection d'une place sur la carte
     */
    //@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_PLACE_PICKER_DEPART) {
            // This result is from the PlacePicker dialog.

            // Enable the picker option
            //showPickAction(true);

            if (resultCode == Activity.RESULT_OK) {
                /* User has picked a place, extract data.
                   Data is extracted from the returned intent by retrieving a Place object from
                   the PlacePicker.
                 */
                //final Place place = PlacePicker.getPlace(this, data);
                /* A Place object contains details about that place, such as its name, address
                and phone number. Extract the name, address, phone number, place ID and place types.
                 */

                this.placeDepart = PlacePicker.getPlace(this, data);
                ui_lieuDepart.setText(this.placeDepart.getAddress());
                    //place.get
            } else {
                // User has not selected a place, hide the card.
            }

        }else if(requestCode == REQUEST_PLACE_PICKER_RDV){
            if (resultCode == Activity.RESULT_OK) {
                //final Place place = PlacePicker.getPlace(this, data);
                this.placeRdv = PlacePicker.getPlace(this, data);
                ui_lieuRdv.setText(this.placeRdv.getAddress());

            } else {
                // User has not selected a place, hide the card.
            }
        }else {

            super.onActivityResult(requestCode, resultCode, data);
        }

    }



    // Partie Toubi //
    private ArrayAdapter<String> getEmailAddress(Context context) {
        ContentResolver resolver = getContentResolver();
        ArrayList<String> listEmails = new ArrayList<String>();

        Cursor cursorEmail = resolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI,null,null,
                null,null);
        while (cursorEmail.moveToNext()){
            String email = cursorEmail.getString(cursorEmail.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

            listEmails.add(email);


        }
        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, listEmails);

    }
    private View.OnClickListener clickerPourModifier(final TextView textRappelParam){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //***************************************************

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddEventActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner,null);
                mBuilder.setTitle("Modifier votre rappel");
                final Spinner mSpinner = (Spinner) mView.findViewById(spinner);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddEventActivity.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.rappelList));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Autres ...")){

                            textRappelParam.setText(mSpinner.getSelectedItem().toString());

                            int spinner_pos = mSpinner.getSelectedItemPosition();
                            String[] rappelValues = getResources().getStringArray(R.array.rappelValues);


                            if(textRappelParam.equals(textRappel1)) {
                                rappelEnMinute1 = Integer.valueOf(rappelValues[spinner_pos]);
                            }
                            else if(textRappelParam.equals(textRappel2)){
                                rappelEnMinute2 = Integer.valueOf(rappelValues[spinner_pos]);

                            }


                            /*
                            if(textRappelParam.equals(textRappel1)) {
                                Toast.makeText(AddEventActivity.this,
                                        String.valueOf(rappelEnMinute1 + "*1"),
                                        Toast.LENGTH_SHORT).show();
                            }
                            else if(textRappelParam.equals(textRappel2)){
                                Toast.makeText(AddEventActivity.this,
                                        String.valueOf(rappelEnMinute2 + "*2"),
                                        Toast.LENGTH_SHORT).show();

                            }
                            */

                            dialog.dismiss();
                        }
                    }
                });

                mBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                mBuilder.setView(mView);
                final AlertDialog ad = mBuilder.create();
                ad.show();


                //***************************************************

                mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {

                        if(position == 7){

                            ad.dismiss();


                            AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(AddEventActivity.this);
                            final View mView2 = getLayoutInflater().inflate(R.layout.dialog_editrappel,null);
                            mBuilder2.setTitle("Personaliser votre rappel ");


                            final EditText editRappelMinute = (EditText) mView2.findViewById(R.id.editRappelMinute);

                            final RadioGroup radioRappelGroup = (RadioGroup) mView2.findViewById(R.id.radioRappel);


                            mBuilder2.setPositiveButton("Personaliser", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(!editRappelMinute.getText().toString().equalsIgnoreCase("0") &&
                                            !editRappelMinute.getText().toString().equalsIgnoreCase(" ") ){

                                        int selectedId = radioRappelGroup.getCheckedRadioButtonId();

                                        RadioButton radioRappelButton = (RadioButton) mView2.findViewById(selectedId);

                                        if(textRappelParam.equals(textRappel1)) {

                                            switch (radioRappelButton.getText().toString()){

                                                case "Minutes":
                                                    rappelEnMinute1 = Integer.valueOf(editRappelMinute.getText().toString());

                                                    if(rappelEnMinute1<60){
                                                        textRappel1.setText("Avant "+rappelEnMinute1+" minutes");
                                                    }else{
                                                        int nbrDeHeure = rappelEnMinute1 / 60;
                                                        int nbrMinute = rappelEnMinute1 % 60;

                                                        textRappel1.setText("Avant "+nbrDeHeure+" heures" + " et "+ nbrMinute +" minutes");
                                                    }

                                                    break;

                                                case "Heures":
                                                    int rappelEnheures = Integer.valueOf(editRappelMinute.getText().toString());
                                                    rappelEnMinute1 = rappelEnheures * 60;

                                                    if(rappelEnheures<24){
                                                        textRappel1.setText("Avant "+rappelEnheures+" heures" );
                                                    }else{
                                                        int nbrDeJours = rappelEnheures / 24;
                                                        int nbrHeures = rappelEnheures % 24;

                                                        textRappel1.setText("Avant "+nbrDeJours+" jours" + " et "+ nbrHeures +" heures");
                                                    }

                                                    break;
                                                case "Jours":
                                                    int rappelEnJours = Integer.valueOf(editRappelMinute.getText().toString());
                                                    rappelEnMinute1 = rappelEnJours *24*60;

                                                    if(rappelEnJours<365){
                                                        textRappel1.setText("Avant "+rappelEnJours+" jours" );
                                                    }else{
                                                        int nbrDesAnnees = rappelEnJours / 365;
                                                        int nbrJours = rappelEnJours % 365;

                                                        textRappel1.setText("Avant "+nbrDesAnnees+" années" + " et "+ nbrJours +" jours");
                                                    }
                                                    break;
                                                case "Semaines":
                                                    int rappelEnSemaine = Integer.valueOf(editRappelMinute.getText().toString());
                                                    rappelEnMinute1 = rappelEnSemaine *7*24*60;

                                                    if(rappelEnSemaine<4){
                                                        textRappel1.setText("Avant "+rappelEnSemaine+" semaines" );
                                                    }else{
                                                        int nbrDeMois = rappelEnSemaine / 4;
                                                        int nbrDeSemaines = rappelEnSemaine % 4;

                                                        textRappel1.setText("Avant "+nbrDeMois+" mois" + " et "+ nbrDeSemaines +" semaines");
                                                    }
                                                    break;


                                            }



                                        }
                                        else if(textRappelParam.equals(textRappel2)){

                                            switch (radioRappelButton.getText().toString()){

                                                case "Minutes":
                                                    rappelEnMinute2 = Integer.valueOf(editRappelMinute.getText().toString());

                                                    if(rappelEnMinute2<60){
                                                        textRappel2.setText("Avant "+rappelEnMinute2+" minutes");
                                                    }else{
                                                        int nbrDeHeure = rappelEnMinute2 / 60;
                                                        int nbrMinute = rappelEnMinute2 % 60;

                                                        textRappel2.setText("Avant "+nbrDeHeure+" heures" + " et "+ nbrMinute +" minutes");
                                                    }

                                                    break;

                                                case "Heures":
                                                    int rappelEnheures = Integer.valueOf(editRappelMinute.getText().toString());
                                                    rappelEnMinute2 = rappelEnheures * 60;

                                                    if(rappelEnheures<24){
                                                        textRappel2.setText("Avant "+rappelEnheures+" heures" );
                                                    }else{
                                                        int nbrDeJours = rappelEnheures / 24;
                                                        int nbrHeures = rappelEnheures % 24;

                                                        textRappel2.setText("Avant "+nbrDeJours+" jours" + " et "+ nbrHeures +" heures");
                                                    }

                                                    break;
                                                case "Jours":
                                                    int rappelEnJours = Integer.valueOf(editRappelMinute.getText().toString());
                                                    rappelEnMinute2 = rappelEnJours *24*60;

                                                    if(rappelEnJours<365){
                                                        textRappel2.setText("Avant "+rappelEnJours+" jours" );
                                                    }else{
                                                        int nbrDesAnnees = rappelEnJours / 365;
                                                        int nbrJours = rappelEnJours % 365;

                                                        textRappel2.setText("Avant "+nbrDesAnnees+" années" + " et "+ nbrJours +" jours");
                                                    }
                                                    break;
                                                case "Semaines":
                                                    int rappelEnSemaine = Integer.valueOf(editRappelMinute.getText().toString());
                                                    rappelEnMinute2 = rappelEnSemaine *7*24*60;

                                                    if(rappelEnSemaine<4){
                                                        textRappel2.setText("Avant "+rappelEnSemaine+" semaines" );
                                                    }else{
                                                        int nbrDeMois = rappelEnSemaine / 4;
                                                        int nbrDeSemaines = rappelEnSemaine % 4;

                                                        textRappel2.setText("Avant "+nbrDeMois+" mois" + " et "+ nbrDeSemaines +" semaines");
                                                    }
                                                    break;
                                            }
                                        }


                                        dialog.dismiss();

                                        /*
                                        if(textRappelParam.equals(textRappel1)) {
                                            Toast.makeText(AddEventActivity.this,
                                                    String.valueOf(rappelEnMinute1 + "*1"),
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                        else if(textRappelParam.equals(textRappel2)){

                                            Toast.makeText(AddEventActivity.this,
                                                    String.valueOf(rappelEnMinute2 + "*2"),
                                                    Toast.LENGTH_SHORT).show();


                                        }
                                        */



                                    }
                                    else{
                                        dialog.dismiss();
                                    }
                                }
                            });

                            mBuilder2.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });

                            mBuilder2.setView(mView2);
                            AlertDialog dialog2 = mBuilder2.create();
                            dialog2.show();

                        }

                        else {
                            ad.show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });






            }
        };
    }
    private TextView creerTextRappel (TextView textRappel , View view){

        textRappel = new TextView(view.getContext());
        textRappel.setGravity(View.TEXT_ALIGNMENT_CENTER);
        textRappel.setTextColor(Color.DKGRAY);
        textRappel.setTextSize(16);
        textRappel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_rappel,0,0,0);
        textRappel.setCompoundDrawablePadding(5);
        textRappel.setPadding(0,5,0,5);


        return textRappel;
    }


    // OnCheekTouteLaJournee
    public CompoundButton.OnCheckedChangeListener touteLaJourneeListener(){
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(ui_touteLaJournee.isChecked()){
                    ui_heureDebut.setVisibility(View.INVISIBLE);
                    ui_heureFin.setVisibility(View.INVISIBLE);
                    dateDebut.set(Calendar.HOUR_OF_DAY, 0);
                    dateDebut.set(Calendar.MINUTE,0);
                    dateFin.set(Calendar.HOUR_OF_DAY,23);
                    dateFin.set(Calendar.MINUTE,59);
                }else {

                    ui_heureDebut.setVisibility(View.VISIBLE);
                    ui_heureFin.setVisibility(View.VISIBLE);
                    dateFin.set(Calendar.HOUR_OF_DAY, (calendar.get(Calendar.HOUR_OF_DAY))+1);
                }

            }
        };
    }

    // ajouter rappel listener
    private View.OnClickListener ajouterRappelListener(){
        return new View.OnClickListener() {
            public void onClick(final View v) {

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddEventActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_spinner,null);
                final Spinner mSpinner = (Spinner) mView.findViewById(spinner);
                mBuilder.setTitle("Ajouter votre rappel");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddEventActivity.this,
                        android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.rappelList));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(!mSpinner.getSelectedItem().toString().equalsIgnoreCase("Auccun") &&
                                !mSpinner.getSelectedItem().toString().equalsIgnoreCase("Autres ...")) {

                            if(nombreDeNotification == 0) {
                                textRappel1 = creerTextRappel(textRappel1,v);
                                ui_layoutRappel.addView(textRappel1);

                                textRappel1.setText(mSpinner.getSelectedItem().toString());


                                int  spinner_pos = mSpinner.getSelectedItemPosition();
                                String[] rappelValues = getResources().getStringArray(R.array.rappelValues);
                                rappelEnMinute1 = Integer.valueOf(rappelValues[spinner_pos]);


                                /*
                                Toast.makeText(AddEventActivity.this,
                                        String.valueOf(rappelEnMinute1 + "*1*"),
                                        Toast.LENGTH_SHORT).show();*/

                                dialog.dismiss();
                            }

                            //###############################################################
                            textRappel1.setOnClickListener(clickerPourModifier(textRappel1));


                            //################################################################

                            if(nombreDeNotification == 1) {


                                textRappel2 = creerTextRappel(textRappel2,v);
                                ui_layoutRappel.addView(textRappel2);

                                textRappel2.setText(mSpinner.getSelectedItem().toString());

                                nombreDeNotification += 1;

                                if(nombreDeNotification == 2) {
                                    ui_ajouterRappel.setVisibility(View.GONE);
                                    nombreDeNotification = 0;
                                }



                                int  spinner_pos = mSpinner.getSelectedItemPosition();
                                String[] rappelValues = getResources().getStringArray(R.array.rappelValues);
                                rappelEnMinute2 = Integer.valueOf(rappelValues[spinner_pos]);


                                /*
                                Toast.makeText(AddEventActivity.this,
                                        String.valueOf(rappelEnMinute2 + "*2*"),
                                        Toast.LENGTH_SHORT).show();
                                        */

                                dialog.dismiss();
                            }

                            nombreDeNotification += 1;

                            if(textRappel2 != null){

                                //###############################################################
                                textRappel2.setOnClickListener(clickerPourModifier(textRappel2));
                                //################################################################

                            }
                        }
                    }
                });

                mBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });


                mBuilder.setView(mView);
                final AlertDialog ad = mBuilder.create();
                ad.show();


                mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, final View view, int position, long id) {

                        if(position == 7){
                            ad.dismiss();
                            AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(AddEventActivity.this);
                            final View mView2 = getLayoutInflater().inflate(R.layout.dialog_editrappel,null);
                            mBuilder2.setTitle("Personaliser votre rappel ");


                            final EditText editRappelMinute = (EditText) mView2.findViewById(R.id.editRappelMinute);
                            final RadioGroup radioRappelGroup = (RadioGroup) mView2.findViewById(R.id.radioRappel);

                            mBuilder2.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();

                                    int selectedId = radioRappelGroup.getCheckedRadioButtonId();

                                    RadioButton radioRappelButton = (RadioButton) mView2.findViewById(selectedId);

                                    if(!editRappelMinute.getText().toString().equalsIgnoreCase("0") &&
                                            !editRappelMinute.getText().toString().equalsIgnoreCase(" ") ){

                                        if(nombreDeNotification == 0){


                                            textRappel1 = creerTextRappel(textRappel1,view);
                                            ui_layoutRappel.addView(textRappel1);


                                            switch (radioRappelButton.getText().toString()){

                                                case "Minutes":
                                                    rappelEnMinute1 = Integer.valueOf(editRappelMinute.getText().toString());

                                                    if(rappelEnMinute1<60){
                                                        textRappel1.setText("Avant "+rappelEnMinute1+" minutes");
                                                    }else{
                                                        int nbrDeHeure = rappelEnMinute1 / 60;
                                                        int nbrMinute = rappelEnMinute1 % 60;

                                                        textRappel1.setText("Avant "+nbrDeHeure+" heures" + " et "+ nbrMinute +" minutes");
                                                    }

                                                    break;

                                                case "Heures":
                                                    int rappelEnheures = Integer.valueOf(editRappelMinute.getText().toString());
                                                    rappelEnMinute1 = rappelEnheures * 60;

                                                    if(rappelEnheures<24){
                                                        textRappel1.setText("Avant "+rappelEnheures+" heures" );
                                                    }else{
                                                        int nbrDeJours = rappelEnheures / 24;
                                                        int nbrHeures = rappelEnheures % 24;

                                                        textRappel1.setText("Avant "+nbrDeJours+" jours" + " et "+ nbrHeures +" heures");
                                                    }

                                                    break;
                                                case "Jours":
                                                    int rappelEnJours = Integer.valueOf(editRappelMinute.getText().toString());
                                                    rappelEnMinute1 = rappelEnJours *24*60;

                                                    if(rappelEnJours<365){
                                                        textRappel1.setText("Avant "+rappelEnJours+" jours" );
                                                    }else{
                                                        int nbrDesAnnees = rappelEnJours / 365;
                                                        int nbrJours = rappelEnJours % 365;

                                                        textRappel1.setText("Avant "+nbrDesAnnees+" années" + " et "+ nbrJours +" jours");
                                                    }
                                                    break;
                                                case "Semaines":
                                                    int rappelEnSemaine = Integer.valueOf(editRappelMinute.getText().toString());
                                                    rappelEnMinute1 = rappelEnSemaine *7*24*60;

                                                    if(rappelEnSemaine<4){
                                                        textRappel1.setText("Avant "+rappelEnSemaine+" semaines" );
                                                    }else{
                                                        int nbrDeMois = rappelEnSemaine / 4;
                                                        int nbrDeSemaines = rappelEnSemaine % 4;

                                                        textRappel1.setText("Avant "+nbrDeMois+" mois" + " et "+ nbrDeSemaines +" semaines");
                                                    }
                                                    break;


                                            }

                                            dialog.dismiss();
                                            /*
                                            Toast.makeText(AddEventActivity.this,
                                                    String.valueOf(rappelEnMinute1 + "*1"),
                                                    Toast.LENGTH_SHORT).show();
                                                    */
                                        }
                                        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                                        textRappel1.setOnClickListener(clickerPourModifier(textRappel1));
                                        //++++++++++++++++++++++++++++++++++++++++++++++++++++++

                                        if(nombreDeNotification == 1){

                                            textRappel2 = creerTextRappel(textRappel2,view);
                                            ui_layoutRappel.addView(textRappel2);


                                            switch (radioRappelButton.getText().toString()){

                                                case "Minutes":
                                                    rappelEnMinute2 = Integer.valueOf(editRappelMinute.getText().toString());

                                                    if(rappelEnMinute2<60){
                                                        textRappel2.setText("Avant "+rappelEnMinute2+" minutes");
                                                    }else{
                                                        int nbrDeHeure = rappelEnMinute2 / 60;
                                                        int nbrMinute = rappelEnMinute2 % 60;

                                                        textRappel2.setText("Avant "+nbrDeHeure+" heures" + " et "+ nbrMinute +" minutes");
                                                    }

                                                    break;

                                                case "Heures":
                                                    int rappelEnheures = Integer.valueOf(editRappelMinute.getText().toString());
                                                    rappelEnMinute2 = rappelEnheures * 60;

                                                    if(rappelEnheures<24){
                                                        textRappel2.setText("Avant "+rappelEnheures+" heures" );
                                                    }else{
                                                        int nbrDeJours = rappelEnheures / 24;
                                                        int nbrHeures = rappelEnheures % 24;

                                                        textRappel2.setText("Avant "+nbrDeJours+" jours" + " et "+ nbrHeures +" heures");
                                                    }

                                                    break;
                                                case "Jours":
                                                    int rappelEnJours = Integer.valueOf(editRappelMinute.getText().toString());
                                                    rappelEnMinute2 = rappelEnJours *24*60;

                                                    if(rappelEnJours<365){
                                                        textRappel2.setText("Avant "+rappelEnJours+" jours" );
                                                    }else{
                                                        int nbrDesAnnees = rappelEnJours / 365;
                                                        int nbrJours = rappelEnJours % 365;

                                                        textRappel2.setText("Avant "+nbrDesAnnees+" années" + " et "+ nbrJours +" jours");
                                                    }
                                                    break;
                                                case "Semaines":
                                                    int rappelEnSemaine = Integer.valueOf(editRappelMinute.getText().toString());
                                                    rappelEnMinute2 = rappelEnSemaine *7*24*60;

                                                    if(rappelEnSemaine<4){
                                                        textRappel2.setText("Avant "+rappelEnSemaine+" semaines" );
                                                    }else{
                                                        int nbrDeMois = rappelEnSemaine / 4;
                                                        int nbrDeSemaines = rappelEnSemaine % 4;

                                                        textRappel2.setText("Avant "+nbrDeMois+" mois" + " et "+ nbrDeSemaines +" semaines");
                                                    }
                                                    break;
                                            }
                                            nombreDeNotification += 1;

                                            if(nombreDeNotification == 2) {
                                                ui_ajouterRappel.setVisibility(View.GONE);
                                                nombreDeNotification = 0;
                                            }
                                            dialog.dismiss();
                                            /*
                                            Toast.makeText(AddEventActivity.this,
                                                    String.valueOf(rappelEnMinute2+ "*2"),
                                                    Toast.LENGTH_SHORT).show();
                                                    */
                                        }
                                        nombreDeNotification += 1;
                                        if(textRappel2 != null){
                                            textRappel2.setOnClickListener(clickerPourModifier(textRappel2));

                                        }
                                    }
                                    else{
                                        dialog.dismiss();
                                    }
                                }
                            });

                            mBuilder2.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });

                            mBuilder2.setView(mView2);
                            AlertDialog dialog2 = mBuilder2.create();
                            dialog2.show();


                        }else {
                            ad.show();
                        }
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        };
    }

    //Moyenne Transport
    public AdapterView.OnItemSelectedListener onSelectMoyenTrans(){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        moyenDeTransport = null;
                        break;
                    case 1:
                        moyenDeTransport = "Voiture";
                        break;
                    case 2:
                        moyenDeTransport = "Vélo";
                        break;
                    case 3:
                        moyenDeTransport = "Transport commun";
                        break;
                    case 4:
                        moyenDeTransport = "à pieds";
                        break;
                }
                /*
                Toast.makeText(AddEventActivity.this,
                        String.valueOf(moyenDeTransport),
                        Toast.LENGTH_SHORT).show();
                */
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                moyenDeTransport = null;
                /*
                Toast.makeText(AddEventActivity.this,
                        String.valueOf(moyenDeTransport),
                        Toast.LENGTH_SHORT).show();
                        */
            }
        };
    }

    // changer la couleur
    public View.OnClickListener changerCouleurListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AddEventActivity.this);
                mBuilder.setTitle("Choisissez votre couleur ");
                final View mView = getLayoutInflater().inflate(R.layout.colorevents,null);
                final RadioGroup radioRappelGroup = (RadioGroup) mView.findViewById(R.id.radioGroupCouleur);

                for (ColorEvent color : ColorEvent.getEventColors(v.getContext())){
                    AppCompatRadioButton rButton = new AppCompatRadioButton (v.getContext());
                        int c = new Color().parseColor(color.getCode());
                        rButton.setTextColor(c);
                        //rButton.setBackgroundTintList(ColorStateList.valueOf(c));
                        rButton.setSupportButtonTintList(ColorStateList.valueOf(c));
                        rButton.setHighlightColor(c);
                        rButton.setDrawingCacheBackgroundColor(c);
                        rButton.setText(color.getName());
                        radioRappelGroup.addView(rButton);
                }

                radioRappelGroup.setVerticalScrollBarEnabled(true);

                final Drawable mDrawable = AddEventActivity.this.getResources().getDrawable(R.drawable.ic_couleur);


                mBuilder.setPositiveButton("Appliquer", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int selectedId = radioRappelGroup.getCheckedRadioButtonId();
                        RadioButton radioCouleurButton = (RadioButton) mView.findViewById(selectedId);
                        couleurEvenement = radioCouleurButton.getCurrentTextColor();

                        //couleurEvenement = radioCouleurButton.getText().toString();
                        ui_modifierCouleur.setTextColor(couleurEvenement);
                        mDrawable.setColorFilter(couleurEvenement, PorterDuff.Mode.MULTIPLY);
                        toolbar.setBackgroundColor(couleurEvenement);

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog2 = mBuilder.create();
                dialog2.show();

            }
        };
    }
}
