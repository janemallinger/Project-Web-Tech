package com.example.frogcrew.availability;

import com.example.frogcrew.exception.ConflictException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AvailabilityService {
    private final AvailabilityRepository availabilityRepository;

    public AvailabilityService(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public Availability createAvailability(Availability availability) {

        boolean availabilityExists = availabilityRepository.existsByCrewMemberAndGame(
                availability.getCrewMember(), availability.getGame());

        if (availabilityExists) {
            throw new ConflictException("Availability already exists for user "
                    + availability.getCrewMember().getUserId() + " and game " + availability.getGame().getGameId());
        }

        return availabilityRepository.save(availability);
    }
}

