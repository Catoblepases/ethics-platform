package com.example.demo.model;

import com.example.demo.model.clingo.ClingoAction;
import com.example.demo.model.menu.EventItem;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class Simulation {
    List<EventItem> actions;
    String name;

    public Simulation(String name) {
        this.name = name;
        this.actions = new ArrayList<>();
    }

    public void addAction(String action, int time) {
        actions.add(new EventItem(true, "", action, time));
    }

    public void update(Simulation simulation) {
        this.name = simulation.getName();
        this.actions = simulation.getActions();
    }
}
