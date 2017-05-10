package fr.amu.univ.smartcalendar.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

    public SmartCalendarParticipantModel getParticipantsByEventId(int eventId){
        SmartCalendarParticipantModel participant = new SmartCalendarParticipantModel();
        String query = "SELECT " + COL_PARTICIPANT + " FROM " + TABLE_NAME + " WHERE " + COL_EVENT_ID + " = " + eventId;

        open();
        Cursor cursor = db.rawQuery(query, null); //query(true, TABLE_NAME, )

        if(cursor != null && cursor.getCount() > 0){
            List<Integer> participants = new ArrayList<>();
            cursor.moveToFirst();
            do {
                //Log.d("DEBUG", "test" + cursor.getInt(cursor.getColumnIndex(COL_PARTICIPANT)));
                participants.add(cursor.getInt(cursor.getColumnIndex(COL_PARTICIPANT)));
            }while(cursor.moveToNext());
            participant.setParticipants(participants);
        }
        close();
        return participant;
    }
}
