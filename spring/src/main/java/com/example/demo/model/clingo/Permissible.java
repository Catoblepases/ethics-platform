package com.example.demo.model.clingo;

import lombok.Data;

/*Le classe Permissible represente les actions admissibles*/
@Data
public class Permissible{
    String name;
    String action;

    public Permissible(String name, String action) {
        this.action = action;
        this.name = name;
    }
}
