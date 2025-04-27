package com.example.frogcrew.game.service;

import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.model.GameSchedule;
import com.example.frogcrew.game.repository.GameRepository;
import com.example.frogcrew.game.repository.GameScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class GameScheduleService {

    private final GameRepository gameRepository;
    private final GameScheduleRepository gameScheduleRepository;

    public GameScheduleService(GameRepository gameRepository, GameScheduleRepository gameScheduleRepository) {
        this.gameRepository = gameRepository;
        this.gameScheduleRepository = gameScheduleRepository;
    }
    public GameSchedule createGameSchedule(GameSchedule gameSchedule) {
        return gameScheduleRepository.save(gameSchedule);
    }
    public List<GameSchedule> findAllSchedules(){
        return gameScheduleRepository.findAll();

    }
    public void addGame(Long gameId , Long scheduleId ){
        //find game
        Game game = gameRepository.findById(gameId).orElseThrow(()-> new ObjectNotFoundException("game",gameId));
        //find schedule
        GameSchedule schedule = gameScheduleRepository.findById(scheduleId).orElseThrow(()-> new ObjectNotFoundException("game schedule",scheduleId));

        //add game to gameschedule
        schedule.addGame(game);
        gameScheduleRepository.save(schedule);

    }
}
