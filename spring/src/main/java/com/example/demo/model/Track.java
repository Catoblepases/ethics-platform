package com.example.demo.model;


import java.util.ArrayList;

/*Le class Track, qui est une liste de troncon*/
public class Track extends ArrayList<Carriage> implements ComponentTrolley {
    private static final long serialVersionUID = 1L;
    private String name;


    public Track(String name, int length) {
        this.name = name;
        for (int i = 0; i < length; i++) {
            addNewCarriage();
        }
    }

    @Override
    public String toString() {
        return "Track: " + name + "(" + Generator.generateNumber(size()) + ")";
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Track(String name) {
        this.name = name;
    }

    /*Ajouter un troncon */
    public void addNewCarriage() {
        add(new Carriage(name + "(" + size() + ")", name, size()));
        if (size() >= 2) {
            get(size() - 2).suivant = get(size() - 1);
        }
        if (size() != 1) {
            get(size() - 1).before = get(size() - 2);
        }
    }

    /*Ajouter des troncons*/
    public void addNewCarriage(int idx) {
        add(idx, new Carriage(name + "(" + size() + ")", name, size()));
        if (idx >= 1) {
            get(idx - 1).suivant = get(idx);
        }
        if (idx != (size() - 1)) {
            get(idx + 1).before = get(idx);
        }
    }

    /*retourne la longeur de la voie*/
    public String getInformation() {
        return name + "(" + Generator.generateNumber(size()) + ")";
    }

    /*retourne le nombre de switch  */
    public int nbSwitch() {
        int nb = 0;
        for (int i = 0; i < size(); i++) {
            if (get(i).getSwitch() != null) {
                nb++;
            }
        }
        return nb;
    }

    /*Supprimer une voie*/
    public void delete() {
        for (int i = 0; i < size(); i++) {
            Carriage c = get(i);
            Switch sw=c.getSwitch();
            if (sw!=null){
                sw.getTrackBegin().setSwitch(null);
            }
        }
    }
}
