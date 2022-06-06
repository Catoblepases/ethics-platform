package com.example.demo.model.menu;


import lombok.Builder;
import lombok.Data;

@Data
public class Edge {
    static int cpt = 0;
    String source;
    String target;
    String id;
    String connectType;

    public Edge(String source, String target) {
        this.source = source;
        this.target = target;
        this.id = String.format("%d", ++cpt);
    }

    public Edge(String source, String target, String connectType) {
        this.source = source;
        this.target = target;
        this.id = String.format("%d", ++cpt);
        this.connectType = connectType;
    }
}
