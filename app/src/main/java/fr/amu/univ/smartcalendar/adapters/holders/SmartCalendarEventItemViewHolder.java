package fr.amu.univ.smartcalendar.adapters.holders;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;
import fr.amu.univ.smartcalendar.model.entity.SmartCalendarEventModel;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.utils.SmartCalendarDateFormat;

/**
 *
 * Created by j.Katende on 05/05/2017.
 */

public class SmartCalendarEventItemViewHolder extends RecyclerView.ViewHolder{
    private final TextView ui_cell_eventNumDay;
    private final TextView ui_cell_eventNameDay;
    private final TextView ui_cell_eventMonth;
    private RecyclerView ui_eventContentRecyclerView;
    //private SmartCalendarEventCellContentAdapter adapterContentRecyclerView;

    private LinearLayout linearLayout;

    private String lastDisplayedMonth ="";

    private EvenementDAO evenementDAO;

    public SmartCalendarEventItemViewHolder(View cell){
        super(cell);
        ui_cell_eventNumDay = (TextView) cell.findViewById(R.id.eventNumDay);
        ui_cell_eventNameDay = (TextView) cell.findViewById(R.id.eventNameDay);
        ui_cell_eventMonth = (TextView) cell.findViewById(R.id.eventMonth);
        //linearLayout = (LinearLayout) cell.findViewById(R.id.contentTest);
        ui_eventContentRecyclerView = (RecyclerView) cell.findViewById(R.id.eventListContent_recyclerView);
        ui_eventContentRecyclerView.setLayoutManager(new LinearLayoutManager(cell.getContext()));


        evenementDAO = new EvenementDAO(cell.getContext());
    }

    public void layoutForEvent(SmartCalendarEventItemViewHolder item, Long dateEvent){
        String currentMonth = SmartCalendarDateFormat.dateFormatMonth(new Date(dateEvent));
        Log.e("Exec","Execute : " + SmartCalendarDateFormat.getEventNumDay(new Date(dateEvent))+" s : "+ SmartCalendarDateFormat.getDateFormatYearMonthDay(new Date(dateEvent)));


        item.ui_cell_eventNumDay.setText(SmartCalendarDateFormat.getEventNumDay(new Date(dateEvent)));
        /*item.ui_cell_eventNameDay.setText(SmartCalendarDateFormat.getEventNameDay(new Date(dateEvent)));
        item.adapterContentRecyclerView = new SmartCalendarEventCellContentAdapter(item.itemView.getContext());
        item.ui_eventContentRecyclerView.setAdapter(adapterContentRecyclerView);
        item.adapterContentRecyclerView.setmDataSet(evenementDAO.findByDateEvent(new Date(dateEvent))); */
        item.ui_eventContentRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public boolean existsDeja(List<String> li, String value){
        return li.contains(value);
    }
}