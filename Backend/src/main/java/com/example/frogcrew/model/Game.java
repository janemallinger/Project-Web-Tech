package com.example.frogcrew.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Game {
    @Id
    private Long id;
    public Game(){

    }
}
