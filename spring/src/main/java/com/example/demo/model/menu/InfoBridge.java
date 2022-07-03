package com.example.demo.model.menu;

import com.example.demo.model.*;
import lombok.Builder;
import lombok.Data;

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

    void updateBridge(Carriage carriage, Generator generator) {
        if (name == "" || name == null) {
            carriage.setBridge(null);
            return;
        }
        Bridge bridge = carriage.getBridge();
        if (bridge == null) {
            bridge = createBridge(carriage);
            generator.bridges.add(bridge);
            return;
        }
        if (!bridge.getName().equals(name)) {
            bridge.setName(name);
        }
        if (group != null) {
            group.updateGroup(carriage.getBridge(),generator);
        }else if(bridge.getGroup()!=null){
            generator.groups.remove(bridge.getGroup());
        }
    }
}
