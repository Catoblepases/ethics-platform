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
    Map<Integer, List<ClingoAction>> actionByTime;

    public ClingoSimulation(String name) {
        actionByTime = new HashMap<>();
        this.name=name;
    }

    public void addActionByTime(int t, String action, String actionType) {
        ClingoAction clingoAction = new ClingoAction(actionType, action,t);
        if (actionByTime.containsKey(t)) {
            actionByTime.get(t).add(clingoAction);
        } else {
            List<ClingoAction> ls = new ArrayList<>();
            ls.add(clingoAction);
            actionByTime.put(t, ls);
        }
    }

    public String toString() {
        return name + actionByTime.toString();
    }
}
