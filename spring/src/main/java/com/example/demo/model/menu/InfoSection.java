package com.example.demo.model.menu;

import com.example.demo.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*le classe InfoCarriage, information de carriage*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InfoSection {
    String id;
    String track;
    int index;
    InfoSwitch switchs;
    InfoGroup group;
    InfoBridge bridge;

    Boolean positionOriginal;

    public InfoSection(Section section, Boolean positionOriginal) {
        this.positionOriginal = positionOriginal;
        this.id = section.getInformation();
        this.track = section.getTrack();
        Group g = section.getGroup();
        if (g != null) {
            this.group = InfoGroup.builder().name(g.getName()).size(g.getNb()).build();
        } else {
            this.group = null;
        }
        Bridge b = section.getBridge();
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
        Switch s = section.getSwitch();
        this.index = section.getIndex();


        if (s != null) {
            this.switchs = new InfoSwitch(s.getTrackBegin(), s.getTrackEnd());
        } else {
            this.switchs = null;
        }
    }
    /*Ajouter et sauver un troncon*/
    public void updateSection(Section section, Generator generator) {
        //ajouter et sauver un group
        if (group != null) {
            group.updateGroup(section,generator);
        } else {
            if (section.getGroup() != null) {
                generator.groups.remove(section.getGroup());
            }
            section.setGroup(null);
        }
        //ajouter et sauver un pont s'il l'exsite
        if (bridge != null) {
            bridge.updateBridge(section, generator);
        } else {
            if (section.getBridge() != null) {
                generator.bridges.remove(section.getBridge());
            }
            section.setBridge(null);
        }
        //ajouter et sauver un interrupteur  s'il l'exsite
        if (switchs != null) {
            switchs.update(section, generator);
        } else {
            if (section.getSwitch() != null) {
                generator.switchs.remove(section.getSwitch());
            }
            section.deleteSwitch();
        }
        if (positionOriginal) {
            generator.train.setOriginPosition(section);
        }
    }
}
