package com.example.frogcrew.availability;

import com.example.frogcrew.crewmember.model.CrewMember;
import com.example.frogcrew.crewmember.repository.CrewMemberRepository;
import com.example.frogcrew.exception.ConflictException;
import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.repository.GameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvailabilityServiceTest {

    @Mock
    AvailabilityRepository availabilityRepository;

    @Mock
    GameRepository gameRepository;


    @Mock
    CrewMemberRepository crewMemberRepository;

    @InjectMocks
    AvailabilityService availabilityService;

    Availability availability1;
    CrewMember crewMember1;
    Game game1;

    @BeforeEach
    void setUp() {
        crewMember1 = new CrewMember();
        crewMember1.setUserId(1L);
        crewMember1.setFirstName("Test");
        crewMember1.setLastName("User");
        crewMember1.setEmail("test.user@example.com");

        game1 = new Game();
        game1.setGameId(101L);

        availability1 = new Availability();
        availability1.setId(1L);
        availability1.setCrewMember(crewMember1);
        availability1.setGame(game1);
        availability1.setAvailable(true);
        availability1.setComment("Ready to go");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreateAvailabilitySuccess() {
        // Given
        given(availabilityRepository.existsByCrewMemberAndGame(crewMember1, game1)).willReturn(false);
        // Simulate saving the availability
        given(availabilityRepository.save(any(Availability.class))).willReturn(availability1);

        // When
        Availability savedAvailability = availabilityService.createAvailability(availability1);

        // Then
        assertThat(savedAvailability.getId()).isEqualTo(availability1.getId());
        assertThat(savedAvailability.getCrewMember().getUserId()).isEqualTo(crewMember1.getUserId());
        assertThat(savedAvailability.getGame().getGameId()).isEqualTo(game1.getGameId());
        assertThat(savedAvailability.getAvailable()).isTrue();
        assertThat(savedAvailability.getComment()).isEqualTo(availability1.getComment());
        verify(availabilityRepository, times(1)).existsByCrewMemberAndGame(crewMember1, game1);
        verify(availabilityRepository, times(1)).save(any(Availability.class));
    }

    @Test
    void testCreateAvailabilityConflict() {
        // Given
        given(availabilityRepository.existsByCrewMemberAndGame(crewMember1, game1)).willReturn(true);

        // When
        Throwable thrown = catchThrowable(() -> {
            availabilityService.createAvailability(availability1);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ConflictException.class)
                .hasMessage("Availability already exists for user "
                        + crewMember1.getUserId() + " and game " + game1.getGameId());
        verify(availabilityRepository, times(1)).existsByCrewMemberAndGame(crewMember1, game1);
        verify(availabilityRepository, never()).save(any(Availability.class));
    }

    //this test does not work because the service layer does not check this. the controller(dto) layer does this check via the converter
//    @Test
//    void testAddFailsWhenGameNotFound() {
//        // Given
//        given(crewMemberRepository.existsById(1L)).willReturn(true);
//        given(gameRepository.existsById(2L)).willReturn(false);
//
//        // When
//        Throwable thrown = catchThrowable(() -> availabilityService.createAvailability(availability1));
//
//        // Then
//        assertThat(thrown)
//                .isInstanceOf(ObjectNotFoundException.class)
//                .hasMessage("Could not find game with id 2");
//
//        verify(availabilityRepository, never()).save(any(Availability.class));
//    }
}