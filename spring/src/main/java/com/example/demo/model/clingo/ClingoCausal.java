package com.example.demo.model.clingo;

import lombok.Data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Data
public class ClingoCausal {
    List<ClingoCausalTree> causalTrees;

    public ClingoCausal(String nameFile) {
        System.out.println(nameFile);
        File file = new File(nameFile);
        causalTrees = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            int lineNumber = 0;
            while (scanner.hasNextLine()) {
                ++lineNumber;
                String line = scanner.nextLine();
                System.out.println(lineNumber + ":" + line);
                readEvent(line);
            }
            scanner.close();
        } catch (Exception e) {
            System.out.println("excep: " + e.toString());
        }
    }

    /**
     * Analyse la commande originale en plusieurs chaînes de caractères,
     * la première étant le nom de la fonction,
     * suivie du premier argument, du deuxième et du troisième de la fonction....
     */
    public static List<String> findCompleteCommande(String line) {
        int bracketCount = 0;
        int begin = 0;
        List<String> list = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '(') {
                bracketCount++;
                if (bracketCount == 1) {
                    list.add(line.substring(begin, i));
                    begin = i + 1;
                }
            } else if (line.charAt(i) == ')') {
                bracketCount--;
                if (bracketCount == 0) {
                    list.add(line.substring(begin, i));
                }
            } else if (line.charAt(i) == ',' && bracketCount == 1) {
                list.add(line.substring(begin, i));
                begin = i + 1;
            }
        }
        System.out.println(list);
        return list;
    }

    public ClingoCausalTree findTreebyName(String id) {
        for (int i = 0; i < causalTrees.size(); i++) {
            if (causalTrees.get(i).getName().equals(id)) {
                return causalTrees.get(i);
            }
        }
        ClingoCausalTree tr = new ClingoCausalTree(id);
        causalTrees.add(tr);
        return tr;
    }

    public void readEvent(String line) {
        List<String> elements = findCompleteCommande(line);
        ClingoCausalTree tree = findTreebyName(elements.get(1));
        if (elements.size() == 5) {
            tree.addEvent(elements.get(2), elements.get(3), elements.get(0));
        } else if (elements.size() == 6) {
            tree.addEvent(elements.get(3), elements.get(5), elements.get(0) + elements.get(2));
        }
    }
}
