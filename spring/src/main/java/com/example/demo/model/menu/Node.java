package com.example.demo.model.menu;

import com.example.demo.model.Carriage;

/*le classe Node*/
public class Node extends NodeStandard {
    private InfoCarriage infoCarriage;
    public Node(String id, int x, int y, String typeName, String label, String fill, Carriage carriage, boolean orginal){
        super(id,x,y,typeName,label,fill,carriage,orginal);
    }
}
