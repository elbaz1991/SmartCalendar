package fr.amu.univ.smartcalendar.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.adapters.holders.SmartCalendarEventContentHolder;
import fr.amu.univ.smartcalendar.model.entity.SmartCalendarEventModel;

/**
 * Created by elbaz on 19/04/2017.
 * Cette classe est chargé de l'adaptation des données pour qu'ils soient
 * compatible avec le RecyclerView(La vue qui affiche la liste des évènement
 */
public class SmartCalendarEventCellContentAdapter extends RecyclerView.Adapter<SmartCalendarEventContentHolder> {
    private List<SmartCalendarEventModel> mDataSet;
    private List<Long> mDateEventSet;
    private LayoutInflater mInflater;

    private SmartCalendarEventListAdapter eventViewAdapter;

    public SmartCalendarEventCellContentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        eventViewAdapter = new SmartCalendarEventListAdapter(context);
        mDateEventSet = new ArrayList<>();
    }

    @Override
    public SmartCalendarEventContentHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View cell = mInflater.inflate(R.layout.content_event_cell,parent,false);
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"cell clicked : "+view.getTag(),Toast.LENGTH_SHORT).show();
            }
        });
        return new SmartCalendarEventContentHolder(cell);
    }

    @Override
    public void onBindViewHolder(SmartCalendarEventContentHolder holder, int position) {
        //holder.layoutForEvent(mDataSet.get(position), holder);
    }

    @Override
    public int getItemCount() {
        //Log.e("Size : ",String.valueOf(mDataSet.size()));
        int itemCount = 0;
        if(mDataSet != null){
            itemCount = mDataSet.size();
        }
        return itemCount;
    }

    public void setmDataSet(List<SmartCalendarEventModel> mDataSet) {
        if (mDataSet == null) {
            this.mDataSet = new ArrayList<>();
        }else {
            this.mDataSet = mDataSet;
        }
        notifyDataSetChanged();
    }
}
