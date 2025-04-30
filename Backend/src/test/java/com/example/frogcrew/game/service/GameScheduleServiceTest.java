package com.example.frogcrew.game.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.game.converter.GameDtoToGame;
import com.example.frogcrew.game.dto.GameRequest.GameRequestDto;
import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.model.GameSchedule;
import com.example.frogcrew.game.repository.GameRepository;
import com.example.frogcrew.game.repository.GameScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameScheduleServiceTest {

    @Mock
    GameRepository gameRepository;

    @Mock
    GameScheduleRepository gameScheduleRepository;

    @Mock
    GameDtoToGame gameDtoToGame;

    @InjectMocks
    GameScheduleService gameScheduleService;

    GameSchedule schedule1;
    GameSchedule schedule2;
    GameRequestDto gameRequestDto;
    Game gameFromDto;
    Game savedGame;

    @BeforeEach
    void setUp() {
        schedule1 = new GameSchedule();
        schedule1.setScheduleId(1L);
        schedule1.setSport("Football");
        schedule1.setSeason("2025-2026");

        schedule2 = new GameSchedule();
        schedule2.setScheduleId(2L);
        schedule2.setSport("Basketball");
        schedule2.setSeason("2025-2026");

        gameRequestDto = new GameRequestDto(
                LocalDate.of(2025, 9, 20),
                "Stadium A",
                "Team B",
                false
        );

        gameFromDto = new Game();
        gameFromDto.setGameDate(LocalDate.of(2025, 9, 20));
        gameFromDto.setVenue("Stadium A");
        gameFromDto.setOpponent("Team B");
        gameFromDto.setFinalized(false);

        savedGame = new Game();
        savedGame.setGameId(101L);
        savedGame.setSchedule(schedule1);
        savedGame.setGameDate(LocalDate.of(2025, 9, 20));
        savedGame.setVenue("Stadium A");
        savedGame.setOpponent("Team B");
        savedGame.setFinalized(false);
    }

    @Test
    void testCreateGameScheduleSuccess() {
        given(gameScheduleRepository.save(schedule1)).willReturn(schedule1);

        GameSchedule createdSchedule = gameScheduleService.createGameSchedule(schedule1);

        assertThat(createdSchedule).isNotNull();
        assertThat(createdSchedule.getScheduleId()).isEqualTo(1L);
        assertThat(createdSchedule.getSport()).isEqualTo("Football");
        verify(gameScheduleRepository, times(1)).save(schedule1);
    }

    @Test
    void testFindAllSchedulesSuccess() {
        List<GameSchedule> schedules = List.of(schedule1, schedule2);
        given(gameScheduleRepository.findAll()).willReturn(schedules);

        List<GameSchedule> foundSchedules = gameScheduleService.findAllSchedules();

        assertThat(foundSchedules).isNotNull();
        assertThat(foundSchedules).hasSize(2);
        assertThat(foundSchedules).containsExactlyInAnyOrder(schedule1, schedule2);
        verify(gameScheduleRepository, times(1)).findAll();
    }

    @Test
    void testFindAllSchedulesEmpty() {
        given(gameScheduleRepository.findAll()).willReturn(List.of());

        List<GameSchedule> foundSchedules = gameScheduleService.findAllSchedules();

        assertThat(foundSchedules).isNotNull();
        assertThat(foundSchedules).isEmpty();
        verify(gameScheduleRepository, times(1)).findAll();
    }


    @Test
    void testAddGameSuccess() {
        // Given
        given(gameScheduleRepository.findById(1L)).willReturn(Optional.of(schedule1));
        given(gameDtoToGame.convert(gameRequestDto)).willReturn(gameFromDto);
        given(gameRepository.save(any(Game.class))).willReturn(savedGame);

        // When
        Game addedGame = gameScheduleService.addGame(1L, gameRequestDto);

        // Then
        assertThat(addedGame).isNotNull();
        assertThat(addedGame.getGameId()).isEqualTo(101L);
        assertThat(addedGame.getSchedule()).isNotNull();
        assertThat(addedGame.getSchedule().getScheduleId()).isEqualTo(1L);
        assertThat(addedGame.getOpponent()).isEqualTo("Team B");

        // Capture the argument passed to gameRepository.save
        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository, times(1)).save(gameArgumentCaptor.capture());

        // Verify that the schedule was set on the game object *before* saving
        Game gameToSave = gameArgumentCaptor.getValue();
        assertThat(gameToSave.getSchedule()).isEqualTo(schedule1);

        verify(gameScheduleRepository, times(1)).findById(1L);
        verify(gameDtoToGame, times(1)).convert(gameRequestDto);
    }

    @Test
    void testAddGameScheduleNotFound() {
        // Given
        Long nonExistentScheduleId = 99L;
        given(gameScheduleRepository.findById(nonExistentScheduleId)).willReturn(Optional.empty());

        // When & Then
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            gameScheduleService.addGame(nonExistentScheduleId, gameRequestDto);
        });

        assertThat(exception.getMessage()).isEqualTo("Could not find game schedule with id " + nonExistentScheduleId);
        verify(gameScheduleRepository, times(1)).findById(nonExistentScheduleId);
        verify(gameDtoToGame, never()).convert(any(GameRequestDto.class));
        verify(gameRepository, never()).save(any(Game.class));
    }
}