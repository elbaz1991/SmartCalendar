package fr.amu.univ.smartcalendar.plugins.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by j.Katende on 02/05/2017.
 */

public class SmartCalendarWeatherDetailModel implements Parcelable {
    @SerializedName("id")
    private int id;

    @SerializedName("main")
    private String weather;

    @SerializedName("description")
    private String description;

    @SerializedName("icon")
    private String icon;

    public SmartCalendarWeatherDetailModel(){}

    protected SmartCalendarWeatherDetailModel(Parcel in){
        id = in.readInt();
        weather = in.readString();
        description = in.readString();
        icon = in.readString();
    }

    public int getId(){ return this.id; }

    public void setId(int i){ this.id = i; }

    public String getWeather(){ return this.weather; }

    public void setWeather(String w){ this.weather = w; }

    public String getDescription(){ return this.description; }

    public void set(String desc){ this.description = desc; }

    public String getIcon(){ return this.icon; }

    public void setIcon(String ic){ this.icon = ic; }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeInt(id);
        dest.writeString(weather);
        dest.writeString(description);
        dest.writeString(icon);
    }

    @Override
    public int describeContents(){ return 0; }

    public static final Creator<SmartCalendarWeatherDetailModel> CREATOR = new Creator<SmartCalendarWeatherDetailModel>() {
        @Override
        public SmartCalendarWeatherDetailModel createFromParcel(Parcel parcel) {
            return new SmartCalendarWeatherDetailModel(parcel);
        }

        @Override
        public SmartCalendarWeatherDetailModel[] newArray(int size) {
            return new SmartCalendarWeatherDetailModel[size];
        }
    };
}
