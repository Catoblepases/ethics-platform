package com.example.demo.model.menu;


import com.example.demo.model.Section;
import com.example.demo.model.Generator;
import com.example.demo.model.Switch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;

/*Le classe InfoSwitch,tous les infomation de interrupteur */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class InfoSwitch {
    String trackBegin;
    String trackEnd;
    String trackBeginName;
    int trackBeginNumber;
    String trackEndName;
    int trackEndNumber;

    public InfoSwitch(Section trackBegin, Section trackEnd) {
        this.trackBegin = trackBegin.getName();
        this.trackEnd = trackEnd.getName();
        this.trackBeginNumber = trackBegin.getIndex();
        this.trackBeginName = trackBegin.getTrack();
        this.trackEndNumber = trackEnd.getIndex();
        this.trackEndName = trackEnd.getTrack();
    }

    Switch createSwitch(Generator generator) {
        Section begin = (Section) generator.readPosition(trackBegin);
        Section end = (Section) generator.readPosition(trackEnd);
        Switch sw = new Switch(begin, end);
        return sw;
    }

    /*Ajouter et Sauver un switch*/
    void update(Section section, Generator generator) {
        Switch s = section.getSwitch();
        Section begin = (Section) generator.readPosition(trackBegin);
        Section end = (Section) generator.readPosition(trackEnd);
        if (s == null) {
            Switch sw = new Switch(begin, end);
            generator.switchs.add(sw);
        } else if (!s.getTrackBegin().equals(trackBegin)) {
            s.setTrackBegin(begin);
        } else if (!s.getTrackEnd().equals(trackEnd)) {
            s.setTrackEnd(end);
        }
    }
}
