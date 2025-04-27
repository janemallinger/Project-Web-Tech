package com.example.frogcrew.game.service;

import com.example.frogcrew.game.model.GameSchedule;
import com.example.frogcrew.game.repository.GameRepository;
import com.example.frogcrew.game.repository.GameScheduleRepository;

public class GameScheduleService {

    private GameRepository gameRepository;
    private GameScheduleRepository gameScheduleRepository;

    public GameScheduleService(GameRepository gameRepository, GameScheduleRepository gameScheduleRepository) {
        this.gameRepository = gameRepository;
        this.gameScheduleRepository = gameScheduleRepository;
    }
    public GameSchedule createGameSchedule(GameSchedule gameSchedule) {
        return gameScheduleRepository.save(gameSchedule);
    }
}
