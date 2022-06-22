package com.example.demo.model.clingo;

import lombok.Data;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClingoCausalTree {

    String name;
    List<String> listEvent;
    List<NodeEvent> nodeEventList;
    List<EdgeEvent> edgeEvents;

    public ClingoCausalTree(String name) {
        this.name = name;
        listEvent = new ArrayList<>();
        nodeEventList = new ArrayList<>();
        edgeEvents = new ArrayList<>();
    }

    public NodeEvent findOrAddNodeEvent(String event) {
        if (!listEvent.contains(event)) {
            listEvent.add(event);
            List<String> ls = ClingoCausal.findCompleteCommande(event, ',');
            String color = switch (ls.get(0)) {
                case "switch" -> "pink";
                case "on" -> "lightgrey";
                case "crash" -> "red";
                case "run" -> "grey";
                case "push" -> "orange";
                case "alive" -> "lightgreen";
                default -> null;
            };
            NodeEvent nodeEvent = new NodeEvent(event, 0, 0, "event", event, color);
            nodeEventList.add(nodeEvent);
            return nodeEvent;
        }
        for (int i = 0; i < nodeEventList.size(); i++) {
            if (nodeEventList.get(i).getId().equals(event)) {
                return nodeEventList.get(i);
            }
        }
        return null;
    }

    public void addEvent(String event1, String event2, String connectType) {
        findOrAddNodeEvent(event1);
        findOrAddNodeEvent(event2);
        EdgeEvent edgeEvent = new EdgeEvent(event1, event2, connectType);
        edgeEvents.add(edgeEvent);
    }

    public String toString() {
        String out = name + ":\n";
        out += nodeEventList + "\n";
        out += edgeEvents + "\n";
        return out;
    }
}
