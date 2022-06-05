package com.example.demo.model.menu;

import lombok.Data;

@Data
public class EventItem {

    Boolean valid;
    String eventType;
    String eventDescription;

    public EventItem(Boolean valid, String eventType, String eventDescription) {
        this.valid = valid;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
    }
}
