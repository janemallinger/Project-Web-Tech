package com.example.frogcrew.crewmember.service;

import com.example.frogcrew.exception.DuplicateEmailException;
import com.example.frogcrew.exception.ObjectNotFoundException;
import com.example.frogcrew.crewmember.model.CrewMember;
import com.example.frogcrew.crewmember.repository.CrewMemberRepository;
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
        return crewMemberRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException(id));
    }
    public void deleteCrewMemberByID(Long id){
        if (!crewMemberRepository.existsById(id)) {
            throw new ObjectNotFoundException(id);
        }
        crewMemberRepository.deleteById(id);


    }
    public CrewMember createMember(CrewMember crewMember) {

        if (crewMemberRepository.existsByEmail(crewMember.getEmail())){
            throw new DuplicateEmailException("Email is already in use.");

        }

        return crewMemberRepository.save(crewMember);

    }

}
