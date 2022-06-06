package com.example.demo.model.clingo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClingoConfig {
    String actionTheory;
    String context;
    String causalTheory;
    String ethicalSpec;
    String ethicalTheories;
    String trace;
    String causalTrace;
    String[] results;
    String output;
}
