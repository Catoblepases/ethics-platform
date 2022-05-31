package com.example.demo.model;


import lombok.Builder;
import lombok.Data;

@Data
public class Edge {
    static int cpt = 0;
    String source;
    String target;
    String id;

    public Edge(String source, String target) {
        this.source = source;
        this.target = target;
        this.id = String.format("%d", ++cpt);
    }
}
