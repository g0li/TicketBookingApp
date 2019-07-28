package com.lilliemountain.ticketbookingapp.model;

import com.google.firebase.Timestamp;

public class Ticket {
    Timestamp timestamp;
    String email;
    Event event;

    public Ticket() {
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Ticket(Timestamp timestamp, String email, Event event) {
        this.timestamp = timestamp;
        this.email = email;
        this.event = event;
    }
}
