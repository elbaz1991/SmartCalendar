package fr.amu.univ.smartcalendar.ui.activities;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.ImageView;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.analytics.ecommerce.Product;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.amu.univ.smartcalendar.model.bean.ColorEvent;
import fr.amu.univ.smartcalendar.outils.DateFormater;
import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;
import fr.amu.univ.smartcalendar.ui.activities.adapters.EventCellAdapter;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected CompactCalendarView compactCalendarView;


    /* Assosiation des views Xml -> Java*/
    private ImageView ui_up_down_imgView;// TextView afficher / cacher -> calendar
    private RecyclerView ui_eventListRecyclerView;

    private long selectedDateDepart;
    /* */
    private EventCellAdapter adapterRecyclerView;

    //protected List<Evenement> eventList = new ArrayList<>();

    private EvenementDAO evenementDAO = new EvenementDAO(this);
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(DateFormater.dateFormatMonth(calendar.getTime())); //
        /* Création du calendrier */
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        //compactCalendarView.setLocale(TimeZone.getDefault(),Locale.FRANCE);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                selectedDateDepart = dateClicked.getTime();
                LinearLayoutManager layoutManager = (LinearLayoutManager) ui_eventListRecyclerView
                        .getLayoutManager();
                //layoutManager.scrollToPosition()
                //loadDayEvents(dateClicked);

                //Toast.makeText(getApplicationContext(),DateFormater.dateFormat(dateClicked),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getSupportActionBar().setTitle(DateFormater.dateFormatMonth(firstDayOfNewMonth));
                loadMonthEvents();
                //Toast.makeText(getApplicationContext(),String.valueOf(DateFormater.getMonthFromTime(firstDayOfNewMonth)),Toast.LENGTH_SHORT).show();
                //loadMonthEvents();
            }

        });


        final Intent openAddEvent = new Intent(this, AddEventActivity.class);
        FloatingActionButton addEventBtn = (FloatingActionButton) findViewById(R.id.addEventBtn);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDateDepart != 0)
                    openAddEvent.putExtra("selectedDateDepart", selectedDateDepart);
                startActivity(openAddEvent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Initialisation des views 
        ui_up_down_imgView = (ImageView) findViewById(R.id.up_down_calendar);
        ui_eventListRecyclerView = (RecyclerView) findViewById(R.id.eventList_recycler_view);
        ui_eventListRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterRecyclerView = new EventCellAdapter(this);
        ui_eventListRecyclerView.setAdapter(adapterRecyclerView);


        /*
        ui_eventListRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMonthEvents();
            }
        });
        */


    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // Animation du calendrier // aficher ou cacher
    private boolean calendarVisible = false;
    public void onClickUpDown(View view) {
        if (!compactCalendarView.isAnimating()) {
            if (calendarVisible) {
                compactCalendarView.showCalendarWithAnimation();
                ui_up_down_imgView.setImageResource(R.drawable.ic_sort_up);
            } else {
                compactCalendarView.hideCalendarWithAnimation();
                ui_up_down_imgView.setImageResource(R.drawable.ic_sort_down);
            }
            calendarVisible = !calendarVisible;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        loadMonthEvents();
        //loadMonthEvents();
        //loadAllEvents();
        //compactCalendarView.showCalendarWithAnimation();

    }



    private void loadAllEvents(){
        List<Long> allEventsDate = evenementDAO.findAllEventsDates();
        /*
        AutoCompleteTextView a = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        a.setTok
        */
        if(allEventsDate != null) {
            compactCalendarView.removeAllEvents();
            for (Long dateEvent : allEventsDate) {
                compactCalendarView.addEvent(new Event(Color.RED, dateEvent, ""));
            }
        }
        List<Long> distinctEventsDate = evenementDAO.findDistinctEventsDates();
        adapterRecyclerView.setmDataSet(distinctEventsDate);
    }

    private void loadMonthEvents(){
        List<Long> allEventsDate = evenementDAO.findAllEventsByMonth(compactCalendarView.getFirstDayOfCurrentMonth());
        if(allEventsDate != null) {
            compactCalendarView.removeAllEvents();
            for (Long dateEvent : allEventsDate) {
                compactCalendarView.addEvent(new Event(Color.RED, dateEvent, ""));
            }
        }
        List<Long> distinctEventsDate = evenementDAO.findDistinctMonthEvents(compactCalendarView.getFirstDayOfCurrentMonth());
        adapterRecyclerView.setmDataSet(distinctEventsDate);
    }

    /*
    private void loadMonthEvents(){
         eventList = evenementDAO.findByDateEvent(compactCalendarView.getFirstDayOfCurrentMonth());
                if(eventList != null) {
                    compactCalendarView.removeAllEvents();
                    for (Evenement event : eventList) {
                        compactCalendarView.addEvent(new Event(Color.RED, event.getDateDebut(), event.getTitre()));
                    }
                }
        adapterRecyclerView.setmDataSet(eventList);
    }
    */


    /*
    private void loadDayEvents(Date date){
        eventList = evenementDAO.findByDateEvent(date);
        if(eventList != null) {
            compactCalendarView.removeAllEvents();
            for (Evenement event : eventList) {
                compactCalendarView.addEvent(new Event(Color.RED, event.getDateDebut(), event.getTitre()));
            }
        }
        adapterRecyclerView.setmDataSet(eventList);
    }
    */


}

