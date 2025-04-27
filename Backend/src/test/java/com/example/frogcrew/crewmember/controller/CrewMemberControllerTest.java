package com.example.frogcrew.crewmember.controller;

import com.example.frogcrew.crewmember.converter.CrewMemberToSimpleCrewMemberDTO;
import com.example.frogcrew.crewmember.dto.request.CrewMemberCreationRequestDTO;
import com.example.frogcrew.crewmember.dto.response.CrewMemberCreationResponseDTO;
import com.example.frogcrew.crewmember.dto.response.SimpleCrewMemberResponse;
import com.example.frogcrew.crewmember.model.CrewMember;
import com.example.frogcrew.crewmember.service.CrewMemberService;
import com.example.frogcrew.exception.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import com.example.frogcrew.util.JsonUtil;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@WebMvcTest(controllers = CrewMemberController.class)
class CrewMemberControllerTest {
    @MockitoBean
    private CrewMemberService crewMemberService;

    @MockitoBean
    CrewMemberCreationResponseDTO responseDTO;
    @MockitoBean
    CrewMemberToSimpleCrewMemberDTO crewMemberToSimpleCrewMemberDTO;

    @MockitoBean
    CrewMemberCreationRequestDTO crewMemberCreationRequestDTO;
    @MockitoBean
    private CrewMemberToSimpleCrewMemberDTO CrewMemberCreationRequestToCrewMemberConverter;

    @Autowired
    private MockMvc mockMvc;


    List<CrewMember> members = new ArrayList<>();

    @BeforeEach
    void setUp() {
        CrewMember c1 = new CrewMember();
        c1.setUserId(1L);
        c1.setFirstName("John");
        c1.setLastName("Doe");
        c1.setEmail("john.doe@example.com");
        c1.setPhoneNumber("1234567890");
        c1.setPassword("123456");
        c1.setRole("ADMIN");
        c1.setQualifiedPositions(Arrays.asList("DIRECTOR", "PRODUCER"));
        this.members.add(c1);

        CrewMember c2 = new CrewMember();
        c2.setUserId(2L);
        c2.setFirstName("Jane");
        c2.setLastName("Smith");
        c2.setEmail("jane.smith@example.com");
        c2.setPhoneNumber("1112223333");
        c2.setPassword("abcdef");
        c2.setRole("MEMBER");
        c2.setQualifiedPositions(Arrays.asList("Assistant Director", "EDITOR"));
        this.members.add(c2);

        CrewMember c3 = new CrewMember();
        c3.setUserId(3L);
        c3.setFirstName("Peter");
        c3.setLastName("Jones");
        c3.setEmail("peter.jones@example.com");
        c3.setPhoneNumber("1122334455");
        c3.setPassword("ghijkl");
        c3.setRole("MEMBER");
        c3.setQualifiedPositions(List.of("CAMERA OPERATOR"));
        this.members.add(c3);

        CrewMember c4 = new CrewMember();
        c4.setUserId(4L);
        c4.setFirstName("Alice");
        c4.setLastName("Williams");
        c4.setEmail("alice.williams@example.com");
        c4.setPhoneNumber("5544332211");
        c4.setPassword("mnopqr");
        c4.setRole("ADMIN");
        c4.setQualifiedPositions(List.of("DIRECTOR"));
        this.members.add(c4);

        CrewMember c5 = new CrewMember();
        c5.setUserId(5L);
        c5.setFirstName("Bob");
        c5.setLastName("Brown");
        c5.setEmail("bob.brown@example.com");
        c5.setPhoneNumber("1029384756");
        c5.setPassword("stuvwx");
        c5.setRole("MEMBER");
        c5.setQualifiedPositions(List.of("Graphics Operator"));
        this.members.add(c5);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCrewMember() throws Exception {
        CrewMember member = this.members.get(0);

        CrewMemberCreationRequestDTO requestDTO = new CrewMemberCreationRequestDTO(
                "John", "Doe", "john.doe@example.com", "1234567890", "123456", "ADMIN", Arrays.asList("DIRECTOR", "PRODUCER")
        );

        given(this.crewMemberService.createMember(any(CrewMember.class))).willReturn(member);

        this.mockMvc.perform(post("/api/v1/crewMember")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(requestDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Add Success"))
                .andExpect(jsonPath("$.data.userId").value(member.getUserId()))
                .andExpect(jsonPath("$.data.firstName").value(member.getFirstName()))
                .andExpect(jsonPath("$.data.lastName").value(member.getLastName()))
                .andExpect(jsonPath("$.data.email").value(member.getEmail()))
                .andExpect(jsonPath("$.data.phoneNumber").value(member.getPhoneNumber()))
                .andExpect(jsonPath("$.data.role").value(member.getRole()))
                .andExpect(jsonPath("$.data.positions").value(containsInAnyOrder("DIRECTOR", "PRODUCER")));
    }

    @Test
    void findAllCrewMembers_whenMembersExist() throws Exception {
        // Arrange: Return a list of CrewMember objects from the service
        given(this.crewMemberService.findAll()).willReturn(new ArrayList<>(members));

        // Mock the conversion from CrewMember to SimpleCrewMemberResponse
        given(crewMemberToSimpleCrewMemberDTO.convert(any(CrewMember.class)))
                .willAnswer(invocation -> {
                    CrewMember member = invocation.getArgument(0);
                    return new SimpleCrewMemberResponse(
                            member.getUserId(),
                            member.getFirstName() + " " + member.getLastName(),
                            member.getEmail(),
                            member.getPhoneNumber()
                    );
                });

        this.mockMvc.perform(get("/api/v1/crewMember").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())  // Expect HTTP 200
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Find Success"))  // Success message
                .andExpect(jsonPath("$.data").isArray())  // Ensure 'data' is an array
                .andExpect(jsonPath("$.data", hasSize(5)))  // Check the number of members
                .andExpect(jsonPath("$.data[*].userId").value(containsInAnyOrder(1, 2, 3, 4, 5)))  // Check userIds
                .andExpect(jsonPath("$.data[*].fullName").value(containsInAnyOrder(
                        "John Doe", "Jane Smith", "Peter Jones", "Alice Williams", "Bob Brown")))  // Check full names
                .andExpect(jsonPath("$.data[*].email").value(containsInAnyOrder(
                        "john.doe@example.com", "bob.brown@example.com", "alice.williams@example.com",
                        "jane.smith@example.com", "peter.jones@example.com")))  // Check emails
                .andExpect(jsonPath("$.data[*].phoneNumber").value(containsInAnyOrder(
                        "1234567890", "1029384756", "5544332211", "1112223333", "1122334455")))  // Check phone numbers
        ;
    }

    @Test
    void findAllCrewMembers_whenNoMembersExist() throws Exception {
        given(this.crewMemberService.findAll()).willReturn(new ArrayList<>());

        // Act & Assert: Perform GET request and check the response for no members
        this.mockMvc.perform(get("/api/v1/crewMember").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))  // Success flag
                .andExpect(jsonPath("$.code").value(HttpStatus.NO_CONTENT.value()))  // Status code
                .andExpect(jsonPath("$.message").value("No members found"))  // Message for no content
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void deleteCrewMember_whenMemberExists() throws Exception {
        CrewMember c1 = members.get(0);
        given(this.crewMemberService.findById(c1.getUserId())).willReturn(c1);

        this.mockMvc.perform(delete("/api/v1/crewMember/{id}", c1.getUserId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.message").value("Delete Success of crew member with id: "+c1.getUserId()));
    }

    @Test
    void deleteCrewMember_whenMemberDoesNotExist() throws Exception {
        Long nonExistingId = 999L;
        doThrow(new ObjectNotFoundException(nonExistingId)).when(this.crewMemberService).deleteCrewMemberByID(nonExistingId);


        this.mockMvc.perform(delete("/api/v1/crewMember/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.message").value("Could not find user with id 999"));
    }
}