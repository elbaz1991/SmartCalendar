package fr.amu.univ.smartcalendar.plugins.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * Created by j.Katende on 30/04/2017.
 */

public class SmartCalendarWeatherModel implements Parcelable {
    @SerializedName("coord")
    private SmartCalendarWeatherLocationModel location;

    /*@SerializedName("sys")
    private SmartCalendarWeatherParamsModel system_params;
*/
    @SerializedName("weather")
    private List<SmartCalendarWeatherDetailModel> weathers;

    @SerializedName("main")
    private SmartCalendarWeatherDataModel data;

    /*
    @SerializedName("wind")
    private SmartCalendarWeatherWindModel wind;

    @SerializedName("rain")
    private SmartCalendarWeatherRainModel rains_info;

    @SerializedName("clouds")
    private SmartCalendarWeatherCloudsModel cloud_info;

    @SerializedName("dt")
    private long computation_time;

    @SerializedName("id")
    private int city_id;*/

    @SerializedName("base")
    private String base;

    @SerializedName("name")
    private String city_name;

    @SerializedName("cod")
    private int code;

    public SmartCalendarWeatherModel(){}

    protected SmartCalendarWeatherModel(Parcel in){
        location = in.readParcelable(SmartCalendarWeatherLocationModel.class.getClassLoader());
        weathers = in.readArrayList(SmartCalendarWeatherDetailModel.class.getClassLoader());
        data = in.readParcelable(SmartCalendarWeatherDataModel.class.getClassLoader());
        city_name = in.readString();
        code = in.readInt();
    }

    public SmartCalendarWeatherLocationModel getLocation(){
        return this.location;
    }

    public void setLocation(SmartCalendarWeatherLocationModel loc){ this.location = loc; }

    public SmartCalendarWeatherDataModel getData(){ return  this.data; }

    public void setData(SmartCalendarWeatherDataModel d){
        this.data = d;
    }

    public String getBase(){ return this.base; }

    public void setBase(String b){ this.base = b; }

    public String getCityName(){ return this.city_name; }

    public void setCityName(String name){ this.city_name = name;}

    public int getCode(){ return this.code; }

    public void setCode(int c){ this.code = c; }

    public List<SmartCalendarWeatherDetailModel> getWeathers(){ return this.weathers; }

    public void setWeathers(List<SmartCalendarWeatherDetailModel> w){ this.weathers = w;}

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeParcelable(location, flags);
        dest.writeTypedList(weathers);
        dest.writeParcelable(data, flags);
        dest.writeString(city_name);
        dest.writeInt(code);
    }

    @Override
    public int describeContents(){ return  0; }

    public static final Creator<SmartCalendarWeatherModel> CREATOR = new Creator<SmartCalendarWeatherModel>() {
        @Override
        public SmartCalendarWeatherModel createFromParcel(Parcel parcel) {
            return new SmartCalendarWeatherModel(parcel);
        }

        @Override
        public SmartCalendarWeatherModel[] newArray(int size) {
            return new SmartCalendarWeatherModel[size];
        }
    };
}
