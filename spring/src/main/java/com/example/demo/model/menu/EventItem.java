package com.example.demo.model.menu;

import lombok.Data;
import lombok.NoArgsConstructor;

/*Le classe EvenItem, l'information de evenement*/
@Data
@NoArgsConstructor
public class EventItem {

    //Activé ou non
    Boolean valid;
    //type de evenement
    String eventType;
    String eventDescription;
    // moment d'activé
    int time;

    public EventItem(Boolean valid, String eventType, String eventDescription, int time) {
        this.valid = valid;
        this.eventType = eventType;
        this.eventDescription = eventDescription;
        this.time = time;
    }
}
