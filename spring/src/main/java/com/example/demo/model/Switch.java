package com.example.demo.model;

public class Switch implements Event {

    private Section trackBegin;
    private Section trackEnd;
    private Section present;
    private boolean valid;
    private int time;

    public Switch(Section t1, Section t2) {
        trackBegin = t1;
        trackEnd = t2;
        present = t2;
        valid = true;
        t1.add(this);
    }

    public Switch(Section present) {
        trackBegin = present;
        this.present = present;
        present.add(this);
    }

    public void addSection(Section section) {
        trackEnd = section;
        section.add(this);
    }

    public Section getTrackBegin() {
        return trackBegin;
    }

    public Section getTrackEnd() {
        return trackEnd;
    }

    public void changeTrack(String track) {
        if (present == trackBegin) {
            present = trackEnd;
        } else if (present == trackEnd) {
            present = trackBegin;
        }
    }

    public Section next() {
        return trackEnd;
    }

    public Section next(Section section) {
        if (section.equals(present)) {
            return present.suivant;
        }
        return trackEnd;
    }

    public Section getOtherSection(Section c) {
        if (c == trackBegin) {
            return trackEnd;
        } else {
            return trackBegin;
        }
    }


    public void setTrackBegin(Section trackBegin) {
        this.trackBegin = trackBegin;
        trackBegin.setSwitch(this);
    }

    public void setTrackEnd(Section trackEnd) {
        this.trackEnd = trackEnd;
        trackEnd.setSwitch(this);
    }

    @Override
    public String toString() {
        return "switch: " + trackBegin.toString() + "-" + present.toString();
    }

    public void delSection(Section c) {
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
        if (trackEnd.getIndex() == trackBegin.getIndex()) {
            return "switch(" + trackBegin.toString() + ")";
        } else {
            return "switch(" + trackBegin.toString() + "," + trackEnd.toString() + ")";
        }

    }
}
