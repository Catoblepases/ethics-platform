package com.example.demo.model.clingo;

import com.example.demo.model.Generator;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ClingoSimulation {

    String name;
    Map<Integer, List<G6Info>> actionByTime;

    public ClingoSimulation(String name) {
        actionByTime = new HashMap<>();
        this.name = name;
    }

    public ClingoSimulation(){}

    public void addActionByTime(int t, String action, String actionType, Generator generator) {
        G6Info clingoG6Info = new ClingoAction(actionType, action, t).toG6Graph(generator);
        if (clingoG6Info == null) {
            return;
        }
        if (actionByTime.containsKey(t)) {
            actionByTime.get(t).add(clingoG6Info);
        } else {
            List<G6Info> ls = new ArrayList<>();
            ls.add(clingoG6Info);
            actionByTime.put(t, ls);
        }
    }

    public String toString() {
        return name + actionByTime.toString();
    }
}
