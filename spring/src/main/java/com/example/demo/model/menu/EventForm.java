package com.example.demo.model.menu;

import com.example.demo.model.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/*Le classe EvenForm,une liste de evenement*/
@Data
public class EventForm {
    List<EventItem> eventItemList;

    public EventForm(Generator generator) {
        this();
        for (Switch sw : generator.switchs) {
            EventItem eventItem = new EventItem(false, "switch", sw.getEventInfo(), sw.getTime());
            eventItemList.add(eventItem);
        }
        for (Bridge bridge : generator.bridges) {
            if (bridge.getGroup() != null) {
                EventItem eventItem = new EventItem(false, "bridge", bridge.getEventInfo(), bridge.getTime());
                eventItemList.add(eventItem);
            }
        }
    }


    public EventForm() {
        eventItemList = new ArrayList<>();
    }
}
