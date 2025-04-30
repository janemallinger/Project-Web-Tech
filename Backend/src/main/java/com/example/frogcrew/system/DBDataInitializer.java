package com.example.frogcrew.system;

import com.example.frogcrew.assignment.Assignment;

import com.example.frogcrew.assignment.AssignmentRepository;
import com.example.frogcrew.availability.Availability;
import com.example.frogcrew.availability.AvailabilityRepository;
import com.example.frogcrew.crewmember.model.CrewMember;
import com.example.frogcrew.crewmember.repository.CrewMemberRepository;
import com.example.frogcrew.game.model.Game;
import com.example.frogcrew.game.model.GameSchedule;
import com.example.frogcrew.game.repository.GameRepository;
import com.example.frogcrew.game.repository.GameScheduleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class DBDataInitializer implements CommandLineRunner {

    private final CrewMemberRepository crewMemberRepository;
    private final GameRepository gameRepository;
    private final AssignmentRepository assignmentRepository;
    private final AvailabilityRepository availabilityRepository;
    private final GameScheduleRepository gameScheduleRepository;

    public DBDataInitializer(CrewMemberRepository crewMemberRepository,
                             GameRepository gameRepository,
                             AssignmentRepository assignmentRepository,
                             AvailabilityRepository availabilityRepository,
                             GameScheduleRepository gameScheduleRepository
    ){
        this.crewMemberRepository = crewMemberRepository;
        this.gameRepository = gameRepository;
        this.assignmentRepository = assignmentRepository;
        this.availabilityRepository = availabilityRepository;
        this.gameScheduleRepository = gameScheduleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        CrewMember c1 = new CrewMember();
        c1.setFirstName("Admin");
        c1.setLastName("User");
        c1.setEmail("admin@example.com");
        c1.setPhoneNumber("1234567890");
        c1.setPassword("password123");
        c1.setRole("ADMIN");
        c1.setQualifiedPositions(List.of("DIRECTOR", "PRODUCER", "CAMERA"));

        CrewMember c2 = new CrewMember();
        c2.setFirstName("Jane");
        c2.setLastName("Doe");
        c2.setEmail("jane.doe@example.com");
        c2.setPhoneNumber("1112223333");
        c2.setPassword("password456");
        c2.setRole("MEMBER");
        c2.setQualifiedPositions(List.of("CAMERA", "EDITOR"));

        CrewMember c3 = new CrewMember();
        c3.setFirstName("Bob");
        c3.setLastName("Williams");
        c3.setEmail("bob.williams@example.com");
        c3.setPhoneNumber("5556667777");
        c3.setPassword("password789");
        c3.setRole("MEMBER");
        c3.setQualifiedPositions(List.of("REPLAY", "GRAPHICS", "CAMERA"));

        CrewMember c4 = new CrewMember();
        c4.setFirstName("Alice");
        c4.setLastName("Brown");
        c4.setEmail("alice.brown@example.com");
        c4.setPhoneNumber("8889990000");
        c4.setPassword("passwordABC");
        c4.setRole("MEMBER");
        c4.setQualifiedPositions(List.of("AUDIO", "CAMERA"));

        this.crewMemberRepository.saveAll(List.of(c1, c2, c3, c4));

        GameSchedule gs1 = new GameSchedule();
        gs1.setSport("Football");
        gs1.setSeason("2025-2026");

        GameSchedule gs2 = new GameSchedule();
        gs2.setSport("Basketball");
        gs2.setSeason("2025-2026");

        this.gameScheduleRepository.saveAll(List.of(gs1, gs2));

        Game game1 = new Game();
        game1.setOpponent("Texas Tech");
        game1.setVenue("Amon G. Carter Stadium");
        game1.setGameDate(LocalDate.of(2025, 9, 13));
        game1.setFinalized(false);
        game1.setSchedule(gs1);

        Game game2 = new Game();
        game2.setOpponent("SMU");
        game2.setVenue("Amon G. Carter Stadium");
        game2.setGameDate(LocalDate.of(2025, 9, 20));
        game2.setFinalized(false);
        game2.setSchedule(gs1);

        Game game3 = new Game();
        game3.setOpponent("Oklahoma State");
        game3.setVenue("Boone Pickens Stadium");
        game3.setGameDate(LocalDate.of(2025, 10, 4));
        game3.setFinalized(false);
        game3.setSchedule(gs1);

        Game game4 = new Game();
        game4.setOpponent("Baylor Bears (Basketball)");
        game4.setVenue("Schollmaier Arena");
        game4.setGameDate(LocalDate.of(2025, 11, 15));
        game4.setFinalized(false);
        game4.setSchedule(gs2);

        this.gameRepository.saveAll(List.of(game1, game2, game3, game4));

        Assignment a1 = new Assignment();
        a1.setGame(game1);
        a1.setCrewMember(c1);
        a1.setPosition("DIRECTOR");
        a1.setReportTime(LocalTime.of(15, 0));
        a1.setReportLocation("Control Room A");

        Assignment a2 = new Assignment();
        a2.setGame(game1);
        a2.setCrewMember(c2);
        a2.setPosition("CAMERA");
        a2.setReportTime(LocalTime.of(16, 0));
        a2.setReportLocation("Gate 5");

        Assignment a3 = new Assignment();
        a3.setGame(game1);
        a3.setCrewMember(c3);
        a3.setPosition("REPLAY");
        a3.setReportTime(LocalTime.of(15, 30));
        a3.setReportLocation("Truck");

        Assignment a4 = new Assignment();
        a4.setGame(game2);
        a4.setCrewMember(c1);
        a4.setPosition("PRODUCER");
        a4.setReportTime(LocalTime.of(14, 30));
        a4.setReportLocation("Control Room B");

        Assignment a5 = new Assignment();
        a5.setGame(game2);
        a5.setCrewMember(c4);
        a5.setPosition("AUDIO");
        a5.setReportTime(LocalTime.of(15, 00));
        a5.setReportLocation("Audio Booth");

        this.assignmentRepository.saveAll(List.of(a1, a2, a3, a4, a5));

        Availability avail1 = new Availability();
        avail1.setCrewMember(c2);
        avail1.setGame(game1);
        avail1.setAvailable(true);

        Availability avail2 = new Availability();
        avail2.setCrewMember(c3);
        avail2.setGame(game1);
        avail2.setAvailable(true);

        Availability avail3 = new Availability();
        avail3.setCrewMember(c4);
        avail3.setGame(game1);
        avail3.setAvailable(false);
        avail3.setComment("Out of town");

        Availability avail4 = new Availability();
        avail4.setCrewMember(c2);
        avail4.setGame(game2);
        avail4.setAvailable(true);

        Availability avail5 = new Availability();
        avail5.setCrewMember(c3);
        avail5.setGame(game2);
        avail5.setAvailable(false);
        avail5.setComment("Prior commitment");

        Availability avail6 = new Availability();
        avail6.setCrewMember(c4);
        avail6.setGame(game2);
        avail6.setAvailable(true);

        this.availabilityRepository.saveAll(List.of(avail1, avail2, avail3, avail4, avail5, avail6));

        System.out.println("Database initialized with sample data.");
    }
}