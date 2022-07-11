package com.example.demo.model.clingo;

import com.example.demo.model.Section;
import com.example.demo.model.menu.NodeStandard;
/*Le classe NodeEvent représente les nœuds du graphe de l'événement  */
public class NodeEvent extends NodeStandard {

    public NodeEvent(String id, int x, int y, String typeName, String label, String fill){
        super(id,x,y,typeName,label,fill,new Section("","",0),false);
    }
}
