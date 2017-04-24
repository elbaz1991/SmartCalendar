package fr.amu.univ.smartcalendar.utils.direction.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * Created by j.Katende 23/04/2017.
 */

public class SmartCalendarDirectionRouteModel implements Parcelable{
    @SerializedName("bounds")
    private SmartCalendarDirectionBoundModel bound;
    private String copy_rights;

    @SerializedName("legs")
    private List<SmartCalendarDirectionLegModel> legs;

    @SerializedName("overview_polyline")
    private SmartCalendarDirectionRoutePolylineModel overviewPolyline;
    private String summary;
    private SmartCalendarDirectionFareModel fare;

    @SerializedName("warnings")
    private List<String> warning_list;

    public SmartCalendarDirectionRouteModel(){}

    protected SmartCalendarDirectionRouteModel(Parcel in){
        bound = in.readParcelable(SmartCalendarDirectionBoundModel.class.getClassLoader());
        copy_rights = in.readString();
        summary = in.readString();
        fare = in.readParcelable(SmartCalendarDirectionFareModel.class.getClassLoader());
        warning_list = in.createStringArrayList();
    }

    public SmartCalendarDirectionBoundModel getBound(){ return bound; }

    public void setBound(SmartCalendarDirectionBoundModel boundModel){
        this.bound = boundModel;
    }

    public String getCopyRights(){
        return this.copy_rights;
    }

    public void setCopyRights(String copyRights){
        this.copy_rights = copyRights;
    }

    public List<SmartCalendarDirectionLegModel> getLegList(){ return  this.legs; }

    public void setLegLst(List<SmartCalendarDirectionLegModel> legList){ this.legs = legList; }

    public SmartCalendarDirectionRoutePolylineModel getOverviewPolyline(){ return this.overviewPolyline; }

    public void setOverviewPolyline(SmartCalendarDirectionRoutePolylineModel routePolyline){ this.overviewPolyline = routePolyline; }

    public String getSummary(){ return summary; }

    public void setSummary(String s){ this.summary = s; }

    public SmartCalendarDirectionFareModel getFare(){ return  this.fare; }

    public void setFare(SmartCalendarDirectionFareModel f){
        this.fare = f;
    }

    public List<String> getWarningList(){ return warning_list; }

    public void setWarningList(List<String> wl){ this.warning_list = wl; }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(bound, flags);
        parcel.writeString(copy_rights);
        parcel.writeString(summary);
        parcel.writeParcelable(this.fare, flags);
        parcel.writeStringList(this.warning_list);
    }
}
