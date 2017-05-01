package fr.amu.univ.smartcalendar.plugins.direction.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by j.Katende on 25/04/2017.
 */

public class SmartCalendarDirectionInfoModel implements Parcelable {
    private String text;

    private String value;

    public SmartCalendarDirectionInfoModel(){}

    protected SmartCalendarDirectionInfoModel(Parcel in){
        text = in.readString();
        value = in.readString();
    }

    public String getText(){ return this.text; }

    public void setText(String t){ this.text = t; }

    public String getValue(){ return this.value; }

    public void setValue(String v){ this.value = v; }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(text);
        dest.writeString(value);
    }

    @Override
    public int describeContents(){ return  0; }

    public static final Creator<SmartCalendarDirectionInfoModel> CREATOR = new Creator<SmartCalendarDirectionInfoModel>() {
        @Override
        public SmartCalendarDirectionInfoModel createFromParcel(Parcel parcel) {
            return new SmartCalendarDirectionInfoModel(parcel);
        }

        @Override
        public SmartCalendarDirectionInfoModel[] newArray(int size) {
            return new SmartCalendarDirectionInfoModel[size];
        }
    };
}
