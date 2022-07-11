package com.example.demo.model;


/*
    Le class Bridge represente le pont
*/
public class Bridge extends Position {
    private Position position;
    //vrai si pousser la personne sur pont
    private boolean push;
    // moment de pousser
    private int time;

    public Bridge(String name, Position position) {
        super(name);
        this.position = position;
        if (this.position instanceof Carriage) {
            Carriage tmp = (Carriage) this.position;
            tmp.add(this);
        }
        push = true;
    }

    public void delPosition() {
        if (this.position instanceof Carriage) {
            Carriage tmp = (Carriage) this.position;
            tmp.deleteBridge();
        }
        position = null;
    }

    public void setPosition(Position p) {
        position = p;
        if (this.position instanceof Carriage) {
            Carriage tmp = (Carriage) this.position;
            tmp.add(this);
        }
    }

    public Position getPosition() {
        return position;
    }

    public String getInformation() {
        return name + "," + position.getName();
    }

    public boolean getPush() {
        return push;
    }

    public void setPush(boolean b) {
        push = b;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Bridge: " + name + "-" + position;
    }

    //retourne l'evenement, le nom de groupe qui est sur pont et le nom de pont
    public String getEventInfo() {
        if (group == null) {
            return null;
        }
        return "push(" + group.getName() + "," + name + ")";
    }
}
