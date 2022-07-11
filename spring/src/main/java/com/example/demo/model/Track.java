package com.example.demo.model;


import java.util.ArrayList;

/*Le class Track, qui est une liste de troncon*/
public class Track extends ArrayList<Section> implements ComponentTrolley {
    private static final long serialVersionUID = 1L;
    private String name;


    public Track(String name, int length) {
        this.name = name;
        for (int i = 0; i < length + 1; i++) {
            addNewSection();
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

    public void addNewSection() {
        add(new Section(name + "(" + size() + ")", name, size()));
        if (size() >= 2) {
            get(size() - 2).suivant = get(size() - 1);
        }
        if (size() != 1) {
            get(size() - 1).before = get(size() - 2);
        }
    }

    public void addNewSection(int idx) {
        add(idx, new Section(name + "(" + size() + ")", name, size()));
        if (idx >= 1) {
            get(idx - 1).suivant = get(idx);
        }
        if (idx != (size() - 1)) {
            get(idx + 1).before = get(idx);
        }
    }

    public String getInformation() {
        return name + "(" + Generator.generateNumber(size()) + ")";
    }

    public int nbSwitch() {
        int nb = 0;
        for (int i = 0; i < size(); i++) {
            if (get(i).getSwitch() != null) {
                nb++;
            }
        }
        return nb;
    }

    public void delete() {
        for (int i = 0; i < size(); i++) {
            Section c = get(i);
            Switch sw = c.getSwitch();
            if (sw != null) {
                sw.getTrackBegin().setSwitch(null);
            }
        }
    }
}
