package fr.amu.univ.smartcalendar.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.adapters.SmartCalendarEventListAdapter;
import fr.amu.univ.smartcalendar.model.entity.SmartCalendarEventModel;

/**
 * Created by elbaz on 19/04/2017.
 * Cette classe est chargé de l'adaptation des données pour qu'ils soient
 * compatible avec le RecyclerView(La vue qui affiche la liste des évènement
 */

public class SmartCalendarEventContentHolder extends RecyclerView.ViewHolder {
    private final TextView ui_cell_content_title;
    private final TextView ui_cell_contentDuree;
    private TextView ui_cell_contentPosition;
    private TextView ui_cell_ContentColor;

    public SmartCalendarEventContentHolder(View cell){
        super(cell);
        ui_cell_content_title = (TextView) cell.findViewById(R.id.eventContentTitle);
        ui_cell_contentDuree = (TextView) cell.findViewById(R.id.eventDuree);
        ui_cell_contentPosition = (TextView) cell.findViewById(R.id.eventPosition);
        ui_cell_ContentColor = (TextView) cell.findViewById(R.id.eventContentColor);
    }

    public void layoutForEvent(SmartCalendarEventModel event, SmartCalendarEventListAdapter eventViewAdapter, SmartCalendarEventContentHolder holder){
        ui_cell_ContentColor.setBackgroundColor(event.getColor());
        /*holder.ui_cell_content_title.setText(eventViewAdapter.formatTitre(event.getTitre(),event.getDateDebut(),event.getDateFin(),event.getCurrentDate()));
        holder.ui_cell_contentDuree.setText(eventViewAdapter.getDuree(event.getDateDebut(),event.getDateFin(),event.getCurrentDate()));

        if(event.getAdresseRdv() != null) {
            holder.ui_cell_contentPosition.setText(event.getAdresseRdv().getAdresse());
            holder.ui_cell_contentPosition.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_location_on_black_24dp, 0, 0, 0);
        }else{
            holder.ui_cell_contentPosition.setVisibility(View.GONE);
        }*/

        holder.itemView.setTag(event.getEventId());
    }
}
