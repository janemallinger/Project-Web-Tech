package com.example.frogcrew.crewmember.converter;

import com.example.frogcrew.crewmember.dto.response.CrewMemberCreationResponseDTO;
import com.example.frogcrew.crewmember.model.CrewMember;
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
