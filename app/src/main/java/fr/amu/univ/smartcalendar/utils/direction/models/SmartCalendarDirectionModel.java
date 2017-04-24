package fr.amu.univ.smartcalendar.utils.direction.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import fr.amu.univ.smartcalendar.utils.direction.constants.SmartCalendarDirectionRequest;

/**
 *
 * Created by j.Katende on 22/04/2017.
 */

public class SmartCalendarDirectionModel implements Parcelable {
    @SerializedName("geocoded_waypoints")
    private List<SmartCalendarDirectionGeoCodedWayPointModel> geo_coded_way_point_list;

    @SerializedName("routes")
    private List<SmartCalendarDirectionRouteModel> map_routes;

    private String status;

    @SerializedName("error_message")
    private String error_message;

    public SmartCalendarDirectionModel(){}

    public SmartCalendarDirectionModel(Parcel in){
        status = in.readString();
        error_message = in.readString();
    }

    public void setGeoCodedWayPointList(List geoCodedWayPointList){
        this.geo_coded_way_point_list = geoCodedWayPointList;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
