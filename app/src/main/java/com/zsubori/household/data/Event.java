package com.zsubori.household.data;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    private String eventName;
    private Date eventDate;
    private String eventPlace;
    private String eventComment;
    private ArrayList<User> participants;

    public Event() {}

    public Event(String eventName, Date eventDate, String eventPlace, String eventComment, ArrayList<User> participants) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventPlace = eventPlace;
        this.eventComment = eventComment;
        this.participants = participants;
    }

    public Event(String eventName, Date eventDate, String eventComment) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventPlace = null;
        this.eventComment = eventComment;
        this.participants = null;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventPlace() {
        return eventPlace;
    }

    public void setEventPlace(String eventPlace) {
        this.eventPlace = eventPlace;
    }

    public String getEventComment() {
        return eventComment;
    }

    public void setEventComment(String eventComment) {
        this.eventComment = eventComment;
    }

    public ArrayList<User> getParticipants() {
        return participants;
    }

    public void setParticipants(ArrayList<User> participants) {
        this.participants = participants;
    }
}
