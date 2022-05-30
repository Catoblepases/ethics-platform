package com.example.demo.model;



/*
    Le class Bridge.
*/
public class Bridge extends Position {
    private Position position;
    private boolean  push;

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
    public Position getPosition() { return position; }
    public String   getInformation() { return name + "," + position.getName(); }
    public boolean  getPush() { return push; }
    public void     setPush(boolean b) { push = b; }
    @Override
    public String toString() {
        return "Bridge: " + name + "-" + position;
    }
}
