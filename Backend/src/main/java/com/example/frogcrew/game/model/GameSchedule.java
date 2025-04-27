package com.example.frogcrew.game.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Entity
public class GameSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer scheduleId;

    private String sport;
    private String season;

    public GameSchedule(){}
    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
