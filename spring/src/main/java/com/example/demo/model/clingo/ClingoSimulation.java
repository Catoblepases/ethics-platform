package com.example.demo.model.clingo;

import com.example.demo.model.Generator;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*le classe ClingoSimulation contient une liste de action*/
@Data
public class ClingoSimulation {

    String name;
    List<List<G6Info>> actionByTime;

    public ClingoSimulation(String name) {
        actionByTime = new ArrayList<>();
        this.name = name;
    }

    public ClingoSimulation() {
    }

    /*Ajouter une action d'un moment*/
    public void addActionByTime(int t, String action, String actionType, Generator generator) {
        G6Info clingoG6Info = new ClingoAction(actionType, action, t).toG6Graph(generator);
        if (clingoG6Info == null) {
            return;
        }

        while (actionByTime.size() <= t) {
            List<G6Info> ls = new ArrayList<>();
            actionByTime.add(ls);
        }
        actionByTime.get(t).add(clingoG6Info);
    }

    public String toString() {
        return name + actionByTime.toString();
    }
}
