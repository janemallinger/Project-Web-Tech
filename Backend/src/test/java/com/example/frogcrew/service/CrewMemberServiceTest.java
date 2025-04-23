package com.example.frogcrew.service;

import com.example.frogcrew.exception.CrewMemberNotFoundException;
import com.example.frogcrew.model.CrewMember;
import com.example.frogcrew.repository.CrewMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CrewMemberServiceTest {

    @Mock
    private CrewMemberRepository crewMemberRepository;

    @InjectMocks
    private CrewMemberService crewMemberService;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAll() {

        //given
        CrewMember c1 = new CrewMember();
        c1.setUserId(1L);
        c1.setFirstName("John");
        c1.setLastName("Doe");
        c1.setEmail("john.doe@example.com");
        c1.setPhoneNumber("1234567890");
        c1.setPassword("123456");
        c1.setRole("ADMIN");
        c1.setQualifiedPositions(Arrays.asList("DIRECTOR", "PRODUCER"));

        CrewMember c2 = new CrewMember();
        c2.setUserId(2L);
        c2.setFirstName("Jane");
        c2.setLastName("Smith");
        c2.setEmail("jane.smith@example.com");
        c2.setPhoneNumber("1112223333");
        c2.setPassword("abcdef");
        c2.setRole("MEMBER");
        c2.setQualifiedPositions(Arrays.asList("Assistant Director", "EDITOR"));

        List<CrewMember> expectedMembers = Arrays.asList(c1, c2);
        // when
        given(this.crewMemberRepository.findAll()).willReturn(Arrays.asList(c1, c2));

        //then
        List<CrewMember> resultMembers = this.crewMemberService.findAll();

        assertThat(resultMembers).isNotNull();
        assertThat(resultMembers).isEqualTo(expectedMembers);
        assertThat(resultMembers.size()).isEqualTo(2);
        assertThat(resultMembers).isEqualTo(expectedMembers);
        verify(this.crewMemberRepository, times(1)).findAll();

    }

    @Test
    void testFindById() {
        //given. arrange inputs and targets. define behavior of mock
        CrewMember a = new CrewMember();
        a.setUserId(1L);
        a.setFirstName("John");
        a.setEmail("john.doe@example.com");
        a.setPhoneNumber("1234567890");
        a.setRole("ADMIN");
        a.setQualifiedPositions(new ArrayList<>(Arrays.asList("DIRECTOR", "PRODUCER")));

        given(this.crewMemberRepository.findById(1L)).willReturn(java.util.Optional.of(a));

        //when . act on the target behavior.
        CrewMember resultCrewMember = this.crewMemberService.findById(1L);

        assertThat(resultCrewMember.getUserId()).isEqualTo(a.getUserId());
        assertThat(resultCrewMember.getFirstName()).isEqualTo(a.getFirstName());
        assertThat(resultCrewMember.getEmail()).isEqualTo(a.getEmail());
        assertThat(resultCrewMember.getPhoneNumber()).isEqualTo(a.getPhoneNumber());
        assertThat(resultCrewMember.getRole()).isEqualTo(a.getRole());
        assertThat(resultCrewMember.getQualifiedPositions()).isEqualTo(a.getQualifiedPositions());

        verify(this.crewMemberRepository, times(1)).findById(1L);

    }

    @Test
    void testFindByIDNotFound() {
        given(crewMemberRepository.findById(Mockito.anyLong())).willReturn(java.util.Optional.empty());

        Throwable thrown = catchThrowable(() -> {
            CrewMember resultCrewMember = this.crewMemberService.findById(1L);
        });

        assertThat(thrown).isInstanceOf(CrewMemberNotFoundException.class).hasMessage("Could not find crew member with id 1");
        verify(this.crewMemberRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateMember() {

        //given. arrange inputs and targets. define behavior of mock
        CrewMember a = new CrewMember();
        a.setUserId(1L);
        a.setFirstName("John");
        a.setEmail("john.doe@example.com");
        a.setPhoneNumber("1234567890");
        a.setRole("ADMIN");
        a.setQualifiedPositions(new ArrayList<>(Arrays.asList("DIRECTOR", "PRODUCER")));

        given(this.crewMemberRepository.save(Mockito.any(CrewMember.class))).willReturn(a);

        //when
        CrewMember resultCrewMember = this.crewMemberService.createMember(a);

        //then
        assertThat(resultCrewMember).isNotNull();
        assertThat(resultCrewMember.getUserId()).isEqualTo(a.getUserId());
        assertThat(resultCrewMember.getFirstName()).isEqualTo(a.getFirstName());
        assertThat(resultCrewMember.getEmail()).isEqualTo(a.getEmail());
        assertThat(resultCrewMember.getPhoneNumber()).isEqualTo(a.getPhoneNumber());
        assertThat(resultCrewMember.getRole()).isEqualTo(a.getRole());
        assertThat(resultCrewMember.getQualifiedPositions()).isEqualTo(a.getQualifiedPositions());

        verify(this.crewMemberRepository, times(1)).save(Mockito.any(CrewMember.class));
    }
}