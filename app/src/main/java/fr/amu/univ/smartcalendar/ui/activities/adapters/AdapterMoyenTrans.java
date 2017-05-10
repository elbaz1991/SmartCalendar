package fr.amu.univ.smartcalendar.ui.activities.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

import fr.amu.univ.smartcalendar.R;

/**
 * Created by elbaz on 05/05/2017.
 */

public class AdapterMoyenTrans extends ArrayAdapter<String> {
    Context context;
    static String[] moyensDetrans = {"  Choisissez votre moyen de transport","  Voiture","  Vélo" , "  Transport commun","  à pieds"};
    int [] imagesMoyenDeTrans = {0, R.drawable.ic_voiture,R.drawable.ic_velo,R.drawable.ic_transportcommun,R.drawable.ic_apieds} ;

   // final static HashMap<Integer,String> moyensTrans = new HashMap<>();

    public AdapterMoyenTrans(Context context){
        super(context,R.layout.model,moyensDetrans);
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.model,null);
        }

        TextView textMoyenDeTrans = (TextView) convertView.findViewById(R.id.textViewModel);
        ImageView imageMoyenDeTrans = (ImageView) convertView.findViewById(R.id.imageViewModel);

        textMoyenDeTrans.setText(moyensDetrans[position]);
        imageMoyenDeTrans.setImageResource(imagesMoyenDeTrans[position]);


        return convertView;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.model,null);
        }

        TextView textMoyenDeTrans = (TextView) convertView.findViewById(R.id.textViewModel);
        ImageView imageMoyenDeTrans = (ImageView) convertView.findViewById(R.id.imageViewModel);

        textMoyenDeTrans.setText(moyensDetrans[position]);
        imageMoyenDeTrans.setImageResource(imagesMoyenDeTrans[position]);


        return convertView;
    }
}
