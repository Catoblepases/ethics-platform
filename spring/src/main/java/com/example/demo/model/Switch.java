package com.example.demo.model;

public class Switch implements Event {

    private Carriage trackBegin;
    private Carriage trackEnd;
    private Carriage present;
    private boolean valid;
    private int time;

    public Switch(Carriage t1, Carriage t2) {
        trackBegin = t1;
        trackEnd = t2;
        present = t1;
        valid = true;
        t1.add(this);
    }

    public Switch(Carriage present) {
        trackBegin = present;
        this.present = present;
        present.add(this);
    }

    public void addCarriage(Carriage carriage) {
        trackEnd = carriage;
        carriage.add(this);
    }

    public Carriage getTrackBegin() {
        return trackBegin;
    }

    public Carriage getTrackEnd() {
        return trackEnd;
    }

    public void changeTrack(String track) {
        if (present == trackBegin) {
            present = trackEnd;
        } else if (present == trackEnd) {
            present = trackBegin;
        }
    }

    public Carriage next() {
        return present.suivant;
    }

    public Carriage next(Carriage carriage) {
        if (carriage.equals(present)) {
            return present.suivant;
        }
        return present;
    }

    public Carriage getOtherCarriage(Carriage c) {
        if (c == trackBegin) {
            return trackEnd;
        } else {
            return trackBegin;
        }
    }


    public void setTrackBegin(Carriage trackBegin) {
        this.trackBegin = trackBegin;
        trackBegin.setSwitch(this);
    }

    public void setTrackEnd(Carriage trackEnd) {
        this.trackEnd = trackEnd;
        trackEnd.setSwitch(this);
    }

    @Override
    public String toString() {
        return "switch: " + trackBegin.toString() + "-" + present.toString();
    }

    public void delCarriage(Carriage c) {
        c.setSwitch(null);
        if (trackBegin == c) {
            trackBegin = null;
        } else if (trackEnd == c) {
            trackEnd = null;
        }
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getEventInfo() {
        return "switch(" + trackBegin.toString() + ")";
    }
}
