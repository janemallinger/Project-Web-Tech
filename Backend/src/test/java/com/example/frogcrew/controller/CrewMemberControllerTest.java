package com.example.frogcrew.controller;

import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.service.CrewMemberService;
import com.example.frogcrew.model.CrewMember;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CrewMemberControllerTest {

    @MockitoBean
    private CrewMemberService crewMemberService;

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
    void testFindAllCrewMembers() throws Exception {
        given(this.crewMemberService.findAll()).willReturn(new ArrayList<>(members));
        this.mockMvc.perform(get("/api/v1/crewMember").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Find Success"))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data", hasSize(5)))
                .andExpect(jsonPath("$.data[*].userId").value(containsInAnyOrder(1,2,3,4,5)))
                .andExpect(jsonPath("$.data[*].firstName").value(containsInAnyOrder("John", "Bob", "Alice", "Jane", "Peter")))
                .andExpect(jsonPath("$.data[*].lastName").value(containsInAnyOrder("Doe", "Brown", "Williams", "Smith", "Jones")))
                .andExpect(jsonPath("$.data[*].email").value(containsInAnyOrder("john.doe@example.com", "bob.brown@example.com", "alice.williams@example.com", "jane.smith@example.com", "peter.jones@example.com")))
                .andExpect(jsonPath("$.data[*].phoneNumber").value(containsInAnyOrder("1234567890", "1029384756", "5544332211", "1112223333", "1122334455")))
                .andExpect(jsonPath("$.data[*].role").value(containsInAnyOrder("ADMIN", "MEMBER", "MEMBER", "MEMBER", "ADMIN")))
                ;

    }
    @Test
    void testFindByIdNotFound() throws Exception {
        //given
        Long idToFind = 10L;
        given(this.crewMemberService.findById(idToFind)).willThrow(new ObjectNotFoundException(idToFind));


        //when and then
        this.mockMvc.perform(get("/api/v1/crewMember/{id}", idToFind).accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(false))
                .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.message").value("Could not find user with id " +idToFind))
                ;

    }

    @Test
    void testFindAllCrewMembersWhenEmpty() throws Exception {
        given(this.crewMemberService.findAll()).willReturn(new ArrayList<>());
        this.mockMvc.perform(get("/api/v1/crewMember").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(HttpStatus.NO_CONTENT.value()))
                .andExpect(jsonPath("$.message").value("No members found"))
                .andExpect(jsonPath("$.data").isEmpty());

    }
    @Test
    void testFindMemberById() throws Exception {
        given(this.crewMemberService.findById(1L)).willReturn(members.get(0));
        this.mockMvc.perform(get("/api/v1/crewMember/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Found member with Id: 1"))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.firstName").value("John"))
                .andExpect(jsonPath("$.data.lastName").value("Doe"))
                .andExpect(jsonPath("$.data.email").value("john.doe@example.com"));
    }
    @Test
    void testDeleteCrewMember() throws Exception{
        Long userIdToDelete = 1L;
        Mockito.doNothing().when(this.crewMemberService).deleteCrewMemberByID(userIdToDelete);

        this.mockMvc.perform(delete("/api/v1/crewMember/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.flag").value(true))
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.message").value("Delete Success of crew member with id: "+userIdToDelete));

        verify(this.crewMemberService, times(1)).deleteCrewMemberByID(1L);
    }
    public static String asJsonString(final Object obj) {

        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}