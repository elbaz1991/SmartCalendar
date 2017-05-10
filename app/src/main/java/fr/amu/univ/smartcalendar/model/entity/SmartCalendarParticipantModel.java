package fr.amu.univ.smartcalendar.model.entity;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.amu.univ.smartcalendar.model.dao.ParticipantDAO;

/**
 *
 * Created by j.Katende on 07/05/2017.
 */

public class SmartCalendarParticipantModel {
    private int event_id;

    private List<Integer> participants;

    private Context context;

    public SmartCalendarParticipantModel(){}

    public SmartCalendarParticipantModel(Context base, int eventId){
        context = base;

        if(eventId > 0){
            ParticipantDAO participantDAO = new ParticipantDAO(this.context);
            SmartCalendarParticipantModel participant = participantDAO.getParticipantsByEventId(eventId);
            this.event_id = participant.getEventId();
            this.participants = participant.getParticipants();
        }
    }

    public int getEventId(){ return this.event_id; }

    public void setEventId(int eventId){
        this.event_id = eventId;
    }

    public List<Integer> getParticipants(){ return this.participants; }

    public void addParticipant(int participantId){
        if(participants == null){
            participants = new ArrayList<>();
            Collections.synchronizedList(participants);
            participants.add(participantId);
        }else if(!participants.contains(participantId)){
            participants.add(participantId);
        }
    }

    public void setParticipants(List<Integer> guests){
        this.participants = guests;
    }
}
