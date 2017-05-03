package fr.amu.univ.smartcalendar.plugins.direction.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by j.Katende 23/04/2017.
 */

public class SmartCalendarDirectionRouteModel implements Parcelable {
    @SerializedName("legs")
    private List<SmartCalendarDirectionLegModel> legs;

    @SerializedName("overview_polyline")
    private SmartCalendarDirectionRoutePolylineModel overview_polyline;

    @SerializedName("warnings")
    private List<String> warning_list;

    protected SmartCalendarDirectionRouteModel(Parcel in){
        //legs = in.readArrayList(SmartCalendarDirectionLegModel.class.getClassLoader());
    }

    public List<SmartCalendarDirectionLegModel> getLegList(){ return  this.legs; }

    public void setLegList(List<SmartCalendarDirectionLegModel> legList){ this.legs = legList; }

    public SmartCalendarDirectionRoutePolylineModel getOverviewPolyline(){ return this.overview_polyline; }

    public void setOverviewPolyline(SmartCalendarDirectionRoutePolylineModel routePolyline){
        this.overview_polyline = routePolyline;
    }

    public List<String> getWarningList(){ return warning_list; }

    public void setWarningList(List<String> wl){ this.warning_list = wl; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeTypedList(legs, flags);
    }

    public static List<LatLng> decodePolyLine(String encodedData){
        ArrayList<LatLng> polyLine = new ArrayList<>();

        int index = 0;
        int length = encodedData.length();
        int latitude = 0, longitude = 0;
        int b, shiftCount, result;

        while(index < length){
            shiftCount = 0;
            result = 0;

            do{
                b = encodedData.charAt(index++) - 63;
                result |= (b & 0x1f) << shiftCount;
                shiftCount += 5;
            }while(b >= 0x20);
            latitude += ((result & 1) != 0 ? - (result >> 1) : (result >> 1));


            shiftCount = 0;
            result = 0;

            do{
                b = encodedData.charAt(index++) - 63;
                result |= (b & 0x1f)  << shiftCount;
                shiftCount += 5;
            }while(b >= 0x20);
            longitude += ((result & 1) != 0 ? -(result >> 1) : (result >> 1));
            //Log.d("DEBUG", latitude + ", " + longitude);

            polyLine.add(new LatLng((double)(latitude/1E5), (double)(longitude/1E5)));
        }

        return polyLine;
    }

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
}
