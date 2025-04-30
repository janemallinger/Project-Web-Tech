package com.example.frogcrew.availability;

import com.example.frogcrew.availability.converter.AvailabilityDtoToAvailabilityConverter;
import com.example.frogcrew.availability.converter.AvailabilityToAvailabilityDtoConverter;
import com.example.frogcrew.availability.dto.AvailabilityDto;
import com.example.frogcrew.crewmember.model.CrewMember;
import com.example.frogcrew.exception.ConflictException;
import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.util.JsonUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AvailabilityControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockitoBean
    private AvailabilityService availabilityService;

    @MockitoBean
    private AvailabilityDtoToAvailabilityConverter availabilityDtoToAvailabilityConverter;

    @MockitoBean
    private AvailabilityToAvailabilityDtoConverter availabilityToAvailabilityDtoConverter;

    @Value("${api.base-path}")
    private String baseUrl;

    Availability availability1;
    AvailabilityDto availabilityDto1;
    CrewMember crewMember1;
    Game game1;


    @BeforeEach
    void setUp() {
        crewMember1 = new CrewMember();
        crewMember1.setUserId(1L);
        crewMember1.setFirstName("Test");
        crewMember1.setLastName("User");

        game1 = new Game();
        game1.setGameId(101L);

        availability1 = new Availability();
        availability1.setId(1L); // ID assigned after save
        availability1.setCrewMember(crewMember1);
        availability1.setGame(game1);
        availability1.setAvailable(true);
        availability1.setComment("Ready to go");

        availabilityDto1 = new AvailabilityDto(
                crewMember1.getUserId(),
                game1.getGameId(),
                availability1.getAvailable(),
                availability1.getComment()
        );
    }

    @Test
    void testAddAvailabilitySuccess() throws Exception {
        // Given
        given(availabilityDtoToAvailabilityConverter.convert(any(AvailabilityDto.class))).willReturn(availability1);
        given(availabilityService.createAvailability(any(Availability.class))).willReturn(availability1);
        given(availabilityToAvailabilityDtoConverter.convert(any(Availability.class))).willReturn(availabilityDto1);

        // Act & Assert
        this.mockMvc.perform(post(baseUrl + "/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(availabilityDto1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(HttpStatus.CREATED.value())) //201
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.userId").value(crewMember1.getUserId()))
                .andExpect(jsonPath("$.data.gameId").value(game1.getGameId()))
                .andExpect(jsonPath("$.data.available").value(true))
                .andExpect(jsonPath("$.data.comment").value("Ready to go"));
    }


    @Test
    void testAddAvailabilityConflict() throws Exception {
        // Arrange
        given(availabilityDtoToAvailabilityConverter.convert(any(AvailabilityDto.class))).willReturn(availability1);
        given(availabilityService.createAvailability(any(Availability.class)))
                .willThrow(new ConflictException("Availability already exists for user "
                        + crewMember1.getUserId() + " and game " + game1.getGameId()));

        // Act & Assert
        this.mockMvc.perform(post(baseUrl + "/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(availabilityDto1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(HttpStatus.CONFLICT.value()))
                .andExpect(jsonPath("$.message").value("Availability already exists for user 1 and game 101"))
         .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void testAddAvailabilityNotFound() throws Exception {
        // Arrange
        // Simulate converter throwing ObjectNotFoundException because CrewMember doesn't exist
        given(availabilityDtoToAvailabilityConverter.convert(any(AvailabilityDto.class)))
                .willThrow(new ObjectNotFoundException("CrewMember", availabilityDto1.userId()));

        // Act & Assert
        this.mockMvc.perform(post(baseUrl + "/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(availabilityDto1))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false)) // Assuming error response structure
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Could not find CrewMember with id " + availabilityDto1.userId()))
        .andExpect(jsonPath("$.data").isEmpty()); // Or null depending on handler
    }

    @Test
    void testAddAvailabilityBadRequest() throws Exception {
        AvailabilityDto invalidDto = new AvailabilityDto(null, game1.getGameId(), true, "Comment");

        // Act & Assert
        this.mockMvc.perform(post(baseUrl + "/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(invalidDto)) // or objectMapper.writeValueAsString(invalidDto)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false)) // Assuming error response structure from validation handler
                .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.message").value("Provided arguments are invalid, see data for details.")) // Example message
                .andExpect(jsonPath("$.data.userId").value("userId is required.")); // Example validation error message
    }
}