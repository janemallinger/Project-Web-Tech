package com.example.frogcrew.assignment;


import com.example.frogcrew.assignment.dto.CrewAssignmentDto;
import com.example.frogcrew.assignment.dto.CrewListDto;
import com.example.frogcrew.system.Result;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/${api.version}")
public class AssignmentController {

//    private static Logger log = new Logger(AssignmentController.class);
    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    // Use Case 6: Crew Member Views Crew List
    @GetMapping("/crewList/{gameId}")
    public Result getCrewListForGame(@PathVariable Long gameId) {
        CrewListDto crewList = assignmentService.getCrewListForGame(gameId);


        return new Result(true, HttpStatus.OK.value(), "Find Success", crewList);
    }

    // Use Case 23: Admin adds new crew Schedule
    @PostMapping("/crewSchedule/{gameId}")
    public Result addCrewSchedule(@PathVariable Long gameId, @RequestBody @Valid List<CrewAssignmentDto> assignments) {
        List<CrewAssignmentDto> savedAssignments = assignmentService.saveCrewSchedule(gameId, assignments);
        return new Result(true, HttpStatus.OK.value(), "Add Success", savedAssignments);
    }
}
