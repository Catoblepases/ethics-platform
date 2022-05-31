package com.example.demo.model.menu;


import com.example.demo.model.Group;
import com.example.demo.model.Position;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InfoGroup {
    int size;
    String name;

    Group createGroup(Position position) {
        Group g = new Group(size, position, name);
        return g;
    }

    boolean equal(Object object) {
        if (object instanceof InfoGroup) {
            InfoGroup ig = (InfoGroup) object;
            return (ig.getName().equals(name)) && (ig.getSize() == size);
        } else if (object instanceof Group) {
            Group g = (Group) object;
            return (g.getName().equals(name)) && (g.getNb() == size);
        }
        return false;
    }

    void updateGroup(Position position) {
        Group g = position.getGroup();
        if (g == null) {
            position.setGroup(createGroup(position));
        } else if (!equal(g)) {
            g.setNb(size);
            g.setName(name);
        }
    }
}
