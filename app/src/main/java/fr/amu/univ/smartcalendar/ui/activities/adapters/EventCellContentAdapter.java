package fr.amu.univ.smartcalendar.ui.activities.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;

import java.util.ArrayList;
import java.util.List;
import fr.amu.univ.smartcalendar.R;
import fr.amu.univ.smartcalendar.model.entity.Evenement;
import fr.amu.univ.smartcalendar.ui.activities.DetailsEventActivity;


/**
 * Created by elbaz on 19/04/2017.
 * Cette classe est chargé de l'adaptation des données pour qu'ils soient
 * compatible avec le RecyclerView(La vue qui affiche la liste des évènement
 */
public class EventCellContentAdapter extends RecyclerView.Adapter<EventCellContentAdapter.EventHolder>{
    private List<Evenement> mDataSet;
    private LayoutInflater mInflater;
    private EventViewAdapter eventViewAdapter;
    private EventCellAdapter eventCellAdapter;
    private Activity mainActivity;

    public EventCellContentAdapter(Context context,EventCellAdapter eventCellAdapter,Activity mainActivity){
        mInflater = LayoutInflater.from(context);
        eventViewAdapter = new EventViewAdapter(context);
        this.eventCellAdapter = eventCellAdapter;
        this.mainActivity = mainActivity;
    }

    @Override
    public EventHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View cell = mInflater.inflate(R.layout.content_event_cell,parent,false);

        EventHolder holder = new EventHolder(cell);
        return holder;
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        holder.layoutForEvent(mDataSet.get(position),holder);
    }

    @Override
    public int getItemCount() {
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
        private final View ui_deleteLayout;
        private final CardView ui_cardView;
        private final SwipeRevealLayout ui_swipe_layout;

        public EventHolder(View cell) {
            super(cell);
            ui_cell_content_title = (TextView) cell.findViewById(R.id.eventContentTitle);
            ui_cell_contentDuree = (TextView) cell.findViewById(R.id.eventDuree);
            ui_cell_contentPosition = (TextView) cell.findViewById(R.id.eventPosition);
            ui_cell_ContentColor = (TextView) cell.findViewById(R.id.eventContentColor);
            ui_deleteLayout = cell.findViewById(R.id.delete_layout);
            ui_cardView = (CardView) cell.findViewById(R.id.cardView);
            ui_swipe_layout = (SwipeRevealLayout) cell.findViewById(R.id.swipe_layout);
        }


        public void layoutForEvent(Evenement event, final EventHolder holder){
            holder.itemView.setTag(event.getId());

           holder.ui_cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
               //      Toast.makeText(view.getContext(),"cell clicked : "+holder.itemView.getTag(),Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(holder.itemView.getContext(), DetailsEventActivity.class);
                    intent.putExtra("idEvenement",(int)holder.itemView.getTag());

                    mainActivity.startActivity(intent);
                }
            });

            holder.ui_cell_ContentColor.setBackgroundColor(event.getColor());
            holder.ui_cell_content_title.setText(eventViewAdapter.formatTitre(event.getTitre(),event.getDateDebut(),event.getDateFin(),event.getCurrentDate()));
            holder.ui_cell_contentDuree.setText(eventViewAdapter.getDuree(event.getDateDebut(),event.getDateFin(),event.getCurrentDate()));

            if(event.getAdresseRdv() != null) {
                holder.ui_cell_contentPosition.setText(event.getAdresseRdv().getAdresse());
                holder.ui_cell_contentPosition.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_location_on_black_24dp, 0, 0, 0);
            }else{
                holder.ui_cell_contentPosition.setVisibility(View.GONE);
            }


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                holder.ui_deleteLayout.setBackgroundTintList(ColorStateList.valueOf(event.getColor()));
            }
            holder.ui_swipe_layout.setSwipeListener(new SwipeRevealLayout.SimpleSwipeListener(){
                @Override
                public void onOpened(SwipeRevealLayout view) {
                    super.onOpened(view);
                    AlertDialog.Builder alert = new AlertDialog.Builder(holder.itemView.getContext());
                    //alert.setTitle("Alert!!");
                    alert.setMessage("Voulez-vous supprimer définitivement cet évènement ?");
                    alert.setPositiveButton("OUI", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            eventCellAdapter.deleteEvent(Integer.valueOf(holder.itemView.getTag().toString()));
                            dialog.dismiss();

                        }
                    });
                    alert.setNegativeButton("NON", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.ui_swipe_layout.close(true);
                            dialog.dismiss();
                        }
                    });

                    alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            holder.ui_swipe_layout.close(true);
                            dialogInterface.dismiss();
                        }
                    });

                    alert.show();

                }
            });

           


        }


    }


}

