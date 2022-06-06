package com.example.demo.model.clingo;

import com.example.demo.model.menu.Edge;

public class EdgeEvent extends Edge {
    public EdgeEvent(String source, String target, String connectType) {
        super(source, target, connectType);
    }

    public String toString() {
        return "[" + getSource() + "," + getTarget() + "," + getConnectType() + "]";
    }
}
