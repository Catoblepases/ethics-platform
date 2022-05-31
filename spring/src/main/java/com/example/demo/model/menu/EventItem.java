package com.example.demo.model.menu;

import lombok.Data;

@Data
public class EventItem {
    String eventType;
    String eventDescription;

    public EventItem(String eventType, String eventDescription) {
        this.eventType = eventType;
        this.eventDescription = eventDescription;
    }
}
