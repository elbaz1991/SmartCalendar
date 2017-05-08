package fr.amu.univ.smartcalendar.model.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * Created by j.Katende on 07/05/2017.
 */

public class SmartCalendarParticipantModel {
    private int event_id;

    private List<Integer> participants;

    public SmartCalendarParticipantModel(int eventId){

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
}
