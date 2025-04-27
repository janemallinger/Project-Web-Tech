package com.example.frogcrew.game.service;

import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.game.converter.GameDtoToGame;
import com.example.frogcrew.game.dto.GameRequest.GameRequestDto;
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
    private final GameDtoToGame gameDtoToGame;

    public GameScheduleService(GameRepository gameRepository, GameScheduleRepository gameScheduleRepository, GameDtoToGame gameDtoToGame) {
        this.gameRepository = gameRepository;
        this.gameScheduleRepository = gameScheduleRepository;
        this.gameDtoToGame = gameDtoToGame;
    }
    public GameSchedule createGameSchedule(GameSchedule gameSchedule) {
        return gameScheduleRepository.save(gameSchedule);
    }
    public List<GameSchedule> findAllSchedules(){
        return gameScheduleRepository.findAll();

    }
    public Game addGame( Long scheduleId, GameRequestDto requestGame ){
        //find schedule
        GameSchedule schedule = gameScheduleRepository.findById(scheduleId).orElseThrow(()-> new ObjectNotFoundException("game schedule",scheduleId));

        //construct game

        Game game = gameDtoToGame.convert(requestGame);
        game.setSchedule(schedule);


        return gameRepository.save(game);

    }
}
