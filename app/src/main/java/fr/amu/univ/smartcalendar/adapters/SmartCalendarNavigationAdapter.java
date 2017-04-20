package fr.amu.univ.smartcalendar.adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import fr.amu.univ.smartcalendar.R;

/**
 *
 * Created by j.Katende on 19/04/2017.
 */

public class SmartCalendarNavigationAdapter extends BaseAdapter {
    private Context context;
    public SmartCalendarNavigationAdapter(Context base){
        context = base;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.navigation_layout, viewGroup, false);
        return null;
    }
}
