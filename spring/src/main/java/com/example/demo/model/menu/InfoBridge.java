package com.example.demo.model.menu;

import com.example.demo.model.*;
import lombok.Builder;
import lombok.Data;

/*le classe InfoBridge, information de pont*/
@Data
@Builder
public class InfoBridge {
    String name;
    InfoGroup group;

    Bridge createBridge(Position position) {
        Bridge bridge = new Bridge(name, position);
        Group g = null;
        if (group != null) {
            g = group.createGroup(position);
        }
        bridge.setGroup(g);
        return bridge;
    }

    /*Ajouter et sauver un pont*/
    void updateBridge(Section section, Generator generator) {
        if (name == "" || name == null) {
            section.setBridge(null);
            return;
        }
        Bridge bridge = section.getBridge();
        if (bridge == null) {
            bridge = createBridge(section);
            generator.bridges.add(bridge);
            return;
        }
        if (!bridge.getName().equals(name)) {
            bridge.setName(name);
        }
        if (group != null) {
            group.updateGroup(section.getBridge(),generator);
        }else if(bridge.getGroup()!=null){
            generator.groups.remove(bridge.getGroup());
        }
    }
}
