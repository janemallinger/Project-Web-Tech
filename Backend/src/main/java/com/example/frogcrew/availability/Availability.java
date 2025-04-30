package com.example.frogcrew.availability;

import com.example.frogcrew.crewmember.model.CrewMember;
import com.example.frogcrew.game.model.Game;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.io.Serializable;

@Entity
public class Availability implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne(optional = false)
    @NotNull(message = "crew id is required.")
    private CrewMember crewMember;

    @ManyToOne(optional = false)
    @NotNull(message = "gameId is required.")
    private Game game;

    @NotNull(message = "Available is required.")
    @Column(nullable = false)
    private Boolean available;

    private String comment;

    public Availability() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CrewMember getCrewMember() {
        return crewMember;
    }

    public void setCrewMember(CrewMember crewMember) {
        this.crewMember = crewMember;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getComment(){
        return this.comment;
    }

    public Boolean getAvailable(){
        return this.available;
    }
}
