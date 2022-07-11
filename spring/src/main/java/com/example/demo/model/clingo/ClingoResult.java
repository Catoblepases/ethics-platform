package com.example.demo.model.clingo;

import java.io.File;
import java.util.*;

/*le classe ClingoResult construit un tableau de resultat */
public class ClingoResult {

    List<Permissible> permissibleList;
    List<Permissible> impermissibleList;
    List<String> eventList;
    List<String> assessments;
    Map<String, String>[] clingoResultMap;

    public ClingoResult(String nameFile) {
        System.out.println(nameFile);
        File file = new File(nameFile);
        permissibleList = new ArrayList<>();
        impermissibleList = new ArrayList<>();
        eventList = new ArrayList<>();
        assessments = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                ++lineNumber;
                String line = scanner.nextLine();
                System.out.println(lineNumber + ":" + line);
                readResult(line);
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Ajouter une chaine de caracter dans la liste s'il n'existe pas*/
    public void findOrAddPPL(String s, List<String> ls) {
        if (!ls.contains(s)) {
            ls.add(s);
        }
    }

    /*Lire la resultat par ligne*/
    public void readResult(String line) {
        List<String> commands = ClingoCausal.findCompleteCommande(line,',');
        findOrAddPPL(commands.get(1), assessments);
        findOrAddPPL(commands.get(2), eventList);

        switch (commands.get(0)) {
            case "imp" -> impermissibleList.add(new Permissible(commands.get(1), commands.get(2)));
            case "per" -> permissibleList.add(new Permissible(commands.get(1), commands.get(2)));
        }
    }

    /*Trouver une  action dans la liste de action admissibles */
    public boolean findPAndIM(String action, String name, List<Permissible> lp) {
        for (int i = 0; i < lp.size(); i++) {
            if (lp.get(i).getAction().equals(action) && lp.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public static void printMatrice(String[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.printf(m[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printMatrice(List<List<String>> m) {
        for (int i = 0; i < m.size(); i++) {
            for (int j = 0; j < m.get(0).size(); j++) {
                System.out.printf(m.get(i).get(j) + "\t");
            }
            System.out.println();
        }
    }

    public List<List<String>> calculateForm() {
        int sizeEvent = eventList.size();
        int sizeAccess = assessments.size();
        List<List<String>> form = new ArrayList<List<String>>();
        for (int i = 0; i < sizeEvent + 1; i++) {
            List<String> access = new ArrayList<>();
            if (i == 0) {
                access.add("event");
            } else {
                access.add(eventList.get(i - 1));
            }
            for (int j = 0; j < sizeAccess; j++) {
                if (i == 0) {
                    access.add(assessments.get(j));
                } else if (findPAndIM(eventList.get(i - 1), assessments.get(j), permissibleList)) {
                    access.add("T");
                } else if (findPAndIM(eventList.get(i - 1), assessments.get(j), impermissibleList)) {
                    access.add("F");
                } else {
                    access.add("A");
                }
            }
            form.add(access);
        }
        return form;
    }

    public Map<String, Object> calculateMap() {
        int sizeEvent = eventList.size();
        int sizeAccess = assessments.size();
        Map<String, String>[] clingoResultMap = new HashMap[sizeEvent];
        List<String> items = new ArrayList<>();
        items.add("event");
        for (int i = 0; i < sizeEvent; i++) {
            HashMap<String, String> access = new HashMap<>();
            access.put("event", eventList.get(i));
            for (int j = 0; j < sizeAccess; j++) {
                if (i == 0) {
                    items.add(assessments.get(j));
                }
                if (findPAndIM(eventList.get(i), assessments.get(j), permissibleList)) {
                    access.put(assessments.get(j), "T");
                } else if (findPAndIM(eventList.get(i), assessments.get(j), impermissibleList)) {
                    access.put(assessments.get(j), "F");
                } else {
                    access.put(assessments.get(j), "A");
                }
            }
            clingoResultMap[i] = access;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("items", items);
        map.put("data", clingoResultMap);
        return map;
    }
}
