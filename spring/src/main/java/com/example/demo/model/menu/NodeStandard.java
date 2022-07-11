package com.example.demo.model.menu;


import com.example.demo.model.Section;
import lombok.Data;

@Data
public class NodeStandard {
    private static int cpt = 0;

    private String id;
    private int x;
    private int y;
    private String typeName;
    private String label;

    private String fill;
    private InfoSection infoSection;

    public NodeStandard(String id, int x, int y, String typeName, String label, String fill, Section section, boolean orginal) {
        this.x = x;
        this.y = y;
        this.label = label;
        this.id = id;
        this.typeName = typeName;
        this.fill = fill;
        this.infoSection = new InfoSection(section, orginal);
    }
}
