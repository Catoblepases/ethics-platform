package com.example.demo.model.menu;

import com.example.demo.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InfoCarriage {
    String id;
    String track;
    int index;
    InfoSwitch switchs;
    InfoGroup group;
    InfoBridge bridge;

    public InfoCarriage(Carriage carriage) {
        this.id = carriage.getInformation();
        this.track = carriage.getTrack();
        Group g = carriage.getGroup();
        if (g != null) {
            this.group = InfoGroup.builder().name(g.getName()).size(g.getNb()).build();
        } else {
            this.group = null;
        }
        Bridge b = carriage.getBridge();
        if (b != null) {
            Group gb = b.getGroup();
            InfoGroup igb = InfoGroup.builder().name(gb.getName()).size(gb.getNb()).build();
            this.bridge = InfoBridge.builder().name(b.getName()).group(igb).build();
        } else {
            this.bridge = null;
        }
        Switch s = carriage.getSwitch();
        this.index = carriage.getIndex();

        if (s != null) {
            this.switchs = InfoSwitch.builder().trackBegin(s.getTrackBegin()).trackEnd(s.getTrackEnd()).build();
        } else {
            this.switchs = null;
        }
    }

    public void updateCarriage(Carriage carriage, Generator generator) {
        if (group != null) {
            group.updateGroup(carriage);
        } else {
            carriage.setGroup(null);
        }
        if (bridge != null) {
            bridge.updateBridge(carriage);
        } else {
            carriage.setBridge(null);
        }
        if (switchs != null) {
            switchs.update(carriage,generator);
        } else {
            carriage.setSwitch(null);
        }
    }


}
