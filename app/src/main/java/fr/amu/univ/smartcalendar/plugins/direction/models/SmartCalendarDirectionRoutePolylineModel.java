package fr.amu.univ.smartcalendar.plugins.direction.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 *
 * Created by j.Katende on 23/04/2017.
 */

public class SmartCalendarDirectionRoutePolylineModel  implements Parcelable {
    @SerializedName("points")
    private String raw_point_list;

    public SmartCalendarDirectionRoutePolylineModel(){}

    protected SmartCalendarDirectionRoutePolylineModel(Parcel in){
        raw_point_list = in.readString();
    }

    public String getRawPointList(){ return this.raw_point_list; }

    public void setRawPointList(String rawPointList){ this.raw_point_list = rawPointList; }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.raw_point_list);
    }

    @Override
    public int describeContents(){ return 0; }

    public static final Creator<SmartCalendarDirectionRoutePolylineModel> CREATOR = new Creator<SmartCalendarDirectionRoutePolylineModel>() {
        @Override
        public SmartCalendarDirectionRoutePolylineModel createFromParcel(Parcel parcel) {
            return new SmartCalendarDirectionRoutePolylineModel(parcel);
        }

        @Override
        public SmartCalendarDirectionRoutePolylineModel[] newArray(int size) {
            return new SmartCalendarDirectionRoutePolylineModel[0];
        }
    };
}
