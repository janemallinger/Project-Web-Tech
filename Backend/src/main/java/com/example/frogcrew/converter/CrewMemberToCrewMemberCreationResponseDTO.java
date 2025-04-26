package com.example.frogcrew.converter;

import com.example.frogcrew.dto.response.CrewMemberCreationResponseDTO;
import com.example.frogcrew.model.CrewMember;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CrewMemberToCrewMemberCreationResponseDTO implements Converter<CrewMember, CrewMemberCreationResponseDTO> {

    @Override
    public CrewMemberCreationResponseDTO convert(CrewMember crewMember) {
        return new CrewMemberCreationResponseDTO(
                crewMember.getUserId(),
                crewMember.getFirstName(),
                crewMember.getLastName(),
                crewMember.getEmail(),
                crewMember.getPhoneNumber(),
                crewMember.getRole(),
                crewMember.getQualifiedPositions()
        );
    }
}
