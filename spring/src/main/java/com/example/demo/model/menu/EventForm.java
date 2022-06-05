package com.example.demo.model.menu;

import com.example.demo.model.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class EventForm {
    List<EventItem> eventItemList;

    public EventForm(Generator generator) {
        this();
        for (Switch sw : generator.switchs) {
            EventItem eventItem = new EventItem(sw.isValid(), "switch", sw.toString());
            eventItemList.add(eventItem);
        }
        for (Bridge bridge : generator.bridges) {
            if (bridge.getGroup() != null) {
                EventItem eventItem = new EventItem(bridge.getPush(), "bridge", bridge.toString());
                eventItemList.add(eventItem);
            }
        }
    }


    public EventForm() {
        eventItemList = new ArrayList<>();
    }
}
