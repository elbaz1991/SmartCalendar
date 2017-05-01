package fr.amu.univ.smartcalendar.ui.extension;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 *
 * Created by j.Katende on 01/05/2017.
 */

public class SmartCalendarListView extends ListView {
    private boolean expanded = false;

    public SmartCalendarListView(Context context){
        super(context);
    }

    public SmartCalendarListView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public SmartCalendarListView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }

    public void setExpanded(boolean expand){ this.expanded = expand; }

    public boolean isExpanded(){ return this.expanded; }

    @Override
    public void onMeasure(int widthMeasureSpec, int heigthMeasureSpec){
        if(isExpanded()){
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, heigthMeasureSpec);
            ViewGroup.LayoutParams params = getLayoutParams();
            params.height = getMeasuredHeight();
        }else{
            super.onMeasure(widthMeasureSpec, heigthMeasureSpec);
        }
    }
}
