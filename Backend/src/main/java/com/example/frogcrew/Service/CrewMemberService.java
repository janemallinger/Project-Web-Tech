package com.example.frogcrew.Service;

import com.example.frogcrew.model.CrewMember;
import com.example.frogcrew.repository.CrewMemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        return crewMemberRepository.findById(id).orElse(null);
    }
    public CrewMember createMember(CrewMember crewMember) {
        return crewMemberRepository.save(crewMember);
    }
}
