package com.example.frogcrew.crewmember.service;

import com.example.frogcrew.crewmember.model.CrewMember;
import com.example.frogcrew.crewmember.repository.CrewMemberRepository;
import com.example.frogcrew.exception.DuplicateEmailException;
import com.example.frogcrew.exception.ObjectNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrewMemberServiceTest {

    @Mock
    CrewMemberRepository crewMemberRepository;

    @InjectMocks
    CrewMemberService crewMemberService;

    List<CrewMember> crewMembers;

    @BeforeEach
    void setUp() {
        CrewMember cm1 = new CrewMember();
        cm1.setUserId(1L);
        cm1.setFirstName("John");
        cm1.setLastName("Doe");
        cm1.setEmail("john@example.com");

        CrewMember cm2 = new CrewMember();
        cm2.setUserId(2L);
        cm2.setFirstName("Jane");
        cm2.setLastName("Smith");
        cm2.setEmail("jane@example.com");

        this.crewMembers = new ArrayList<>();
        this.crewMembers.add(cm1);
        this.crewMembers.add(cm2);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindAllSuccess() {
        // Given
        given(crewMemberRepository.findAll()).willReturn(this.crewMembers);

        // When
        List<CrewMember> actualCrewMembers = crewMemberService.findAll();

        // Then
        assertThat(actualCrewMembers.size()).isEqualTo(this.crewMembers.size());
        verify(crewMemberRepository, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        // Given
        CrewMember cm = this.crewMembers.get(0);

        given(crewMemberRepository.findById(1L)).willReturn(Optional.of(cm));

        // When
        CrewMember returnedCrewMember = crewMemberService.findById(1L);

        // Then
        assertThat(returnedCrewMember.getUserId()).isEqualTo(cm.getUserId());
        assertThat(returnedCrewMember.getFirstName()).isEqualTo(cm.getFirstName());
        assertThat(returnedCrewMember.getLastName()).isEqualTo(cm.getLastName());
        assertThat(returnedCrewMember.getEmail()).isEqualTo(cm.getEmail());
        verify(crewMemberRepository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        // Given
        given(crewMemberRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> {
            CrewMember returnedCrewMember = crewMemberService.findById(1L);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find crew member with id 1");
        verify(crewMemberRepository, times(1)).findById(Mockito.anyLong());
    }

    @Test
    void testCreateMemberSuccess() {
        // Given
        CrewMember newCrewMember = this.crewMembers.get(1);

        given(crewMemberRepository.save(newCrewMember)).willReturn(newCrewMember);

        // When
        CrewMember returnedCrewMember = crewMemberService.createMember(newCrewMember);

        // Then
        assertThat(returnedCrewMember.getFirstName()).isEqualTo(newCrewMember.getFirstName());
        assertThat(returnedCrewMember.getLastName()).isEqualTo(newCrewMember.getLastName());
        verify(crewMemberRepository, times(1)).save(newCrewMember);
    }

    @Test
    void testCreateMemberDuplicateEmail() {
        // Given
        CrewMember newCrewMember = this.crewMembers.get(0);

        when(crewMemberRepository.existsByEmail(newCrewMember.getEmail())).thenReturn(true);

        // When
        Throwable thrown = assertThrows(DuplicateEmailException.class, ()-> {
            this.crewMemberService.createMember(newCrewMember);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(DuplicateEmailException.class)
                .hasMessage("Email is already in use.");
        verify(crewMemberRepository, times(1)).existsByEmail("john@example.com");
    }

    @Test
    void testDeleteCrewMemberByIDSuccess() {

        given(crewMemberRepository.existsById(1L)).willReturn(true);
        doNothing().when(crewMemberRepository).deleteById(1L);

        // When
        crewMemberService.deleteCrewMemberByID(1L);

        // Then
        verify(crewMemberRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCrewMemberByIDNotFound() {
        // Given
        given(crewMemberRepository.existsById(1L)).willReturn(false);

        // When
        Throwable thrown = catchThrowable(() -> {
            crewMemberService.deleteCrewMemberByID(1L);
        });

        // Then
        assertThat(thrown)
                .isInstanceOf(ObjectNotFoundException.class)
                .hasMessage("Could not find crew member with id 1");
        verify(crewMemberRepository, times(1)).existsById(1L);
    }
}
