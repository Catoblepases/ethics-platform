package com.example.demo.model.menu;

import com.example.demo.model.Section;

/*le classe Node*/
public class Node extends NodeStandard {
    private InfoSection infoSection;
    public Node(String id, int x, int y, String typeName, String label, String fill, Section section, boolean orginal){
        super(id,x,y,typeName,label,fill,section,orginal);
    }
}
