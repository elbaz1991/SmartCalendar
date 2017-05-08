package fr.amu.univ.smartcalendar.model.dao;

import android.content.ContentValues;
import android.content.Context;

import fr.amu.univ.smartcalendar.model.entity.SmartCalendarParticipantModel;

/**
 *
 * Created by j.Katende on 04/05/2017.
 */

public class ParticipantDAO extends DatabaseDAO {
    public static final String TABLE_NAME = "Participants";
//    public static final String
    public static final String COL_EVENT_ID = "Event_id";
    public static final String COL_PARTICIPANT = "Participant_id";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            COL_EVENT_ID + " INTEGER, " + COL_PARTICIPANT + " INTEGER, " +
            " PRIMARY KEY (" + COL_EVENT_ID + ", " + COL_PARTICIPANT + ") )";

    public static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;

    public ParticipantDAO(Context context){
        super(context);
    }

    public boolean insert(SmartCalendarParticipantModel participant){
        //open();
        ContentValues values = new ContentValues();

        int eventId = participant.getEventId();
        long result = -1;
        for(int participantId : participant.getParticipants()) {
            values.put(COL_EVENT_ID, eventId);
            values.put(COL_PARTICIPANT, participantId);
            result = getDb().insert(TABLE_NAME, null, values);
            values.clear();
        }
        close();
        return (result > -1);
    }

    public  boolean update(SmartCalendarParticipantModel participant){
        return true;
    }

    public boolean delete(){
        return true;
    }

    //public static List<>
}
