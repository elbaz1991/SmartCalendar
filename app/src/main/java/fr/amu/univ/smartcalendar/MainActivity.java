package fr.amu.univ.smartcalendar;



import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import fr.amu.univ.smartcalendar.model.dao.DatabaseHelper;
import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;
import fr.amu.univ.smartcalendar.model.entity.Evenement;
import fr.amu.univ.smartcalendar.ui.activities.AddEventActivity;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CompactCalendarView compactCalendarView;
    private SimpleDateFormat dateForrmatMonth = new SimpleDateFormat("MMMM - yyyy", Locale.FRANCE);

    /* Assosiation des views Xml -> Java*/
    private ImageView ui_up_down_imgView;// TextView afficher / cacher -> calendar
    private RecyclerView ui_eventListRecyclerView;


    // Base de donnée
    //DatabaseHelper sqliteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(dateForrmatMonth.format(new Date())); //
        /* Création du calendrier */
        compactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
        compactCalendarView.setLocale(TimeZone.getDefault(),Locale.FRANCE);
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            SimpleDateFormat dateForrma = new SimpleDateFormat("dd - MMMM - yyyy", Locale.FRANCE);
            @Override
            public void onDayClick(Date dateClicked) {
                Toast.makeText(getApplicationContext(),dateForrma.format(dateClicked).toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                getSupportActionBar().setTitle(dateForrmatMonth.format(firstDayOfNewMonth));
            }
        });


        final Intent openAddEvent = new Intent(this,AddEventActivity.class);
        FloatingActionButton addEventBtn = (FloatingActionButton) findViewById(R.id.addEventBtn);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        ui_eventListRecyclerView.setAdapter(new EventListAdapter());



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




    /**
     * Created by elbaz on 19/04/2017.
     * Cette classe est chargé de l'adaptation des données pour qu'ils soient
     * compatible avec le RecyclerView(La vue qui affiche la liste des évènement
     */

    class EventListAdapter extends RecyclerView.Adapter<EventHolder>{

        private List<Evenement> eventList;

        public EventListAdapter(){
            eventList = new ArrayList<>();
            eventList.add(new Evenement("walo","helooooooo !"));
            eventList.add(new Evenement("Yes","Holaaaaa !"));
            eventList.add(new Evenement("No","Amigoooooo !"));
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
     */

    class EventHolder extends RecyclerView.ViewHolder {

        private final TextView ui_eventTitle;

        public EventHolder(View cell) {
            super(cell);
            ui_eventTitle = (TextView) cell.findViewById(R.id.eventTitle);
        }

        public void layoutForEvent(Evenement event){
            ui_eventTitle.setText(event.getTitre());
        }
    }
}

