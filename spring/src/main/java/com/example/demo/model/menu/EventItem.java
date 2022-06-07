package com.example.demo.model.menu;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EventItem {

    Boolean valid;
    String eventType;
    String eventDescription;
    int time;

    public EventItem(Boolean valid, String eventType, String eventDescription, int time) {
        this.valid = valid;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.time = time;
    }
}
