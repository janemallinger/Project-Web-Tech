package com.example.frogcrew.availability.converter; // Adjust package if necessary

import com.example.frogcrew.availability.Availability;
import com.example.frogcrew.availability.dto.AvailabilityDto;
import com.example.frogcrew.crewmember.model.CrewMember; // Assuming model location
import com.example.frogcrew.crewmember.repository.CrewMemberRepository; // Assuming repository location
import com.example.frogcrew.game.model.Game; // Assuming model location
import com.example.frogcrew.game.repository.GameRepository; // Assuming repository location
import com.example.frogcrew.exception.ObjectNotFoundException; // Assuming exception location

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AvailabilityDtoToAvailabilityConverterTest {

    @Mock
    CrewMemberRepository crewMemberRepository;

    @Mock
    GameRepository gameRepository;


    @InjectMocks
    AvailabilityDtoToAvailabilityConverter converter;

    CrewMember crewMember;
    Game game;
    AvailabilityDto availabilityDto;

    @BeforeEach
    void setUp() {
        // Initialize common test data
        crewMember = new CrewMember();
        crewMember.setUserId(1L);
        crewMember.setFirstName("Test");
        crewMember.setLastName("Member");

        game = new Game();
        game.setGameId(101L);
        game.setVenue("Test Stadium");
        availabilityDto = new AvailabilityDto(
                crewMember.getUserId(),
                game.getGameId(),
                true,
                "Test comment"
        );

    }

    @Test
    void testConvertSuccess() {
        given(crewMemberRepository.findById(1L)).willReturn(Optional.of(crewMember));
        given(gameRepository.findById(101L)).willReturn(Optional.of(game));

        // Act: Call the converter method
        Availability result = converter.convert(availabilityDto);

        assertThat(result).isNotNull();
        assertThat(result.getCrewMember()).isEqualTo(crewMember);
        assertThat(result.getGame()).isEqualTo(game);
        assertThat(result.getAvailable()).isTrue();
        assertThat(result.getComment()).isEqualTo("Test comment");

        // Verify repository methods were called
        verify(crewMemberRepository, times(1)).findById(1L);
        verify(gameRepository, times(1)).findById(101L);
    }

    @Test
    void testConvertThrowsObjectNotFoundExceptionWhenCrewMemberNotFound() {
        Long memberId = 1L;
        Long gameId = 101L;
        AvailabilityDto dto = new AvailabilityDto(memberId, gameId, true, "Test");

        given(crewMemberRepository.findById(memberId)).willReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> {
            converter.convert(dto);
        });

        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessageContaining("Could not find CrewMember with id " + memberId); // Adjust message based on your exception's format

        verify(crewMemberRepository, times(1)).findById(memberId);
        verify(gameRepository, times(0)).findById(gameId); //exception should be thrown before we try to find the game
    }

    @Test
    void testConvertThrowsObjectNotFoundExceptionWhenGameNotFound() {
        Long memberId = 1L;
        Long gameId = 101L;
        AvailabilityDto dto = new AvailabilityDto(memberId, gameId, true, "Test");

        given(crewMemberRepository.findById(memberId)).willReturn(Optional.of(crewMember));
        given(gameRepository.findById(gameId)).willReturn(Optional.empty());

        // Act: Call the converter method and catch the thrown exception
        Throwable thrown = catchThrowable(() -> {
            converter.convert(dto);
        });

        // Assert: Check that the correct exception was thrown
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                // Use the specific class name from your ObjectNotFoundException if it includes it
                .hasMessageContaining("Could not find Game with id " + gameId); // Adjust message based on your exception's format

        // Verify repository methods were called
        verify(crewMemberRepository, times(1)).findById(memberId);
        verify(gameRepository, times(1)).findById(gameId);
    }
}