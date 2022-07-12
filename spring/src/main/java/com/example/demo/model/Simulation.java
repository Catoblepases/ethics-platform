package com.example.demo.model;

import com.example.demo.model.clingo.ClingoAction;
import com.example.demo.model.menu.EventItem;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor

/*Lq classe simulation*/
public class Simulation {
    List<EventItem> actions;
    String name;

    public Simulation(String name) {
        this.name = name;
        this.actions = new ArrayList<>();
    }

    /*Ajouter une action*/
    public void addAction(String action, int time) {
        actions.add(new EventItem(true, "", action, time));
    }

    /*mise à jour une simulation*/
    public void update(Simulation simulation) {
        this.name = simulation.getName();
        this.actions = simulation.getActions();
    }

    /*Supprimer les action non activé */
    public void normalize() {
        EventItem[] le = actions.toArray(new EventItem[actions.size()]);
        for (EventItem action : le) {
            if (action.getValid() == false) {
                actions.remove(action);
            }
        }
    }
}
