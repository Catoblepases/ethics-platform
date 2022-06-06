package com.example.demo.model.clingo;

import lombok.Data;
import org.python.antlr.ast.Str;

@Data
public class ClingoAction {
    String action;
    String actionType;

    public ClingoAction(String actionType, String action){
        this.action=action;
        this.actionType=actionType;
    }
}
