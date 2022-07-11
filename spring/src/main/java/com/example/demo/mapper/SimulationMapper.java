package com.example.demo.mapper;

import com.example.demo.model.Event;
import com.example.demo.model.Generator;
import com.example.demo.model.Simulation;
import com.example.demo.model.menu.EventItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*Le classe SimulationMapper represente les simulation */
public class SimulationMapper {
    List<String> nameAndEvents;
    List<Map<String, String>> simulations;

    public SimulationMapper(Generator generator) {
        nameAndEvents = new ArrayList<>();
        simulations = new ArrayList<>();
        nameAndEvents.add("name");
        for (int i = 0; i < generator.bridges.size(); i++) {
            if (generator.bridges.get(i).getGroup()!=null){
                nameAndEvents.add(generator.bridges.get(i).getEventInfo());
            }
        }
        for (int i = 0; i < generator.switchs.size(); i++) {
            nameAndEvents.add(generator.switchs.get(i).getEventInfo());
        }
        List<Simulation> sims = generator.simulations;
        for (int i = 0; i < sims.size(); i++) {
            Map<String, String> map = new HashMap();
            Simulation sim = sims.get(i);
            map.put(nameAndEvents.get(0), sim.getName());
            for (int j = 0; j < sim.getActions().size(); j++) {
                EventItem e = sim.getActions().get(j);
                if (nameAndEvents.contains(e.getEventDescription()) && e.getValid()) {
                    map.put(e.getEventDescription(), "O(" + e.getTime() + ")");
                }
            }
            for (int k = 1; k < nameAndEvents.size(); k++) {
                if (!map.containsKey(nameAndEvents.get(k))) {
                    map.put(nameAndEvents.get(k), "X");
                }
            }
            simulations.add(map);
        }
        System.out.println(simulations);
    }

    public List<String> getNameAndEvents() {
        return nameAndEvents;
    }

    public void setNameAndEvents(List<String> nameAndEvents) {
        this.nameAndEvents = nameAndEvents;
    }

    public List<Map<String, String>> getSimulations() {
        return simulations;
    }

    public void setSimulations(List<Map<String, String>> simulations) {
        this.simulations = simulations;
    }
}
