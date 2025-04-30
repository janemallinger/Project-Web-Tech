package com.example.frogcrew.game.controller;

import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.model.GameSchedule;
import com.example.frogcrew.game.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    MockMvc mockMvc; // To perform simulated HTTP requests

    @MockBean // Create a mock of GameService and add it to the application context
    GameService gameService;

    @Autowired
    ObjectMapper objectMapper; // For JSON serialization/deserialization

    List<Game> games;
    String apiBasePath = "/api/v1"; // Assuming this is your base path, adjust if needed

    @BeforeEach
    void setUp() {
        // Configure Jackson to handle Java 8 Date/Time types
        objectMapper.registerModule(new JavaTimeModule());

        GameSchedule schedule1 = new GameSchedule();
        schedule1.setScheduleId(10L);

        Game game1 = new Game();
        game1.setGameId(1L);
        game1.setOpponent("Team A");
        game1.setVenue("Venue 1");
        game1.setGameDate(LocalDate.of(2025, 10, 1));
        game1.setFinalized(false);
        game1.setSchedule(schedule1); // Link game to schedule

        Game game2 = new Game();
        game2.setGameId(2L);
        game2.setOpponent("Team B");
        game2.setVenue("Venue 2");
        game2.setGameDate(LocalDate.of(2025, 10, 8));
        game2.setFinalized(true);
        game2.setSchedule(schedule1); // Link game to schedule

        games = List.of(game1, game2);
    }

    @Test
    void testGetGeneralGameScheduleSuccess() throws Exception {
        // Given: Mock the service call
        given(gameService.findAllGames()).willReturn(games);

        // When & Then: Perform GET request and assert response
        mockMvc.perform(get(apiBasePath + "/games").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // HTTP status 200
                .andExpect(jsonPath("$.flag", is(true)))
                .andExpect(jsonPath("$.code", is(HttpStatus.OK.value()))) // 200 in Result
                .andExpect(jsonPath("$.message", is("Find Success")))
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.data[0].gameId", is(1)))
                .andExpect(jsonPath("$.data[0].opponent", is("Team A")))
                .andExpect(jsonPath("$.data[0].scheduleId", is(10)))
                .andExpect(jsonPath("$.data[0].isFinalized", is(false)))
                .andExpect(jsonPath("$.data[1].gameId", is(2)))
                .andExpect(jsonPath("$.data[1].opponent", is("Team B")))
                .andExpect(jsonPath("$.data[1].isFinalized", is(true)));
    }

    @Test
    void testGetGeneralGameScheduleNotFound() throws Exception {
        // Given: Mock the service call to return an empty list
        given(gameService.findAllGames()).willReturn(new ArrayList<>());

        // When & Then: Perform GET request and assert response for empty case
        mockMvc.perform(get(apiBasePath + "/games").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Controller returns 200 OK even if empty
                .andExpect(jsonPath("$.flag", is(true)))
                .andExpect(jsonPath("$.code", is(HttpStatus.NO_CONTENT.value()))) // 204 in Result
                .andExpect(jsonPath("$.message", is("No games found")))
                .andExpect(jsonPath("$.data").doesNotExist()); // Data should be null or absent
    }
}