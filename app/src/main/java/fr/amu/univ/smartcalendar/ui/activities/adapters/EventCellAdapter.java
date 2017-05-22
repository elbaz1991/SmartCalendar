package fr.amu.univ.smartcalendar.ui.activities.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.amu.univ.smartcalendar.ui.activities.multiple.view.MultipleViewActivity;
import fr.amu.univ.smartcalendar.outils.DateFormater;
import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;
import fr.amu.univ.smartcalendar.ui.activities.MainActivity;


/**
 * Created by elbaz on 19/04/2017.
 * Cette classe est chargé de l'adaptation des données pour qu'ils soient
 * compatible avec le RecyclerView(La vue qui affiche la liste des évènement
 */
public class EventCellAdapter extends RecyclerView.Adapter<EventCellAdapter.EventHolder>{
    private List<Long> mDataSet;
    private LayoutInflater mInflater;
    private static Map<String,Long> mapUniqueMonth;
    private EvenementDAO evenementDAO;
    private MainActivity mainActivity;

    public EventCellAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        mapUniqueMonth = new HashMap<>();
        evenementDAO = new EvenementDAO(context);
        mainActivity = (MainActivity) context;
        loadMap();
    }

    @Override
    public EventHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View cell = mInflater.inflate(R.layout.event_cell,parent,false);
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getBaseContext(),"cell clicked",Toast.LENGTH_SHORT).show();
                //parent.sele
            }
        });
        EventHolder holder = new EventHolder(cell);
        return holder;
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        holder.layoutForEvent(holder,mDataSet.get(position));
        //holder.adapterContentRecyclerView.addDateEvent(mDataSet.get(position));
        //adapterContentRecyclerView.addDateEvent(dateEvent);
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if(mDataSet != null){
            itemCount = mDataSet.size();
        }
        return itemCount;
    }

    public void setmDataSet(List<Long> mDataSet) {
       if (mDataSet == null) {
           this.mDataSet = new ArrayList<>();
       }else {
           this.mDataSet = mDataSet;
       }
        notifyDataSetChanged();
    }



    /**
     * Created by elbaz on 19/04/2017.
     * Holder c'est un objet qui Gère la vue qui est visible dans la liste des évènement
     */

    class EventHolder extends RecyclerView.ViewHolder {

        private final TextView ui_cell_eventNumDay;
        private final TextView ui_cell_eventNameDay;
        private final TextView ui_cell_eventMonth;
        private RecyclerView ui_eventContentRecyclerView;
        private EventCellContentAdapter adapterContentRecyclerView;







        public EventHolder(View cell) {
            super(cell);
            ui_cell_eventNumDay = (TextView) cell.findViewById(R.id.eventNumDay);
            ui_cell_eventNameDay = (TextView) cell.findViewById(R.id.eventNameDay);
            ui_cell_eventMonth = (TextView) cell.findViewById(R.id.eventMonth);
            ui_eventContentRecyclerView = (RecyclerView) cell.findViewById(R.id.eventListContent_recyclerView);
            ui_eventContentRecyclerView.setLayoutManager(new LinearLayoutManager(cell.getContext()));
            cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(view.getContext(),view.getTag().toString(),Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mainActivity.getApplicationContext(), MultipleViewActivity.class);
                    intent.putExtra("date",Long.valueOf(view.getTag().toString()));
                    mainActivity.startActivity(intent);


                }
            });


        }

        public void layoutForEvent(final EventHolder holder, Long dateEvent){

            String currentMonth = DateFormater.dateFormatMMMMyyyy(new Date(dateEvent));
            int currentDay = DateFormater.getDay(dateEvent);
            holder.itemView.setTag(dateEvent);


            if(currentDay == 1){
                setBackgroundMonth(new Date(dateEvent).getMonth() + 1,holder.ui_cell_eventMonth);
                holder.ui_cell_eventMonth.setText(currentMonth);
                holder.ui_cell_eventMonth.setVisibility(View.VISIBLE);
                holder.ui_cell_eventMonth.setClickable(false);
            }else{
                holder.ui_cell_eventMonth.setBackground(null);
                holder.ui_cell_eventMonth.setText(null);
                holder.ui_cell_eventMonth.setVisibility(View.GONE);
            }

            /*
            if(mapUniqueMonth != null && mapUniqueMonth.size() == 0)
                loadMap();

            if(mapUniqueMonth.get(currentMonth).toString().equals(holder.itemView.getTag().toString())){
                setBackgroundMonth(new Date(dateEvent).getMonth() + 1,holder.ui_cell_eventMonth);
                holder.ui_cell_eventMonth.setText(currentMonth);
                holder.ui_cell_eventMonth.setVisibility(View.VISIBLE);
            }else{
                holder.ui_cell_eventMonth.setBackground(null);
                holder.ui_cell_eventMonth.setText(null);
                holder.ui_cell_eventMonth.setVisibility(View.GONE);
            }
            */


            holder.ui_cell_eventNumDay.setText(DateFormater.getEventNumDay(new Date(dateEvent)));
            holder.ui_cell_eventNameDay.setText(DateFormater.getEventNameDay(new Date(dateEvent)));
            if(holder.ui_cell_eventNameDay.getText().equals("Dim.")){
                int color = holder.itemView.getResources().getColor(R.color.colorWeekCell);
                holder.ui_cell_eventNumDay.setTextColor(color);
                holder.ui_cell_eventNameDay.setTextColor(color);
            }else{
                int color = holder.itemView.getResources().getColor(R.color.colorTextDate);
                holder.ui_cell_eventNumDay.setTextColor(color);
                holder.ui_cell_eventNameDay.setTextColor(color);
            }



            holder.adapterContentRecyclerView = new EventCellContentAdapter(holder.itemView.getContext(),EventCellAdapter.this,mainActivity);
            holder.ui_eventContentRecyclerView.setAdapter(adapterContentRecyclerView);
            holder.adapterContentRecyclerView.setmDataSet(evenementDAO.findByDateEvent(new Date(dateEvent)));
            holder.ui_eventContentRecyclerView.getAdapter().notifyDataSetChanged();




        }




}




    public static Map<String, Long> getMapUniqueMonth() {
        return mapUniqueMonth;
    }


    private void setBackgroundMonth(int month, TextView textView){
        switch (month){
            case 1 : textView.setBackgroundResource(R.drawable.bg_month_01);break;
            case 2 : textView.setBackgroundResource(R.drawable.bg_month_02);break;
            case 3 : textView.setBackgroundResource(R.drawable.bg_month_03);break;
            case 4 : textView.setBackgroundResource(R.drawable.bg_month_04);break;
            case 5 : textView.setBackgroundResource(R.drawable.bg_month_05);break;
            case 6 : textView.setBackgroundResource(R.drawable.bg_month_06);break;
            case 7 : textView.setBackgroundResource(R.drawable.bg_month_07);break;
            case 8 : textView.setBackgroundResource(R.drawable.bg_month_08);break;
            case 9 : textView.setBackgroundResource(R.drawable.bg_month_09);break;
            case 10 : textView.setBackgroundResource(R.drawable.bg_month_10);break;
            case 11 : textView.setBackgroundResource(R.drawable.bg_month_11);break;
            case 12 : textView.setBackgroundResource(R.drawable.bg_month_12);break;
        }
    }

    private void loadMap(){
        mapUniqueMonth.clear();
        //List<Long> li = evenementDAO.findDistinctAllEvents();
        List<Long> li =loadAllEvents();
        Calendar c = Calendar.getInstance();
        String currentMonth;

        if(li != null) {
            for (Long dateEvent : li) {
                c.setTimeInMillis(dateEvent);
                currentMonth = DateFormater.dateFormatMonthYear(c.getTime());
                // ajouter les dates de debut de chaque mois
                if (!mapUniqueMonth.containsKey(currentMonth)) {
                    mapUniqueMonth.put(currentMonth, dateEvent);
                }
            }
        }
    }

    public void deleteEvent(int idEvent){
        evenementDAO.delete(idEvent);
        mainActivity.loadAllEvents();
        loadMap();
    }

    public List<Long> loadAllEvents(){
        List<Long> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int startYear = calendar.get(Calendar.YEAR) +1;
        calendar.set(Calendar.MONTH,Calendar.JANUARY );
        calendar.set(Calendar.DAY_OF_YEAR,0);
        for(int i = 1;i<365;i++){
            calendar.set(Calendar.DAY_OF_YEAR,i);
            //calendar.add(Calendar.DATE,i);
            dates.add(calendar.getTimeInMillis());
        }
        return dates;
        //adapterRecyclerView.setmDataSet(dates);
    }

    public int getDatePosition(long date){
        for(int i =0;i<=mDataSet.size();i++){
            if(mDataSet.get(i) == date)
                return i;
        }
        return 0;
    }



}