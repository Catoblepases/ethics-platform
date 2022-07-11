package com.example.demo.model.clingo;

import lombok.Data;
import lombok.NoArgsConstructor;
/*Le classe ClingoConfig, contient tous les fichiers neccesaire pour clingo */
@Data
public class ClingoConfig {
    String actionTheory;
    String context;
    String causalTheory;
    String ethicalSpec;
    String ethicalTheories;
    String trace;
    String causalTrace;
    String results;
    String output;
    public ClingoConfig() {
        actionTheory = "./eventMotorOnly.lp";
        context = "./trolley1act.lp";
        causalTheory = "./causalMotorPrevents.lp";
        ethicalSpec = "./trollEthSpec.lp";
        ethicalTheories = "./restrEthicalMotor.lp";
        trace = "./traceTroll.lp";
        causalTrace = "./traceTrollC.lp";
        results = "imp/2\nper/2";
        output = "./resultTroll.lp";
    }
}
