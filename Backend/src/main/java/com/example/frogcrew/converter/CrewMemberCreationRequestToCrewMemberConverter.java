package com.example.frogcrew.converter;

import com.example.frogcrew.dto.request.CrewMemberCreationRequestDTO;
import com.example.frogcrew.model.CrewMember;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CrewMemberCreationRequestToCrewMemberConverter  implements Converter<CrewMemberCreationRequestDTO, CrewMember> {
    @Override
    public CrewMember convert(CrewMemberCreationRequestDTO source) {
        CrewMember crewMember = new CrewMember();
        crewMember.setFirstName(source.firstName());
        crewMember.setLastName(source.lastName());
        crewMember.setEmail(source.email());
        crewMember.setPhoneNumber(source.phoneNumber());
        crewMember.setPassword(source.password());
        crewMember.setRole(source.role());
        crewMember.setQualifiedPositions(source.positions());
        return crewMember;
    }

}
