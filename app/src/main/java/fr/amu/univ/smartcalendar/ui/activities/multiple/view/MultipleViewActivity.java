package fr.amu.univ.smartcalendar.ui.activities.multiple.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;
import fr.amu.univ.smartcalendar.model.entity.Evenement;
import fr.amu.univ.smartcalendar.outils.DateFormater;
import fr.amu.univ.smartcalendar.ui.activities.adapters.EventViewAdapter;
import fr.amu.univ.smartcalendar.ui.activities.multiple.view.library.WeekView;
import fr.amu.univ.smartcalendar.ui.activities.multiple.view.library.WeekViewEvent;

public class MultipleViewActivity extends BaseActivity {
    EvenementDAO evenementDAO = new EvenementDAO(this);
    EventViewAdapter eventViewAdapter = new EventViewAdapter(this);



    @Override
    public List<? extends WeekViewEvent> onMonthChange(int newYear, int newMonth) {


        // Populate the week view with some events.
        List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,newYear);
        c.set(Calendar.MONTH,newMonth - 2);

        //getSupportActionBar().setTitle(DateFormater.dateFormatMonth(c.getTime()));



        //List<Long> eventsDates = evenementDAO.findDistinctMonthEvents(newMonth);


        List<Evenement> eventsList = evenementDAO.findByMonthAndYear(newMonth);
        if(eventsList!=null) {
            for (Evenement e : eventsList) {
                Calendar startTime = Calendar.getInstance();
                Calendar endTime = Calendar.getInstance();
                startTime.setTimeInMillis(e.getDateDebut());
                endTime.setTimeInMillis(e.getDateFin());
                String titre = eventViewAdapter.formatTitre(e.getTitre());
                WeekViewEvent event;
                if(DateFormater.isAllDay(e.getDateDebut(),e.getDateFin()))
                      event = new WeekViewEvent(e.getId(), titre, null,startTime, endTime,true);
                else
                    event = new WeekViewEvent(e.getId(), titre,startTime, endTime);

                event.setColor(e.getColor());
                if(e.getAdresseRdv() != null)
                    event.setLocation(e.getAdresseRdv().getNom());
                events.add(event);
            }
        }
        //getSupportActionBar().setTitle(DateFormater.dateFormatMonth(mWeekView.getLastVisibleDay().getTime()));

        return events;
    }




}
