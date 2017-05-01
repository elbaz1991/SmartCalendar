package fr.amu.univ.smartcalendar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import fr.amu.univ.smartcalendar.R;

/**
 *
 * Created by j.Katende on 26/04/2017.
 */

public class SmartCalendarWeatherAdapter extends BaseAdapter {
    private Context context;

    private ArrayList<HashMap<String, String>> renderData;

    private static LayoutInflater inflater = null;

    public SmartCalendarWeatherAdapter(Context base, ArrayList<HashMap<String, String>> data){
        context = base;
        renderData = data;
        inflater = (LayoutInflater)base.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount(){ return renderData.size(); }

    @Override
    public long getItemId(int index){ return 0; }

    @Override
    public Object getItem(int index){ return renderData.get(index); }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup){
        view = LayoutInflater.from(context).inflate(R.layout.weather_wrapper, viewGroup, false);
        return view;
    }
}
