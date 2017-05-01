package fr.amu.univ.smartcalendar.plugins.direction.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import fr.amu.univ.smartcalendar.plugins.direction.constant.SmartCalendarDirectionRequest;

/**
 *
 * Created by j.Katende on 30/04/2017.
 */

public class SmartCalendarDirectionModel implements Parcelable {
    @SerializedName("routes")
    private List<SmartCalendarDirectionRouteModel> map_routes;

    private String status;

    public SmartCalendarDirectionModel(){}

    public SmartCalendarDirectionModel(Parcel in){
        status = in.readString();
    }

    public boolean isOk(){
        return SmartCalendarDirectionRequest.DATA_FOUNDED.equals(status);
    }

    public void setMapRoutes(List<SmartCalendarDirectionRouteModel> routes){
        this.map_routes = routes;
    }

    public List<SmartCalendarDirectionRouteModel> getRouteList(){
        return map_routes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(status);
    }

    public static final Creator<SmartCalendarDirectionModel> CREATOR = new Creator<SmartCalendarDirectionModel>() {
        @Override
        public SmartCalendarDirectionModel createFromParcel(Parcel parcel) {
            return new SmartCalendarDirectionModel(parcel);
        }

        @Override
        public SmartCalendarDirectionModel[] newArray(int size){
            return new SmartCalendarDirectionModel[0];
        }
    };
}
