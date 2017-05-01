package fr.amu.univ.smartcalendar.plugins.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by j.Katende on 30/04/2017.
 */

public class SmartCalendarWeatherLocationModel implements Parcelable{
    /*@SerializedName("lat")
    private double latitude;

    @SerializedName("lon")
    private double longitude; */

    public SmartCalendarWeatherLocationModel(){}

    protected SmartCalendarWeatherLocationModel(Parcel in){
        /*latitude = in.readDouble();
        longitude = in.readDouble();*/
    }
    /*
        public double getLatitude(){ return this.latitude; }

        public void setLatitude(double lat){ this.latitude = lat; }

        public double getLongitude(){ return this.longitude; }

        public void setLongitude(double lon){ this.longitude = lon; }
    */
    @Override
    public void writeToParcel(Parcel dest, int flags){
        //dest.writeDouble(latitude);
        //dest.writeDouble(longitude);
    }

    @Override
    public int describeContents(){ return 0; }

    public static final Creator<SmartCalendarWeatherLocationModel> CREATOR = new Creator<SmartCalendarWeatherLocationModel>() {
        @Override
        public SmartCalendarWeatherLocationModel createFromParcel(Parcel parcel) {
            return new SmartCalendarWeatherLocationModel(parcel);
        }

        @Override
        public SmartCalendarWeatherLocationModel[] newArray(int size) {
            return new SmartCalendarWeatherLocationModel[size];
        }
    };
}
