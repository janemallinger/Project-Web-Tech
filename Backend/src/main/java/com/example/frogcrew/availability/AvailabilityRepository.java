package com.example.frogcrew.availability;

import com.example.frogcrew.crewmember.model.CrewMember;
import com.example.frogcrew.game.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    //check game and crew member
    boolean existsByCrewMemberAndGame(CrewMember crewMember, Game game);
}
