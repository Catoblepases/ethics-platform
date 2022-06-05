package com.example.demo.model.menu;


import com.example.demo.model.Carriage;
import com.example.demo.model.Generator;
import com.example.demo.model.Switch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;

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

    public InfoSwitch(Carriage trackBegin, Carriage trackEnd) {
        this.trackBegin = trackBegin.getName();
        this.trackEnd = trackEnd.getName();
        this.trackBeginNumber = trackBegin.getIndex();
        this.trackBeginName = trackBegin.getTrack();
        this.trackEndNumber = trackEnd.getIndex();
        this.trackEndName = trackEnd.getTrack();
    }

    Switch createSwitch(Generator generator) {
        Carriage begin = (Carriage) generator.readPosition(trackBegin);
        Carriage end = (Carriage) generator.readPosition(trackEnd);
        Switch sw = new Switch(begin, end);
        return sw;
    }

    void update(Carriage carriage, Generator generator) {
        Switch s = carriage.getSwitch();
        Carriage begin = (Carriage) generator.readPosition(trackBegin);
        Carriage end = (Carriage) generator.readPosition(trackEnd);
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
