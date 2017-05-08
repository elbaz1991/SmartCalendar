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
    private List<Long> calendarDataSet;
    private LayoutInflater viewInflater;
    private static List<String> li;

    public List<SmartCalendarEventModel> items;

    public SmartCalendarEventListAdapter(Context context){ //List<SmartCalendarEventModel> eventList){
        viewInflater = LayoutInflater.from(context);
        li = new ArrayList<>();
        items = new ArrayList<>(); //Collections.synchronizedList()//new eventList;
    }

    @Override
    public SmartCalendarEventItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_cell, parent, true);
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        SmartCalendarEventItemViewHolder holder = new SmartCalendarEventItemViewHolder(cell);
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
        //holder.layoutForEvent(items.get(position));
    }

    public void setCalendarDataSet(List<Long> dataSet){
        if(dataSet == null){
            this.calendarDataSet = new ArrayList<>();
        }else{
            this.calendarDataSet = dataSet;
        }
        notifyDataSetChanged();
    }
}
