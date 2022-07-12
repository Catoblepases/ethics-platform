package com.example.demo.model.menu;


import lombok.Builder;
import lombok.Data;
/*Le classe Edge, le connexion des nœuds d'un graphe*/
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
