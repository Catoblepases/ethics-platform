package com.example.demo.model.clingo;

import lombok.Data;

@Data
public class Impermissible {
    String name;
    String action;

    public Impermissible(String name, String action) {
        this.action = action;
        this.name = name;
    }
}
