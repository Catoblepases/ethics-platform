package com.example.demo.model;


public class Section extends Position {
    private String track;
    public Section suivant;
    public Section before;
    private Switch sw;
    private Bridge bridge;
    private int index;


    public Section(String name, String track, int index) {
        super(name);
        this.track = track;
        this.index = index;
        suivant = null;
        before = null;
        sw = null;
        bridge = null;
    }

    public String getName() {
        return name;
    }

    public String getTrack() {
        return this.track;
    }

    public Section getSuivant() {
        return this.suivant;
    }

    public Switch getSwitch() {
        return this.sw;
    }

    public Bridge getBridge() {
        return bridge;
    }

    public int getIndex() {
        return index;
    }

    public void addBefore(Section before) {
        this.before = before;
    }

    public void add(Section suivant) {
        this.suivant = suivant;
    }

    public void add(Group group) {
        this.group = group;
    }

    public void add(Bridge bridge) {
        this.bridge = bridge;
    }

    public void add(Switch sw) {
        setSwitch(sw);
    }

    public void deleteBridge() {
        bridge = null;
    }

    public void deleteGroup() {
        group = null;
    }

    public void deleteSwitch() {
        if (sw != null) {
            Section c1 = sw.getTrackBegin();
            Section c2 = sw.getTrackEnd();
            c1.setSwitch(null);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public String getInformation() {
        return name;
    }

    public void delete() {
        deleteBridge();
        deleteGroup();
        deleteSwitch();
        before.suivant = suivant;
    }

    public Section next() {
        if (sw != null) {
            return sw.next();
        }
        return suivant;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public void setSwitch(Switch sw) {
        this.sw = sw;
    }

    public void setBridge(Bridge bridge) {
        this.bridge = bridge;
    }
}
