package com.example.frogcrew.assignment.converter;

import com.example.frogcrew.assignment.Assignment;
import com.example.frogcrew.assignment.dto.CrewAssignmentDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CrewAssignmentToCrewAssignmentDtoConverter implements Converter<Assignment, CrewAssignmentDto> {

    @Override
    public CrewAssignmentDto convert(Assignment source) {
        return new CrewAssignmentDto(
                source.getId(),
                source.getCrewMember().getUserId(),
                source.getGame().getGameId(),
                source.getPosition(),
                source.getCrewMember().getFirstName() + " " + source.getCrewMember().getLastName(),
                source.getReportTime(),
                source.getReportLocation()
        );
    }
}
