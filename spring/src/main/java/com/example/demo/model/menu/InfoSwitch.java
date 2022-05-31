package com.example.demo.model.menu;


import com.example.demo.model.Carriage;
import com.example.demo.model.Generator;
import com.example.demo.model.Switch;
import lombok.Builder;
import lombok.Data;

import javax.annotation.Resource;

@Data
@Builder
public class InfoSwitch {
    String trackBegin;
    String trackEnd;
    Switch createSwitch(Generator generator) {
        Carriage begin = (Carriage) generator.readPosition(trackBegin);
        Carriage end = (Carriage) generator.readPosition(trackEnd);
        Switch sw = new Switch(begin, end);
        return sw;
    }

    void update(Carriage carriage,Generator generator) {
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
