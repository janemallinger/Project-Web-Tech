package com.example.frogcrew.game.service;

import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.game.converter.GameDtoToGameConverter;
import com.example.frogcrew.game.dto.GameDTO;
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
    private final GameDtoToGameConverter gameDtoToGameConverter;

    public GameScheduleService(GameRepository gameRepository, GameScheduleRepository gameScheduleRepository, GameDtoToGameConverter gameDtoToGameConverter) {
        this.gameRepository = gameRepository;
        this.gameScheduleRepository = gameScheduleRepository;
        this.gameDtoToGameConverter = gameDtoToGameConverter;
    }
    public GameSchedule createGameSchedule(GameSchedule gameSchedule) {
        return gameScheduleRepository.save(gameSchedule);
    }
    public List<GameSchedule> findAllSchedules(){
        return gameScheduleRepository.findAll();

    }
    public void addGame(GameDTO requestGame, Long scheduleId ){
        //find schedule
        GameSchedule schedule = gameScheduleRepository.findById(scheduleId).orElseThrow(()-> new ObjectNotFoundException("game schedule",scheduleId));

        //construct game
        Game game = gameDtoToGameConverter.convert(requestGame);
        game.setSchedule(schedule);

        gameRepository.save(game);

    }
}
