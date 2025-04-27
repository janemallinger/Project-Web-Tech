package com.example.frogcrew.game.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

@Transactional
@Entity
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer gameId;

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private GameSchedule schedule;


    @NotNull(message = "date is required")
    private LocalDate gameDate;

    @NotEmpty(message =  "venue is required")
    private String venue;

    @NotEmpty(message =  "opponent is required")
    private String opponent;

    @JsonProperty("isFinalized")
    @NotNull(message =  "finalized is required")
    private Boolean isFinalized;
    public Game() {
    }

    public Game(Integer gameId, GameSchedule schedule, LocalDate gameDate, String venue, String opponent) {
        this.gameId = gameId;
        this.schedule = schedule;
        this.gameDate = gameDate;
        this.venue = venue;
        this.opponent = opponent;
    }



    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public Boolean getFinalized() {
        return isFinalized;
    }

    public void setFinalized(Boolean finalized) {
        isFinalized = finalized;
    }

    public GameSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(GameSchedule schedule) {
        this.schedule = schedule;
    }



}