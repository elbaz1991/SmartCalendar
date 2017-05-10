package fr.amu.univ.smartcalendar.adapters.holders;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import fr.amu.univ.smartcalendar.model.dao.EvenementDAO;
import fr.amu.univ.smartcalendar.model.entity.SmartCalendarEventModel;

import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.ui.activities.AddEventActivity;
import fr.amu.univ.smartcalendar.ui.activities.ViewEventActivity;
import fr.amu.univ.smartcalendar.ui.constants.SmartCalendarFieldsLabel;
import fr.amu.univ.smartcalendar.utils.SmartCalendarDateFormat;
import fr.amu.univ.smartcalendar.utils.SmartCalendarDateUtils;

/**
 *
 * Created by j.Katende on 05/05/2017.
 */

public class SmartCalendarEventItemViewHolder extends RecyclerView.ViewHolder{
    private Context context;
    private final LinearLayout smartCalendarDateWrapper;
    private final TextView smartCalendarEventCellStartDateDay;
    private final TextView smartCalendarEventCellStartDateMonth;
    private final TextView smartCalendarEventCellTitle;
    private final TextView smartCalendarEventCellStartDateTime;
    private final TextView smartCalendarEventCellToWrapper;
    private final ImageButton smartCalendarEventCellEdit;
    private RecyclerView ui_eventContentRecyclerView;
    //private SmartCalendarEventCellContentAdapter adapterContentRecyclerView;

    private final LinearLayout smartCalendarEventCellDetailWrapper;

    private String lastDisplayedMonth ="";

    private EvenementDAO evenementDAO;

    public SmartCalendarEventItemViewHolder(View cell, Context base){
        super(cell);
        this.context = base;
        smartCalendarDateWrapper = (LinearLayout)cell.findViewById(R.id.smart_calendar_event_cell_start_date_wrapper);
        smartCalendarEventCellStartDateDay = (TextView)cell.findViewById(R.id.smart_calendar_event_cell_start_date_day);
        smartCalendarEventCellStartDateMonth = (TextView)cell.findViewById(R.id.smart_calendar_event_cell_start_date_month);
        smartCalendarEventCellTitle = (TextView)cell.findViewById(R.id.smart_calendar_event_cell_title);
        smartCalendarEventCellStartDateTime = (TextView)cell.findViewById(R.id.smart_calendar_event_cell_start_date_time);
        smartCalendarEventCellToWrapper = (TextView)cell.findViewById(R.id.smart_calendar_event_cell_to_wrapper);
        smartCalendarEventCellEdit = (ImageButton)cell.findViewById(R.id.smart_calendar_event_cell_edit);
        smartCalendarEventCellDetailWrapper = (LinearLayout)cell.findViewById(R.id.smart_calendar_event_cell_detail_wrapper);
        /*/linearLayout = (LinearLayout) cell.findViewById(R.id.contentTest);
        ui_eventContentRecyclerView = (RecyclerView) cell.findViewById(R.id.eventListContent_recyclerView);
        ui_eventContentRecyclerView.setLayoutManager(new LinearLayoutManager(cell.getContext()));


        evenementDAO = new EvenementDAO(cell.getContext()); */
    }

    public void layoutForEvent(final SmartCalendarEventModel event){
        smartCalendarEventCellTitle.setText(event.getTitre());
        SmartCalendarDateUtils eventDate = new SmartCalendarDateUtils(event.getDateDebut());
        smartCalendarEventCellStartDateDay.setText(String.valueOf(eventDate.day));
        smartCalendarEventCellStartDateMonth.setText(String.valueOf(eventDate.month));
        smartCalendarEventCellStartDateTime.setText(
                context.getResources().getString(R.string.activity_at_label) + " " +
                String.valueOf(eventDate.hour) + " : " + String.valueOf(eventDate.minute)
        );
        eventDate = new SmartCalendarDateUtils(event.getDateFin());
        smartCalendarEventCellToWrapper.setText(
                context.getResources().getString(R.string.activity_to_label) + " " +
                        eventDate.getDateAsString() + " " +
                        context.getResources().getString(R.string.activity_at_label) + " " +
                        String.valueOf(eventDate.hour) + " : " + String.valueOf(eventDate.minute)
        );
        smartCalendarEventCellEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent editIntent = new Intent(context, AddEventActivity.class);
                editIntent.setAction(Intent.ACTION_SEND);
                editIntent.putExtra(SmartCalendarFieldsLabel.SMART_CALENDAR_EVENT_ID, String.valueOf(event.getEventId()));

                context.startActivity(editIntent);
            }
        });
        smartCalendarEventCellDetailWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewIntent = new Intent(context, ViewEventActivity.class);
                viewIntent.setAction(Intent.ACTION_SEND);
                viewIntent.putExtra(SmartCalendarFieldsLabel.SMART_CALENDAR_EVENT_ID, String.valueOf(event.getEventId()));

                context.startActivity(viewIntent);
            }
        });
        /*String currentMonth = SmartCalendarDateFormat.dateFormatMonth(new Date(dateEvent));
        Log.e("Exec","Execute : " + SmartCalendarDateFormat.getEventNumDay(new Date(dateEvent))+" s : "+ SmartCalendarDateFormat.getDateFormatYearMonthDay(new Date(dateEvent)));

       /*item.smartCalendarEventNumDay.setText(SmartCalendarDateFormat.getEventNumDay(new Date(dateEvent)));
        item.smartCalendarEventNameDay.setText(SmartCalendarDateFormat.getEventNameDay(new Date(dateEvent)));
        /*item.adapterContentRecyclerView = new SmartCalendarEventCellContentAdapter(item.itemView.getContext());
        item.ui_eventContentRecyclerView.setAdapter(adapterContentRecyclerView);
        item.adapterContentRecyclerView.setmDataSet(evenementDAO.findByDateEvent(new Date(dateEvent))); * /
        item.ui_eventContentRecyclerView.getAdapter().notifyDataSetChanged();*/
    }

    /*public boolean existsDeja(List<String> li, String value){
        return li.contains(value);
    }*/
}