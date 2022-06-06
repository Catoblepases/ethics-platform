package com.example.demo.model.menu;

import lombok.Data;

@Data
public class Fichierclingo {
    private String nom;

    public Fichierclingo(String nom){
        this.nom=nom;
    }
}
