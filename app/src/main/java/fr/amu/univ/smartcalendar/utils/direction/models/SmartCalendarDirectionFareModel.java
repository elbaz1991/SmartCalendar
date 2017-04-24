package fr.amu.univ.smartcalendar.utils.direction.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * Created by j.Katende on 23/04/2017.
 */

public class SmartCalendarDirectionFareModel implements Parcelable{
    private String currency;

    private String value;

    private String text;

    public SmartCalendarDirectionFareModel(){}

    protected SmartCalendarDirectionFareModel(Parcel in){
        currency = in.readString();
        value = in.readString();
        text = in.readString();
    }

    public String getCurrency(){ return currency; }

    public void setCurrency(String c){ this.currency = c;  }

    public String getValue(){ return value; }

    public void setValue(String v){ this.value = v; }

    public String getText(){ return this.text; }

    public void setText(String t){ this.text = t; }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(currency);
        dest.writeString(value);
        dest.writeString(text);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    public static final Creator<SmartCalendarDirectionFareModel> CREATOR = new Creator<SmartCalendarDirectionFareModel>() {
        @Override
        public SmartCalendarDirectionFareModel createFromParcel(Parcel parcel) {
            return new SmartCalendarDirectionFareModel(parcel);
        }

        @Override
        public SmartCalendarDirectionFareModel[] newArray(int size) {
            return new SmartCalendarDirectionFareModel[size];
        }
    };
}
