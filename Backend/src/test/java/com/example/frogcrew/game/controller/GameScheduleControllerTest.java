package com.example.frogcrew.game.controller;

import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.game.converter.GameScheduleDtoToGameSchedule;
import com.example.frogcrew.game.dto.GameRequest.GameRequestDto;
import com.example.frogcrew.game.dto.GameScheduleRequest.GameScheduleDto;
import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.model.GameSchedule;
import com.example.frogcrew.game.service.GameScheduleService;
import com.example.frogcrew.game.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class GameScheduleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    GameScheduleService gameScheduleService;

    @MockBean
    GameScheduleDtoToGameSchedule gameScheduleDtoToGameSchedule;

    @MockBean
    GameService gameService;

    @Value("${api.base-path:/api/v1}") // Provide a default value
    String apiBasePath;

    GameScheduleDto gameScheduleDto;
    GameSchedule gameSchedule;
    GameSchedule createdGameSchedule;
    GameRequestDto gameRequestDto;
    Game createdGame;


    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());

        gameScheduleDto = new GameScheduleDto("Football", "2025-2026");

        gameSchedule = new GameSchedule();
        gameSchedule.setSport("Football");
        gameSchedule.setSeason("2025-2026");

        createdGameSchedule = new GameSchedule();
        createdGameSchedule.setScheduleId(1L);
        createdGameSchedule.setSport("Football");
        createdGameSchedule.setSeason("2025-2026");

        gameRequestDto = new GameRequestDto(
                LocalDate.of(2025, 9, 15),
                "Home Stadium",
                "Rival U",
                false
        );

        createdGame = new Game();
        createdGame.setGameId(101L);
        createdGame.setGameDate(LocalDate.of(2025, 9, 15));
        createdGame.setVenue("Home Stadium");
        createdGame.setOpponent("Rival U");
        createdGame.setFinalized(false);
        GameSchedule parentSchedule = new GameSchedule();
        parentSchedule.setScheduleId(1L);
        createdGame.setSchedule(parentSchedule);
    }

    @Test
    void testCreateGameScheduleSuccess() throws Exception {
        String json = objectMapper.writeValueAsString(gameScheduleDto);

        given(gameScheduleDtoToGameSchedule.convert(any(GameScheduleDto.class))).willReturn(gameSchedule);
        given(gameScheduleService.createGameSchedule(any(GameSchedule.class))).willReturn(createdGameSchedule);

        mockMvc.perform(post(apiBasePath + "/gameSchedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag", is(true)))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Add Success")))
                .andExpect(jsonPath("$.data.scheduleId", is(1)))
                .andExpect(jsonPath("$.data.sport", is("Football")))
                .andExpect(jsonPath("$.data.season", is("2025-2026")));

        verify(gameScheduleDtoToGameSchedule).convert(any(GameScheduleDto.class));
        verify(gameScheduleService).createGameSchedule(any(GameSchedule.class));
    }

    @Test
    void testCreateGameScheduleValidationError() throws Exception {
        GameScheduleDto invalidDto = new GameScheduleDto("", ""); // Assuming validation exists
        String json = objectMapper.writeValueAsString(invalidDto);

        mockMvc.perform(post(apiBasePath + "/gameSchedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    void testAddGameToScheduleSuccess() throws Exception {
        Long scheduleId = 1L;
        String json = objectMapper.writeValueAsString(gameRequestDto);

        given(gameScheduleService.addGame(eq(scheduleId), any(GameRequestDto.class))).willReturn(createdGame);

        mockMvc.perform(post(apiBasePath + "/gameSchedule/{scheduleId}/games", scheduleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.flag", is(true)))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value())))
                .andExpect(jsonPath("$.message", is("Add Success")))
                .andExpect(jsonPath("$.data.gameId", is(101)))
                .andExpect(jsonPath("$.data.scheduleId", is(1)))
                .andExpect(jsonPath("$.data.opponent", is("Rival U")))
                .andExpect(jsonPath("$.data.gameDate", is("2025-09-15")))
                .andExpect(jsonPath("$.data.isFinalized", is(false)));

        verify(gameScheduleService).addGame(eq(scheduleId), any(GameRequestDto.class));
    }

    @Test
    void testAddGameToScheduleValidationError() throws Exception {
        Long scheduleId = 1L;
        GameRequestDto invalidDto = new GameRequestDto(null, "", "", null); // Assuming validation exists
        String json = objectMapper.writeValueAsString(invalidDto);

        mockMvc.perform(post(apiBasePath + "/gameSchedule/{scheduleId}/games", scheduleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddGameToScheduleNotFound() throws Exception {
        Long scheduleId = 99L;
        String json = objectMapper.writeValueAsString(gameRequestDto);

        given(gameScheduleService.addGame(eq(scheduleId), any(GameRequestDto.class)))
                .willThrow(new ObjectNotFoundException("game schedule", scheduleId));

        mockMvc.perform(post(apiBasePath + "/gameSchedule/{scheduleId}/games", scheduleId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                        .andExpect(jsonPath("$.message").value("Could not find game schedule with id " + scheduleId))
                .andExpect(jsonPath("$.flag").value(is(false)));
        ;

        verify(gameScheduleService).addGame(eq(scheduleId), any(GameRequestDto.class));
    }
}