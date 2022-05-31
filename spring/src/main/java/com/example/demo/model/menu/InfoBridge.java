package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoBridge {
    String name;
    InfoGroup group;

    Bridge createBridge(Position position) {
        Bridge bridge = new Bridge(name, position);
        Group g = group.createGroup(position);
        bridge.setGroup(g);
        return bridge;
    }

    void updateBridge(Carriage carriage) {
        Bridge bridge = carriage.getBridge();
        if (!bridge.getName().equals(name)) {
            bridge.setName(name);
        }
        group.updateGroup(carriage);
    }
}
