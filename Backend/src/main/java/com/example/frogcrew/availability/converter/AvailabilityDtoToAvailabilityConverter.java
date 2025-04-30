package com.example.frogcrew.availability.converter;

import com.example.frogcrew.availability.Availability;
import com.example.frogcrew.availability.dto.AvailabilityDto;
import com.example.frogcrew.crewmember.model.CrewMember;
import com.example.frogcrew.crewmember.repository.CrewMemberRepository;
import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.repository.GameRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AvailabilityDtoToAvailabilityConverter implements Converter<AvailabilityDto, Availability> {
    private final CrewMemberRepository crewMemberRepository;
    private final GameRepository gameRepo;

    public AvailabilityDtoToAvailabilityConverter(
            CrewMemberRepository memberRepo,
            GameRepository       gameRepo
    ) {
        this.crewMemberRepository = memberRepo;
        this.gameRepo   = gameRepo;
    }

    @Override
    public Availability convert(AvailabilityDto source) {

        CrewMember member = crewMemberRepository.findById(source.userId())
                .orElseThrow(() -> new ObjectNotFoundException("CrewMember", source.userId()));
        Game game = gameRepo.findById(source.gameId())
                .orElseThrow(() -> new ObjectNotFoundException("Game", source.gameId()));

        Availability a = new Availability();
        a.setCrewMember(member);
        a.setGame(game);
        a.setAvailable(source.available());
        a.setComment(source.comment());
        return a;
    }
}
