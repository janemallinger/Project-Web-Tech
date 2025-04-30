package com.example.frogcrew.assignment;

import com.example.frogcrew.crewmember.model.CrewMember;
import com.example.frogcrew.game.model.Game;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalTime;

@Entity
public class Assignment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Game game;

    @ManyToOne
    private CrewMember crewMember;

    @Column(nullable = false)
    private String position; // e.g., "Camera", "Producer", etc.

    @Column(nullable = false)
    private LocalTime reportTime;

    @Column(nullable = false)
    private String reportLocation;

    public Assignment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public CrewMember getCrewMember() {
        return crewMember;
    }

    public void setCrewMember(CrewMember crewMember) {
        this.crewMember = crewMember;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public LocalTime getReportTime() {
        return reportTime;
    }

    public void setReportTime(LocalTime reportTime) {
        this.reportTime = reportTime;
    }

    public String getReportLocation() {
        return reportLocation;
    }

    public void setReportLocation(String reportLocation) {
        this.reportLocation = reportLocation;
    }
}
