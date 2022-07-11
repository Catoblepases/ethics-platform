package com.example.demo.model.menu;


import com.example.demo.model.Generator;
import com.example.demo.model.Group;
import com.example.demo.model.Position;
import lombok.Builder;
import lombok.Data;

/*le classe InfoGroup, information de groupe*/
@Data
@Builder
public class InfoGroup {
    int size;
    String name;

    Group createGroup(Position position) {
        Group g = new Group(size, position, name);
        return g;
    }

    /*Tester si l'object donnée est ce groupe*/
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

    /*Ajouter et sauver un groupe*/
    void updateGroup(Position position, Generator generator) {
        Group g = position.getGroup();
        if (g == null) {
            Group group = createGroup(position);
            position.setGroup(group);
            generator.groups.add(group);
        } else if (!equal(g)) {
            g.setNb(size);
            g.setName(name);
        }
    }
}
