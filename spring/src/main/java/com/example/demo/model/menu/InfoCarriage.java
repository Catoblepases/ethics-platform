package com.example.demo.model.menu;

import com.example.demo.model.*;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*le classe InfoCarriage, information de carriage*/
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

    Boolean positionOriginal;

    public InfoCarriage(Carriage carriage, Boolean positionOriginal) {
        this.positionOriginal = positionOriginal;
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
            InfoGroup igb = null;
            if (gb != null) {
                igb = InfoGroup.builder().name(gb.getName()).size(gb.getNb()).build();
            }
            this.bridge = InfoBridge.builder().name(b.getName()).group(igb).build();
        } else {
            this.bridge = null;
        }
        Switch s = carriage.getSwitch();
        this.index = carriage.getIndex();


        if (s != null) {
            this.switchs = new InfoSwitch(s.getTrackBegin(), s.getTrackEnd());
        } else {
            this.switchs = null;
        }
    }

    /*Ajouter et sauver un troncon*/
    public void updateCarriage(Carriage carriage, Generator generator) {
        //ajouter et sauver un group s'il l'exsite
        if (group != null) {
            group.updateGroup(carriage,generator);
        } else {
            if (carriage.getGroup() != null) {
                generator.groups.remove(carriage.getGroup());
            }
            carriage.setGroup(null);
        }
        //ajouter et sauver un pont s'il l'exsite
        if (bridge != null) {
            bridge.updateBridge(carriage, generator);
        } else {
            if (carriage.getBridge() != null) {
                generator.bridges.remove(carriage.getBridge());
            }
            carriage.setBridge(null);
        }
        //ajouter et sauver un interrupteur  s'il l'exsite
        if (switchs != null) {
            switchs.update(carriage, generator);
        } else {
            if (carriage.getSwitch() != null) {
                generator.switchs.remove(carriage.getSwitch());
            }
            carriage.deleteSwitch();
        }
        if (positionOriginal) {
            generator.train.setOriginPosition(carriage);
        }
    }
}
