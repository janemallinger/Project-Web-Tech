package com.example.frogcrew.service;

import com.example.frogcrew.exception.CrewMemberNotFoundException;
import com.example.frogcrew.model.CrewMember;
import com.example.frogcrew.repository.CrewMemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CrewMemberService {

    private final CrewMemberRepository crewMemberRepository;


    public CrewMemberService(CrewMemberRepository crewMemberRepository) {
        this.crewMemberRepository = crewMemberRepository;
    }
    public List<CrewMember> findAll() {
        return crewMemberRepository.findAll();
    }
    public CrewMember findById(Long id) {
        return crewMemberRepository.findById(id).orElseThrow(()-> new CrewMemberNotFoundException(id));
    }
    public void deleteCrewMemberByID(Long id){
        if (crewMemberRepository.existsById(id)) {
            crewMemberRepository.deleteById(id);
        }
        throw new CrewMemberNotFoundException(id);

    }
    public CrewMember createMember(CrewMember crewMember) {
        return crewMemberRepository.save(crewMember);
    }

}
