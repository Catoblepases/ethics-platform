package com.example.demo.model.clingo;

import com.example.demo.model.Carriage;
import com.example.demo.model.menu.NodeStandard;
/*Le classe NodeEvent représente les nœuds du graphe de l'événement  */
public class NodeEvent extends NodeStandard {

    public NodeEvent(String id, int x, int y, String typeName, String label, String fill){
        super(id,x,y,typeName,label,fill,new Carriage("","",0),false);
    }
}
