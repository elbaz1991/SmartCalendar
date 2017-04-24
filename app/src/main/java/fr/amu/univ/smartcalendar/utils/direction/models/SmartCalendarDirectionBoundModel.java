package fr.amu.univ.smartcalendar.utils.direction.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by j.Katende on 23/04/2017.
 */

public class SmartCalendarDirectionBoundModel implements Parcelable{
    private SmartCalendarDirectionCoordinationModel north_east;
    private SmartCalendarDirectionCoordinationModel south_west;

    public SmartCalendarDirectionBoundModel(){}

    protected SmartCalendarDirectionBoundModel(Parcel in){
        this.north_east = in.readParcelable(SmartCalendarDirectionCoordinationModel.class.getClassLoader());
        this.south_west = in.readParcelable(SmartCalendarDirectionCoordinationModel.class.getClassLoader());
    }

    public SmartCalendarDirectionCoordinationModel getNorthEastCoordinate(){ return this.north_east; }

    public void setNorthEastCoordinate(SmartCalendarDirectionCoordinationModel northEast){ this.north_east = northEast; }

    public SmartCalendarDirectionCoordinationModel getSouthWestCoordinate(){ return this.south_west; }

    public void setSouthWestCoordinate(SmartCalendarDirectionCoordinationModel southWest){
        this.south_west = southWest;
    }

    @Override
    public int describeContents(){ return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeParcelable(this.north_east, flags);
        dest.writeParcelable(this.south_west, flags);
    }

    public static final Creator<SmartCalendarDirectionBoundModel> CREATOR  = new Creator<SmartCalendarDirectionBoundModel>() {
        @Override
        public SmartCalendarDirectionBoundModel createFromParcel(Parcel parcel) {
            return new SmartCalendarDirectionBoundModel(parcel);
        }

        @Override
        public SmartCalendarDirectionBoundModel[] newArray(int size) {
            return new SmartCalendarDirectionBoundModel[size];
        }
    };
}
