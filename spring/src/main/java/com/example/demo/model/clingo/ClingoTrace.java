package com.example.demo.model.clingo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClingoTrace {

    List<ClingoSimulation> clingoSimulations;

    public ClingoTrace(String nameFile) {
        System.out.println(nameFile);
        File file = new File(nameFile);
        clingoSimulations=new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                ++lineNumber;
                String line = scanner.nextLine();
                System.out.println(lineNumber + ":" + line);
                addAction(line);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ClingoSimulation findOrAddClingoSimulation(String name) {
        for (int i = 0; i < clingoSimulations.size(); i++) {
            if (clingoSimulations.get(i).getName().equals(name)) {
                return clingoSimulations.get(i);
            }
        }
        ClingoSimulation clingoSimulation = new ClingoSimulation(name);
        clingoSimulations.add(clingoSimulation);
        return clingoSimulation;
    }

    public void addAction(String line) {
        List<String> ls = ClingoCausal.findCompleteCommande(line);
        if (ls.size() == 4) {
            ClingoSimulation clingoSimulation = findOrAddClingoSimulation(ls.get(1));
            int time = Integer.parseInt(ls.get(3));
            clingoSimulation.addActionByTime(time, ls.get(2), ls.get(0));
        }
    }

    public String toString(){
        return clingoSimulations.toString();
    }
}
