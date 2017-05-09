package fr.amu.univ.smartcalendar.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.adapters.holders.SmartCalendarEventItemViewHolder;
import fr.amu.univ.smartcalendar.model.entity.SmartCalendarEventModel;

/**
 *
 * Created by j.Katende on 05/05/2017.
 */

public class SmartCalendarEventListAdapter extends RecyclerView.Adapter<SmartCalendarEventItemViewHolder>{
    private List<SmartCalendarEventModel> calendarDataSet;
    private LayoutInflater viewInflater;
    private static List<String> li;
    private Context context;

    //public List<SmartCalendarEventModel> items;

    public SmartCalendarEventListAdapter(Context context){
        this(context, new ArrayList<SmartCalendarEventModel>());
    }

    public SmartCalendarEventListAdapter(Context context, List<SmartCalendarEventModel> eventList){
        this.context = context;
        viewInflater = LayoutInflater.from(context);
        calendarDataSet = eventList;
        Collections.synchronizedList(calendarDataSet);
    }

    @Override
    public SmartCalendarEventItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cell_item, parent, false);
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        SmartCalendarEventItemViewHolder holder = new SmartCalendarEventItemViewHolder(cell, this.context);
        return holder;
    }

    @Override
    public int getItemCount(){
        int count = 0;
        if(calendarDataSet != null){
            count = calendarDataSet.size();
        }
        return count;
    }

    @Override
    public void onBindViewHolder(SmartCalendarEventItemViewHolder holder, int position){
        if(calendarDataSet.size() > 0 && calendarDataSet.size() > position) {
            holder.layoutForEvent(calendarDataSet.get(position));
        }
    }

    public void setCalendarDataSet(List<SmartCalendarEventModel> dataSet){
        if(dataSet == null){
            this.calendarDataSet = new ArrayList<>();
        }else{
            this.calendarDataSet = dataSet;
        }
        Log.d("DEBUG", " la taille du nuveau tableau " + calendarDataSet.size());
        notifyDataSetChanged();
    }
}