package fr.amu.univ.smartcalendar.plugins.direction.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * Created by j.Katende on 23/04/2017.
 */

public class SmartCalendarDirectionLegModel {
    @SerializedName("distance")
    private SmartCalendarDirectionInfoModel distance;

    private SmartCalendarDirectionInfoModel duration;

    public SmartCalendarDirectionInfoModel getDistance(){
        return this.distance;
    }

    public void setDistance(SmartCalendarDirectionInfoModel dist){
        this.distance =  dist;
    }

    public SmartCalendarDirectionInfoModel getDuration(){ return this.duration; }

}
