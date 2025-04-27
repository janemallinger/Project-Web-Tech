package com.example.frogcrew.game.model;

import jakarta.persistence.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Entity
public class GameSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scheduleId;

    private String sport;
    private String season;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "schedule")
    private List<Game> games = new ArrayList<>();

    public GameSchedule(){}
    public void setSport(String sport) {
        this.sport = sport;
    }

    public void setSeason(String season) {
        this.season = season;
    }
    public void addGame(Game game){
        game.setSchedule(this);
        games.add(game);
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public String getSport() {
        return sport;
    }

    public String getSeason() {
        return season;
    }
}
