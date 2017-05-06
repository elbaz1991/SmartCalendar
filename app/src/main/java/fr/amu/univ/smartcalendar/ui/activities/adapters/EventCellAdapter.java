package fr.amu.univ.smartcalendar.ui.activities.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.amu.univ.smartcalendar.outils.DateFormater;
import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;


/**
 * Created by elbaz on 19/04/2017.
 * Cette classe est chargé de l'adaptation des données pour qu'ils soient
 * compatible avec le RecyclerView(La vue qui affiche la liste des évènement
 */
public class EventCellAdapter extends RecyclerView.Adapter<EventCellAdapter.EventHolder>{
    private List<Long> mDataSet;
    private LayoutInflater mInflater;
    private static List<String> li;

    public EventCellAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        li = new ArrayList<>();
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

        private LinearLayout linearLayout;

        private String lastDisplayedMonth ="";

        private EvenementDAO evenementDAO;


        public EventHolder(View cell) {
            super(cell);
            ui_cell_eventNumDay = (TextView) cell.findViewById(R.id.eventNumDay);
            ui_cell_eventNameDay = (TextView) cell.findViewById(R.id.eventNameDay);
            ui_cell_eventMonth = (TextView) cell.findViewById(R.id.eventMonth);
            //linearLayout = (LinearLayout) cell.findViewById(R.id.contentTest);
            ui_eventContentRecyclerView = (RecyclerView) cell.findViewById(R.id.eventListContent_recyclerView);
            ui_eventContentRecyclerView.setLayoutManager(new LinearLayoutManager(cell.getContext()));


            evenementDAO = new EvenementDAO(cell.getContext());
        }

        public void layoutForEvent(EventHolder holder,Long dateEvent){
            String currentMonth = DateFormater.dateFormatMonth(new Date(dateEvent));


            /*
            if(!existDeja(li,currentMonth)){
                //Log.e("ExecuteExist","Yeeeeeeeeeeees");
                li.add(currentMonth);
                ui_cell_eventMonth.setVisibility(View.VISIBLE);
                ui_cell_eventMonth.setText(currentMonth + " : "+li.size());
            }
            */

            Log.e("Exec","Execute : " + DateFormater.getEventNumDay(new Date(dateEvent))+" s : "+DateFormater.dateFormatYyMmDd(new Date(dateEvent)));


            holder.ui_cell_eventNumDay.setText(DateFormater.getEventNumDay(new Date(dateEvent)));
            holder.ui_cell_eventNameDay.setText(DateFormater.getEventNameDay(new Date(dateEvent)));
            holder.adapterContentRecyclerView = new EventCellContentAdapter(holder.itemView.getContext());
            holder.ui_eventContentRecyclerView.setAdapter(adapterContentRecyclerView);
            holder.adapterContentRecyclerView.setmDataSet(evenementDAO.findByDateEvent(new Date(dateEvent)));
            holder.ui_eventContentRecyclerView.getAdapter().notifyDataSetChanged();

            /*
            List<Evenement> listEvent = evenementDAO.findByDateEvent(new Date(dateEvent));
           for (Evenement e : listEvent){
                TextView ext = new TextView(holder.itemView.getContext());
                ext.setText("Test");
                ext.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                ext.setWidth(20);
                ext.setHeight(20);

            linearLayout.addView(ext);
                //linearLayout.setVisibility(View.INVISIBLE);
                //linearLayout.addView(new TextView(holder.itemView.getContext()));
            }
            */
            //notifyDataSetChanged();
        }

        public boolean existDeja(List<String> li,String value){
            for(int i=0;i<li.size();i++){
                if(li.get(i).equals(value))
                    return true;
            }
            return false;
        }
}


}