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
            EventItem eventItem = new EventItem("switch", sw.toString());
            eventItemList.add(eventItem);
        }
        for (Bridge bridge : generator.bridges) {
            EventItem eventItem = new EventItem("bridge", bridge.toString());
            eventItemList.add(eventItem);
        }
        for (Group group : generator.groups) {
            EventItem eventItem = new EventItem("group", group.toString());
            eventItemList.add(eventItem);
        }
    }


    public EventForm() {
        eventItemList = new ArrayList<>();
    }
}
