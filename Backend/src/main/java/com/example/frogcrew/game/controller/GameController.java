package com.example.frogcrew.game.controller;

import com.example.frogcrew.game.dto.GameResponse.GameResponseDto;
import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.service.GameService;
import com.example.frogcrew.system.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.base-path}/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService){
        this.gameService = gameService;
    }

    @GetMapping
    public Result getGeneralGameSchedule(){
        List<Game> games = this.gameService.findAllGames();
        if(games.isEmpty()){
            return new Result(true, HttpStatus.NO_CONTENT.value(), "No games found");
        }
        List<GameResponseDto> gameResponseDtos = games.stream()
                .map(game -> new GameResponseDto(
                        game.getGameId(),
                        game.getSchedule().getScheduleId(),
                        game.getGameDate(),
                        game.getVenue(),
                        game.getOpponent(),
                        game.getFinalized()
                ))
                .toList();


        return new Result(true, HttpStatus.OK.value(), "Find Success", gameResponseDtos);
    }

}
