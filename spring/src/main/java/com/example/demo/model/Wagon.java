package com.example.demo.model;
public class Wagon extends Position {
    private String track;
    public Wagon suivant;
    public Wagon before;
    private Switch sw;
    private Bridge bridge;

    private String name;
    private int index;
    public Wagon(String name, String track, int index) {
        super(name);
        this.track = track;
        this.index = index;
        suivant = null;
        before = null;
        sw = null;
        bridge = null;
    }

    public Wagon(String name){
        super(name);
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

    public Wagon getSuivant() {
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

    public void addBefore(Wagon before) {
        this.before = before;
    }

    public void add(Wagon suivant) {
        this.suivant = suivant;
    }

    public void add(Group group) {
        this.group = group;
    }

    public void add(Bridge bridge) {
        this.bridge = bridge;
    }

    public void add(Switch sw) {
        this.sw = sw;
    }

    public void deleteBridge() {
        bridge = null;
    }

    public void deleteGroup() {
        group = null;
    }

    public void deleteSwitch() {
        sw = null;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getInformation() {
        return name;
    }

    public Wagon next() {
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
