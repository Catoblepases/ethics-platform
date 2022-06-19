package com.example.demo.model.clingo;

import com.example.demo.model.Bridge;
import com.example.demo.model.Generator;
import com.example.demo.model.Group;
import com.example.demo.model.Position;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class G6Info {
    String info;
    int time;
    String position;
    String pType;
    String gid;

    private G6Info(String info, int time) {
        this.info = info;
        this.time = time;
    }

    public static G6Info createMove(String pid, String gid, int time, Generator generator) {
        if (gid.equals("train")) {
            return createMoveTrain(pid, time);
        } else {
            return createMoveGroup(pid, gid, time, generator);
        }
    }

    public static G6Info createMoveTrain(String pid, int time) {
        G6Info g = new G6Info("train", time);
        g.position = pid;
        g.gid = "train";
        return g;
    }

    public static G6Info createMoveGroup(String pid, String gid, int time, Generator generator) {
        G6Info g = new G6Info("moveGroup", time);
        Position pos = generator.findGroup(gid).getPosition();
        Position opos = generator.readPosition(pid);
        if (pos instanceof Bridge && !(opos instanceof Bridge)) {
            g.pType = "bridge";
            g.position = pid;
            g.gid = gid;
            return g;
        }
        return null;
    }

    public static G6Info createAlive(String gid, Generator generator, int time) {
        if (time != 0) {
            return null;
        }
        G6Info g = new G6Info("alive", time);
        Position pos = generator.findGroup(gid).getPosition();
        if (pos instanceof Bridge) {
            g.position = ((Bridge) pos).getPosition().getName();
        } else {
            g.position = pos.getName();
        }
        g.gid = gid;
        return g;
    }

    public static G6Info createSetGroup(String gid, Generator generator, int time) {
        G6Info g = new G6Info("group", time);
        Group group = generator.findGroup(gid);
        Position pos = group.getPosition();
        g.gid = gid;
        if (pos instanceof Bridge) {
            Bridge bridge = (Bridge) pos;
            g.pType = "bridge";
            g.position = ((Bridge) pos).getPosition().getName();
        } else {
            g.position = pos.getName();
            g.pType = "carriage";
        }
        return g;
    }

    public boolean equal(Object object) {
        if (!(object instanceof G6Info)) {
            return false;
        }
        G6Info g6Info = (G6Info) object;
        return g6Info.gid.equals(gid) && g6Info.position.equals(position) && g6Info.info.equals(info);
    }

}
