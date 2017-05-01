package fr.amu.univ.smartcalendar.plugins.direction.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * Created by j.Katende 23/04/2017.
 */

public class SmartCalendarDirectionRouteModel implements Parcelable {
    @SerializedName("legs")
    private List<SmartCalendarDirectionLegModel> legs;

    protected SmartCalendarDirectionRouteModel(Parcel in){
        //legs = in.createA
    }

    public List<SmartCalendarDirectionLegModel> getLegList(){ return  this.legs; }

    public void setLegList(List<SmartCalendarDirectionLegModel> legList){ this.legs = legList; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

    }

    public static final Creator<SmartCalendarDirectionRouteModel> CREATOR = new Creator<SmartCalendarDirectionRouteModel>() {
        @Override
        public SmartCalendarDirectionRouteModel createFromParcel(Parcel in) {
            return new SmartCalendarDirectionRouteModel(in);
        }

        @Override
        public SmartCalendarDirectionRouteModel[] newArray(int size) {
            return new SmartCalendarDirectionRouteModel[size];
        }
    };
}
