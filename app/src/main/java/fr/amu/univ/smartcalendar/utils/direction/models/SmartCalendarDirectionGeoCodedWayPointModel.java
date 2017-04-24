package fr.amu.univ.smartcalendar.utils.direction.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 *
 * Created by j.Katende on 22/04/2017.
 */

public class SmartCalendarDirectionGeoCodedWayPointModel implements Parcelable{
    private String status;

    private String place_key;

    private List<String> types;

    public SmartCalendarDirectionGeoCodedWayPointModel(){}

    protected SmartCalendarDirectionGeoCodedWayPointModel(Parcel in) {
    }

    public String getStatus(){ return status; }

    public void setStatus(String status){
        this.status = status;
    }

    public static final Creator<SmartCalendarDirectionGeoCodedWayPointModel> CREATOR = new Creator<SmartCalendarDirectionGeoCodedWayPointModel>() {
        @Override
        public SmartCalendarDirectionGeoCodedWayPointModel createFromParcel(Parcel in) {
            return new SmartCalendarDirectionGeoCodedWayPointModel(in);
        }

        @Override
        public SmartCalendarDirectionGeoCodedWayPointModel[] newArray(int size) {
            return new SmartCalendarDirectionGeoCodedWayPointModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
