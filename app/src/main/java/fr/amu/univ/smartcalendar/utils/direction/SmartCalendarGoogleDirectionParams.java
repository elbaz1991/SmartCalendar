package fr.amu.univ.smartcalendar.utils.direction;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import fr.amu.univ.smartcalendar.utils.direction.constants.SmartCalendarDirectionAvoidType;
import fr.amu.univ.smartcalendar.utils.direction.constants.SmartCalendarTransportMode;

/**
 *
 * Created by j.Katende on 22/04/2017.
 */

public class SmartCalendarGoogleDirectionParams implements Parcelable {
    private LatLng origin;

    private LatLng destination;

    private String transport_mode = SmartCalendarTransportMode.DRIVING;

    private String departure_mode = "";

    private String departure_time = "";

    private String transit_mode = "";

    private boolean alternatives = false;

    private String api_key;

    private String unit = "metric";

    private String avoid = SmartCalendarDirectionAvoidType.ALLOW_EVERY_WAYS;

    private String language ="fr";

    private List<LatLng> way_points;

    private boolean optimize_way_points;

    public SmartCalendarGoogleDirectionParams(){}

    public SmartCalendarGoogleDirectionParams(Parcel in){
        origin = in.readParcelable(LatLng.class.getClassLoader());
        destination = in.readParcelable(LatLng.class.getClassLoader());
        transport_mode = in.readString();
        departure_mode = in.readString();
        language = in.readString();
        unit = in.readString();
        avoid = in.readString();
        transit_mode = in.readString();
        alternatives = in.readByte() != 0;
        api_key = in.readString();
        way_points = in.createTypedArrayList(LatLng.CREATOR);
        optimize_way_points = in.readByte() != 0;
    }

    public LatLng getOrigin(){ return origin; }

    public SmartCalendarGoogleDirectionParams setOrigin(LatLng originPosition){
        this.origin = originPosition;
        return this;
    }

    public LatLng getDestination(){ return destination; }

    public SmartCalendarGoogleDirectionParams setDestination(LatLng destinationPosition){
        this.destination = destinationPosition;
        return this;
    }

    public String getTransportMode(){ return this.transport_mode; }

    public void setTransportMode(String transportMode){ this.transit_mode = transportMode; }

    public String getLanguage(){ return language; }

    public void setLanguage(String lang){
        this.language = lang;
    }

    public String getUnit(){ return unit; }

    public void setUnit(String unit){
        this.unit = unit;
    }

    public String getAvoid(){ return this.avoid; }

    public void setAvoid(String avoid){ this.avoid = avoid; }

    public String getTransitMode(){ return this.transit_mode; }

    public void setTransitMode(String transitMode){ this.transit_mode = transitMode; }

    public boolean isAlternatives(){
        return alternatives;
    }

    public void setAlternatives(boolean alternative){
        this.alternatives = alternative;
    }

    public String getApiKey(){ return this.api_key; }

    public SmartCalendarGoogleDirectionParams setApiKey(String apiKey){
        this.api_key = apiKey;
        return this;
    }

    public String getDepartureTime(){ return this.departure_time; }

    public void setDepartureTime(String departureTime){
        this.departure_time = departureTime;
    }

    public List<LatLng> getWayPoints(){ return this.way_points; }

    public void setWayPoints(List<LatLng> wayPoints){ this.way_points = wayPoints; }

    public boolean isOptimizeWayPoints(){ return optimize_way_points; }

    public void setOptimizeWayPoints(boolean optimizeWayPoints){
        this.optimize_way_points = optimizeWayPoints;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeParcelable(origin, flags);
        parcel.writeParcelable(destination, flags);
        parcel.writeString(transport_mode);
        parcel.writeString(departure_mode);
        parcel.writeString(language);
        parcel.writeString(unit);
        parcel.writeString(avoid);
        parcel.writeString(transit_mode);
        parcel.writeByte((byte)(alternatives ? 1 : 0));
        parcel.writeString(api_key);
        parcel.writeTypedList(way_points);
        parcel.writeByte((byte)(optimize_way_points ? 1 : 0));
    }

    public static final Creator<SmartCalendarGoogleDirectionParams> CREATOR = new Creator<SmartCalendarGoogleDirectionParams>() {
        @Override
        public SmartCalendarGoogleDirectionParams createFromParcel(Parcel parcel) {
            return new SmartCalendarGoogleDirectionParams(parcel);
        }

        @Override
        public SmartCalendarGoogleDirectionParams[] newArray(int size) {
            return new SmartCalendarGoogleDirectionParams[size];
        }
    };
}
