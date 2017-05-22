package fr.amu.univ.smartcalendar.plugins.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by j.Katende on 30/04/2017.
 */

public class SmartCalendarWeatherDataModel implements Parcelable {
    @SerializedName("temp")
    private float temperature;

    @SerializedName("pressure")
    private float humidity;

    @SerializedName("temp_min")
    private float min_temperature;

    @SerializedName("temp_max")
    private float max_temperature;

    public SmartCalendarWeatherDataModel(){}

    protected SmartCalendarWeatherDataModel(Parcel in){
        temperature = in.readFloat();
        humidity = in.readFloat();
        min_temperature = in.readFloat();
        max_temperature = in.readFloat();
    }


    public float getTemperature(){ return this.temperature; }

    public void setTemperature(float temp){ this.temperature = temp; }

    public float getHumidity(){ return this.humidity; }

    public void setHumidity(float h){ this.humidity = h; }

    public float getMinTemperature(){ return this.min_temperature; }

    public void setMinTemperature(float minTemp){ this.min_temperature = minTemp; }

    public float getMaxTemperature(){ return this.max_temperature; }

    public void setMaxTemperature(float maxTemp){ this.max_temperature = maxTemp; }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeFloat(this.temperature);
        dest.writeFloat(this.humidity);
        dest.writeFloat(this.min_temperature);
        dest.writeFloat(this.max_temperature);
    }

    @Override
    public int describeContents(){ return 0; }

    public Creator<SmartCalendarWeatherDataModel> CREATOR = new Creator<SmartCalendarWeatherDataModel>() {
        @Override
        public SmartCalendarWeatherDataModel createFromParcel(Parcel parcel) {
            return new SmartCalendarWeatherDataModel(parcel);
        }

        @Override
        public SmartCalendarWeatherDataModel[] newArray(int size) {
            return new SmartCalendarWeatherDataModel[size];
        }
    };

}
