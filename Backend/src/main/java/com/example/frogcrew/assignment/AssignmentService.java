package com.example.frogcrew.assignment;

import com.example.frogcrew.assignment.converter.CrewAssignmentDtoToCrewAssignmentConverter;
import com.example.frogcrew.assignment.converter.CrewAssignmentToCrewAssignmentDtoConverter;
import com.example.frogcrew.assignment.dto.CrewAssignmentDto;
import com.example.frogcrew.assignment.dto.CrewListDto;
import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final CrewAssignmentToCrewAssignmentDtoConverter crewAssignmentToCrewAssignmentDtoConverter;
    private final CrewAssignmentDtoToCrewAssignmentConverter crewAssignmentDtoToCrewAssignmentConverter;
    private final GameRepository gameRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, CrewAssignmentToCrewAssignmentDtoConverter crewAssignmentToCrewAssignmentDtoConverter, CrewAssignmentDtoToCrewAssignmentConverter crewAssignmentDtoToCrewAssignmentConverter, GameRepository gameRepository) {
        this.assignmentRepository = assignmentRepository;
        this.crewAssignmentToCrewAssignmentDtoConverter = crewAssignmentToCrewAssignmentDtoConverter;
        this.crewAssignmentDtoToCrewAssignmentConverter = crewAssignmentDtoToCrewAssignmentConverter;
        this.gameRepository = gameRepository;
    }

    public CrewListDto getCrewListForGame(Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ObjectNotFoundException("Game", gameId));

        List<Assignment> assignments = assignmentRepository.findByGame_GameId(gameId);

        List<CrewAssignmentDto> crewedMembers = assignments.stream()
                .map(crewAssignmentToCrewAssignmentDtoConverter::convert)
                .toList();

        return new CrewListDto(
                game.getGameId(),
                game.getDate().toString(), //based on docs , i have no clue where game time comes from .
                game.getDate().toString(),
                game.getVenue(),
                game.getOpponent(),
                crewedMembers
        );
    }

    public List<CrewAssignmentDto> saveCrewSchedule(Long gameId, List<CrewAssignmentDto> assignments) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ObjectNotFoundException("Game", gameId));

        return assignments.stream()
                .map(dto -> {
                    Assignment assignment = crewAssignmentDtoToCrewAssignmentConverter.convert(dto);
                    assignment.setGame(game);
                    return assignmentRepository.save(assignment);
                })
                .map(crewAssignmentToCrewAssignmentDtoConverter::convert)
                .toList();
    }

}
