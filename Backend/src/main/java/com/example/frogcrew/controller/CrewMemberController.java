package com.example.frogcrew.controller;

import com.example.frogcrew.converter.CrewMemberCreationRequestToCrewMemberConverter;
import com.example.frogcrew.converter.CrewMemberToCrewMemberCreationResponseDTO;
import com.example.frogcrew.converter.CrewMemberToSimpleCrewMemberDTO;
import com.example.frogcrew.dto.SimpleCrewMemberResponse;
import com.example.frogcrew.dto.request.CrewMemberCreationRequestDTO;
import com.example.frogcrew.dto.response.CrewMemberCreationResponseDTO;
import com.example.frogcrew.service.CrewMemberService;
import com.example.frogcrew.model.CrewMember;
import com.example.frogcrew.system.Result;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/crewMember")
public class CrewMemberController {

    private final CrewMemberService crewMemberService;
    private final CrewMemberCreationRequestToCrewMemberConverter crewMemberCreationRequestToCrewMemberConverter;
    private final CrewMemberToCrewMemberCreationResponseDTO crewMemberToCrewMemberCreationResponseDTO;
    private final CrewMemberToSimpleCrewMemberDTO crewMemberToSimpleCrewMemberDTO;
    public CrewMemberController(CrewMemberService crewMemberService, CrewMemberCreationRequestToCrewMemberConverter crewMemberCreationRequestToCrewMemberConverter, CrewMemberToCrewMemberCreationResponseDTO crewMemberToCrewMemberCreationResponseDTO, CrewMemberToSimpleCrewMemberDTO crewMemberToSimpleCrewMemberDTO) {
        this.crewMemberService = crewMemberService;
        this.crewMemberCreationRequestToCrewMemberConverter = crewMemberCreationRequestToCrewMemberConverter;
        this.crewMemberToCrewMemberCreationResponseDTO = crewMemberToCrewMemberCreationResponseDTO;
        this.crewMemberToSimpleCrewMemberDTO = crewMemberToSimpleCrewMemberDTO;
    }

    @PostMapping
    public Result createCrewMember(@Valid @RequestBody CrewMemberCreationRequestDTO crewMemberCreationRequestDTO) {
        CrewMember newCrewMember = this.crewMemberCreationRequestToCrewMemberConverter.convert(crewMemberCreationRequestDTO);
        CrewMember crewMember = this.crewMemberService.createMember(newCrewMember);
        CrewMemberCreationResponseDTO responseDTO = this.crewMemberToCrewMemberCreationResponseDTO.convert(crewMember);
        return new Result(true, HttpStatus.OK.value(), "Add Success", responseDTO);
     }


    @GetMapping
    public Result findAllCrewMembers() {
        List<CrewMember> members = this.crewMemberService.findAll();

        if (members.isEmpty()) {
            return new Result(true, HttpStatus.NO_CONTENT.value(), "No members found");
        }

        List<SimpleCrewMemberResponse> responseDTOs = members.stream()
                .map(member -> this.crewMemberToSimpleCrewMemberDTO.convert(member))
                .collect(Collectors.toList());

        return new Result(true, HttpStatus.OK.value(), "Find Success", responseDTOs);
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
