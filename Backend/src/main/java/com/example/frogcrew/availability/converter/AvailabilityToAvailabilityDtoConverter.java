package com.example.frogcrew.availability.converter;

import com.example.frogcrew.availability.Availability;
import com.example.frogcrew.availability.dto.AvailabilityDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityToAvailabilityDtoConverter implements Converter<Availability, AvailabilityDto> {

    @Override
    public AvailabilityDto convert(Availability source) {
        return new AvailabilityDto(
                source.getCrewMember().getUserId(),
                source.getGame().getGameId(),
                source.getAvailable(),
                source.getComment()

        );

    }
}
