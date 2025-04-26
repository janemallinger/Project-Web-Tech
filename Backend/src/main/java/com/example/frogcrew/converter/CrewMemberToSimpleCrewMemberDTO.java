package com.example.frogcrew.converter;

import com.example.frogcrew.dto.SimpleCrewMemberResponse;
import com.example.frogcrew.model.CrewMember;
import org.springframework.core.convert.converter.Converter;

public class CrewMemberToSimpleCrewMemberDTO implements Converter<CrewMember, SimpleCrewMemberResponse> {
    @Override
    public SimpleCrewMemberResponse convert(CrewMember crewMember) {
        String fullName = crewMember.getFirstName() + " " + crewMember.getLastName();
        return new SimpleCrewMemberResponse(
                crewMember.getUserId(),
                fullName,
                crewMember.getEmail(),
                crewMember.getPhoneNumber()
        );
    }

}
