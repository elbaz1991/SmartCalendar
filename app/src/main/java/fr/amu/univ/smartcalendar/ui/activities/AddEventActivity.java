package fr.amu.univ.smartcalendar.ui.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import fr.amu.univ.smartcalendar.MainActivity;
import fr.amu.univ.smartcalendar.R;

public class AddEventActivity extends AppCompatActivity {

    private TextView ui_dateDebut;
    private TextView ui_dateFin;
    private TextView ui_heureDebut;
    private TextView ui_heureFin;


    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy", Locale.FRANCE);
    SimpleDateFormat simpleHeureFormat = new SimpleDateFormat("HH:mm",Locale.FRANCE);

    private Calendar dateDebut = Calendar.getInstance();
    private Calendar dateFin = Calendar.getInstance();
    private final Calendar calendar = Calendar.getInstance();

    private static final int DIALOG_ID =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setDisplayHomeAsUpEnabled(true);


        //actionBar.setIcon(R.drawable.ic_menu_send);


       final TextView btnSave = (TextView) findViewById(R.id.toolbar_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Enregistrement ... !",Toast.LENGTH_SHORT).show();
            }
        });

        // =========

        ui_dateDebut = (TextView) findViewById(R.id.dateDebut);
        ui_dateFin = (TextView) findViewById(R.id.dateFin);
        ui_heureDebut = (TextView) findViewById(R.id.heureDebut);
        ui_heureFin = (TextView) findViewById(R.id.heureFin);


        Date date = calendar.getTime();

        ui_dateDebut.setText(simpleDateFormat.format(date).toString());
        ui_dateDebut.setOnClickListener(onClickListenerDatePicker());

        ui_dateFin.setText(simpleDateFormat.format(date).toString());
        ui_dateFin.setOnClickListener(onClickListenerDatePicker());

        ui_heureDebut.setText(simpleHeureFormat.format(date).toString());
        ui_heureFin.setOnClickListener(onClickListenerDatePicker());

        ui_heureFin.setText(simpleHeureFormat.format(date).toString());
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
                            int annee = dateDebut.get(Calendar.YEAR);
                            int mois = dateDebut.get(Calendar.MONTH);
                            int jour = dateDebut.get(Calendar.DAY_OF_WEEK);

                            dateDebut.set(annee,mois,jour,heure,minute);
                            ui_heureDebut.setText(simpleHeureFormat.format(dateDebut.getTime()));
                        }
                    },heure,minutes,false);
                    timePickerDialog.show();
                }
                if(view == ui_heureFin ){
                    int heure = dateFin.get(Calendar.HOUR_OF_DAY);
                    int minutes = dateFin.get(Calendar.MINUTE);

                    TimePickerDialog timePickerDialog = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int heure, int minute) {
                            int annee = dateFin.get(Calendar.YEAR);
                            int mois = dateFin.get(Calendar.MONTH);
                            int jour = dateFin.get(Calendar.DAY_OF_WEEK);
                            dateFin.set(annee,mois,jour,heure,minute);
                            ui_heureFin.setText(simpleHeureFormat.format(dateFin.getTime()));
                        }
                    },heure,minutes,false);
                    timePickerDialog.show();
                }

            }
        };
    }

    public void showDatePicDialog(TextView textView){

    }
    public void onClickHeureDebut(View view) {

    }
}
