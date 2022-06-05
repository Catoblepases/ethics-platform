package com.example.demo.model.menu;


import com.example.demo.model.Carriage;
import lombok.Data;

@Data
public class Node {
    private static int cpt = 0;

    private String id;
    private int x;
    private int y;
    private String typeName;
    private String label;

    private String fill;

    private InfoCarriage infoCarriage;

    public Node(String id, int x, int y, String typeName, String label, String fill, Carriage carriage, boolean orginal) {
        this.x = x;
        this.y = y;
        this.label = label;
        this.id = id;
        this.typeName = typeName;
        this.fill = fill;
        this.infoCarriage = new InfoCarriage(carriage, orginal);
    }
}
