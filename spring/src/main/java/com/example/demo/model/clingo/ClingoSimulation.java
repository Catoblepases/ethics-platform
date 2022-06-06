package com.example.demo.model.clingo;

import com.example.demo.model.Generator;

import java.util.ArrayList;
import java.util.List;

public class ClingoSimulation {

    Generator generator;

    ArrayList<String> listCommand[];


    public ClingoSimulation(){
        int time=generator.getTime();
        listCommand = new ArrayList[time];
    }
}
