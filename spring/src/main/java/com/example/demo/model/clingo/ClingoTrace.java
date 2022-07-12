package com.example.demo.model.clingo;

import com.example.demo.model.Generator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*Le classe ClingoTrace est une liste de trace de la simulation par lire le fichier de trace obtenu par clingo ,  */
public class ClingoTrace {

    public List<ClingoSimulation> getClingoSimulations() {
        return clingoSimulations;
    }

    public void setClingoSimulations(List<ClingoSimulation> clingoSimulations) {
        this.clingoSimulations = clingoSimulations;
    }

    List<ClingoSimulation> clingoSimulations;

    public ClingoTrace(){}

    public ClingoTrace(String nameFile, Generator generator) {
        System.out.println(nameFile);
        File file = new File(nameFile);
        clingoSimulations = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                ++lineNumber;
                String line = scanner.nextLine();
                System.out.println(lineNumber + ":" + line);
                addAction(line, generator);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Trouver un simulation sinon l'ajouter*/
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

    /*Ajouter une action */
    public void addAction(String line, Generator generator) {
        List<String> ls = ClingoCausal.findCompleteCommande(line, ',');
        if (ls.size() == 4) {
            ClingoSimulation clingoSimulation = findOrAddClingoSimulation(ls.get(1));
            int time = Integer.parseInt(ls.get(3));
            clingoSimulation.addActionByTime(time, ls.get(2), ls.get(0), generator);
        }
    }

    public String toString() {
        return clingoSimulations.toString();
    }
}
