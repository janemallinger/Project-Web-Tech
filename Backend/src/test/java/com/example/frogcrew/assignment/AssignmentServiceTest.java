package com.example.frogcrew.assignment;

import com.example.frogcrew.assignment.converter.CrewAssignmentDtoToCrewAssignmentConverter;
import com.example.frogcrew.assignment.converter.CrewAssignmentToCrewAssignmentDtoConverter;
import com.example.frogcrew.assignment.dto.CrewAssignmentDto;
import com.example.frogcrew.assignment.dto.CrewListDto;
import com.example.frogcrew.crewmember.model.CrewMember; // Assuming structure
import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.game.model.Game; // Assuming structure
import com.example.frogcrew.game.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {

    @Mock
    AssignmentRepository assignmentRepository;

    @Mock
    GameRepository gameRepository;

    @Mock
    CrewAssignmentToCrewAssignmentDtoConverter crewAssignmentToCrewAssignmentDtoConverter;

    @Mock
    CrewAssignmentDtoToCrewAssignmentConverter crewAssignmentDtoToCrewAssignmentConverter;

    @InjectMocks
    AssignmentService assignmentService;

    Game game;
    CrewMember crewMember1;
    CrewMember crewMember2;
    Assignment assignment1;
    Assignment assignment2;
    CrewAssignmentDto assignmentDto1;
    CrewAssignmentDto assignmentDto2;
    List<Assignment> assignments;
    List<CrewAssignmentDto> assignmentDtos;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setGameId(1L);
        game.setOpponent("Rival U");
        game.setVenue("Home Stadium");
        // Assuming Game has LocalDate - adjust if using String
        game.setDate(LocalDate.of(2025, 9, 15));
        // Add time if Game object has it

        crewMember1 = new CrewMember();
        crewMember1.setUserId(101L);
        crewMember1.setFirstName("Jane");
        crewMember1.setLastName("Doe");

        crewMember2 = new CrewMember();
        crewMember2.setUserId(102L);
        crewMember2.setFirstName("John");
        crewMember2.setLastName("Smith");

        assignment1 = new Assignment();
        assignment1.setId(1L);
        assignment1.setGame(game);
        assignment1.setCrewMember(crewMember1);
        assignment1.setPosition("Camera 1");
        assignment1.setReportTime(LocalTime.of(15, 0));
        assignment1.setReportLocation("Gate A");

        assignment2 = new Assignment();
        assignment2.setId(2L);
        assignment2.setGame(game);
        assignment2.setCrewMember(crewMember2);
        assignment2.setPosition("Director");
        assignment2.setReportTime(LocalTime.of(14, 30));
        assignment2.setReportLocation("Control Room");

        assignments = List.of(assignment1, assignment2);

        // Assume DTOs corresponding to the assignments
        assignmentDto1 = new CrewAssignmentDto(1L, 101L, 1L, "Camera 1", "Jane Doe", LocalTime.of(15, 0), "Gate A");
        assignmentDto2 = new CrewAssignmentDto(2L, 102L, 1L, "Director", "John Smith", LocalTime.of(14, 30), "Control Room");
        assignmentDtos = List.of(assignmentDto1, assignmentDto2);
    }

    @Test
    void testGetCrewListForGameSuccess() {
        // Given
        given(gameRepository.findById(1L)).willReturn(Optional.of(game));
        given(assignmentRepository.findByGame_GameId(1L)).willReturn(assignments);
        given(crewAssignmentToCrewAssignmentDtoConverter.convert(assignment1)).willReturn(assignmentDto1);
        given(crewAssignmentToCrewAssignmentDtoConverter.convert(assignment2)).willReturn(assignmentDto2);

        // When
        CrewListDto result = assignmentService.getCrewListForGame(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.gameId()).isEqualTo(1L);
        assertThat(result.opponent()).isEqualTo("Rival U");
        assertThat(result.venue()).isEqualTo("Home Stadium");
        assertThat(result.gameDate()).isEqualTo(game.getDate().toString()); // same thing .. docs dont specify how this came to be
        assertThat(result.gameStart()).isEqualTo(game.getDate().toString()); //
        assertThat(result.crewedMembers()).hasSize(2);
        assertThat(result.crewedMembers()).containsExactlyInAnyOrder(assignmentDto1, assignmentDto2);

        verify(gameRepository, times(1)).findById(1L);
        verify(assignmentRepository, times(1)).findByGame_GameId(1L);
        verify(crewAssignmentToCrewAssignmentDtoConverter, times(1)).convert(assignment1);
        verify(crewAssignmentToCrewAssignmentDtoConverter, times(1)).convert(assignment2);
    }

    @Test
    void testGetCrewListForGameNotFound() {
        // Given
        given(gameRepository.findById(99L)).willReturn(Optional.empty());

        // When & Then
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            assignmentService.getCrewListForGame(99L);
        });
        assertThat(exception.getMessage()).isEqualTo("Could not find Game with id 99");
        verify(gameRepository, times(1)).findById(99L);
        verify(assignmentRepository, never()).findByGame_GameId(anyLong());
    }

    @Test
    void testGetCrewListForGameNoAssignments() {
        // Given
        given(gameRepository.findById(1L)).willReturn(Optional.of(game));
        // Return empty list when repository is called
        given(assignmentRepository.findByGame_GameId(1L)).willReturn(new ArrayList<>());

        // When
        CrewListDto result = assignmentService.getCrewListForGame(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.gameId()).isEqualTo(1L);
        assertThat(result.opponent()).isEqualTo("Rival U");
        assertThat(result.crewedMembers()).isNotNull();
        assertThat(result.crewedMembers()).isEmpty(); // Expect empty list

        verify(gameRepository, times(1)).findById(1L);
        verify(assignmentRepository, times(1)).findByGame_GameId(1L);
        verify(crewAssignmentToCrewAssignmentDtoConverter, never()).convert(any(Assignment.class)); // Converter should not be called
    }


    @Test
    void testSaveCrewScheduleSuccess() {
        // Given
        // DTOs for saving (crewedUserId might be null initially)
        CrewAssignmentDto inputDto1 = new CrewAssignmentDto(null, 101L, 1L, "Camera 1", null, LocalTime.of(15, 0), "Gate A");
        CrewAssignmentDto inputDto2 = new CrewAssignmentDto(null, 102L, 1L, "Director", null, LocalTime.of(14, 30), "Control Room");
        List<CrewAssignmentDto> inputDtos = List.of(inputDto1, inputDto2);

        // Corresponding entities returned by the converter
        Assignment convertedAssignment1 = new Assignment(); // Assume converter creates this
        convertedAssignment1.setCrewMember(crewMember1); // Converter would set this

        convertedAssignment1.setGame(game);

        Assignment convertedAssignment2 = new Assignment();
        convertedAssignment2.setCrewMember(crewMember2);
        // ... other fields set by converter
        convertedAssignment2.setGame(game); // Service sets this

        // Saved entities (with IDs)
        Assignment savedAssignment1 = assignment1; // Assume repository returns this
        Assignment savedAssignment2 = assignment2; // Assume repository returns this


        given(gameRepository.findById(1L)).willReturn(Optional.of(game));
        // Mock the DTO -> Entity conversion
        given(crewAssignmentDtoToCrewAssignmentConverter.convert(inputDto1)).willReturn(convertedAssignment1);
        given(crewAssignmentDtoToCrewAssignmentConverter.convert(inputDto2)).willReturn(convertedAssignment2);
        // Mock the repository save operation
        given(assignmentRepository.save(convertedAssignment1)).willReturn(savedAssignment1);
        given(assignmentRepository.save(convertedAssignment2)).willReturn(savedAssignment2);
        // Mock the Entity -> DTO conversion for the result
        given(crewAssignmentToCrewAssignmentDtoConverter.convert(savedAssignment1)).willReturn(assignmentDto1);
        given(crewAssignmentToCrewAssignmentDtoConverter.convert(savedAssignment2)).willReturn(assignmentDto2);


        // When
        List<CrewAssignmentDto> resultDtos = assignmentService.saveCrewSchedule(1L, inputDtos);

        // Then
        assertThat(resultDtos).isNotNull();
        assertThat(resultDtos).hasSize(2);
        assertThat(resultDtos).containsExactlyInAnyOrder(assignmentDto1, assignmentDto2);

        verify(gameRepository, times(1)).findById(1L);
        verify(crewAssignmentDtoToCrewAssignmentConverter, times(1)).convert(inputDto1);
        verify(crewAssignmentDtoToCrewAssignmentConverter, times(1)).convert(inputDto2);
        verify(assignmentRepository, times(1)).save(convertedAssignment1);
        verify(assignmentRepository, times(1)).save(convertedAssignment2);
        verify(crewAssignmentToCrewAssignmentDtoConverter, times(1)).convert(savedAssignment1); // Check conversion back to DTO
        verify(crewAssignmentToCrewAssignmentDtoConverter, times(1)).convert(savedAssignment2);
    }

    @Test
    void testSaveCrewScheduleGameNotFound() {
        // Given
        // DTOs for saving (crewedUserId might be null initially)
        CrewAssignmentDto inputDto1 = new CrewAssignmentDto(null, 101L, 99L, "Camera 1", null, LocalTime.of(15, 0), "Gate A");
        List<CrewAssignmentDto> inputDtos = List.of(inputDto1);

        given(gameRepository.findById(99L)).willReturn(Optional.empty());

        // When & Then
        ObjectNotFoundException exception = assertThrows(ObjectNotFoundException.class, () -> {
            assignmentService.saveCrewSchedule(99L, inputDtos);
        });
        assertThat(exception.getMessage()).isEqualTo("Could not find Game with id 99");

        verify(gameRepository, times(1)).findById(99L);
        verify(assignmentRepository, never()).save(any(Assignment.class)); // Save should not be called
        verify(crewAssignmentDtoToCrewAssignmentConverter, never()).convert(any(CrewAssignmentDto.class));
        verify(crewAssignmentToCrewAssignmentDtoConverter, never()).convert(any(Assignment.class));
    }
}