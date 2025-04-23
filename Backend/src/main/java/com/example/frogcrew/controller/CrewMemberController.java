package com.example.frogcrew.controller;

import com.example.frogcrew.service.CrewMemberService;
import com.example.frogcrew.model.CrewMember;
import com.example.frogcrew.system.Result;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crewMember")
public class CrewMemberController {

    private final CrewMemberService crewMemberService;


    public CrewMemberController(CrewMemberService crewMemberService) {
        this.crewMemberService = crewMemberService;
    }

    @PostMapping
    public Result createCrewMember(@Valid @RequestBody CrewMember crewMember) {
        this.crewMemberService.createMember(crewMember);
        return new Result(true, HttpStatus.OK.value(), "Add Success", crewMember);
     }


    @GetMapping
    public Result findAllCrewMembers() {
        List<CrewMember> members = this.crewMemberService.findAll();

        if (members.isEmpty()) {
            return new Result(true, HttpStatus.NO_CONTENT.value(), "No members found");
        }

        //placeholder for now.. will use dto to return objects

        return new Result(true, HttpStatus.OK.value(), "Find Success", members);
    }
    @GetMapping("/{userId}")
    public Result findMemberById(@PathVariable Long userId) {
        CrewMember foundMember = this.crewMemberService.findById(userId);
        return new Result(true, HttpStatus.OK.value(), "Found member with Id: " + userId, foundMember);
    }
    @DeleteMapping("/{userId}")
    public Result deleteCrewMember(@PathVariable Long userId){
        this.crewMemberService.deleteCrewMemberByID(userId);
        return new Result(true, HttpStatus.OK.value(), "Delete Success of crew member with id: "+userId);
    }


}
