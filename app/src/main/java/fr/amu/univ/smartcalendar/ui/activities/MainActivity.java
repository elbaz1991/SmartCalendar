package fr.amu.univ.smartcalendar.ui.activities;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
/*
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;

import fr.amu.univ.smartcalendar.adapters.SmartCalendarEventListAdapter;
import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;
import fr.amu.univ.smartcalendar.model.entity.SmartCalendarEventModel;
import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.ui.constants.SmartCalendarFieldsLabel;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);
    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateFormatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.FRANCE);

    /* Association des views Xml -> Java*/
    private ImageView ui_up_down_imgView;// TextView afficher / cacher -> calendar
    private RecyclerView ui_eventListRecyclerView;

    private long selectedDateDepart;

    private EvenementDAO evenementDAO = new EvenementDAO(this);

    private SmartCalendarEventListAdapter adapterRecyclerView;

    private FloatingActionButton addEventBtn;

    // Base de donnée
    //DatabaseHelper sqliteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //PropertyConfigurator.configure(new Properties().load(new In)); //.getConfigurator(this).configure();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(dateFormatMonth.format(new Date())); //
        /* Création du calendrier */
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        //compactCalendarView.setLocale(TimeZone.getDefault(),Locale.FRANCE);


        addEventBtn = (FloatingActionButton) findViewById(R.id.addEventBtn);


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
        adapterRecyclerView = new SmartCalendarEventListAdapter(this);
        ui_eventListRecyclerView.setAdapter(adapterRecyclerView);
        ui_eventListRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(ui_eventListRecyclerView.getContext(), );
        addEventListeners();
    }

    private void addEventListeners(){
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent openAddEvent = new Intent(MainActivity.this,AddEventActivity.class);
                if(selectedDateDepart != 0){
                    openAddEvent.putExtra(SmartCalendarFieldsLabel.SELECTED_START_DATE, selectedDateDepart);
                }
                openAddEvent.putExtra(SmartCalendarFieldsLabel.SMART_CALENDAR_EVENT_ID, "0");
                startActivityForResult(openAddEvent, SmartCalendarFieldsLabel.REQUEST_EVENT_EDITION_CODE);
            }
        });

        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMMM - yyyy", Locale.FRANCE);
            @Override
            public void onDayClick(Date dateClicked) {
                selectedDateDepart = dateClicked.getTime();
                Toast.makeText(getApplicationContext(), dateFormat.format(dateClicked),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getSupportActionBar().setTitle(dateFormatMonth.format(firstDayOfNewMonth));
                loadMonthEvents();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case SmartCalendarFieldsLabel.REQUEST_EVENT_EDITION_CODE :
                SmartCalendarEventModel localEvent = (SmartCalendarEventModel)data.getSerializableExtra(SmartCalendarFieldsLabel.SMART_CALENDAR_EVENT_OBJECT);
                adapterRecyclerView.swapCalendarEvent(localEvent);
                break;
        }
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
    protected void onResume(){
        super.onResume();
        loadMonthEvents();
    }

    private void loadAllEvents(){
        //todo List<Long> allEventsDate = evenementDAO.findAllEventsDates();

        /*if(allEventsDate != null){
            compactCalendarView.removeAllEvents();
            for (Long dateEvent : allEventsDate){
                compactCalendarView.addEvent(new Event(Color.RED, dateEvent, ""));
            }
        }
        List<Long> distinctEventsDate = evenementDAO.findDistinctEventsDates();
        adapterRecyclerView.setCalendarDataSet(distinctEventsDate); */
    }

    private void loadMonthEvents(){
        List<SmartCalendarEventModel> allEventsDate = evenementDAO.findAllEventsByMonth(compactCalendarView.getFirstDayOfCurrentMonth());

        if(allEventsDate != null && allEventsDate.size() > 0) {
            compactCalendarView.removeAllEvents();
            for (SmartCalendarEventModel eventData : allEventsDate) {
                //compactCalendarView.addEvent(new Event(Color.RED, dateEvent, ""));
            }
        }
        List<SmartCalendarEventModel> distinctEventsDate = evenementDAO.findAllEventsByMonth(compactCalendarView.getFirstDayOfCurrentMonth());
        adapterRecyclerView.setCalendarDataSet(distinctEventsDate);
    }

    /**
     * Created by elbaz on 19/04/2017.
     * Cette classe est chargé de l'adaptation des données pour qu'ils soient
     * compatible avec le RecyclerView(La vue qui affiche la liste des évènement
     * /

    class EventListAdapter extends RecyclerView.Adapter<EventHolder>{

        private List<SmartCalendarEventModel> eventList;

        public EventListAdapter(){
            eventList = SmartCalendarEventModel.getSmartCalendarEvents();
            //eventList.add(new SmartCalendarEventModel("walo","helooooooo !"));
            //eventList.add(new SmartCalendarEventModel("Yes","Holaaaaa !"));
            //eventList.add(new SmartCalendarEventModel("No","Amigoooooo !"));
            notifyDataSetChanged();
        }


        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View cell = LayoutInflater.from(MainActivity.this).inflate(R.layout.event_cell,parent,false);
            EventHolder holder = new EventHolder(cell);
            return holder;
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            holder.layoutForEvent(eventList.get(position));
        }

        @Override
        public int getItemCount() {
            int itemCount = 0;
            if(eventList != null){
                itemCount = eventList.size();
            }
            return itemCount;
        }
    }

    /**
     * Created by elbaz on 19/04/2017.
     * Holder c'est un objet qui Gère la vue qui est visible dans la liste des évènement
     * /

    class EventHolder extends RecyclerView.ViewHolder {

        private final TextView ui_eventTitle;

        public EventHolder(View cell) {
            super(cell);
            ui_eventTitle = (TextView) cell.findViewById(R.id.eventTitle);
        }

        public void layoutForEvent(SmartCalendarEventModel event){
            ui_eventTitle.setText(event.getTitre());
        }
    }*/
}