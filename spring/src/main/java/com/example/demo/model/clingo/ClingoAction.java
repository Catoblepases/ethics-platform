package com.example.demo.model.clingo;

import lombok.Data;
import org.python.antlr.ast.Str;

@Data
public class ClingoAction {
    String action;
    String actionType;

    int time;

    public ClingoAction(String actionType, String action,int time){
        this.action=action;
        this.actionType=actionType;
        this.time=time;
    }
}
