package fr.amu.univ.smartcalendar.ui.activities.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.internal.PlaceImpl;

import java.util.ArrayList;
import java.util.List;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.model.entity.Evenement;


/**
 * Created by elbaz on 19/04/2017.
 * Cette classe est chargé de l'adaptation des données pour qu'ils soient
 * compatible avec le RecyclerView(La vue qui affiche la liste des évènement
 */
public class EventCellContentAdapter extends RecyclerView.Adapter<EventCellContentAdapter.EventHolder>{
    private List<Evenement> mDataSet;
    private List<Long> mDateEventSet;
    private LayoutInflater mInflater;
    EventViewAdapter eventViewAdapter;

    public EventCellContentAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        eventViewAdapter = new EventViewAdapter(context);
        mDateEventSet = new ArrayList<>();
    }

    @Override
    public EventHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View cell = mInflater.inflate(R.layout.content_event_cell,parent,false);
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"cell clicked : "+view.getTag(),Toast.LENGTH_SHORT).show();
            }
        });
        EventHolder holder = new EventHolder(cell);
        return holder;
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        holder.layoutForEvent(mDataSet.get(position),holder);
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




    public void setmDataSet(List<Evenement> mDataSet) {
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
        private final TextView ui_cell_content_title;
        private final TextView ui_cell_contentDuree;
        private TextView ui_cell_contentPosition;
        private TextView ui_cell_ContentColor;

        public EventHolder(View cell) {
            super(cell);
            ui_cell_content_title = (TextView) cell.findViewById(R.id.eventContentTitle);
            ui_cell_contentDuree = (TextView) cell.findViewById(R.id.eventDuree);
            ui_cell_contentPosition = (TextView) cell.findViewById(R.id.eventPosition);
            ui_cell_ContentColor = (TextView) cell.findViewById(R.id.eventContentColor);
        }


        public void layoutForEvent(Evenement event, EventHolder holder){
            ui_cell_ContentColor.setBackgroundColor(event.getColor());
            holder.ui_cell_content_title.setText(eventViewAdapter.formatTitre(event.getTitre(),event.getDateDebut(),event.getDateFin(),event.getCurrentDate()));
            holder.ui_cell_contentDuree.setText(eventViewAdapter.getDuree(event.getDateDebut(),event.getDateFin(),event.getCurrentDate()));

            if(event.getAdresseRdv() != null) {
                holder.ui_cell_contentPosition.setText(event.getAdresseRdv().getAdresse());
                holder.ui_cell_contentPosition.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_location_on_black_24dp, 0, 0, 0);
            }else{
                holder.ui_cell_contentPosition.setVisibility(View.GONE);
            }

            holder.itemView.setTag(event.getId());
        }


    }


}

