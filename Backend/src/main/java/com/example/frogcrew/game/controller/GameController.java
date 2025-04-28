package com.example.frogcrew.game.controller;

import com.example.frogcrew.game.converter.GameScheduleDtoToGameSchedule;
import com.example.frogcrew.game.dto.GameRequest.GameRequestDto;
import com.example.frogcrew.game.dto.GameResponse.GameResponseDto;
import com.example.frogcrew.game.dto.GameScheduleRequest.GameScheduleDto;
import com.example.frogcrew.game.dto.GameScheduleResponse.GameScheduleResponse;
import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.model.GameSchedule;
import com.example.frogcrew.game.service.GameScheduleService;
import com.example.frogcrew.system.Result;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.base-path}/gameSchedule")
public class GameController {

    private final GameScheduleService gameScheduleService;
    private final GameScheduleDtoToGameSchedule gameScheduleDtoToGameSchedule;

    public GameController(GameScheduleService gameScheduleService, GameScheduleDtoToGameSchedule gameScheduleDtoToGameSchedule) {
        this.gameScheduleService = gameScheduleService;
        this.gameScheduleDtoToGameSchedule = gameScheduleDtoToGameSchedule;
    }
    @PostMapping
    public Result createGameSchedule(@Valid @RequestBody GameScheduleDto gameScheduleDTO){
        GameSchedule gameSchedule = this.gameScheduleDtoToGameSchedule.convert(gameScheduleDTO);
        GameSchedule createdGameSchedule = this.gameScheduleService.createGameSchedule(gameSchedule);
        GameScheduleResponse gameScheduleResponse = new GameScheduleResponse(createdGameSchedule.getScheduleId(), createdGameSchedule.getSport(), createdGameSchedule.getSeason());

        return new Result(true, HttpStatus.OK.value(), "Add Success", gameScheduleResponse);

    }
    @PostMapping("/{scheduleId}/games")
    public Result addGameToSchedule(@PathVariable Long scheduleId, @Valid @RequestBody GameRequestDto gameRequestDTO){
        Game createdGame = this.gameScheduleService.addGame(scheduleId, gameRequestDTO);
        GameResponseDto gameResponseDto = new GameResponseDto(createdGame.getGameId(), scheduleId,
                createdGame.getGameDate(), createdGame.getVenue(), createdGame.getOpponent(), createdGame.getFinalized());
        return new Result(true, HttpStatus.OK.value(), "Add Success", gameResponseDto);


    }


}
