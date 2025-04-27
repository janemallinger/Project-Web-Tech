package com.example.frogcrew.game.service;

import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.model.GameSchedule;
import com.example.frogcrew.game.repository.GameRepository;
import com.example.frogcrew.game.repository.GameScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
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
    public void addGame(Long gameId , Long scheduleId ){
        //find game

        //find schedule

        //add game to gameschedule

    }
}
