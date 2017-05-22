package fr.amu.univ.smartcalendar.ui.activities;



import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.amu.univ.smartcalendar.model.entity.Evenement;
import fr.amu.univ.smartcalendar.outils.DateFormater;
import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;
import fr.amu.univ.smartcalendar.ui.activities.adapters.EventCellAdapter;
import fr.amu.univ.smartcalendar.ui.activities.multiple.view.MultipleViewActivity;

import static fr.amu.univ.smartcalendar.R.id.dateDebut;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private CompactCalendarView compactCalendarView;


    /* Assosiation des views Xml -> Java*/
    private ImageView ui_up_down_imgView;// TextView afficher / cacher -> calendar
    private RecyclerView ui_eventListRecyclerView;

    private long selectedDateDepart;
    /* */
    private EventCellAdapter adapterRecyclerView;

    //protected List<Evenement> eventList = new ArrayList<>();

    private EvenementDAO evenementDAO = new EvenementDAO(this);
    private Calendar calendar = Calendar.getInstance();
    private LinearLayoutManager layoutManagerRecyclerView;
    private static int firstVisibleInListview;
    private boolean calendarClicked = false;
    private NavigationView ui_navigationView;

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


        selectedDateDepart = DateFormater.getDateWithoutTime();
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override
            public void onDayClick(Date dateClicked) {
                selectedDateDepart = dateClicked.getTime();
                layoutManagerRecyclerView.scrollToPositionWithOffset(adapterRecyclerView.getDatePosition(dateClicked.getTime()), 0);
                calendarClicked = true;
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getSupportActionBar().setTitle(DateFormater.dateFormatMonth(firstDayOfNewMonth));
                selectedDateDepart = firstDayOfNewMonth.getTime();
                layoutManagerRecyclerView.scrollToPositionWithOffset(adapterRecyclerView.getDatePosition(firstDayOfNewMonth.getTime()),0);
                compactCalendarView.setCurrentDate(firstDayOfNewMonth);
                calendarClicked = true;
            }

        });


        final Intent openAddEvent = new Intent(this, AddEventActivity.class);
        FloatingActionButton addEventBtn = (FloatingActionButton) findViewById(R.id.addEventBtn);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDateDepart != 0) {
                    Calendar c = (Calendar) calendar.clone();
                    c.setTimeInMillis(selectedDateDepart);
                    c.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
                    c.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
                    openAddEvent.putExtra("selectedDateDepart", c.getTimeInMillis());
                }startActivity(openAddEvent);
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


        layoutManagerRecyclerView = (LinearLayoutManager) ui_eventListRecyclerView.getLayoutManager();
        firstVisibleInListview = layoutManagerRecyclerView.findFirstVisibleItemPosition();


        ui_navigationView = (NavigationView) findViewById(R.id.nav_view);

        ui_eventListRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!calendarClicked) {
                    int currentFirstVisible = layoutManagerRecyclerView.findFirstVisibleItemPosition();
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(selectedDateDepart);
                    if (dy > 0) {
                        if (currentFirstVisible > firstVisibleInListview) {
                            c.add(Calendar.DATE, 1);
                            compactCalendarView.setCurrentDate(c.getTime());
                            selectedDateDepart = c.getTimeInMillis();
                            Log.i("RecyclerView scrolled: ", "scroll up!");
                        }
                    } else {
                        if (currentFirstVisible < firstVisibleInListview) {
                            c.add(Calendar.DATE, -1);
                            compactCalendarView.setCurrentDate(c.getTime());
                            selectedDateDepart = c.getTimeInMillis();
                            Log.i("RecyclerView scrolled: ", "scroll DOWN!");
                        }
                        //Log.i("RecyclerView scrolled: ", "scroll DOWN!");
                    }

                    firstVisibleInListview = currentFirstVisible;
                    getSupportActionBar().setTitle(DateFormater.dateFormatMonth(new Date(selectedDateDepart)));
                /*
                if(currentFirstVisible > firstVisibleInListview)
                    Log.i("RecyclerView scrolled: ", "scroll up!");
                else
                    Log.i("RecyclerView scrolled: ", "scroll down!");
                */


                } else {
                    calendarClicked = false;
                }
            }

        });



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
        /*
        if (id == R.id.action_settings) {
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id!= R.id.main_viewMonth) {
            Intent intent = new Intent(this, MultipleViewActivity.class);
            if (id == R.id.main_viewDay) {
                intent.putExtra("viewType", 1);
            } else if (id == R.id.main_view3Days) {
                intent.putExtra("viewType", 3);
            } else if (id == R.id.main_view7Days) {
                intent.putExtra("viewType", 7);
            }
            startActivity(intent);
            //finish();
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
        //loadMonthEvents();
        loadAllEvents();

        layoutManagerRecyclerView.scrollToPositionWithOffset(adapterRecyclerView.getDatePosition(selectedDateDepart),0);


        // pour effacer les valeurs des moins unique afin de recharger les nouvelles date de debut de chaque mois
        EventCellAdapter.getMapUniqueMonth().clear();

        ui_navigationView.getMenu().getItem(0).setChecked(true);
    }



    public void loadAllEvents(){
        loadAllCallendarEvent();
        //List<Long> distinctEventsDate = evenementDAO.findDistinctAllEvents();
        //adapterRecyclerView.setmDataSet(distinctEventsDate);



        List<Long> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(DateFormater.getDateWithoutTime());
        calendar.set(Calendar.MONTH,Calendar.JANUARY );

        int startYear = calendar.get(Calendar.YEAR) - 15;
        int endYear = calendar.get(Calendar.YEAR) + 15;
        int currentYear = startYear;
        calendar.set(Calendar.YEAR,startYear);

        while (currentYear <= endYear){
            calendar.add(Calendar.DATE,1);
            dates.add(calendar.getTimeInMillis());
            currentYear = calendar.get(Calendar.YEAR);
        }


        adapterRecyclerView.setmDataSet(dates);
    }




    private void loadAllCallendarEvent(){
        List<Evenement> events = evenementDAO.findAll();
        compactCalendarView.removeAllEvents();
        if(events != null) {
            for (Evenement event : events) {
                long difference = DateFormater.getDifferenceDays(event.getDateDebut(), event.getDateFin());

                compactCalendarView.addEvent(new Event(event.getColor(), event.getDateDebut(), event.getTitre()));
                if (difference > 0) {
                    for (int i = 1; i <= difference; i++) {
                        Calendar c = Calendar.getInstance();
                        c.setTimeInMillis(event.getDateDebut());
                        c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + i);
                        compactCalendarView.addEvent(new Event(event.getColor(), c.getTimeInMillis(), event.getTitre()));

                    }
                }
            }
        }
    }


    /*
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
    */

}

