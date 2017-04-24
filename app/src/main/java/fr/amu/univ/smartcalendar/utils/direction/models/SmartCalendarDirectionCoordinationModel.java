package fr.amu.univ.smartcalendar.utils.direction.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by j.Katende on 23/04/2017.
 */

public class SmartCalendarDirectionCoordinationModel implements Parcelable {
    @SerializedName("lat")
    private double latitude;

    @SerializedName("lng")
    private double longitude;

    public SmartCalendarDirectionCoordinationModel(){}

    protected SmartCalendarDirectionCoordinationModel(Parcel in){
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public double getLatitude(){ return this.latitude; }

    public void setLatitude(double lat){ this.latitude = lat; }

    public double getLongitude(){ return this.longitude; }

    public void setLongitude(double lng){ this.longitude = lng; }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    public static final Creator<SmartCalendarDirectionCoordinationModel> CREATOR = new Creator<SmartCalendarDirectionCoordinationModel>() {
        @Override
        public SmartCalendarDirectionCoordinationModel createFromParcel(Parcel parcel) {
            return new SmartCalendarDirectionCoordinationModel(parcel);
        }

        @Override
        public SmartCalendarDirectionCoordinationModel[] newArray(int size) {
            return new SmartCalendarDirectionCoordinationModel[size];
        }
    };
}
