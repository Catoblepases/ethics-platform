package com.example.demo.model;

/*Le classe position, endroits où mettre groupe*/
public abstract class Position implements ComponentTrolley {
    protected String name;
    protected Group  group;
    public Position(String name) {
        this.group = null;
        this.name  = name;
    }
    public String getName() { return name; }
    public void   setName(String name) { this.name = name; }
    public Group  getGroup() { return group; }
    public void   setGroup(Group group) { this.group = group; }
}
