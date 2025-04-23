package com.example.frogcrew.controller;

import com.example.frogcrew.service.CrewMemberService;
import com.example.frogcrew.model.CrewMember;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

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
        c2.setPhoneNumber("9876543210");
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
    void findAllCrewMembers() {
    }
}