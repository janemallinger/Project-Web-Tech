package com.example.frogcrew.game.service;

import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    GameRepository gameRepository;

    @InjectMocks
    GameService gameService;

    List<Game> games;

    @BeforeEach
    void setUp() {
        Game game1 = new Game();
        game1.setGameId(1L);
        game1.setOpponent("Team A");
        game1.setVenue("Venue 1");
        game1.setGameDate(LocalDate.of(2025, 10, 1));

        Game game2 = new Game();
        game2.setGameId(2L);
        game2.setOpponent("Team B");
        game2.setVenue("Venue 2");
        game2.setGameDate(LocalDate.of(2025, 10, 8));

        games = List.of(game1, game2);
    }

    @Test
    void testFindAllGamesSuccess() {
        // Given
        given(gameRepository.findAll()).willReturn(games);

        // When
        List<Game> actualGames = gameService.findAllGames();

        // Then
        assertThat(actualGames).isNotNull();
        assertThat(actualGames).hasSize(2);
        assertThat(actualGames).isEqualTo(games);
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    void testFindAllGamesEmpty() {
        // Given
        given(gameRepository.findAll()).willReturn(new ArrayList<>());

        // When
        List<Game> actualGames = gameService.findAllGames();

        // Then
        assertThat(actualGames).isNotNull();
        assertThat(actualGames).isEmpty();
        verify(gameRepository, times(1)).findAll();
    }
}