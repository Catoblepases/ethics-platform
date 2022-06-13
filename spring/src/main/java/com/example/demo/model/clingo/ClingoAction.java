package com.example.demo.model.clingo;

import com.example.demo.model.Generator;
import lombok.Data;
import org.python.antlr.ast.Str;

import java.util.List;
import java.util.Map;

@Data
public class ClingoAction {
    String action;
    String actionType;
    int time;

    public ClingoAction(String actionType, String action, int time) {
        this.action = action;
        this.actionType = actionType;
        this.time = time;
    }

    public G6Info toG6Graph(Generator generator) {
        List<String> ls = ClingoCausal.findCompleteCommande(action, ',');
        return switch (ls.get(0)) {
            case "on" -> G6Info.createMove(ls.get(2), ls.get(1), time, generator);
            case "run" -> G6Info.createMoveTrain(ls.get(2), time);
            case "crash" -> G6Info.createSetGroup(ls.get(1), generator, time);
            default -> null;
        };
    }
}
