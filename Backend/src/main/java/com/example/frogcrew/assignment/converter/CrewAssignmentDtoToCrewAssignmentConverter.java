package com.example.frogcrew.assignment.converter;

import com.example.frogcrew.assignment.Assignment;
import com.example.frogcrew.assignment.dto.CrewAssignmentDto;
import com.example.frogcrew.crewmember.model.CrewMember;
import com.example.frogcrew.crewmember.repository.CrewMemberRepository;
import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.repository.GameRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CrewAssignmentDtoToCrewAssignmentConverter implements Converter<CrewAssignmentDto, Assignment> {

    private final CrewMemberRepository crewMemberRepository;
    private final GameRepository gameRepository;

    public CrewAssignmentDtoToCrewAssignmentConverter(CrewMemberRepository crewMemberRepository,
                                                      GameRepository gameRepository) {
        this.crewMemberRepository = crewMemberRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Assignment convert(CrewAssignmentDto source) {
        Assignment assignment = new Assignment();

        CrewMember crewMember = crewMemberRepository.findById(source.userId())
                .orElseThrow(() -> new ObjectNotFoundException("CrewMember", source.userId()));

        Game game = gameRepository.findById(source.gameId())
                .orElseThrow(() -> new ObjectNotFoundException("Game", source.gameId()));

        assignment.setId(source.crewedUserId());
        assignment.setCrewMember(crewMember);
        assignment.setGame(game);
        assignment.setPosition(source.position());
        assignment.setReportTime(source.reportTime());
        assignment.setReportLocation(source.reportLocation());

        return assignment;
    }
}